package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;

public class Speed extends Module {
    public Speed() {
        super("Speed", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (!PlayerHelper.playerPresent() || !mc.player.isOnGround() || mc.player.forwardSpeed <= 0) return;
        double y = Math.toRadians(mc.player.getYaw());
        mc.player.setVelocity(-Math.sin(y) * 0.3, mc.player.getVelocity().y, Math.cos(y) * 0.3);
    }
}
