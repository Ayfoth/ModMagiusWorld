package com.magius.world.mod.client;

import com.magius.world.mod.corruption.CorruptionLevel;

public class ClientCorruptionData {
    private static int corruption = 0;

    public static void set(int value) {
        corruption = value;
    }

    public static int get() {
        return corruption;
    }

    public static CorruptionLevel getLevel() {
        return CorruptionLevel.fromValue(corruption);
    }
}
