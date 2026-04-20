package com.magius.world.mod.faction.reward;

import net.minecraft.world.item.Item;

public class ItemReward {

    private final Item item;
    private final int count;

    public ItemReward(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }
}