package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class UnchainedChainReactionQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained_chain_reaction"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "La Réaction en chaîne";
    }

    @Override
    public String getDescription() {
        return "Brisez un Sceau du Désastre relié "
                + "à au moins trois autres sceaux.";
    }

    @Override
    public int getPrestigeReward() {
        return 75;
    }

    @Override
    public ResourceLocation getRequiredQuest() {
        return UnchainedTwinsQuest.ID;
    }
}