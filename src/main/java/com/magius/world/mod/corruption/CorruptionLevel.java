package com.magius.world.mod.corruption;

public enum CorruptionLevel {
    PURE(0, 0, 19),
    EXPOSED(1, 20, 39),
    INFECTED(2, 40, 59),
    MUTATED(3, 60, 79),
    CORRUPTED(4, 80, 99),
    ASSIMILATED(5, 100, 100);

    private final int level;
    private final int min;
    private final int max;

    CorruptionLevel(int level, int min, int max) {
        this.level = level;
        this.min = min;
        this.max = max;
    }

    public int getLevel() {
        return level;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public static CorruptionLevel fromValue(int value) {
        for (CorruptionLevel level : values()) {
            if (value >= level.min && value <= level.max) {
                return level;
            }
        }

        return PURE;
    }
}
