package com.magius.world.mod.clan.quest.ancientgear;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class AncientGearFirstQuest implements Quest {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "ancient_gear_sleeping_gears"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Les Rouages endormis";
    }

    @Override
    public String getDescription() {
        return "Retrouvez les mécanismes oubliés de Geartown "
                + "et préparez le réveil de son générateur antique.";
    }

    @Override
    public int getPrestigeReward() {
        return 50;
    }
}
