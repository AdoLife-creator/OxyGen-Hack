package com.oxygenclient.module.combat;

import com.oxygenclient.bypass.AntiCheatBypass;
import com.oxygenclient.bypass.DelayNormalizer;
import com.oxygenclient.bypass.ReachNormalizer;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import java.util.Comparator;
import java.util.List;

public class KillAuraGhost extends Module {
    private long delay = 0;
    public KillAuraGhost() { super("KillAura", Category.COMBAT); }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (System.currentTimeMillis() < delay) return;
        if (!DelayNormalizer.canSendPacket()) return;
        Box box = new Box(mc.player.getX()-4, mc.player.getY()-4, mc.player.getZ()-4, mc.player.getX()+4, mc.player.getY()+4, mc.player.getZ()+4);
        List<Entity> targets = mc.world.getOtherEntities(mc.player, box, e -> (e instanceof HostileEntity || e instanceof PlayerEntity) && e.isAlive() && ReachNormalizer.isWithinReach(e));
        if (targets.isEmpty()) return;
        Entity target = targets.stream().min(Comparator.comparingDouble(e -> e.distanceTo(mc.player))).orElse(null);
        if (target != null) {
            if (AntiCheatBypass.shouldMiss()) { mc.player.swingHand(Hand.MAIN_HAND); return; }
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            delay = System.currentTimeMillis() + AntiCheatBypass.getHumanizedDelay();
        }
    }
}
