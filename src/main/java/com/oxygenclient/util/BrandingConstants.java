package com.oxygenclient.util;

import com.oxygenclient.OxygenClient;

/**
 * Shared branding strings used by HUD and ClickGUI,
 * replacing duplicated "§6OxyGen ... v" + VERSION patterns.
 */
public final class BrandingConstants {
    private BrandingConstants() {}

    public static final String PREFIX = "§6OxyGen";
    public static final int BRAND_COLOR = 0xFFD700;

    public static String hudTitle() {
        return PREFIX + " v" + OxygenClient.VERSION;
    }

    public static String guiTitle() {
        return PREFIX + " Client v" + OxygenClient.VERSION;
    }
}
