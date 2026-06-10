package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;

public class Step extends Module {
    public Step() {
        super("Step", "Auto step blocks", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player != null) mc.player.setStepHeight(2.5f);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.setStepHeight(0.6f);
    }
}
