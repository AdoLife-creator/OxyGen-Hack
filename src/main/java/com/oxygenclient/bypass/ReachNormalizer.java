package com.oxygenclient.bypass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ReachNormalizer {
    
    // Güvenli reach hesapla (max 3.2 blok)
    public static double getSafeReach(Entity target) {
        double baseReach = 3.0;
        double ping = getPing() / 1000.0; // saniye cinsinden
        
        // Ping'e göre reach ayarla
        double pingBonus = Math.min(ping * 2.0, 0.5);
        
        // Hareket halindeyse biraz daha fazla
        if (target instanceof PlayerEntity player && player.isSprinting()) {
            pingBonus += 0.1;
        }
        
        return baseReach + pingBonus;
    }

    // Gerçek mesafeyi hesapla
    public static double getRealDistance(Entity target) {
        if (target == null) return 99.0;
        
        Vec3d attackerEyes = new Vec3d(
            mc().player.getX(),
            mc().player.getEyeY(),
            mc().player.getZ()
        );
        
        Vec3d targetHitbox = target.getPos().add(0, target.getHeight() / 2, 0);
        
        return attackerEyes.distanceTo(targetHitbox);
    }

    // Vuruş mesafesini normalize et
    public static boolean isWithinReach(Entity target) {
        double realDist = getRealDistance(target);
        double safeReach = getSafeReach(target);
        
        // %5 tolerans
        return realDist <= safeReach * 1.05;
    }

    private static double getPing() {
        if (mc().getNetworkHandler() != null && mc().player != null) {
            return mc().getNetworkHandler().getPlayerListEntry(
                mc().player.getUuid()
            ).getLatency();
        }
        return 50;
    }

    private static net.minecraft.client.MinecraftClient mc() {
        return net.minecraft.client.MinecraftClient.getInstance();
    }
}
