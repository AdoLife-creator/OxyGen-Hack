package com.oxygenclient.ui;

import com.oxygenclient.OxygenClient;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.List;

public class ClickGUI extends Screen {
    private Category cat = Category.COMBAT;

    public ClickGUI() {
        super(Text.literal("OxyGen Client"));
    }

    @Override
    protected void init() {
        super.init();
        int cx = 20, cy = 30;
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++) {
            final Category c = cats[i];
            addDrawableChild(ButtonWidget.builder(
                Text.literal(c.getName()),
                b -> cat = c
            ).dimensions(cx + i * 100, cy, 90, 20).build());
        }

        if (OxygenClient.moduleManager == null) return;
        List<Module> mods = OxygenClient.moduleManager.getByCategory(cat);
        int y = cy + 30;
        for (Module m : mods) {
            boolean on = m.isEnabled();
            addDrawableChild(ButtonWidget.builder(
                Text.literal((on ? "§a" : "§c") + m.getName()),
                b -> m.toggle()
            ).dimensions(cx, y, 150, 20).build());
            y += 22;
        }

        addDrawableChild(ButtonWidget.builder(
            Text.literal("§cClose"),
            b -> close()
        ).dimensions(width / 2 - 30, height - 30, 60, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float d) {
        renderBackground(ctx, mx, my, d);
        ctx.drawCenteredTextWithShadow(textRenderer, "§6OxyGen Client v" + OxygenClient.VERSION, width / 2, 10, 0xFFD700);
        super.render(ctx, mx, my, d);
    }

    @Override
    public boolean shouldPause() { return false; }
}
