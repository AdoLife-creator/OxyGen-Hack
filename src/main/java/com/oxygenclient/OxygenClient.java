package com.oxygenclient;

import com.oxygenclient.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OxygenClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("oxygen-client");
    public static final String VERSION = "3.0.0";
    public static ModuleManager moduleManager;

    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();
        LOGGER.info("v{} | {} modules loaded", VERSION, moduleManager.getModules().size());
        LOGGER.info("Press RIGHT SHIFT to open GUI");
    }
}
