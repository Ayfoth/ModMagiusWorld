package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulFirstQuest implements Quest {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "L'Épée sans maître";
    }

    @Override
    public String getDescription() {
        return "Retrouvez la Lame spirituelle brisée.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }
}