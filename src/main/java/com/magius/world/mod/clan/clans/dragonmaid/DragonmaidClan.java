package com.magius.world.mod.clan.clans.dragonmaid;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.api.ClanRank;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DragonmaidClan implements Clan {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "dragonmaid");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Dragonmaid";
    }

    @Override
    public String getDescription() {
        return "Les Dragonmaids sont les gardiennes des anciens dragons et protègent leurs secrets.";
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(   MagiusWorldMod.MOD_ID, "textures/gui/clans/dragonmaid.png");
    }

    @Override
    public List<ClanRank> getRanks() {
        return List.of(
                new ClanRank("Novice", 0),
                new ClanRank("Disciple", 100),
                new ClanRank("Gardien", 300),
                new ClanRank("Chevalier", 700),
                new ClanRank("Maître", 1500),
                new ClanRank("Grand Maître", 3000)
        );
    }
}
