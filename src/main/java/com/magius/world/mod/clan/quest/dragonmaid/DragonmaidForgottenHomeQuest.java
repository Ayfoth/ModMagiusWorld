package com.magius.world.mod.clan.quest.dragonmaid;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class DragonmaidForgottenHomeQuest implements Quest {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    private static final ResourceLocation REQUIRED_QUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Le foyer oublié";
    }

    @Override
    public String getDescription() {
        return "Retrouvez les ruines de l'ancien foyer Dragonmaid et réveillez le Cœur du Foyer.";
    }

    @Override
    public int getPrestigeReward() {
        return 125;
    }

    @Override
    public ResourceLocation getRequiredQuest() {
        return REQUIRED_QUEST;
    }
}
