package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyTreeFeature extends Feature<NoneFeatureConfiguration> {
    public RubyTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int trunkHeight = 4 + random.nextInt(3);

        for (int y = 0; y < trunkHeight; y++) {
            BlockPos pos = origin.above(y);
            if (level.getBlockState(pos).canBeReplaced()) {
                level.setBlock(pos, Blocks.BASALT.defaultBlockState(), 2);
            }
        }

        BlockPos top = origin.above(trunkHeight);

        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos leafPos = top.offset(x, y, z);

                    if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= 3
                            && level.getBlockState(leafPos).canBeReplaced()) {
                        level.setBlock(leafPos, ModBlocks.RUBIS_BLOCK.get().defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
