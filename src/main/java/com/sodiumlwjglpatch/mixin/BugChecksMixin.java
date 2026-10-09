package com.sodiumlwjglpatch.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Disables Sodium's LWJGL version check (CaffeineMC/sodium#2561) via Sodium's
 * own supported opt-out mechanism.
 *
 * <p>Sodium 0.5.x for MC 1.20.1 requires LWJGL 3.3.1 and hard-crashes on any
 * other version. Launchers like Amethyst (iOS) ship a custom LWJGL
 * 3.3.3-snapshot that the user cannot change, so Sodium can never start.
 *
 * <p>Sodium reads the system property {@code sodium.checks.issue2561} when its
 * {@code BugChecks} class is initialized. Setting it to {@code false} skips
 * the check entirely. This mixin injects at the HEAD of
 * {@code BugChecks.<clinit>}, guaranteeing the property is set before
 * Sodium reads it — regardless of mod load order.
 *
 * <p>We only set the property if the user hasn't already set it themselves
 * (e.g. via JVM flags), so an explicit user choice is never overridden.
 */
@Mixin(targets = "net.caffeinemc.mods.sodium.client.compatibility.checks.BugChecks")
public class BugChecksMixin {
    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void sodiumLwjglPatch$disableLwjglVersionCheck(CallbackInfo ci) {
        if (System.getProperty("sodium.checks.issue2561") == null) {
            System.setProperty("sodium.checks.issue2561", "false");
        }
    }
}
