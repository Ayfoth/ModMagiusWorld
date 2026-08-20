package com.magius.world.mod.clan.quest.manager;

import com.magius.world.mod.clan.quest.api.Quest;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidFirstQuest;

public final class QuestRegistry {

    private static final Map<ResourceLocation, Quest> QUESTS =
            new LinkedHashMap<>();

    private QuestRegistry() {
    }
    public static void bootstrap() {

        register(new DragonmaidFirstQuest());

    }

    public static void register(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("La quête ne peut pas être nulle.");
        }

        ResourceLocation id = quest.getId();

        if (id == null) {
            throw new IllegalArgumentException(
                    "L'identifiant de la quête ne peut pas être nul."
            );
        }

        if (QUESTS.containsKey(id)) {
            throw new IllegalStateException(
                    "Une quête possède déjà l'identifiant : " + id
            );
        }

        QUESTS.put(id, quest);
    }

    public static Optional<Quest> get(ResourceLocation id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(QUESTS.get(id));
    }

    public static Optional<Quest> get(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }

        return QUESTS.values()
                .stream()
                .filter(quest ->
                        quest.getId()
                                .getPath()
                                .equalsIgnoreCase(id)
                )
                .findFirst();
    }

    public static Collection<Quest> getAll() {
        return Collections.unmodifiableCollection(QUESTS.values());
    }

    public static boolean contains(ResourceLocation id) {
        return id != null && QUESTS.containsKey(id);
    }

    public static int size() {
        return QUESTS.size();
    }
}
