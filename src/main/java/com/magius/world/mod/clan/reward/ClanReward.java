package com.magius.world.mod.clan.reward;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ClanReward {

    private final ResourceLocation id;
    private final ResourceLocation clanId;

    private final String title;
    private final String description;

    private final int requiredPrestige;

    private final List<ItemStack> items;

    private final ClanRewardType type;
    private final ResourceLocation unlockId;

    public ClanReward(
            ResourceLocation id,
            ResourceLocation clanId,
            String title,
            String description,
            int requiredPrestige,
            ClanRewardType type,
            ResourceLocation unlockId,
            List<ItemStack> items
    ) {
        this.id = id;
        this.clanId = clanId;
        this.title = title;
        this.description = description;
        this.requiredPrestige = requiredPrestige;
        this.type = type;
        this.unlockId = unlockId;

        this.items =
                items == null
                        ? List.of()
                        : List.copyOf(items);
    }

    public ResourceLocation getId() {
        return id;
    }
    public ResourceLocation getUnlockId() {
        return unlockId;
    }

    public ResourceLocation getClanId() {
        return clanId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredPrestige() {
        return requiredPrestige;
    }

    public List<ItemStack> getItems() {
        return items;
    }
    public ClanRewardType getType() {
        return type;
    }
}
