package com.magius.world.mod.clan.quest.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerQuestProvider implements ICapabilitySerializable<CompoundTag> {

    private final PlayerQuestData data = new PlayerQuestData();

    private final LazyOptional<PlayerQuestData> optional =
            LazyOptional.of(() -> data);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(
            @NotNull Capability<T> capability,
            @Nullable Direction direction
    ) {
        return PlayerQuestCapability.INSTANCE.orEmpty(
                capability,
                optional
        );
    }

    @Override
    public CompoundTag serializeNBT() {
        return data.saveNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        data.loadNBT(tag);
    }

    public void invalidate() {
        optional.invalidate();
    }
}