package com.oxygenclient;

import com.oxygenclient.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;

public class OxygenClient implements ClientModInitializer {
    public static final String VERSION = "3.0.0";
    public static ModuleManager moduleManager;

    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();
        System.out.println("[OxyGen] v" + VERSION + " | " + moduleManager.getModules().size() + " modules loaded");
        System.out.println("[OxyGen] Press RIGHT SHIFT to open GUI");
    }
}
