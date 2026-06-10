package com.oxygenclient.module.combat;

import com.oxygenclient.bypass.*;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KillAuraGhost extends Module {
    private double range = 3.2;
    private int cps = 12;
    private long lastAttack = 0;
    private Entity currentTarget = null;
    private int switchDelay = 0;
    private static final Random random = new Random();

    public KillAuraGhost() {
        super("KillAura", "Ghost KillAura - Tespit edilemez!", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.player.isDead()) return;
        
        // Delay kontrolü
        long delay = DelayNormalizer.getAttackDelay(cps);
        if (System.currentTimeMillis() - lastAttack < delay) return;
        if (!DelayNormalizer.canSendPacket()) return;

        Box searchBox = new Box(
            mc.player.getX() - range, mc.player.getY() - range, mc.player.getZ() - range,
            mc.player.getX() + range, mc.player.getY() + range, mc.player.getZ() + range
        );

        List<Entity> targets = mc.world.getOtherEntities(mc.player, searchBox,
            entity -> (entity instanceof HostileEntity || entity instanceof PlayerEntity)
                && entity.isAlive()
                && entity != mc.player
                && ReachNormalizer.isWithinReach(entity));

        if (targets.isEmpty()) {
            currentTarget = null;
            return;
        }

        // Hedef değiştirme mantığı - insan gibi
        if (switchDelay > 0) {
            switchDelay--;
            return;
        }

        // En yakın hedefi bul
        Entity target = targets.stream()
            .min(Comparator.comparingDouble(e -> e.distanceTo(mc.player)))
            .orElse(null);

        if (target != null && target != currentTarget) {
            currentTarget = target;
            switchDelay = random.nextInt(3) + 2; // 2-4 tick bekle
        }

        if (currentTarget != null && currentTarget.isAlive()) {
            attack(currentTarget);
            lastAttack = System.currentTimeMillis();
        }
    }

    private void attack(Entity target) {
        // Rotasyonu hedefe çevir (server tarafında gizli)
        double dx = target.getX() - mc.player.getX();
        double dy = target.getEyeY() - mc.player.getEyeY();
        double dz = target.getZ() - mc.player.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);

        float targetYaw = (float) (MathHelper.atan2(dz, dx) * 180.0 / Math.PI) - 90.0f;
        float targetPitch = (float) (-MathHelper.atan2(dy, dist) * 180.0 / Math.PI);

        // Rastgele sapma ekle
        targetYaw += AntiCheatBypass.getRandomYawOffset();
        targetPitch += AntiCheatBypass.getRandomPitchOffset();

        // Server rotasyonunu güncelle
        RotationSpoofer.setRotation(targetYaw, targetPitch, false);

        // %3 ihtimalle ıskala
        if (AntiCheatBypass.shouldMiss()) {
            mc.player.swingHand(Hand.MAIN_HAND);
            return;
        }

        // Vuruş
        mc.interactionManager.attackEntity(mc.player, currentTarget);
        mc.player.swingHand(Hand.MAIN_HAND);

        // Hitbox offset ekle
        double[] offset = HitboxCorrector.getRandomHitOffset();
        // offset kullanımı mixin ile

        // Pattern reset
        if (random.nextInt(10) == 0) {
            DelayNormalizer.resetPattern();
        }
    }
}
