package com.oxygenclient.module.combat;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.util.Hand;

public class AutoClicker extends Module {
    private int tick = 0;
    public AutoClicker() { super("AutoClicker", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.currentScreen != null || mc.crosshairTarget == null) return;
        tick++;
        if (tick >= 2 && mc.options.attackKey.isPressed()) {
            if (mc.targetedEntity != null) {
                mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
            }
            mc.player.swingHand(Hand.MAIN_HAND);
            tick = 0;
        }
    }
}
