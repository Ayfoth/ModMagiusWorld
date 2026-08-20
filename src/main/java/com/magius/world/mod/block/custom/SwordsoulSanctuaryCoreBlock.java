package com.magius.world.mod.block.custom;

import com.magius.world.mod.block.entity.SwordsoulSanctuaryCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SwordsoulSanctuaryCoreBlock extends Block implements EntityBlock {

    public SwordsoulSanctuaryCoreBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new SwordsoulSanctuaryCoreBlockEntity(
                pos,
                state
        );
    }
}