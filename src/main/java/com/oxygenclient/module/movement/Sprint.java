package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (PlayerHelper.playerPresent() && mc.player.forwardSpeed > 0) {
            mc.player.setSprinting(true);
        }
    }
}
