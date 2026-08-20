package com.magius.world.mod.clan.quest.manager;

import com.magius.world.mod.clan.quest.api.Quest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import com.magius.world.mod.clan.quest.data.PlayerQuestData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public final class QuestManager {

    private QuestManager() {
    }

    public static LazyOptional<PlayerQuestData> get(Player player) {
        if (player == null) {
            return LazyOptional.empty();
        }

        return player.getCapability(PlayerQuestCapability.INSTANCE);
    }

    public static Optional<Quest> getQuest(ResourceLocation questId) {
        return QuestRegistry.get(questId);
    }

    public static QuestStatus getStatus(
            PlayerQuestData data,
            ResourceLocation questId
    ) {
        if (data == null || questId == null) {
            return QuestStatus.NOT_STARTED;
        }

        return data.getStatus(questId);
    }

    public static boolean startQuest(
            PlayerQuestData data,
            ResourceLocation questId
    ) {

        if (data == null || questId == null) {
            return false;
        }

        Optional<Quest> questOptional =
                QuestRegistry.get(questId);

        if (questOptional.isEmpty()) {
            return false;
        }

        if (
                data.getStatus(questId)
                        != QuestStatus.NOT_STARTED
        ) {
            return false;
        }

        Quest quest =
                questOptional.get();

        /*
         * =====================================================
         * PRÉREQUIS
         * =====================================================
         */

        ResourceLocation requiredQuest =
                quest.getRequiredQuest();

        if (requiredQuest != null) {

            /*
             * La quête précédente doit avoir été
             * entièrement récompensée.
             */
            if (
                    data.getStatus(requiredQuest)
                            != QuestStatus.REWARDED
            ) {
                return false;
            }
        }

        /*
         * =====================================================
         * DÉMARRAGE
         * =====================================================
         */

        data.setStatus(
                questId,
                QuestStatus.IN_PROGRESS
        );

        return true;
    }

    public static boolean completeQuest(
            PlayerQuestData data,
            ResourceLocation questId
    ) {
        if (data == null || questId == null) {
            return false;
        }

        if (data.getStatus(questId) != QuestStatus.IN_PROGRESS) {
            return false;
        }

        data.setStatus(questId, QuestStatus.COMPLETED);
        return true;
    }

    public static boolean rewardQuest(
            PlayerQuestData data,
            ResourceLocation questId
    ) {
        if (data == null || questId == null) {
            return false;
        }

        if (data.getStatus(questId) != QuestStatus.COMPLETED) {
            return false;
        }

        data.setStatus(questId, QuestStatus.REWARDED);
        return true;
    }
}
