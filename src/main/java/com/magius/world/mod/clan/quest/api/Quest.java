package com.magius.world.mod.clan.quest.api;

import net.minecraft.resources.ResourceLocation;

public interface Quest {

    ResourceLocation getId();

    String getTitle();

    String getDescription();

    int getPrestigeReward();
}
