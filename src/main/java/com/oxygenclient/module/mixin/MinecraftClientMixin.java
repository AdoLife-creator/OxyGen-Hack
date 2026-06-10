package com.oxygenclient.mixin;

import com.oxygenclient.OxygenClient;
import com.oxygenclient.ui.ClickGUI;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private boolean keyDown = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        try {
            MinecraftClient mc = (MinecraftClient) (Object) this;

            if (OxygenClient.moduleManager != null) {
                OxygenClient.moduleManager.onTick();
            }

            if (mc.getWindow() == null) return;
            long win = mc.getWindow().getHandle();
            boolean pressed = GLFW.glfwGetKey(win, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            if (pressed && !keyDown && mc.currentScreen == null) {
                mc.setScreen(new ClickGUI());
            }
            keyDown = pressed;
        } catch (Exception e) {
            System.err.println("[OxyGen] Error in client tick: " + e.getMessage());
        }
    }
}
