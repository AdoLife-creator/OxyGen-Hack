package com.oxygenclient.bypass;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class PacketSpoofer {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    
    private static double lastX, lastY, lastZ;
    private static float lastYaw, lastPitch;
    private static int packetCounter = 0;
    private static boolean spoofing = false;

    // Konum spoof - GrimAC bypass
    public static PlayerMoveC2SPacket spoofPosition(PlayerMoveC2SPacket original) {
        if (!spoofing) return original;
        
        packetCounter++;
        
        // Her 3 packetten birini normalize et
        if (packetCounter % 3 == 0) {
            double newX = lastX + (original.getX(0) - lastX) * 0.6;
            double newY = lastY + (original.getY(0) - lastY) * 0.6;
            double newZ = lastZ + (original.getZ(0) - lastZ) * 0.6;
            
            lastX = newX;
            lastY = newY;
            lastZ = newZ;
            
            return new PlayerMoveC2SPacket.PositionAndOnGround(newX, newY, newZ, original.isOnGround());
        }
        
        lastX = original.getX(0);
        lastY = original.getY(0);
        lastZ = original.getZ(0);
        
        return original;
    }

    // Rotasyon spoof - AAC bypass
    public static float[] spoofRotation(float yaw, float pitch) {
        // Yumuşak rotasyon geçişi
        float newYaw = lastYaw + (yaw - lastYaw) * 0.4f;
        float newPitch = lastPitch + (pitch - lastPitch) * 0.4f;
        
        // Ani dönüşleri engelle (max 15 derece/tick)
        float yawDiff = Math.abs(newYaw - lastYaw);
        if (yawDiff > 15) {
            newYaw = lastYaw + Math.signum(yaw - lastYaw) * 15;
        }
        
        lastYaw = newYaw;
        lastPitch = newPitch;
        
        return new float[]{newYaw, newPitch};
    }

    public static void setSpoofing(boolean spoofing) {
        PacketSpoofer.spoofing = spoofing;
    }

    // Packet geciktirme - Matrix bypass
    public static boolean shouldDelayPacket(Packet<?> packet) {
        if (packet instanceof PlayerMoveC2SPacket) {
            return packetCounter % 4 == 0;
        }
        return false;
    }

    // OnGround spoof - NCP bypass
    public static boolean spoofOnGround(boolean original) {
        // NCP ground check bypass
        if (spoofing) {
            return packetCounter % 5 != 0;
        }
        return original;
    }
}
