package com.magius.world.mod.clan.quest.loader;

import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidFirstQuest;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;

public final class QuestLoader {

    private QuestLoader() {
    }

    public static void registerQuests() {
        QuestRegistry.register(new DragonmaidFirstQuest());
    }
}
