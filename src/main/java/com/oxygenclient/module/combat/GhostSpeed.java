package com.oxygenclient.module.movement;

import com.oxygenclient.bypass.AntiCheatBypass;
import com.oxygenclient.bypass.PacketSpoofer;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.util.math.Vec3d;

public class GhostSpeed extends Module {
    private int groundTicks = 0;
    private double speed = 0.35;

    public GhostSpeed() {
        super("Speed", "Tespit edilemez hız hacki", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || !mc.player.isOnGround()) return;
        if (!isMoving()) return;

        // Strafe speed
        double yaw = Math.toRadians(mc.player.getYaw());
        double forward = mc.player.input.movementForward;
        double strafe = mc.player.input.movementSideways;

        if (forward == 0 && strafe == 0) return;

        double mx = -Math.sin(yaw) * forward + Math.cos(yaw) * strafe;
        double mz = Math.cos(yaw) * forward + Math.sin(yaw) * strafe;
        double len = Math.sqrt(mx * mx + mz * mz);

        if (len > 0) {
            mx = mx / len * speed;
            mz = mz / len * speed;
        }

        // Zıplama boost
        if (mc.options.jumpKey.isPressed() && mc.player.isOnGround()) {
            mc.player.setVelocity(mx * 1.5, 0.42, mz * 1.5);
        } else {
            mc.player.setVelocity(mx, mc.player.getVelocity().y, mz);
        }
    }

    private boolean isMoving() {
        return mc.player.input.movementForward != 0
