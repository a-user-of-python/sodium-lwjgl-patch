# Sodium LWJGL Patch

A tiny Fabric mod that bypasses Sodium's LWJGL version check (CaffeineMC/sodium#2561).

## Problem

Sodium 0.5.x for Minecraft 1.20.1 requires exactly LWJGL 3.3.1 and hard-crashes on startup with any other version:

```
[main/ERROR]: The game failed to start because the currently active LWJGL version is not compatible.
Installed version: 3.3.3-snapshot
Required version: 3.3.1
```

Some launchers (e.g. **Amethyst** on iOS, PojavLauncher on Android) ship a custom LWJGL build that the user cannot change, making Sodium unloadable.

## Solution

Sodium has a built-in opt-out: setting the system property `sodium.checks.issue2561=false` skips the check. This mod sets that property via two mechanisms:

1. **Mixin** (`BugChecksMixin`): Injects at the HEAD of Sodium's `BugChecks.<clinit>` to set the property before Sodium reads it. This is guaranteed to run first regardless of mod load order.
2. **Pre-launch entrypoint** (`SodiumLwjglPatchPreLaunch`): Sets the property as early as possible during Fabric's pre-launch phase, as a belt-and-suspenders fallback.

If the user has already set the property themselves (e.g. via `-Dsodium.checks.issue2561=false` in JVM args), the mod does not override it.

## Compatibility

- Minecraft 1.20.1
- Fabric Loader 0.16.0+
- Sodium 0.5.x (any 0.5.x version for 1.20.1)
- Client-side only (`environment: client`)

## Building

This mod is intentionally simple and does not require Fabric Loom. Compile with:

```bash
javac --release 17 -proc:none \
  -cp fabric-loader.jar:sponge-mixin.jar \
  -d classes $(find src/main/java -name "*.java")
```

Then package `classes/` with `src/main/resources/` into a JAR.

## Risks

- Sodium's check exists because LWJGL doesn't follow SemVer — a different LWJGL version *could* have binary incompatibilities. In practice, 3.3.3 is a minor bump from 3.3.1 and the community has run Sodium on 3.3.3 successfully (the check is overly strict).
- If Sodium ever removes the `sodium.checks.issue2561` property, the mixin will silently do nothing (the pre-launch entrypoint sets a property nobody reads — harmless).

## License

MIT
