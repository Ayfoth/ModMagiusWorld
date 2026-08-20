package com.magius.world.mod.client;

public final class DragonAwakeningClientData {

    private static int activeTicks;
    private static long cooldownEnd;

    private DragonAwakeningClientData() {
    }

    public static void set(
            int ticks,
            long cooldown
    ) {
        activeTicks = Math.max(0, ticks);
        cooldownEnd = cooldown;
    }

    public static int getActiveTicks() {
        return activeTicks;
    }

    public static void setActiveTicks(int ticks) {
        activeTicks = Math.max(0, ticks);
    }

    public static boolean isActive() {
        return activeTicks > 0;
    }

    public static long getCooldownEnd() {
        return cooldownEnd;
    }

    public static long getRemainingCooldownMillis() {

        return Math.max(
                0L,
                cooldownEnd - System.currentTimeMillis()
        );
    }

    public static boolean isOnCooldown() {
        return getRemainingCooldownMillis() > 0L;
    }

    public static void clear() {
        activeTicks = 0;
        cooldownEnd = 0L;
    }
}
