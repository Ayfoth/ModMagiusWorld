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

public class RubyCrystalClusterFeature extends Feature<NoneFeatureConfiguration> {

    public RubyCrystalClusterFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        if (level.isEmptyBlock(origin.below())) return false;

        int size = 4 + random.nextInt(4);
        boolean placedAny = false;

        for (int i = 0; i < size; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(5) - 2,
                    random.nextInt(3),
                    random.nextInt(5) - 2
            );

            if (!level.getBlockState(pos).canBeReplaced()) continue;

            BlockState state = random.nextFloat() < 0.7f
                    ? ModBlocks.CRYSTAL_SHARD.get().defaultBlockState()
                    : ModBlocks.RUBIS_BLOCK.get().defaultBlockState();

            level.setBlock(pos, state, 2);
            placedAny = true;
        }

        return placedAny;
    }
}
