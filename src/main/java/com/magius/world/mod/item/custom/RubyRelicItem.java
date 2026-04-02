package com.magius.world.mod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class RubyRelicItem extends Item {
    private final String effectKey;
    private final String loreKey;
    private final ChatFormatting color;

    public RubyRelicItem(Properties properties, String effectKey, String loreKey, ChatFormatting color) {
        super(properties);
        this.effectKey = effectKey;
        this.loreKey = loreKey;
        this.color = color;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(this.effectKey).withStyle(this.color));
        tooltip.add(Component.translatable(this.loreKey)
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("item.magiusworldmod.relic")
                .withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
