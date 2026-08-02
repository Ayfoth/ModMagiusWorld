package com.magius.world.mod.clan.manager;

import com.magius.world.mod.clan.clans.dragonmaid.DragonmaidClan;

public final class ClanLoader {

    private ClanLoader() {
    }

    public static void registerClans() {
        ClanRegistry.register(new DragonmaidClan());
    }
}