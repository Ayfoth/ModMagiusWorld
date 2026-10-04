package com.magius.world.mod.clan.quest.ancientgear;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class AncientGearSecondQuest implements Quest {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(
            MagiusWorldMod.MOD_ID, "ancient_gear_first_automaton"
    );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Le premier automate";
    }

    @Override
    public String getDescription() {
        return "Rassemblez une armature, un noyau de commande et une source d'énergie "
                + "pour réveiller puis réparer le premier automate de Geartown.";
    }

    @Override
    public ResourceLocation getRequiredQuest() {
        return AncientGearFirstQuest.ID;
    }

    @Override
    public int getPrestigeReward() {
        return 100;
    }
}
