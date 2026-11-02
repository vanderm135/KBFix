# Velocity Announcer

A server-side Minecraft Forge mod for **Minecraft 1.7.10**. Whenever a player is hurt, it broadcasts that player's resulting motion vector to the server chat on the following tick.

Example output:

```text
[Velocity] PlayerName: X=0.341 Y=0.400 Z=-0.127 (horizontal=0.364)
```

## Build

This project includes a local copy of the legacy Gradle 2.14.1 required by ForgeGradle 1.2. Java 8 is also required. From PowerShell in this folder, run:

```powershell
.\build.ps1 build
```

The finished mod JAR is placed in `build/libs/`. Install it only in the server's `mods` folder. Clients do not need the mod.

## Notes

* All damage that successfully reaches a player is reported, including environmental damage.
* The one-tick delay intentionally captures velocity after vanilla knockback is applied.
* If a player receives multiple hits in one tick, the mod posts one announcement per hit. Those messages share the final motion calculated for that tick.
