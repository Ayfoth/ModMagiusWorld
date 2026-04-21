package com.magius.world.mod.corruption;

import net.minecraft.nbt.CompoundTag;

public class PlayerCorruption {
    private int corruption = 0;

    public int getCorruption() {
        return corruption;
    }

    public void setCorruption(int corruption) {
        this.corruption = Math.max(0, Math.min(5, corruption));
    }

    public void addCorruption(int amount) {
        setCorruption(this.corruption + amount);
    }

    public void removeCorruption(int amount) {
        setCorruption(this.corruption - amount);
    }

    public CorruptionLevel getLevel() {
        return CorruptionLevel.fromInt(corruption);
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
