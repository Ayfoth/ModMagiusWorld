package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.MagiusWorldMod;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

import java.util.Optional;

public class DragonmaidVillageStructure extends Structure {

    public static final Codec<DragonmaidVillageStructure> CODEC =
            simpleCodec(DragonmaidVillageStructure::new);

    public DragonmaidVillageStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(
            GenerationContext context
    ) {

        var templatePoolRegistry =
                context.registryAccess()
                        .registryOrThrow(Registries.TEMPLATE_POOL);

        var startPool =
                templatePoolRegistry.getHolderOrThrow(
                        ResourceKey.create(
                                Registries.TEMPLATE_POOL,
                                ResourceLocation.fromNamespaceAndPath(
                                        MagiusWorldMod.MOD_ID,
                                        "dragonmaid_village/start"
                                )
                        )
                );

        BlockPos startPos =
                new BlockPos(
                        context.chunkPos().getMiddleBlockX(),
                        0,
                        context.chunkPos().getMiddleBlockZ()
                );

        // ---------------------------------------------------------
// Vérification de la zone où sera posé le centre 25x25
// ---------------------------------------------------------

        int centerX = startPos.getX();
        int centerZ = startPos.getZ();

        var generator = context.chunkGenerator();
        var heightAccessor = context.heightAccessor();
        var randomState = context.randomState();

        int centerY = generator.getFirstFreeHeight(
                centerX,
                centerZ,
                Heightmap.Types.WORLD_SURFACE_WG,
                heightAccessor,
                randomState
        );

// On contrôle plusieurs points sous notre structure 25x25.
// 10 blocs permet de rester légèrement à l'intérieur des bords.
        int checkDistance = 10;

        int[] terrainHeights = {
                generator.getFirstFreeHeight(
                        centerX + checkDistance, centerZ,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX - checkDistance, centerZ,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX, centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX, centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),

                // Les quatre coins
                generator.getFirstFreeHeight(
                        centerX + checkDistance, centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX + checkDistance, centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX - checkDistance, centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                ),
                generator.getFirstFreeHeight(
                        centerX - checkDistance, centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor, randomState
                )
        };

        int minY = centerY;
        int maxY = centerY;

        for (int height : terrainHeights) {
            minY = Math.min(minY, height);
            maxY = Math.max(maxY, height);
        }

// On tolère au maximum 4 blocs de différence
// sur la zone occupée par le centre.
        if (maxY - minY > 4) {
            return Optional.empty();
        }

        // ---------------------------------------------------------
// Vérification que la zone n'est pas dans l'eau
// ---------------------------------------------------------

        int[][] samplePoints = {
                {centerX, centerZ},

                {centerX + checkDistance, centerZ},
                {centerX - checkDistance, centerZ},
                {centerX, centerZ + checkDistance},
                {centerX, centerZ - checkDistance},

                {centerX + checkDistance, centerZ + checkDistance},
                {centerX + checkDistance, centerZ - checkDistance},
                {centerX - checkDistance, centerZ + checkDistance},
                {centerX - checkDistance, centerZ - checkDistance}
        };

        for (int[] point : samplePoints) {

            int x = point[0];
            int z = point[1];

            int surfaceY =
                    generator.getFirstFreeHeight(
                            x,
                            z,
                            Heightmap.Types.WORLD_SURFACE_WG,
                            heightAccessor,
                            randomState
                    );

            BlockPos groundPos =
                    new BlockPos(
                            x,
                            surfaceY - 1,
                            z
                    );

            var blockState =
                    context.chunkGenerator()
                            .getBaseColumn(
                                    x,
                                    z,
                                    heightAccessor,
                                    randomState
                            )
                            .getBlock(surfaceY - 1);

            if (!blockState.getFluidState().isEmpty()) {
                return Optional.empty();
            }
        }
        // ---------------------------------------------------------
// Détection simple d'un relief proche
// ---------------------------------------------------------



// On regarde le terrain à 24 blocs dans les 4 directions
        int distance = 24;

        int northY = generator.getFirstFreeHeight(
                centerX,
                centerZ - distance,
                Heightmap.Types.WORLD_SURFACE_WG,
                heightAccessor,
                randomState
        );

        int southY = generator.getFirstFreeHeight(
                centerX,
                centerZ + distance,
                Heightmap.Types.WORLD_SURFACE_WG,
                heightAccessor,
                randomState
        );

        int eastY = generator.getFirstFreeHeight(
                centerX + distance,
                centerZ,
                Heightmap.Types.WORLD_SURFACE_WG,
                heightAccessor,
                randomState
        );

        int westY = generator.getFirstFreeHeight(
                centerX - distance,
                centerZ,
                Heightmap.Types.WORLD_SURFACE_WG,
                heightAccessor,
                randomState
        );

// Pour considérer qu'on est au pied d'un relief,
// au moins un côté doit monter de 8 blocs.
        int requiredRise = 8;

        boolean mountainNearby =
                northY >= centerY + requiredRise ||
                        southY >= centerY + requiredRise ||
                        eastY >= centerY + requiredRise ||
                        westY >= centerY + requiredRise;

// Pas de relief suffisant : on refuse cet emplacement.
        if (!mountainNearby) {
            return Optional.empty();
        }

        // ---------------------------------------------------------
// Détermination de la direction du relief principal
// ---------------------------------------------------------

        int northRise = northY - centerY;
        int southRise = southY - centerY;
        int eastRise = eastY - centerY;
        int westRise = westY - centerY;

        String mountainDirection = "NORTH";
        int highestRise = northRise;

        if (southRise > highestRise) {
            highestRise = southRise;
            mountainDirection = "SOUTH";
        }

        if (eastRise > highestRise) {
            highestRise = eastRise;
            mountainDirection = "EAST";
        }

        if (westRise > highestRise) {
            highestRise = westRise;
            mountainDirection = "WEST";
        }

        System.out.println(
                "[Dragonmaid Village] Relief détecté : "
                        + mountainDirection
                        + " | montée = "
                        + highestRise
                        + " blocs"
        );

        return JigsawPlacement.addPieces(
                context,
                startPool,
                Optional.empty(),
                1,
                startPos,
                false,Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                32
        );
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.DRAGONMAID_VILLAGE.get();
    }
}