package com.example.velocityannouncer.core;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Adds an immediate velocity send after a player's complete melee attack. */
public final class KnockbackFixTransformer implements IClassTransformer {
    private static final String TARGET_CLASS = "net.minecraft.entity.player.EntityPlayer";
    private static final String HOOK_OWNER = "com/example/velocityannouncer/core/KnockbackFixHooks";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!TARGET_CLASS.equals(transformedName)) return basicClass;

        final String originalName = name;
        ClassReader reader = new ClassReader(basicClass);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        reader.accept(new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String methodName, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor visitor = super.visitMethod(access, methodName, descriptor, signature, exceptions);
                String mappedName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(originalName, methodName, descriptor);
                if (!("attackTargetEntityWithCurrentItem".equals(methodName)
                        || "attackTargetEntityWithCurrentItem".equals(mappedName)
                        || "func_71059_n".equals(mappedName))) return visitor;

                System.out.println("[KB Fix] Patched player melee velocity handling.");

                return new MethodVisitor(Opcodes.ASM5, visitor) {
                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.RETURN) {
                            // Local 1 is the target; all sprint/enchantment knockback is now applied.
                            super.visitVarInsn(Opcodes.ALOAD, 1);
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitMethodInsn(Opcodes.INVOKESTATIC, HOOK_OWNER,
                                    "afterMeleeAttack", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
                        }
                        super.visitInsn(opcode);
                    }
                };
            }
        }, 0);
        return writer.toByteArray();
    }
}
