package com.magius.world.mod.item.echo;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class WitherMushroomItem extends BlockItem {
    public WitherMushroomItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            return super.useOn(context); // Shift + clic droit = placer
        }

        return InteractionResult.PASS; // clic droit normal = manger
    }
}
