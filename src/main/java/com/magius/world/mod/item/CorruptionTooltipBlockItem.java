package com.magius.world.mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CorruptionTooltipBlockItem extends BlockItem {

    private final int requiredLevel;

    public CorruptionTooltipBlockItem(net.minecraft.world.level.block.Block block, Properties properties, int requiredLevel) {
        super(block, properties);
        this.requiredLevel = requiredLevel;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {

        if (requiredLevel > 0) {
            tooltip.add(Component.literal("Nécessite corruption : " + getLevelName(requiredLevel)));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

    private String getLevelName(int level) {
        return switch (level) {
            case 2 -> "Infecté";
            case 3 -> "Muté";
            case 4 -> "Corrompu";
            case 5 -> "Assimilé";
            default -> "Inconnu";
        };
    }
}
