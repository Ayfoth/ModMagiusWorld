package com.magius.world.mod.clan.clans.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.api.ClanRank;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SwordsoulClan implements Clan {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Swordsoul";
    }

    @Override
    public String getDescription() {
        return "Les Swordsoul maîtrisent l'art de matérialiser leur esprit sous la forme de lames spirituelles.";
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(
                MagiusWorldMod.MOD_ID,
                "textures/gui/clans/swordsoul.png"
        );
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