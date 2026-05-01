package com.magius.world.mod.block.echo;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PurifyingCoreBlock extends Block {

    public PurifyingCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            CorruptionHelper.removeCorruption(player, 1);
        }
        if (level.isClientSide && level.random.nextFloat() < 0.3f) {
            level.addParticle(
                    ParticleTypes.END_ROD,
                    pos.getX() + 0.5 + (level.random.nextDouble() - 0.5),
                    pos.getY() + 1,
                    pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5),
                    0, 0.05, 0
            );
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        // Lent : 1 chance sur 8 de tenter une purification
        if (random.nextInt(8) != 0) {
            return;
        }

        // Rayon horizontal de 3, légère variation verticale
        BlockPos targetPos = pos.offset(
                random.nextInt(7) - 3,
                random.nextInt(3) - 1,
                random.nextInt(7) - 3
        );

        BlockState targetState = level.getBlockState(targetPos);

        if (!targetState.is(ModBlocks.CORRUPTED_SOIL.get())) {
            return;
        }

        level.setBlock(targetPos, Blocks.COARSE_DIRT.defaultBlockState(), 3);

        // Particules simples
        level.sendParticles(
                net.minecraft.core.particles.ParticleTypes.END_ROD,
                targetPos.getX() + 0.5,
                targetPos.getY() + 0.8,
                targetPos.getZ() + 0.5,
                4,
                0.2, 0.2, 0.2,
                0.01
        );
    }
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        // Particules autour du core
        if (random.nextInt(3) == 0) {
            level.addParticle(
                    net.minecraft.core.particles.ParticleTypes.END_ROD,
                    pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2,
                    pos.getY() + 0.5 + random.nextDouble(),
                    pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2,
                    0, 0.02, 0
            );
        }
    }
}
