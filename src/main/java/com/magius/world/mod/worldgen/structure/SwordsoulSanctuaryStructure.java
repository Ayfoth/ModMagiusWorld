package com.magius.world.mod.worldgen.structure;

import com.magius.world.mod.MagiusWorldMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.Optional;

public class SwordsoulSanctuaryStructure extends Structure {

    public static final Codec<SwordsoulSanctuaryStructure> CODEC =
            simpleCodec(SwordsoulSanctuaryStructure::new);

    public SwordsoulSanctuaryStructure(StructureSettings settings) {
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
                                        "swordsoul_sanctuary/start"
                                )
                        )
                );

        var generator = context.chunkGenerator();
        var heightAccessor = context.heightAccessor();
        var randomState = context.randomState();

        int centerX =
                context.chunkPos().getMiddleBlockX();

        int centerZ =
                context.chunkPos().getMiddleBlockZ();

        int centerY =
                generator.getFirstFreeHeight(
                        centerX,
                        centerZ,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                );


        // =========================================================
        // TERRAIN SUFFISAMMENT PLAT
        // =========================================================
        //
        // Le biome est déjà contrôlé par le tag JSON.
        // Ici on vérifie simplement que le sanctuaire
        // ne sera pas posé sur une pente trop importante.
        //

        int checkDistance = 10;

        int[] terrainHeights = {

                generator.getFirstFreeHeight(
                        centerX + checkDistance,
                        centerZ,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX - checkDistance,
                        centerZ,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX,
                        centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX,
                        centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX + checkDistance,
                        centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX + checkDistance,
                        centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX - checkDistance,
                        centerZ + checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                ),

                generator.getFirstFreeHeight(
                        centerX - checkDistance,
                        centerZ - checkDistance,
                        Heightmap.Types.WORLD_SURFACE_WG,
                        heightAccessor,
                        randomState
                )
        };

        int minY = centerY;
        int maxY = centerY;

        for (int height : terrainHeights) {
            minY = Math.min(minY, height);
            maxY = Math.max(maxY, height);
        }

        /*
         * Maximum 4 blocs de différence
         * sous la zone du sanctuaire.
         */
        if (maxY - minY > 4) {
            return Optional.empty();
        }


        // =========================================================
        // PLACEMENT
        // =========================================================

        BlockPos startPos =
                new BlockPos(
                        centerX,
                        0,
                        centerZ
                );

        return JigsawPlacement.addPieces(
                context,
                startPool,
                Optional.empty(),
                1,
                startPos,
                false,
                Optional.of(
                        Heightmap.Types.WORLD_SURFACE_WG
                ),
                32
        );
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.SWORDSOUL_SANCTUARY.get();
    }
}