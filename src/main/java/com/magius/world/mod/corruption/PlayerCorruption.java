package com.magius.world.mod.corruption;

import net.minecraft.nbt.CompoundTag;

public class PlayerCorruption {
    public static final int MIN_CORRUPTION = 0;
    public static final int MAX_CORRUPTION = 100;

    private int corruption = 0;

    public int getCorruption() {
        return corruption;
    }

    public void setCorruption(int corruption) {
        this.corruption = Math.max(MIN_CORRUPTION, Math.min(MAX_CORRUPTION, corruption));
    }

    public void addCorruption(int amount) {
        setCorruption(this.corruption + amount);
    }

    public void removeCorruption(int amount) {
        setCorruption(this.corruption - amount);
    }

    public CorruptionLevel getLevel() {
        return CorruptionLevel.fromValue(corruption);
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("corruption", corruption);
    }

    public void loadNBTData(CompoundTag tag) {
        corruption = tag.getInt("corruption");
    }

    public void copyFrom(PlayerCorruption source) {
        this.corruption = source.corruption;
    }
}
