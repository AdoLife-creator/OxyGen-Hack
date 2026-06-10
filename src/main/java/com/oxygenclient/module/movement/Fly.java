package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;

public class Fly extends Module {
    public Fly() {
        super("Fly", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (PlayerHelper.playerPresent()) {
            mc.player.getAbilities().flying = true;
            mc.player.getAbilities().allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (PlayerHelper.playerPresent() && !mc.player.isCreative()) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().allowFlying = false;
        }
    }
}
