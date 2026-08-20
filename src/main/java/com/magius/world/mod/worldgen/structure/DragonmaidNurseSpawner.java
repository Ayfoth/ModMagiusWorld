package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.dragonmaid.DragonmaidNurseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public final class DragonmaidNurseSpawner {

    private DragonmaidNurseSpawner() {
    }

    public static void spawnNurse(ServerLevel level, BlockPos markerPos) {

        BlockPos spawnPos = markerPos.above();

        DragonmaidNurseEntity nurse =
                ModEntities.DRAGONMAID_NURSE.get().create(level);

        if (nurse == null) {
            return;
        }

        nurse.moveTo(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D,
                0.0F,
                0.0F
        );

        nurse.setPersistenceRequired();

        level.addFreshEntity(nurse);
    }
}
