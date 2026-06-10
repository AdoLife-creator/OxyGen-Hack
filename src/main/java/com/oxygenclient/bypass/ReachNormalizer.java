package com.oxygenclient.bypass;

import com.oxygenclient.util.MinecraftRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class ReachNormalizer {

    public static double getRealDistance(Entity target) {
        MinecraftClient mc = MinecraftRef.mc();
        if (target == null || mc.player == null) return 99;
        Vec3d eye = mc.player.getEyePos();
        Vec3d t = target.getPos().add(0, target.getHeight() / 2, 0);
        return eye.distanceTo(t);
    }

    public static boolean isWithinReach(Entity target) {
        return getRealDistance(target) <= 3.2;
    }
}
