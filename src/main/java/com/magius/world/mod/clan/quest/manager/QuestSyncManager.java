package com.magius.world.mod.clan.quest.manager;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.S2CQuestDataPacket;
import net.minecraft.server.level.ServerPlayer;

public final class QuestSyncManager {

    private QuestSyncManager() {
    }

    public static void sync(
            ServerPlayer player
    ) {

        if (player == null) {
            return;
        }

        QuestManager.get(player)
                .ifPresent(
                        data ->
                                ModMessages.sendToPlayer(
                                        new S2CQuestDataPacket(
                                                data.saveNBT()
                                        ),
                                        player
                                )
                );
    }
}
