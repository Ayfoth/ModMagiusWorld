package com.magius.world.mod.clan.quest.ancientgear;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

public class AncientGearThirdQuest implements Quest {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(
            MagiusWorldMod.MOD_ID, "ancient_gear_heart_of_geartown"
    );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getTitle() {
        return "Le cœur de Geartown";
    }

    @Override
    public String getDescription() {
        return "Fabriquez un cœur de réacteur antique puis installez-le dans le réacteur "
                + "énergétique afin de rendre sa puissance à Geartown.";
    }

    @Override
    public ResourceLocation getRequiredQuest() {
        return AncientGearSecondQuest.ID;
    }

    @Override
    public int getPrestigeReward() {
        return 150;
    }
}
