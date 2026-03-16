package com.magius.world.mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RubyFireCoreBlock extends Block {
    public RubyFireCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.6D;
        double z = pos.getZ() + 0.5D;

        if (random.nextFloat() < 0.7f) {
            level.addParticle(ParticleTypes.FLAME,
                    x + (random.nextDouble() - 0.5D) * 0.25D,
                    y + random.nextDouble() * 0.2D,
                    z + (random.nextDouble() - 0.5D) * 0.25D,
                    0.0D, 0.01D, 0.0D);
        }

        if (random.nextFloat() < 0.4f) {
            level.addParticle(ParticleTypes.SMALL_FLAME,
                    x + (random.nextDouble() - 0.5D) * 0.2D,
                    y + 0.05D,
                    z + (random.nextDouble() - 0.5D) * 0.2D,
                    0.0D, 0.005D, 0.0D);
        }

        if (random.nextFloat() < 0.5f) {
            level.addParticle(ParticleTypes.LAVA,
                    x + (random.nextDouble() - 0.5D) * 0.18D,
                    y + 0.1D,
                    z + (random.nextDouble() - 0.5D) * 0.18D,
                    0.0D, 0.0D, 0.0D);
        }

        if (random.nextFloat() < 0.25f) {
            level.addParticle(ParticleTypes.SMOKE,
                    x + (random.nextDouble() - 0.5D) * 0.22D,
                    y + 0.2D,
                    z + (random.nextDouble() - 0.5D) * 0.22D,
                    0.0D, 0.02D, 0.0D);
        }
    }
}