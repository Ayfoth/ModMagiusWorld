package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RubyNaturalTreeFeature extends Feature<NoneFeatureConfiguration> {

    public RubyNaturalTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int height = 5 + random.nextInt(3);

        if (level.isEmptyBlock(origin.below())) return false;

        for (int y = 0; y <= height + 3; y++) {
            BlockPos pos = origin.above(y);

            if (pos.getY() <= level.getMinBuildHeight() || pos.getY() >= level.getMaxBuildHeight()) {
                return false;
            }

            if (!level.getBlockState(pos).canBeReplaced()) {
                return false;
            }
        }

        BlockState trunk = ModBlocks.RUBY_LOG.get().defaultBlockState();
        BlockState leaves = ModBlocks.RUBY_LEAVES.get()
                .defaultBlockState()
                .setValue(LeavesBlock.PERSISTENT, true);

        // tronc principal
        for (int y = 0; y < height; y++) {
            level.setBlock(origin.above(y), trunk, 2);
        }

        BlockPos top = origin.above(height);

        // petite branche latérale aléatoire
        if (random.nextBoolean()) {
            BlockPos branchPos = origin.above(height - 2).relative(net.minecraft.core.Direction.Plane.HORIZONTAL.getRandomDirection(random));
            if (level.getBlockState(branchPos).canBeReplaced()) {
                level.setBlock(branchPos, trunk, 2);
            }
        }

        // étage bas du feuillage
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos leafPos = top.offset(x, -1, z);

                if (!level.getBlockState(leafPos).canBeReplaced()) continue;

                if (Math.abs(x) + Math.abs(z) <= 3) {
                    level.setBlock(leafPos, leaves, 2);
                }
            }
        }

        // étage milieu
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos leafPos = top.offset(x, 0, z);

                if (!level.getBlockState(leafPos).canBeReplaced()) continue;

                if (Math.abs(x) + Math.abs(z) <= 2) {
                    level.setBlock(leafPos, leaves, 2);
                }
            }
        }

        // étage haut
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos leafPos = top.offset(x, 1, z);

                if (!level.getBlockState(leafPos).canBeReplaced()) continue;

                if (Math.abs(x) + Math.abs(z) <= 1) {
                    level.setBlock(leafPos, leaves, 2);
                }
            }
        }

        // sommet
        BlockPos topLeaf = top.above(2);
        if (level.getBlockState(topLeaf).canBeReplaced()) {
            level.setBlock(topLeaf, leaves, 2);
        }

        return true;
    }
}
