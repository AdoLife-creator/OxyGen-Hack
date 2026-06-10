package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (PlayerHelper.playerPresent() && mc.player.fallDistance > 2.5f) {
            mc.player.fallDistance = 0;
        }
    }
}
