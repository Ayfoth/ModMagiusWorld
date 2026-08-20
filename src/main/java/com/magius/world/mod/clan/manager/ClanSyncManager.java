package com.magius.world.mod.clan.manager;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.S2CClanDataPacket;
import net.minecraft.server.level.ServerPlayer;

public final class ClanSyncManager {

    private ClanSyncManager() {
    }

    public static void sync(
            ServerPlayer player
    ) {

        if (player == null) {
            return;
        }

        ClanManager.get(player)
                .ifPresent(
                        data ->
                                ModMessages.sendToPlayer(
                                        new S2CClanDataPacket(
                                                data.saveNBT()
                                        ),
                                        player
                                )
                );
    }
}
