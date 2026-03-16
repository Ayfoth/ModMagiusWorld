package com.magius.world.mod.item.custom;


import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RubisAxeItem extends AxeItem {


    public RubisAxeItem(Tier tier, float attackDamage, float attackSpeed, Properties props) {
        super(tier, attackDamage, attackSpeed, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        // si c'est une bûche
        if (state.is(BlockTags.LOGS)) {

            if (!level.isClientSide) {

                BlockState newState = ModBlocks.BLACKWOOD_LOG.get().defaultBlockState();
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

        return super.useOn(context);
    }
}
