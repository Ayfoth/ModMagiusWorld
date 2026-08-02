package com.magius.world.mod.clan.data;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerClanProvider implements ICapabilitySerializable<CompoundTag> {

    private final PlayerClanData data = new PlayerClanData();

    private final LazyOptional<PlayerClanData> optional =
            LazyOptional.of(() -> data);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(
            @NotNull Capability<T> capability,
            @Nullable Direction direction
    ) {
        return PlayerClanCapability.INSTANCE.orEmpty(capability, optional);
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
