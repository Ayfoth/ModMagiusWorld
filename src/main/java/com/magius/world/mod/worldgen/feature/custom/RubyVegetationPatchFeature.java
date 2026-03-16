package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyVegetationPatchFeature extends Feature<NoneFeatureConfiguration> {

    public RubyVegetationPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placedAny = false;

        for (int i = 0; i < 8; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(7) - 3,
                    0,
                    random.nextInt(7) - 3
            );

            if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) continue;
            if (level.isEmptyBlock(pos.below())) continue;
            if (!level.getBlockState(pos).canBeReplaced()) continue;

            float roll = random.nextFloat();
            BlockState stateToPlace;

            if (roll < 0.50f) {
                stateToPlace = ModBlocks.RUBY_FLOWER.get().defaultBlockState();
            } else if (roll < 0.85f) {
                stateToPlace = ModBlocks.RUBY_BUSH.get().defaultBlockState();
            } else {
                stateToPlace = ModBlocks.CRYSTAL_SHARD.get().defaultBlockState();
            }

            level.setBlock(pos, stateToPlace, 2);
            placedAny = true;
        }

        return placedAny;
    }
}
