package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class UnchainedPrisonQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained_abominations_prison"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "La Prison de l'Abomination";
    }

    @Override
    public String getDescription() {
        return "Brisez le premier Sceau enchaîné "
                + "pour libérer l'énergie qu'il retient et initier l'histoire du clan.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }
}
