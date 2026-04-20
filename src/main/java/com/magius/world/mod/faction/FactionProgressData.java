package com.magius.world.mod.faction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class FactionProgressData extends SavedData {

    private static final String DATA_NAME = "magiusworldmod_faction_progress";

    private final Map<String, Set<String>> completedObjectivesByFaction = new HashMap<>();
    private final Map<String, Map<String, Set<UUID>>> claimedRewardsByFaction = new HashMap<>();
    private final Map<String, Map<String, Integer>> objectiveProgressByFaction = new HashMap<>();
    private final Map<String, Map<String, Set<String>>> uniqueProgressKeysByFaction = new HashMap<>();

    public boolean hasUniqueProgressKey(String factionId, String objectiveId, String key) {
        return uniqueProgressKeysByFaction
                .getOrDefault(factionId, Collections.emptyMap())
                .getOrDefault(objectiveId, Collections.emptySet())
                .contains(key);
    }

    public boolean addUniqueProgressKey(String factionId, String objectiveId, String key) {
        Set<String> keys = uniqueProgressKeysByFaction
                .computeIfAbsent(factionId, ignored -> new HashMap<>())
                .computeIfAbsent(objectiveId, ignored -> new HashSet<>());

        boolean added = keys.add(key);
        if (added) {
            setDirty();
        }
        return added;
    }

    public int getUniqueProgressCount(String factionId, String objectiveId) {
        return uniqueProgressKeysByFaction
                .getOrDefault(factionId, Collections.emptyMap())
                .getOrDefault(objectiveId, Collections.emptySet())
                .size();
    }

    public static FactionProgressData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                FactionProgressData::load,
                FactionProgressData::new,
                DATA_NAME
        );
    }

    public static FactionProgressData load(CompoundTag tag) {
        FactionProgressData data = new FactionProgressData();

        CompoundTag completedTag = tag.getCompound("completedObjectivesByFaction");
        for (String factionId : completedTag.getAllKeys()) {
            ListTag list = completedTag.getList(factionId, 8);
            Set<String> objectives = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                objectives.add(list.getString(i));
            }
            data.completedObjectivesByFaction.put(factionId, objectives);
        }

        CompoundTag claimedTag = tag.getCompound("claimedRewardsByFaction");
        for (String factionId : claimedTag.getAllKeys()) {
            CompoundTag factionTag = claimedTag.getCompound(factionId);
            Map<String, Set<UUID>> rewardsByObjective = new HashMap<>();

            for (String objectiveId : factionTag.getAllKeys()) {
                ListTag uuidList = factionTag.getList(objectiveId, 8);
                Set<UUID> claimedPlayers = new HashSet<>();

                for (int i = 0; i < uuidList.size(); i++) {
                    claimedPlayers.add(UUID.fromString(uuidList.getString(i)));
                }

                rewardsByObjective.put(objectiveId, claimedPlayers);
            }

            data.claimedRewardsByFaction.put(factionId, rewardsByObjective);
        }

        CompoundTag uniqueTag = tag.getCompound("uniqueProgressKeysByFaction");
        for (String factionId : uniqueTag.getAllKeys()) {
            CompoundTag factionTag = uniqueTag.getCompound(factionId);
            Map<String, Set<String>> objectiveMap = new HashMap<>();

            for (String objectiveId : factionTag.getAllKeys()) {
                ListTag keyList = factionTag.getList(objectiveId, 8);
                Set<String> keys = new HashSet<>();

                for (int i = 0; i < keyList.size(); i++) {
                    keys.add(keyList.getString(i));
                }

                objectiveMap.put(objectiveId, keys);
            }

            data.uniqueProgressKeysByFaction.put(factionId, objectiveMap);
        }

        return data;
    }
    public int getObjectiveProgress(String factionId, String objectiveId) {
        return objectiveProgressByFaction
                .getOrDefault(factionId, java.util.Collections.emptyMap())
                .getOrDefault(objectiveId, 0);
    }

    public void setObjectiveProgress(String factionId, String objectiveId, int value) {
        objectiveProgressByFaction
                .computeIfAbsent(factionId, ignored -> new HashMap<>())
                .put(objectiveId, value);
        setDirty();
    }

    public int addObjectiveProgress(String factionId, String objectiveId, int amount) {
        Map<String, Integer> factionMap = objectiveProgressByFaction
                .computeIfAbsent(factionId, ignored -> new HashMap<>());

        int newValue = factionMap.getOrDefault(objectiveId, 0) + amount;
        factionMap.put(objectiveId, newValue);
        setDirty();
        return newValue;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag completedTag = new CompoundTag();
        for (Map.Entry<String, Set<String>> entry : completedObjectivesByFaction.entrySet()) {
            ListTag objectivesList = new ListTag();
            for (String objectiveId : entry.getValue()) {
                objectivesList.add(StringTag.valueOf(objectiveId));
            }
            completedTag.put(entry.getKey(), objectivesList);
        }
        tag.put("completedObjectivesByFaction", completedTag);

        CompoundTag claimedTag = new CompoundTag();
        for (Map.Entry<String, Map<String, Set<UUID>>> factionEntry : claimedRewardsByFaction.entrySet()) {
            CompoundTag factionTag = new CompoundTag();

            for (Map.Entry<String, Set<UUID>> objectiveEntry : factionEntry.getValue().entrySet()) {
                ListTag uuidList = new ListTag();
                for (UUID uuid : objectiveEntry.getValue()) {
                    uuidList.add(StringTag.valueOf(uuid.toString()));
                }
                factionTag.put(objectiveEntry.getKey(), uuidList);
            }

            claimedTag.put(factionEntry.getKey(), factionTag);
        }

        tag.put("claimedRewardsByFaction", claimedTag);

        CompoundTag uniqueTag = new CompoundTag();
        for (Map.Entry<String, Map<String, Set<String>>> factionEntry : uniqueProgressKeysByFaction.entrySet()) {
            CompoundTag factionTag = new CompoundTag();

            for (Map.Entry<String, Set<String>> objectiveEntry : factionEntry.getValue().entrySet()) {
                ListTag keyList = new ListTag();
                for (String key : objectiveEntry.getValue()) {
                    keyList.add(StringTag.valueOf(key));
                }
                factionTag.put(objectiveEntry.getKey(), keyList);
            }

            uniqueTag.put(factionEntry.getKey(), factionTag);
        }
        tag.put("uniqueProgressKeysByFaction", uniqueTag);

        return tag;
    }

    public boolean isObjectiveCompleted(String factionId, String objectiveId) {
        return completedObjectivesByFaction
                .getOrDefault(factionId, Collections.emptySet())
                .contains(objectiveId);
    }

    public void completeObjective(String factionId, String objectiveId) {
        completedObjectivesByFaction
                .computeIfAbsent(factionId, ignored -> new HashSet<>())
                .add(objectiveId);
        setDirty();
    }

    public boolean hasClaimedReward(String factionId, String objectiveId, UUID playerId) {
        return claimedRewardsByFaction
                .getOrDefault(factionId, Collections.emptyMap())
                .getOrDefault(objectiveId, Collections.emptySet())
                .contains(playerId);
    }

    public void markRewardClaimed(String factionId, String objectiveId, UUID playerId) {
        claimedRewardsByFaction
                .computeIfAbsent(factionId, ignored -> new HashMap<>())
                .computeIfAbsent(objectiveId, ignored -> new HashSet<>())
                .add(playerId);
        setDirty();
    }

    public Set<String> getCompletedObjectives(String factionId) {
        return Collections.unmodifiableSet(
                completedObjectivesByFaction.getOrDefault(factionId, Collections.emptySet())
        );
    }
}
