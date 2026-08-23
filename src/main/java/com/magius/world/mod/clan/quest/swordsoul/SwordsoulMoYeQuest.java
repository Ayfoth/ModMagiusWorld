package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulMoYeQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_mo_ye_synchronization"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "L'Éveil de Mo Ye";
    }

    @Override
    public String getDescription() {
        return "Forgez votre première Lame spirituelle synchronisée.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }
}