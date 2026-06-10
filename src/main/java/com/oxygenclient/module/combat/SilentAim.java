package com.oxygenclient.module.combat;

import com.oxygenclient.bypass.AntiCheatBypass;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;
import com.oxygenclient.util.TargetFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class SilentAim extends Module {
    public SilentAim() { super("SilentAim", Category.COMBAT); }

    @Override
    public void onTick() {
        if (!PlayerHelper.playerPresent() || !mc.options.attackKey.isPressed()) return;
        Entity t = TargetFinder.findNearest(6,
                e -> e instanceof PlayerEntity && e.isAlive());
        if (t != null) {
            double dx = t.getX()-mc.player.getX(), dy = t.getEyeY()-mc.player.getEyeY(), dz = t.getZ()-mc.player.getZ();
            double dist = Math.sqrt(dx*dx+dz*dz);
            mc.player.setYaw((float)(MathHelper.atan2(dz,dx)*180/Math.PI)-90f + AntiCheatBypass.getRandomYawOffset());
            mc.player.setPitch((float)(-MathHelper.atan2(dy,dist)*180/Math.PI) + AntiCheatBypass.getRandomPitchOffset());
        }
    }
}
