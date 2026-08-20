package com.magius.world.mod.block.entity;

import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SwordsoulSanctuaryCoreBlockEntity extends BlockEntity {

    private boolean spiritForgeUnlocked = false;

    public SwordsoulSanctuaryCoreBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.SWORDSOUL_SANCTUARY_CORE_BE.get(),
                pos,
                state
        );
    }

    public boolean isSpiritForgeUnlocked() {
        return spiritForgeUnlocked;
    }

    public void unlockSpiritForge() {

        if (spiritForgeUnlocked) {
            return;
        }

        spiritForgeUnlocked = true;

        setChanged();

        /*
         * Si le cœur est déjà présent dans le monde,
         * on tente immédiatement d'activer la Forge.
         */
        if (level instanceof ServerLevel serverLevel) {

            activateSpiritForge(
                    serverLevel
            );
        }
    }
    private void activateSpiritForge(
            ServerLevel level
    ) {

        int radius = 32;

        BlockPos.MutableBlockPos mutable =
                new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {

            for (int y = -radius; y <= radius; y++) {

                for (int z = -radius; z <= radius; z++) {

                    mutable.set(
                            worldPosition.getX() + x,
                            worldPosition.getY() + y,
                            worldPosition.getZ() + z
                    );

                    if (level.getBlockState(mutable).is(
                            ModBlocks.SWORDSOUL_SPIRIT_FORGE_MARKER.get()
                    )) {

                        level.setBlock(
                                mutable,
                                ModBlocks.SWORDSOUL_SPIRIT_FORGE.get()
                                        .defaultBlockState(),
                                3
                        );

                        /*
                         * Une seule Forge pour ce sanctuaire.
                         */
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {

        super.saveAdditional(tag);

        tag.putBoolean(
                "SpiritForgeUnlocked",
                spiritForgeUnlocked
        );
    }

    @Override
    public void load(
            CompoundTag tag
    ) {

        super.load(tag);

        spiritForgeUnlocked =
                tag.getBoolean(
                        "SpiritForgeUnlocked"
                );
    }
}
