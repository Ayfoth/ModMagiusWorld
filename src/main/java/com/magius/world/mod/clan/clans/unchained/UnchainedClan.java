package com.magius.world.mod.clan.clans.unchained;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.api.ClanRank;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class UnchainedClan implements Clan {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Les Déchaînés";
    }

    @Override
    public String getDescription() {
        return "Les Déchaînés brisent leurs propres sceaux "
                + "pour libérer des âmes démoniaques et provoquer "
                + "des réactions en chaîne.";
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(
                MagiusWorldMod.MOD_ID,
                "textures/gui/clans/unchained.png"
        );
    }

    @Override
    public List<ClanRank> getRanks() {
        return List.of(
                new ClanRank("Prisonnier", 0),
                new ClanRank("Briseur de chaînes", 100),
                new ClanRank("Déchaîné", 300),
                new ClanRank("Fléau des sceaux", 700),
                new ClanRank("Maître de l'Abomination", 1500),
                new ClanRank("Seigneur du Désastre", 3000)
        );
    }
}
