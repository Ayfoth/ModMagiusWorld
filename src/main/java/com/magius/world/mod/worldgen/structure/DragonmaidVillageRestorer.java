package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public final class DragonmaidVillageRestorer {

    private static final ResourceLocation STAGE_1 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid/village_center_stage1"
            );

    private DragonmaidVillageRestorer() {
    }

    public static boolean restoreStage1(
            ServerLevel level,
            BlockPos hearthPos
    ) {

        StructureTemplate template =
                level.getStructureManager()
                        .getOrCreate(STAGE_1);

        /*
         * Le Cœur est à l'offset 12 / 1 / 12
         * dans notre template 25x11x25.
         */
        BlockPos origin =
                hearthPos.offset(
                        -12,
                        -1,
                        -12
                );

        StructurePlaceSettings settings =
                new StructurePlaceSettings()
                        .setMirror(Mirror.NONE)
                        .setRotation(Rotation.NONE)
                        .setIgnoreEntities(true);

        return template.placeInWorld(
                level,
                origin,
                origin,
                settings,
                level.getRandom(),
                3
        );
    }
}
