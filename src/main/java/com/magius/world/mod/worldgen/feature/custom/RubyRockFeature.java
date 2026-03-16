package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyRockFeature extends Feature<NoneFeatureConfiguration> {
    public RubyRockFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // sécurité : il faut un sol
        if (level.isEmptyBlock(origin.below())) {
            return false;
        }

        int radiusX = 1 + random.nextInt(2); // 1-2
        int radiusY = 1 + random.nextInt(2); // 1-2
        int radiusZ = 1 + random.nextInt(2); // 1-2

        boolean placedAny = false;

        for (int x = -radiusX; x <= radiusX; x++) {
            for (int y = -radiusY; y <= radiusY; y++) {
                for (int z = -radiusZ; z <= radiusZ; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) {
                        continue;
                    }

                    // forme arrondie simple
                    double dx = x / (double) radiusX;
                    double dy = y / (double) radiusY;
                    double dz = z / (double) radiusZ;

                    if ((dx * dx) + (dy * dy) + (dz * dz) <= 1.15D) {
                        BlockState current = level.getBlockState(pos);

                        if (current.canBeReplaced()) {
                            // base du rocher
                            BlockState stateToPlace = switch (random.nextInt(3)) {
                                case 0 -> Blocks.DEEPSLATE.defaultBlockState();
                                case 1 -> Blocks.TUFF.defaultBlockState();
                                default -> Blocks.BASALT.defaultBlockState();
                            };

                            // petite chance de mettre du rubis visible
                            if (random.nextFloat() < 0.12f) {
                                stateToPlace = ModBlocks.RUBIS_BLOCK.get().defaultBlockState();
                            }

                            level.setBlock(pos, stateToPlace, 2);
                            placedAny = true;
                        }
                    }
                }
            }
        }

        return placedAny;
    }
}
