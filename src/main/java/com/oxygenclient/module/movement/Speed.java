package com.oxygenclient.module.movement;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
    public Speed() {
        super("Speed", "Move faster", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || !mc.player.isOnGround()) return;
        Vec3d v = mc.player.getVelocity();
        double yaw = Math.toRadians(mc.player.getYaw());
        if (mc.player.forwardSpeed > 0) {
            mc.player.setVelocity(-Math.sin(yaw) * 0.3, v.y, Math.cos(yaw) * 0.3);
        }
    }
}
