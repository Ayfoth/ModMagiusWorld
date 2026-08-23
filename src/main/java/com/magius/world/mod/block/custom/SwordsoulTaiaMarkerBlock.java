package com.magius.world.mod.block.custom;

import com.magius.world.mod.block.entity.ModBlockEntities;
import com.magius.world.mod.block.entity.SwordsoulTaiaMarkerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SwordsoulTaiaMarkerBlock
        extends Block
        implements EntityBlock {

    public SwordsoulTaiaMarkerBlock(
            Properties properties
    ) {
        super(properties);
    }



    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new SwordsoulTaiaMarkerBlockEntity(
                pos,
                state
        );
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity>
    BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide) {
            return null;
        }

        if (type
                == ModBlockEntities
                .SWORDSOUL_TAIA_MARKER_BE
                .get()) {

            return (BlockEntityTicker<T>)
                    (BlockEntityTicker<
                            SwordsoulTaiaMarkerBlockEntity>)
                            SwordsoulTaiaMarkerBlockEntity::tick;
        }

        return null;
    }
}