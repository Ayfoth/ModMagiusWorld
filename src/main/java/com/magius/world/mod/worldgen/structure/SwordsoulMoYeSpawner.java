package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.swordsoul.SwordsoulMoYeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class SwordsoulMoYeSpawner {

    private SwordsoulMoYeSpawner() {
    }

    public static void spawnMoYe(
            ServerLevel level,
            BlockPos markerPos
    ) {

        BlockPos spawnPos = markerPos.above();

        SwordsoulMoYeEntity moYe =
                ModEntities.SWORDSOUL_MO_YE.get().create(level);

        if (moYe == null) {
            return;
        }

        moYe.moveTo(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D,
                0.0F,
                0.0F
        );

        moYe.setPersistenceRequired();

        level.addFreshEntity(moYe);
    }
}