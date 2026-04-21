package com.magius.world.mod.corruption;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerCorruptionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<PlayerCorruption> PLAYER_CORRUPTION =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerCorruption corruption = new PlayerCorruption();
    private final LazyOptional<PlayerCorruption> optional = LazyOptional.of(() -> corruption);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_CORRUPTION ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        corruption.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        corruption.loadNBTData(nbt);
    }
}
