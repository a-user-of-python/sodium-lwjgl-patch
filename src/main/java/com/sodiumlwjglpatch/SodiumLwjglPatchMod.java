package com.sodiumlwjglpatch;

import net.fabricmc.api.ClientModInitializer;

/**
 * Main mod entrypoint. All the real work happens in the pre-launch
 * entrypoint and the Mixin; this exists so the mod has a client initializer
 * as declared in fabric.mod.json.
 */
public class SodiumLwjglPatchMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // No-op: the LWJGL check bypass runs at pre-launch / class-init time.
    }
}
