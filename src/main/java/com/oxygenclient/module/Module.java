package com.oxygenclient.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public abstract class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final String name;
    private final Category category;
    private boolean enabled;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        enabled = !enabled;
        try {
            if (enabled) onEnable(); else onDisable();
        } catch (Exception e) {
            enabled = !enabled;
            System.err.println("[OxyGen] Error toggling module " + name + ": " + e.getMessage());
            return;
        }
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal((enabled ? "§a✔ " : "§c✘ ") + name + (enabled ? " ON" : " OFF")), true);
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean e) {
        enabled = e;
        try {
            if (e) onEnable(); else onDisable();
        } catch (Exception ex) {
            enabled = !e;
            System.err.println("[OxyGen] Error setting module " + name + " to " + e + ": " + ex.getMessage());
        }
    }
}
