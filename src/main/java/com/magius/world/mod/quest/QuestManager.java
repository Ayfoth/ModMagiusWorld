package com.magius.world.mod.quest;

import net.minecraft.world.entity.player.Player;

public final class QuestManager {

    private QuestManager() {
    }

    public static QuestState getQuestState(Player player, String questId) {
        return player.getCapability(PlayerQuestProvider.PLAYER_QUEST_DATA)
                .map(data -> data.getQuestState(questId))
                .orElse(QuestState.NOT_STARTED);
    }

    public static boolean startQuest(Player player, String questId) {
        if (player.level().isClientSide) {
            return false;
        }

        return player.getCapability(PlayerQuestProvider.PLAYER_QUEST_DATA)
                .map(data -> data.startQuest(questId))
                .orElse(false);
    }

    public static boolean completeQuest(Player player, String questId) {
        if (player.level().isClientSide) {
            return false;
        }

        return player.getCapability(PlayerQuestProvider.PLAYER_QUEST_DATA)
                .map(data -> data.completeQuest(questId))
                .orElse(false);
    }

    public static boolean isQuestStarted(Player player, String questId) {
        return getQuestState(player, questId) == QuestState.STARTED;
    }

    public static boolean isQuestCompleted(Player player, String questId) {
        return getQuestState(player, questId) == QuestState.COMPLETED;
    }
}