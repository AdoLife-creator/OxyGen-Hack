package com.oxygenclient.bypass;

import java.util.Random;

public class DelayNormalizer {
    private static final Random random = new Random();
    private static long lastAttackTime = 0;
    private static long lastPacketTime = 0;
    private static int consecutiveAttacks = 0;
    
    // İnsan gibi değişken saldırı aralığı
    public static long getAttackDelay(int cps) {
        long currentTime = System.currentTimeMillis();
        long baseDelay = 1000 / cps;
        
        // %20 rastgele sapma
        long randomOffset = (long)(baseDelay * (random.nextDouble() - 0.5) * 0.4);
        
        // Çok hızlı saldırıları engelle
        if (consecutiveAttacks > 5) {
            baseDelay += 50; // Yorgunluk simülasyonu
        }
        
        long actualDelay = baseDelay + randomOffset;
        long timeSinceLastAttack = currentTime - lastAttackTime;
        
        if (timeSinceLastAttack < actualDelay) {
            return actualDelay - timeSinceLastAttack;
        }
        
        lastAttackTime = currentTime;
        consecutiveAttacks++;
        return 0;
    }

    // Packet rate limiter
    public static boolean canSendPacket() {
        long currentTime = System.currentTimeMillis();
        long minDelay = AntiCheatBypass.getPacketDelay();
        
        if (currentTime - lastPacketTime < minDelay) {
            return false;
        }
        
        lastPacketTime = currentTime;
        return true;
    }

    // Uzun süreli pattern'leri kır
    public static void resetPattern() {
        consecutiveAttacks = random.nextInt(3);
    }

    // GrimAC transaction bypass
    public static boolean shouldSkipTransaction() {
        // Her 8 transaction'dan 1'ini atla
        return random.nextInt(8) == 0;
    }
}
