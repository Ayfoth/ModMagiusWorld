package com.magius.world.mod.block.custom;

import com.magius.world.mod.client.ClientSwordsoulScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SwordsoulSpiritForgeTerminalBlock extends Block {

    public SwordsoulSpiritForgeTerminalBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {

        if (level.isClientSide) {

            ClientSwordsoulScreens.openSpiritForge(
                    pos
            );
        }

        return InteractionResult.sidedSuccess(
                level.isClientSide
        );
    }
}