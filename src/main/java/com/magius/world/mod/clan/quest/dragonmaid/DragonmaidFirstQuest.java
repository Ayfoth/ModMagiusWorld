package com.magius.world.mod.clan.quest.dragonmaid;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class DragonmaidFirstQuest implements Quest {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Premier contact";
    }

    @Override
    public String getDescription() {
        return "Rencontrez une représentante du clan Dragonmaid.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }
}