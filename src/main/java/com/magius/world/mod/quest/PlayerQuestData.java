package com.magius.world.mod.quest;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuestData {
    private static final String QUESTS_TAG = "quests";

    private final Map<String, QuestState> questStates = new HashMap<>();

    public QuestState getQuestState(String questId) {
        if (questId == null || questId.isBlank()) {
            return QuestState.NOT_STARTED;
        }

        return questStates.getOrDefault(questId, QuestState.NOT_STARTED);
    }

    public void setQuestState(String questId, QuestState state) {
        if (questId == null || questId.isBlank()) {
            throw new IllegalArgumentException(
                    "L'identifiant de quête ne peut pas être vide."
            );
        }

        if (state == null || state == QuestState.NOT_STARTED) {
            questStates.remove(questId);
            return;
        }

        questStates.put(questId, state);
    }

    public boolean startQuest(String questId) {
        if (getQuestState(questId) != QuestState.NOT_STARTED) {
            return false;
        }

        setQuestState(questId, QuestState.STARTED);
        return true;
    }

    public boolean completeQuest(String questId) {
        if (getQuestState(questId) != QuestState.STARTED) {
            return false;
        }

        setQuestState(questId, QuestState.COMPLETED);
        return true;
    }

    public void saveNBTData(CompoundTag tag) {
        CompoundTag questsTag = new CompoundTag();

        for (Map.Entry<String, QuestState> entry : questStates.entrySet()) {
            questsTag.putString(entry.getKey(), entry.getValue().name());
        }

        tag.put(QUESTS_TAG, questsTag);
    }

    public void loadNBTData(CompoundTag tag) {
        questStates.clear();

        CompoundTag questsTag = tag.getCompound(QUESTS_TAG);

        for (String questId : questsTag.getAllKeys()) {
            QuestState state =
                    QuestState.fromName(questsTag.getString(questId));

            if (state != QuestState.NOT_STARTED) {
                questStates.put(questId, state);
            }
        }
    }

    public void copyFrom(PlayerQuestData source) {
        questStates.clear();
        questStates.putAll(source.questStates);
    }
}
