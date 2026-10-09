package com.sodiumlwjglpatch;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/**
 * Backup for the {@code BugChecksMixin}: sets Sodium's LWJGL-check opt-out
 * property as early as possible during pre-launch.
 *
 * <p>The mixin is the primary mechanism (it is guaranteed to run before
 * Sodium reads the property). This entrypoint is a belt-and-suspenders
 * fallback in case Mixin application ordering ever surprises us. It is
 * harmless if the mixin already ran.
 */
public class SodiumLwjglPatchPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        if (System.getProperty("sodium.checks.issue2561") == null) {
            System.setProperty("sodium.checks.issue2561", "false");
        }
    }
}
