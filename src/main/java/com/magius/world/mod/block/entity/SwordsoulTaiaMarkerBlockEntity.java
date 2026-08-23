package com.magius.world.mod.block.entity;

import com.magius.world.mod.entity.swordsoul.SwordsoulTaiaEntity;
import com.magius.world.mod.worldgen.structure.SwordsoulTaiaSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SwordsoulTaiaMarkerBlockEntity extends BlockEntity {

    public SwordsoulTaiaMarkerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.SWORDSOUL_TAIA_MARKER_BE.get(),
                pos,
                state
        );
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            SwordsoulTaiaMarkerBlockEntity blockEntity
    ) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        AABB searchArea =
                new AABB(pos).inflate(16.0D);

        boolean taiaAlreadyPresent =
                !serverLevel.getEntitiesOfClass(
                        SwordsoulTaiaEntity.class,
                        searchArea
                ).isEmpty();

        if (!taiaAlreadyPresent) {
            SwordsoulTaiaSpawner.spawnTaia(
                    serverLevel,
                    pos
            );
        }

        /*
         * Le marqueur disparaît après avoir vérifié
         * ou créé Taia.
         */
        serverLevel.setBlock(
                pos,
                Blocks.AIR.defaultBlockState(),
                3
        );
    }
}
