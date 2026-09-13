package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class UnchainedTwinsQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained_twins_of_destruction"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Les Jumeaux de la Destruction";
    }

    @Override
    public String getDescription() {
        return "Activez une fois le Sceau d'Aruha "
                + "et le Sceau de Rakea.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }

    @Override
    public ResourceLocation getRequiredQuest() {
        return UnchainedPrisonQuest.ID;
    }
}