package com.oxygenclient.util;

import net.minecraft.client.MinecraftClient;

/**
 * Central accessor for {@link MinecraftClient}, replacing scattered
 * {@code MinecraftClient.getInstance()} calls in bypass and UI classes.
 */
public final class MinecraftRef {
    private MinecraftRef() {}

    public static MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }
}
