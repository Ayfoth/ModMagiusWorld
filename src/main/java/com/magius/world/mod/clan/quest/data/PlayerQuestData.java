package com.magius.world.mod.clan.quest.data;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuestData {

    private final Map<ResourceLocation, QuestStatus> quests = new HashMap<>();

    public QuestStatus getStatus(ResourceLocation questId) {
        return quests.getOrDefault(
                questId,
                QuestStatus.NOT_STARTED
        );
    }

    public void setStatus(
            ResourceLocation questId,
            QuestStatus status
    ) {
        if (questId == null || status == null) {
            return;
        }

        quests.put(questId, status);
    }

    public boolean hasStarted(ResourceLocation questId) {
        return getStatus(questId) != QuestStatus.NOT_STARTED;
    }

    public boolean isCompleted(ResourceLocation questId) {
        return getStatus(questId) == QuestStatus.COMPLETED
                || getStatus(questId) == QuestStatus.REWARDED;
    }

    public void reset() {
        quests.clear();
    }

    public CompoundTag saveNBT() {

        CompoundTag root = new CompoundTag();

        CompoundTag questTag = new CompoundTag();

        for (Map.Entry<ResourceLocation, QuestStatus> entry : quests.entrySet()) {
            questTag.putString(
                    entry.getKey().toString(),
                    entry.getValue().name()
            );
        }

        root.put("Quests", questTag);

        return root;
    }

    public void loadNBT(CompoundTag root) {

        quests.clear();

        if (!root.contains("Quests")) {
            return;
        }

        CompoundTag questTag = root.getCompound("Quests");

        for (String key : questTag.getAllKeys()) {

            ResourceLocation id = ResourceLocation.tryParse(key);

            if (id == null) {
                continue;
            }

            try {
                QuestStatus status =
                        QuestStatus.valueOf(
                                questTag.getString(key)
                        );

                quests.put(id, status);

            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}
