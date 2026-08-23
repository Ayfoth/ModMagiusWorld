package com.magius.world.mod.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SwordsoulSpiritForgeMarkerBlock
        extends Block {

    public SwordsoulSpiritForgeMarkerBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);
    }

    /*
     * Le marqueur existe dans le monde,
     * mais aucun modèle n'est affiché.
     */
    @Override
    public RenderShape getRenderShape(
            BlockState state
    ) {
        return RenderShape.INVISIBLE;
    }
}