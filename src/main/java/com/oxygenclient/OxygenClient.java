package com.oxygenclient;

import com.oxygenclient.module.ModuleManager;
import com.oxygenclient.ui.HUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OxygenClient implements ClientModInitializer {
    public static final String NAME = "OxyGen Client";
    public static final String VERSION = "3.0.0";
    public static ModuleManager moduleManager;
    public static HUD hud;
    private static KeyBinding guiKey;

    @Override
    public void onInitializeClient() {
        System.out.println("[OxyGen] Starting " + NAME + " v" + VERSION);
        
        moduleManager = new ModuleManager();
        hud = new HUD();
        
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.oxygen-client.gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.oxygen-client"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            moduleManager.onTick();
            while (guiKey.wasPressed()) {
                client.setScreen(new com.oxygenclient.ui.ClickGUI());
            }
        });
        
        System.out.println("[OxyGen] Loaded " + moduleManager.getModules().size() + " modules");
    }
}
