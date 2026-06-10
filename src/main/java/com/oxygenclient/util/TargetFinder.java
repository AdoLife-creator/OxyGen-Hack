package com.oxygenclient.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Shared entity-search logic extracted from KillAuraGhost and SilentAim.
 * Both modules built a Box, filtered entities, and picked the nearest one.
 */
public final class TargetFinder {
    private TargetFinder() {}

    /**
     * Find entities around the player within the given radius that match the filter.
     */
    public static List<Entity> findTargets(double radius, Predicate<Entity> filter) {
        var mc = MinecraftRef.mc();
        if (mc.player == null || mc.world == null) return List.of();
        double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
        Box box = new Box(x - radius, y - radius, z - radius,
                          x + radius, y + radius, z + radius);
        return mc.world.getOtherEntities(mc.player, box, filter::test);
    }

    /**
     * Return the nearest entity within radius that satisfies the filter, or null.
     */
    public static Entity findNearest(double radius, Predicate<Entity> filter) {
        var mc = MinecraftRef.mc();
        return findTargets(radius, filter)
                .stream()
                .min(Comparator.comparingDouble(e -> e.distanceTo(mc.player)))
                .orElse(null);
    }
}
