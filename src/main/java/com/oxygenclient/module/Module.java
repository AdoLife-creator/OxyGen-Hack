package com.oxygenclient.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public abstract class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int key;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        onToggle();
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal(
                (enabled ? "§a✔ " : "§c✘ ") + name + (enabled ? " ON" : " OFF")
            ), true);
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    
    protected void onToggle() {
        if (enabled) onEnable(); else onDisable();
    }

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean e) { this.enabled = e; onToggle(); }
    public int getKey() { return key; }
    public void setKey(int k) { this.key = k; }
}
