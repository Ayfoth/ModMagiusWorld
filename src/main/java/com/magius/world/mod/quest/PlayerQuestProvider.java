package com.magius.world.mod.quest;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerQuestProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<PlayerQuestData> PLAYER_QUEST_DATA =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerQuestData questData = new PlayerQuestData();
    private final LazyOptional<PlayerQuestData> optional =
            LazyOptional.of(() -> questData);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(
            @NotNull Capability<T> capability,
            @Nullable Direction side
    ) {
        return capability == PLAYER_QUEST_DATA
                ? optional.cast()
                : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        questData.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        questData.loadNBTData(tag);
    }
}
