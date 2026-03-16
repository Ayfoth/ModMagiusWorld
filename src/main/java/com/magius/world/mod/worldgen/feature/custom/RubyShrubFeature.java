package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyShrubFeature extends Feature<NoneFeatureConfiguration> {

    public RubyShrubFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placed = false;

        for (int i = 0; i < 6; i++) {

            BlockPos pos = origin.offset(
                    random.nextInt(5) - 2,
                    0,
                    random.nextInt(5) - 2
            );

            if (level.getBlockState(pos).canBeReplaced()
                    && !level.isEmptyBlock(pos.below())) {

                level.setBlock(pos, ModBlocks.RUBY_BUSH.get().defaultBlockState(), 2);
                placed = true;
            }
        }

        return placed;
    }
}
