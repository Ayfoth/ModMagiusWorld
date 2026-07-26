package com.magius.world.mod.block.echo;




import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.util.ModTags;
import net.minecraft.core.BlockPos;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OrganicFleshBlock extends Block {

    public OrganicFleshBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        if (random.nextFloat() < 0.12f) {

            level.addParticle(
                    ParticleTypes.CRIMSON_SPORE,

                    pos.getX() + random.nextDouble(),
                    pos.getY() + 0.8D,
                    pos.getZ() + random.nextDouble(),

                    0.0D,
                    0.01D,
                    0.0D
            );
        }

        if (random.nextFloat() < 0.05f) {

            level.addParticle(
                    ParticleTypes.SMOKE,

                    pos.getX() + random.nextDouble(),
                    pos.getY() + 1.0D,
                    pos.getZ() + random.nextDouble(),

                    0.0D,
                    0.02D,
                    0.0D
            );
        }
        if (random.nextFloat() < 0.01f) {

            level.playLocalSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),

                    SoundEvents.SCULK_SENSOR_BREAK,
                    SoundSource.BLOCKS,

                    0.3f,
                    0.5f + random.nextFloat() * 0.3f,

                    false
            );
        }

        super.animateTick(state, level, pos, random);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        ItemStack stack = player.getMainHandItem();

        if (stack.is(ModTags.Items.ORGANIC_BLOCK_HARVESTERS)) {
            return super.getDestroyProgress(state, player, level, pos);
        }

        return 0.0f;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModTags.Items.ORGANIC_BLOCK_CARVERS)) {
            if (!level.isClientSide) {
                level.destroyBlock(pos, false);

                ItemEntity item = new ItemEntity(
                        level,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        new ItemStack(ModItems.NECROTIC_FLESH.get())
                );

                level.addFreshEntity(item);

                level.playSound(null, pos, SoundEvents.HONEY_BLOCK_BREAK,
                        SoundSource.BLOCKS, 1.0f, 0.6f);

                stack.hurtAndBreak(1, player,
                        p -> p.broadcastBreakEvent(hand));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
