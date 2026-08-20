package com.magius.world.mod.clan.quest.api;

import net.minecraft.resources.ResourceLocation;

public interface Quest {

    ResourceLocation getId();

    String getTitle();

    String getDescription();

    int getPrestigeReward();

    /*
     * Quête devant être récompensée avant que
     * celle-ci puisse commencer.
     *
     * null = aucun prérequis.
     */
    default ResourceLocation getRequiredQuest() {
        return null;
    }
}