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

public class RubyCliffOreFeature extends Feature<NoneFeatureConfiguration> {

    public RubyCliffOreFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        boolean placedAny = false;

        for (int i = 0; i < 12; i++) {
            BlockPos pos = origin.offset(
                    random.nextInt(7) - 3,
                    random.nextInt(5) - 2,
                    random.nextInt(7) - 3
            );

            BlockState state = level.getBlockState(pos);

            boolean validBase =
                    state.is(Blocks.STONE) ||
                            state.is(Blocks.DEEPSLATE) ||
                            state.is(Blocks.TUFF) ||
                            state.is(Blocks.ANDESITE);

            if (!validBase) continue;

            boolean exposed =
                    level.isEmptyBlock(pos.north()) ||
                            level.isEmptyBlock(pos.south()) ||
                            level.isEmptyBlock(pos.east()) ||
                            level.isEmptyBlock(pos.west()) ||
                            level.isEmptyBlock(pos.above());

            if (!exposed) continue;

            level.setBlock(pos, ModBlocks.RUBIS_ORE.get().defaultBlockState(), 2);
            placedAny = true;
        }

        return placedAny;
    }
}
