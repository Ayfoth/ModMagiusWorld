package com.magius.world.mod.item.echo;

import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LivingAxeItem extends AxeItem {
    public LivingAxeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    // La propagation viendra ici après
    private static final int COOLDOWN_TICKS = 20;


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (context.getPlayer() == null) {
            return InteractionResult.PASS;
        }

        BlockState clickedState = level.getBlockState(context.getClickedPos());

        if (getCorruptedState(clickedState) == null) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            spreadCorruption(level, context.getClickedPos());

            context.getItemInHand().hurtAndBreak(1, context.getPlayer(),
                    player -> player.broadcastBreakEvent(context.getHand()));

            context.getPlayer().getCooldowns().addCooldown(this, COOLDOWN_TICKS);

            level.playSound(
                    null,
                    context.getClickedPos(),
                    SoundEvents.WITHER_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5f,
                    1.4f
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    private void spreadCorruption(Level level, BlockPos center) {
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-2, -1, -2),
                center.offset(2, 1, 2)
        )) {
            BlockState state = level.getBlockState(pos);
            BlockState corrupted = getCorruptedState(state);

            if (corrupted != null && level.random.nextFloat() < 0.25f) {
                level.setBlock(pos, corrupted, 3);

                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                            ParticleTypes.SMOKE,
                            pos.getX() + 0.5,
                            pos.getY() + 1.0,
                            pos.getZ() + 0.5,
                            6,
                            0.25,
                            0.25,
                            0.25,
                            0.02
                    );
                }
            }
        }
    }
    private BlockState getCorruptedState(BlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.GRASS_BLOCK) {
            return ModBlocks.CORRUPTED_SOIL.get().defaultBlockState();
        }

        if (block == Blocks.DIRT) {
            return ModBlocks.CORRUPTED_SOIL.get().defaultBlockState();
        }

        if (block == Blocks.STONE) {
            return ModBlocks.NECRO_STONE.get().defaultBlockState();
        }

        if (block == Blocks.OAK_LOG) {
            return ModBlocks.WITHERED_LOG.get().defaultBlockState();
        }

        if (block == Blocks.OAK_PLANKS) {
            return ModBlocks.REINFORCED_WITHERED_PLANKS.get().defaultBlockState();
        }

        return null;
    }
}