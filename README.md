# KB Fix

KB Fix is a server-side Forge coremod for Minecraft 1.7.10. It backports immediate velocity sending for successful player hits, eliminating the vanilla login-order knockback bug. Vanilla clients can connect normally.

## Build prerequisites

- Java 8
- Gradle 2.14.1
- The official Minecraft 1.7.10 client JAR, saved as `1.7.10.jar` beside `build.gradle`
- The official Minecraft 1.7.10 server JAR, saved as `1.7.10-server.jar` beside `build.gradle`

Run:

```text
gradle build
```

The server JAR is written to `build/libs/kbfix-1.0.0.jar`. Install only that file in the Forge server's `mods` folder, then fully restart the server.

The two local Minecraft JARs are required only to work around the discontinued download URLs in legacy ForgeGradle; they must not be committed or uploaded.
