package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (PlayerHelper.playerPresent() && mc.player.isTouchingWater()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
        }
    }
}
