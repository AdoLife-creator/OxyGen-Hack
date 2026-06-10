package com.oxygenclient.module.combat;

import com.oxygenclient.module.Category;
import com.oxygenclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KillAuraGhost extends Module {
    private final Random random = new Random();
    private int delay = 0;

    public KillAuraGhost() {
        super("KillAura", "Auto attack entities", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (delay > 0) { delay--; return; }

        Box box = new Box(
            mc.player.getX() - 4, mc.player.getY() - 4, mc.player.getZ() - 4,
            mc.player.getX() + 4, mc.player.getY() + 4, mc.player.getZ() + 4
        );

        List<Entity> targets = mc.world.getOtherEntities(mc.player, box,
            e -> (e instanceof HostileEntity || e instanceof PlayerEntity) && e.isAlive());

        if (targets.isEmpty()) return;

        Entity target = targets.stream()
            .min(Comparator.comparingDouble(e -> e.distanceTo(mc.player)))
            .orElse(null);

        if (target != null) {
            mc.player.lookAt(
                net.minecraft.entity.EntityAnchorArgumentPart.EntityAnchor.EYES,
                target.getPos().add(0, target.getHeight() / 2, 0)
            );
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            delay = 8 + random.nextInt(4);
        }
    }
}
