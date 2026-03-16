package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubySpikeFeature extends Feature<NoneFeatureConfiguration> {
    public RubySpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int height = 3 + random.nextInt(4);

        for (int y = 0; y < height; y++) {
            BlockPos pos = origin.above(y);

            if (level.getBlockState(pos).canBeReplaced()) {
                level.setBlock(pos, ModBlocks.RUBIS_BLOCK.get().defaultBlockState(), 2);
            }
        }

        return true;
    }
}
