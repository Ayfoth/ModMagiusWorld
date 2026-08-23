package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class SwordsoulTaiaQuestEvents {

    private static final String ATTRIBUTE_TAG =
            "SwordsoulAttribute";

    private SwordsoulTaiaQuestEvents() {
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

        // Vérification une fois par seconde
        if (player.tickCount % 20 != 0) {
            return;
        }

        QuestManager.get(player).ifPresent(data -> {
            QuestStatus status =
                    QuestManager.getStatus(
                            data,
                            SwordsoulTaiaQuest.ID
                    );

            if (status != QuestStatus.IN_PROGRESS) {
                return;
            }

            if (!hasInfusedBlade(player)) {
                return;
            }

            boolean completed =
                    QuestManager.completeQuest(
                            data,
                            SwordsoulTaiaQuest.ID
                    );

            if (completed) {
                QuestSyncManager.sync(player);
            }
        });
    }

    private static boolean hasInfusedBlade(
            ServerPlayer player
    ) {
        for (int slot = 0;
             slot < player.getInventory().getContainerSize();
             slot++) {

            ItemStack stack =
                    player.getInventory().getItem(slot);

            if (isInfusedSwordsoulBlade(stack)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isInfusedSwordsoulBlade(
            ItemStack stack
    ) {
        if (stack.isEmpty()) {
            return false;
        }

        boolean isSynchronizedBlade =
                stack.is(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get()
                )
                        || stack.is(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE.get()
                )
                        || stack.is(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get()
                )
                        || stack.is(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get()
                );

        if (!isSynchronizedBlade || !stack.hasTag()) {
            return false;
        }

        String attribute =
                stack.getTag()
                        .getString(ATTRIBUTE_TAG);

        return !attribute.isBlank();
    }
}