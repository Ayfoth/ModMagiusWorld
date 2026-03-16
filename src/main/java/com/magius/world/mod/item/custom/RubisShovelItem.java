package com.magius.world.mod.item.custom;



import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;


public class RubisShovelItem extends ShovelItem {


    public RubisShovelItem(Tier tier, float attackDamage, float attackSpeed, Properties props) {
        super(tier, attackDamage, attackSpeed, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);


        if (state.is(Blocks.GRASS_BLOCK)) {

            if (!level.isClientSide) {

                BlockState newState = Blocks.COARSE_DIRT.defaultBlockState();
                level.setBlockAndUpdate(pos, newState);

                level.playSound(
                        player,
                        pos,
                        SoundEvents.AXE_STRIP,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );

                if(player != null) {
                    context.getItemInHand().hurtAndBreak(
                            1,
                            player,
                            p -> p.broadcastBreakEvent(context.getHand())
                    );
                }
            }


            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        BlockPos target = pos.relative(context.getClickedFace());
        FluidState fluid = level.getFluidState(target);

        if (fluid.getType() == Fluids.WATER) {

            if (!level.isClientSide) {

                level.setBlockAndUpdate(target, Blocks.LAVA.defaultBlockState());

                level.playSound(
                        player,
                        pos,
                        SoundEvents.LAVA_POP,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.0F
                );

                if(player != null) {
                    context.getItemInHand().hurtAndBreak(
                            1,
                            player,
                            p -> p.broadcastBreakEvent(context.getHand())
                    );
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }
}
