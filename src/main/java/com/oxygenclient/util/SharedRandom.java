package com.oxygenclient.util;

import java.util.Random;

/**
 * Single shared {@link Random} instance for the entire client,
 * replacing per-class duplicates in AntiCheatBypass, DelayNormalizer, etc.
 */
public final class SharedRandom {
    private static final Random INSTANCE = new Random();

    private SharedRandom() {}

    public static Random get() {
        return INSTANCE;
    }
}
