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

public class RubyGrassPatchFeature extends Feature<NoneFeatureConfiguration> {

    public RubyGrassPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placedAny = false;

        for (int i = 0; i < 10; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(7) - 3,
                    0,
                    random.nextInt(7) - 3
            );

            if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) continue;
            if (level.isEmptyBlock(pos.below())) continue;
            if (!level.getBlockState(pos).canBeReplaced()) continue;

            BlockState grassToPlace = random.nextFloat() < 0.65f
                    ? ModBlocks.RED_GRASS.get().defaultBlockState()
                    : ModBlocks.DARK_RED_GRASS.get().defaultBlockState();

            level.setBlock(pos, grassToPlace, 2);
            placedAny = true;
        }

        return placedAny;
    }
}
