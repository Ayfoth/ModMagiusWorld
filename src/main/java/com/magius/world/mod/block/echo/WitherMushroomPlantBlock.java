package com.magius.world.mod.block.echo;

import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class WitherMushroomPlantBlock extends FlowerBlock {

    public WitherMushroomPlantBlock(Supplier<MobEffect> effect, int duration, BlockBehaviour.Properties properties) {
        super(effect, duration, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (random.nextFloat() > 0.15F) {
            return;
        }

        BlockPos targetPos = pos.offset(
                random.nextInt(5) - 2,
                random.nextInt(3) - 1,
                random.nextInt(5) - 2
        );

        BlockState targetState = level.getBlockState(targetPos);

        if (targetState.is(Blocks.GRASS_BLOCK)
                || targetState.is(Blocks.DIRT)
                || targetState.is(Blocks.COARSE_DIRT)
                || targetState.is(Blocks.ROOTED_DIRT)) {
            level.setBlock(targetPos, ModBlocks.CORRUPTED_SOIL.get().defaultBlockState(), 3);
        }
    }
}
