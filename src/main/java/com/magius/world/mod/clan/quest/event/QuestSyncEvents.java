package com.magius.world.mod.clan.quest.event;

import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class QuestSyncEvents {

    private QuestSyncEvents() {
    }

    // =========================================================
    // CONNEXION
    // =========================================================

    @SubscribeEvent
    public static void onPlayerLoggedIn(
            PlayerEvent.PlayerLoggedInEvent event
    ) {

        if (event.getEntity() instanceof ServerPlayer player) {

            QuestSyncManager.sync(player);
            ClanSyncManager.sync(player);
        }
    }

    // =========================================================
    // RESPAWN
    // =========================================================

    @SubscribeEvent
    public static void onPlayerRespawn(
            PlayerEvent.PlayerRespawnEvent event
    ) {

        if (event.getEntity() instanceof ServerPlayer player) {

            QuestSyncManager.sync(player);
            ClanSyncManager.sync(player);
        }
    }

    // =========================================================
    // CHANGEMENT DE DIMENSION
    // =========================================================

    @SubscribeEvent
    public static void onPlayerChangedDimension(
            PlayerEvent.PlayerChangedDimensionEvent event
    ) {

        if (event.getEntity() instanceof ServerPlayer player) {

            QuestSyncManager.sync(player);
            ClanSyncManager.sync(player);
        }
    }
}
