package com.example.velocityannouncer;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/** Backports the immediate player-velocity send used by Minecraft 1.8. */
@Mod(modid = VelocityAnnouncer.MOD_ID, name = "KB Fix",
        version = VelocityAnnouncer.VERSION, acceptableRemoteVersions = "*")
public final class VelocityAnnouncer {
    public static final String MOD_ID = "velocityannouncer";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void onInitialize(FMLInitializationEvent event) {
        FMLLog.info("[KB Fix] Loaded.");
    }
}
