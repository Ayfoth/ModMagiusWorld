package com.magius.world.mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ColoredNameItem extends Item {

    private final int nameColor;

    public ColoredNameItem(
            Properties properties,
            int nameColor
    ) {
        super(properties);
        this.nameColor = nameColor;
    }

    @NotNull
    @Override
    public Component getName(
            @NotNull ItemStack stack
    ) {
        return super.getName(stack)
                .copy()
                .withStyle(style ->
                        style.withColor(
                                TextColor.fromRgb(nameColor)
                        )
                );
    }
}