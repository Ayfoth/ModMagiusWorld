package com.magius.world.mod.block.echo;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptedSoilBlock extends Block {

    public CorruptedSoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);

        if (!(entity instanceof ServerPlayer player)) return;

// 🛡️ protection par le Purifying Core
        int coreCount = countNearbyPurifyingCores(level, pos);

// aucune protection
        if (coreCount == 0) {
            // continue normalement
        } else {
            // protection totale si plusieurs cores
            if (coreCount >= 2) return;

            // protection partielle si 1 core
            if (player.getRandom().nextFloat() < 0.7f) return;
        }

// cooldown
        int cd = player.getPersistentData().getInt("corruption_soil_cd");
        if (cd > 0) return;

// applique corruption
        CorruptionHelper.addCorruption(player, 1);

// cooldown
        player.getPersistentData().putInt("corruption_soil_cd", 60);


    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        if (random.nextInt(8) != 0) {
            return;
        }

        BlockPos targetPos = pos.offset(
                random.nextInt(3) - 1,
                0,
                random.nextInt(3) - 1
        );

        if (targetPos.equals(pos)) {
            return;
        }

        BlockState targetState = level.getBlockState(targetPos);

        if (!(targetState.is(Blocks.DIRT) || targetState.is(Blocks.GRASS_BLOCK))) {
            return;
        }

        level.setBlock(targetPos, ModBlocks.CORRUPTED_SOIL.get().defaultBlockState(), 3);
        level.sendParticles(
                ParticleTypes.SCULK_SOUL,
                targetPos.getX() + 0.5,
                targetPos.getY() + 1.0,
                targetPos.getZ() + 0.5,
                6, // nombre
                0.2, 0.2, 0.2,
                0.01
        );
        level.playSound(
                null,
                targetPos,
                SoundEvents.SCULK_VEIN_BREAK,
                net.minecraft.sounds.SoundSource.BLOCKS,
                0.5f,
                0.8f
        );
    }
    private int countNearbyPurifyingCores(Level level, BlockPos pos) {
        int radius = 6;
        int count = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = pos.offset(x, y, z);

                    if (level.getBlockState(checkPos).is(ModBlocks.PURIFYING_CORE.get())) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
