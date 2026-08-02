package com.magius.world.mod.clan.manager;

import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.data.PlayerClanData;
import net.minecraft.resources.ResourceLocation;
import com.magius.world.mod.clan.data.PlayerClanCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public final class ClanManager {

    private ClanManager() {
    }

    public static LazyOptional<PlayerClanData> get(Player player) {
        if (player == null) {
            return LazyOptional.empty();
        }

        return player.getCapability(PlayerClanCapability.INSTANCE);
    }
    public static boolean joinClan(PlayerClanData data, String clanName) {

        var clanOptional = ClanRegistry.get(clanName);

        if (clanOptional.isEmpty()) {
            return false;
        }

        joinClan(data, clanOptional.get().getId());

        return true;
    }
    public static Optional<Clan> getClan(PlayerClanData data) {
        if (!data.hasClan()) {
            return Optional.empty();
        }

        return ClanRegistry.get(data.getClanId());
    }


    public static boolean hasClan(PlayerClanData data) {
        return data != null && data.hasClan();
    }

    public static void joinClan(PlayerClanData data, ResourceLocation clanId) {
        if (data == null || clanId == null) {
            return;
        }

        if (data.hasClan()) {
            return;
        }

        data.setClanId(clanId);
        data.setPrestige(0);
        data.setRank(0);
    }

    public static void addPrestige(PlayerClanData data, int amount) {
        if (data == null) {
            return;
        }

        data.addPrestige(amount);
        updateRank(data);
    }

    public static void leaveClan(PlayerClanData data) {
        if (data == null) {
            return;
        }

        data.reset();
    }
    private static void updateRank(PlayerClanData data) {

        Optional<Clan> clanOptional = getClan(data);

        if (clanOptional.isEmpty()) {
            return;
        }

        Clan clan = clanOptional.get();

        int newRank = 0;

        for (int i = 0; i < clan.getRanks().size(); i++) {

            if (data.getPrestige() >= clan.getRanks().get(i).getRequiredPrestige()) {
                newRank = i;
            } else {
                break;
            }
        }

        data.setRank(newRank);
    }
}