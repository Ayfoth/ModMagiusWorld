package com.magius.world.mod.clan.clans.ancientgear;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.api.ClanRank;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class AncientGearClan implements Clan {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "ancient_gear"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Les Rouages Ancients";
    }

    @Override
    public String getDescription() {
        return "Les Rouages Ancients restaurent des machines oubliées "
                + "et réveillent la puissance mécanique d'une civilisation "
                + "industrielle disparue.";
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(
                MagiusWorldMod.MOD_ID,
                "textures/gui/clans/ancient_gear.png"
        );
    }

    @Override
    public List<ClanRank> getRanks() {
        return List.of(
                new ClanRank("Apprenti mécaniste", 0),
                new ClanRank("Ouvrier des engrenages", 100),
                new ClanRank("Ingénieur antique", 300),
                new ClanRank("Maître des automates", 700),
                new ClanRank("Architecte de Geartown", 1500),
                new ClanRank("Seigneur des Rouages", 3000)
        );
    }
}
