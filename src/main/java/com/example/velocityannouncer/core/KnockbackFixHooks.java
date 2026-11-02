package com.example.velocityannouncer.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

/** Called from the transformed vanilla player-melee method. */
public final class KnockbackFixHooks {
    private KnockbackFixHooks() { }

    public static void afterMeleeAttack(Object targetObject, Object attackerObject) {
        if (!(targetObject instanceof EntityPlayerMP)) return;

        EntityPlayerMP target = (EntityPlayerMP) targetObject;
        if (target.velocityChanged) {
            target.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(target));
            target.velocityChanged = false;
        }
    }
}
