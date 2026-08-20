package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public final class DragonmaidVillageSpawnEvents {

    private DragonmaidVillageSpawnEvents() {
    }

//    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {

        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        if (!(event.getChunk() instanceof LevelChunk chunk)) {
            return;
        }

        /*
         * Récupération de notre structure Dragonmaid.
         */
        var structureRegistry =
                level.registryAccess()
                        .registryOrThrow(Registries.STRUCTURE);

        Structure dragonmaidVillage =
                structureRegistry.get(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "dragonmaid_village"
                        )
                );

        if (dragonmaidVillage == null) {
            return;
        }

        /*
         * Vérifie si ce chunk appartient réellement
         * à un village Dragonmaid.
         */
        SectionPos sectionPos =
                SectionPos.of(
                        chunk.getPos(),
                        level.getMinSection()
                );

        StructureStart structureStart =
                level.structureManager()
                        .getStartForStructure(
                                sectionPos,
                                dragonmaidVillage,
                                chunk
                        );

        if (structureStart == null ||
                !structureStart.isValid()) {
            return;
        }

        /*
         * Recherche du marqueur jaune uniquement
         * dans ce chunk.
         */
        int minX =
                chunk.getPos().getMinBlockX();

        int minZ =
                chunk.getPos().getMinBlockZ();

        int maxX =
                chunk.getPos().getMaxBlockX();

        int maxZ =
                chunk.getPos().getMaxBlockZ();

        int minY =
                level.getMinBuildHeight();

        int maxY =
                level.getMaxBuildHeight();

        BlockPos.MutableBlockPos cursor =
                new BlockPos.MutableBlockPos();

        for (int x = minX; x <= maxX; x++) {

            for (int z = minZ; z <= maxZ; z++) {

                for (int y = minY; y < maxY; y++) {

                    cursor.set(x, y, z);

                    if (!level.getBlockState(cursor)
                            .is(Blocks.YELLOW_GLAZED_TERRACOTTA)) {
                        continue;
                    }

                    BlockPos markerPos =
                            cursor.immutable();

                    /*
                     * On supprime le marqueur.
                     *
                     * Pour le premier test on met de l'air.
                     * Ensuite on pourra remettre le véritable
                     * bloc de sol correspondant.
                     */
                    level.setBlock(
                            markerPos,
                            Blocks.AIR.defaultBlockState(),
                            3
                    );

                    DragonmaidNurseSpawner.spawnNurse(
                            level,
                            markerPos
                    );

                    /*
                     * Le marqueur étant unique,
                     * inutile de continuer à scanner.
                     */
                    return;
                }
            }
        }
    }
}
