package com.oxygenclient.util;

/**
 * Null-safety helpers for the local player/world, replacing the
 * {@code if (mc.player == null) return;} guard duplicated across modules.
 */
public final class PlayerHelper {
    private PlayerHelper() {}

    public static boolean playerPresent() {
        var mc = MinecraftRef.mc();
        return mc.player != null;
    }

    public static boolean worldPresent() {
        var mc = MinecraftRef.mc();
        return mc.player != null && mc.world != null;
    }
}
