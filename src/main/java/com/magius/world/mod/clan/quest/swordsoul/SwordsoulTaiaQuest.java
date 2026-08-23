package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulTaiaQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_taia_spiritual_path"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "La Voie de Taia";
    }

    @Override
    public String getDescription() {
        return "Infusez une Lame spirituelle synchronisée avec un sceau d'attribut.";
    }

    @Override
    public int getPrestigeReward() {
        return 75;
    }
}