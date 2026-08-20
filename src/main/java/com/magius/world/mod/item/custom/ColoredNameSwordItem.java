package com.magius.world.mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

public class ColoredNameSwordItem extends SwordItem {

    private final int nameColor;

    public ColoredNameSwordItem(
            Tier tier,
            int attackDamage,
            float attackSpeed,
            Properties properties,
            int nameColor
    ) {
        super(
                tier,
                attackDamage,
                attackSpeed,
                properties
        );

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