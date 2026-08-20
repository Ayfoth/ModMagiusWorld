package com.magius.world.mod.block.entity;

import com.magius.world.mod.entity.dragonmaid.DragonmaidNurseEntity;
import com.magius.world.mod.worldgen.structure.DragonmaidNurseSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class DragonmaidNurseMarkerBlockEntity extends BlockEntity {

    public DragonmaidNurseMarkerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.DRAGONMAID_NURSE_MARKER_BE.get(),
                pos,
                state
        );
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            DragonmaidNurseMarkerBlockEntity blockEntity
    ) {

        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        AABB searchArea =
                new AABB(pos).inflate(16.0D);

        boolean nurseAlreadyPresent =
                !serverLevel.getEntitiesOfClass(
                        DragonmaidNurseEntity.class,
                        searchArea
                ).isEmpty();

        if (!nurseAlreadyPresent) {
            DragonmaidNurseSpawner.spawnNurse(
                    serverLevel,
                    pos
            );
        }

        // Le marqueur a terminé son travail.
        serverLevel.setBlock(
                pos,
                Blocks.GRASS_BLOCK.defaultBlockState(),
                3
        );
    }
}
