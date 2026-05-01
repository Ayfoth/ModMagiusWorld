package com.magius.world.mod.block.echo;

import com.magius.world.mod.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class UnstableNecroStoneBlock extends Block {
    public UnstableNecroStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.35f) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.05;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            double xd = (random.nextDouble() - 0.5) * 0.02;
            double yd = 0.02;
            double zd = (random.nextDouble() - 0.5) * 0.02;

            level.addParticle(
                    ModParticles.UNSTABLE_NECRO_PARTICLE.get(),
                    x, y, z,
                    xd, yd, zd
            );
        }
    }
}
