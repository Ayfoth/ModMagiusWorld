package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.swordsoul.SwordsoulTaiaEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class SwordsoulTaiaSpawner {

    private SwordsoulTaiaSpawner() {
    }

    public static void spawnTaia(
            ServerLevel level,
            BlockPos markerPos
    ) {
        BlockPos spawnPos =
                markerPos.above();

        SwordsoulTaiaEntity taia =
                ModEntities.SWORDSOUL_TAIA.get()
                        .create(level);

        if (taia == null) {
            return;
        }

        taia.moveTo(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D,
                0.0F,
                0.0F
        );

        taia.setPersistenceRequired();

        level.addFreshEntity(taia);
    }
}