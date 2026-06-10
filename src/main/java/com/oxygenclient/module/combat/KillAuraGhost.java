package com.oxygenclient.module.combat;

import com.oxygenclient.bypass.AntiCheatBypass;
import com.oxygenclient.bypass.DelayNormalizer;
import com.oxygenclient.bypass.ReachNormalizer;
import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import com.oxygenclient.util.PlayerHelper;
import com.oxygenclient.util.TargetFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAuraGhost extends Module {
    private long delay = 0;
    public KillAuraGhost() { super("KillAura", Category.COMBAT); }

    @Override
    public void onTick() {
        if (!PlayerHelper.worldPresent()) return;
        if (System.currentTimeMillis() < delay) return;
        if (!DelayNormalizer.canSendPacket()) return;
        Entity target = TargetFinder.findNearest(4,
                e -> (e instanceof HostileEntity || e instanceof PlayerEntity)
                        && e.isAlive() && ReachNormalizer.isWithinReach(e));
        if (target == null) return;
        if (AntiCheatBypass.shouldMiss()) { mc.player.swingHand(Hand.MAIN_HAND); return; }
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        delay = System.currentTimeMillis() + AntiCheatBypass.getHumanizedDelay();
    }
}
