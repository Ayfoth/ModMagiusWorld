package com.magius.world.mod.block.entity;

import com.magius.world.mod.entity.swordsoul.SwordsoulMoYeEntity;
import com.magius.world.mod.worldgen.structure.SwordsoulMoYeSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SwordsoulMoYeMarkerBlockEntity extends BlockEntity {

    public SwordsoulMoYeMarkerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.SWORDSOUL_MO_YE_MARKER_BE.get(),
                pos,
                state
        );
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            SwordsoulMoYeMarkerBlockEntity blockEntity
    ) {

        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        AABB searchArea =
                new AABB(pos).inflate(16.0D);

        boolean moYeAlreadyPresent =
                !serverLevel.getEntitiesOfClass(
                        SwordsoulMoYeEntity.class,
                        searchArea
                ).isEmpty();

        if (!moYeAlreadyPresent) {
            SwordsoulMoYeSpawner.spawnMoYe(
                    serverLevel,
                    pos
            );
        }

        serverLevel.setBlock(
                pos,
                Blocks.AIR.defaultBlockState(),
                3
        );
    }
}