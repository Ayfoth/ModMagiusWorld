package com.magius.world.mod.block.custom;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BossRubyDoorBlock extends Block {

    public BossRubyDoorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() != ModItems.BOSS_RUBY_KEY.get()) {
            player.displayClientMessage(
                    Component.translatable("message.magiusworldmod.boss_ruby_door.locked"),
                    true
            );
            level.playSound(null, pos, SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 0.8F);
            return InteractionResult.CONSUME;
        }

        heldItem.shrink(1);

        player.displayClientMessage(
                Component.translatable("message.magiusworldmod.boss_ruby_door.opening"),
                true
        );

        if (level instanceof ServerLevel serverLevel) {
            startOpeningSequence(serverLevel, pos);
        }

        return InteractionResult.CONSUME;
    }

    private void startOpeningSequence(ServerLevel level, BlockPos pos) {
        level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                40, 0.6, 1.0, 0.6, 0.01);

        level.sendParticles(ParticleTypes.END_ROD,
                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                20, 0.4, 0.8, 0.4, 0.01);

        level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 0.8F);

        level.getServer().tell(new TickTask(level.getServer().getTickCount() + 40, () -> {
            openDoorAnimated(level, pos);
        }));
    }

    private void openDoorAnimated(ServerLevel level, BlockPos bottomLeftPos) {
        // Ouvre une porte 2x2 :
        // ##
        // ##

        for (int y = 0; y < 2; y++) {
            final int currentY = y;
            int delay = y * 5;

            level.getServer().tell(new TickTask(level.getServer().getTickCount() + delay, () -> {
                for (int x = 0; x < 2; x++) {
                    BlockPos target = bottomLeftPos.offset(x, currentY, 0);

                    if (level.getBlockState(target).getBlock() == this) {
                        level.sendParticles(ParticleTypes.SOUL,
                                target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5,
                                10, 0.2, 0.2, 0.2, 0.01);

                        level.sendParticles(ParticleTypes.SMOKE,
                                target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5,
                                8, 0.2, 0.2, 0.2, 0.01);

                        level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
                    }
                }

                level.playSound(null, bottomLeftPos, SoundEvents.NETHER_BRICKS_BREAK, SoundSource.BLOCKS, 1.0F, 0.9F);
            }));
        }
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return 0.0F;
    }
}
