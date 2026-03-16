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

public class RubyBushFeature extends Feature<NoneFeatureConfiguration> {

    public RubyBushFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        if (origin.getY() <= level.getMinBuildHeight() || origin.getY() >= level.getMaxBuildHeight() - 2) {
            return false;
        }

        if (level.isEmptyBlock(origin.below())) {
            return false;
        }

        if (!level.getBlockState(origin).canBeReplaced()) {
            return false;
        }

        // petit tronc / centre
        level.setBlock(origin, Blocks.BASALT.defaultBlockState(), 2);

        BlockState rubyCore = ModBlocks.RUBIS_BLOCK.get().defaultBlockState();
        BlockState rubyOuter = Blocks.RED_STAINED_GLASS.defaultBlockState();

// petite boule cristallisée
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) {
                        continue;
                    }

                    if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= 2
                            && level.getBlockState(pos).canBeReplaced()) {

                        // centre = rubis solide
                        if (x == 0 && y == 1 && z == 0) {
                            level.setBlock(pos, rubyCore, 2);
                        } else {
                            // extérieur = verre rouge
                            level.setBlock(pos, rubyOuter, 2);
                        }
                    }
                }
            }
        }

        // petite variation aléatoire
        if (random.nextBoolean()) {
            BlockPos top = origin.above(2);
            if (level.getBlockState(top).canBeReplaced()) {
                level.setBlock(top, Blocks.RED_STAINED_GLASS.defaultBlockState(), 2);
            }
        }

        return true;
    }
}
