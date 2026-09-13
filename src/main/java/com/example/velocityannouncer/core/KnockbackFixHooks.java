package com.example.velocityannouncer.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

/** Called from the transformed vanilla player-damage method. */
public final class KnockbackFixHooks {
    private KnockbackFixHooks() { }

    public static boolean afterDamageResult(Object playerObject, boolean damageWasApplied) {
        if (!damageWasApplied || !(playerObject instanceof EntityPlayerMP)) return damageWasApplied;

        EntityPlayerMP player = (EntityPlayerMP) playerObject;
        if (player.velocityChanged) {
            player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(player));
            player.velocityChanged = false;
        }
        return true;
    }
}
