package com.magius.world.mod.corruption;

public enum CorruptionLevel {
    PURE(0),
    EXPOSED(1),
    INFECTED(2),
    MUTATED(3),
    CORRUPTED(4),
    ASSIMILATED(5);

    private final int level;

    CorruptionLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static CorruptionLevel fromInt(int value) {
        for (CorruptionLevel level : values()) {
            if (level.level == value) {
                return level;
            }
        }
        return PURE;
    }
}
