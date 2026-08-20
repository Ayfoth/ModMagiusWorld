package com.magius.world.mod.clan.quest.dragonmaid;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class DragonmaidUnexpectedGuestQuest implements Quest {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );
    private static final ResourceLocation REQUIRED_QUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );

    @Override
    public ResourceLocation getRequiredQuest() {
        return REQUIRED_QUEST;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Une invitée inattendue";
    }

    @Override
    public String getDescription() {
        return "Retrouvez la mystérieuse servante apparue après le réveil du Grimoire.";
    }

    @Override
    public int getPrestigeReward() {
        return 75;
    }
}
