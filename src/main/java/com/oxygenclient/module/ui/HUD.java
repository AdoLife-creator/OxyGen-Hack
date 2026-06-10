package com.oxygenclient.ui;

import com.oxygenclient.OxygenClient;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.BrandingConstants;
import com.oxygenclient.util.MinecraftRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import java.util.List;

public class HUD {
    public void render(DrawContext ctx, float d) {
        MinecraftClient mc = MinecraftRef.mc();
        if (mc.player == null) return;
        List<Module> mods = OxygenClient.moduleManager.getModules().stream().filter(Module::isEnabled).toList();
        int y = 5;
        ctx.drawTextWithShadow(mc.textRenderer, Text.literal(BrandingConstants.hudTitle()), 5, y, BrandingConstants.BRAND_COLOR);
        y += 12;
        for (Module m : mods) {
            ctx.drawTextWithShadow(mc.textRenderer, Text.literal("§a" + m.getName()), 5, y, 0x00FF00);
            y += 10;
        }
    }
}
