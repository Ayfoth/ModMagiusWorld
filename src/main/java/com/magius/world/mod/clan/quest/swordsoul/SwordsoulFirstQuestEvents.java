package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class SwordsoulFirstQuestEvents {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private SwordsoulFirstQuestEvents() {
    }

    @SubscribeEvent
    public static void onItemPickedUp(
            PlayerEvent.ItemPickupEvent event
    ) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (!event.getStack().is(
                ModItems.BROKEN_SPIRIT_BLADE.get()
        )) {
            return;
        }

        tryCompleteQuest(player);
    }

    private static void tryCompleteQuest(
            ServerPlayer player
    ) {

        QuestManager.get(player)
                .ifPresent(data -> {

                    QuestStatus status =
                            QuestManager.getStatus(
                                    data,
                                    QUEST_ID
                            );

                    if (status != QuestStatus.IN_PROGRESS) {
                        return;
                    }

                    boolean completed =
                            QuestManager.completeQuest(
                                    data,
                                    QUEST_ID
                            );

                    if (!completed) {
                        return;
                    }

                    QuestSyncManager.sync(player);
                });
    }

    public static void checkInventory(
            ServerPlayer player
    ) {

        if (player == null) {
            return;
        }

        boolean hasBlade =
                player.getInventory().contains(
                        new ItemStack(
                                ModItems.BROKEN_SPIRIT_BLADE.get()
                        )
                );

        if (!hasBlade) {
            return;
        }

        tryCompleteQuest(player);
    }
}