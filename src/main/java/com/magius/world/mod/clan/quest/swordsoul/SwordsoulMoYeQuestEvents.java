package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class SwordsoulMoYeQuestEvents {

    private SwordsoulMoYeQuestEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(
            TickEvent.PlayerTickEvent event
    ) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        /*
         * Vérification une fois par seconde.
         */
        if (player.tickCount % 20 != 0) {
            return;
        }

        QuestManager.get(player)
                .ifPresent(data -> {

                    QuestStatus status =
                            QuestManager.getStatus(
                                    data,
                                    SwordsoulMoYeQuest.ID
                            );

                    if (status != QuestStatus.IN_PROGRESS) {
                        return;
                    }

                    if (!hasSynchronizedBlade(player)) {
                        return;
                    }

                    boolean completed =
                            QuestManager.completeQuest(
                                    data,
                                    SwordsoulMoYeQuest.ID
                            );

                    if (completed) {
                        QuestSyncManager.sync(player);
                    }
                });
    }

    private static boolean hasSynchronizedBlade(
            ServerPlayer player
    ) {
        return player.getInventory().contains(
                new ItemStack(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get()
                )
        )
                || player.getInventory().contains(
                new ItemStack(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE.get()
                )
        )
                || player.getInventory().contains(
                new ItemStack(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get()
                )
        )
                || player.getInventory().contains(
                new ItemStack(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get()
                )
        );
    }
}