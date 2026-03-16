package com.magius.world.mod.worldgen.feature.custom;

import com.magius.world.mod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WildRedWheatFeature extends Feature<NoneFeatureConfiguration> {

    public WildRedWheatFeature(Codec<NoneFeatureConfiguration> codec) {
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

            if (!level.getBlockState(pos).canBeReplaced()) continue;
            if (level.isEmptyBlock(pos.below())) continue;

            // vérifie qu'un ruby bush est proche
            boolean bushNearby = false;

            for (BlockPos check : BlockPos.betweenClosed(pos.offset(-2,0,-2), pos.offset(2,0,2))) {
                if (level.getBlockState(check).is(ModBlocks.RUBY_BUSH.get())) {
                    bushNearby = true;
                    break;
                }
            }

            if (!bushNearby) continue;

            int age = random.nextInt(5);

            BlockState crop = ModBlocks.RED_WHEAT_CROP.get()
                    .defaultBlockState()
                    .setValue(BlockStateProperties.AGE_7, age);

            level.setBlock(pos, crop, 2);
            placedAny = true;
        }

        return placedAny;
    }
}
