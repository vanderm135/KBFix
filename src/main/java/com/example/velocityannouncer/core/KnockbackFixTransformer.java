package com.example.velocityannouncer.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Adds an immediate velocity send to EntityPlayerMP.attackEntityFrom. */
public final class KnockbackFixTransformer implements IClassTransformer {
    private static final String TARGET_CLASS = "net.minecraft.entity.player.EntityPlayerMP";
    private static final String HOOK_OWNER = "com/example/velocityannouncer/core/KnockbackFixHooks";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!TARGET_CLASS.equals(transformedName)) return basicClass;

        ClassReader reader = new ClassReader(basicClass);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        reader.accept(new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String methodName, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor visitor = super.visitMethod(access, methodName, descriptor, signature, exceptions);
                // EntityPlayerMP has exactly one (DamageSource, float) -> boolean method.
                if (!descriptor.endsWith("F)Z")) return visitor;

                return new MethodVisitor(Opcodes.ASM5, visitor) {
                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.IRETURN) {
                            // Stack before IRETURN: [damageWasApplied].
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitInsn(Opcodes.SWAP);
                            super.visitMethodInsn(Opcodes.INVOKESTATIC, HOOK_OWNER,
                                    "afterDamageResult", "(Ljava/lang/Object;Z)Z", false);
                        }
                        super.visitInsn(opcode);
                    }
                };
            }
        }, 0);
        return writer.toByteArray();
    }
}
