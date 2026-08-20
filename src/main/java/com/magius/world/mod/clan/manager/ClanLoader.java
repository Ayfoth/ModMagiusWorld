package com.magius.world.mod.clan.manager;

import com.magius.world.mod.clan.clans.dragonmaid.DragonmaidClan;
import com.magius.world.mod.clan.clans.swordsoul.SwordsoulClan;
import com.magius.world.mod.clan.reward.DragonmaidClanRewards;

public final class ClanLoader {

    private ClanLoader() {
    }

    public static void registerClans() {

        // Enregistrement des clans
        ClanRegistry.register(
                new DragonmaidClan()
        );

        ClanRegistry.register(
                new SwordsoulClan()
        );
    }
}