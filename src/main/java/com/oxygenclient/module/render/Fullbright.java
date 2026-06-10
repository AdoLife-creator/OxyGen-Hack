package com.oxygenclient.module.render;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;

public class Fullbright extends Module {
    private double old;

    public Fullbright() {
        super("Fullbright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if (mc.options == null || mc.options.getGamma() == null) return;
        old = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(16.0);
    }

    @Override
    public void onDisable() {
        if (mc.options == null || mc.options.getGamma() == null) return;
        mc.options.getGamma().setValue(old);
    }
}
