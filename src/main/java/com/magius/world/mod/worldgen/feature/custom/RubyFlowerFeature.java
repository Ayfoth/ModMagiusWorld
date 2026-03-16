package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyFlowerFeature extends Feature<NoneFeatureConfiguration> {

    public RubyFlowerFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placedAny = false;

        for (int i = 0; i < 6; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(5) - 2,
                    0,
                    random.nextInt(5) - 2
            );

            if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) continue;
            if (level.isEmptyBlock(pos.below())) continue;
            if (!level.getBlockState(pos).canBeReplaced()) continue;

            level.setBlock(pos, ModBlocks.RUBY_FLOWER.get().defaultBlockState(), 2);
            placedAny = true;
        }

        return placedAny;
    }
}
