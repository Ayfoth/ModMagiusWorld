package com.magius.world.mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AncientGearGeneratorBlock extends Block {

    public AncientGearGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!level.isClientSide) {
            player.displayClientMessage(
                    Component.literal(
                            "§6Le générateur est inerte. "
                                    + "Il manque trois composants antiques."
                    ),
                    false
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
