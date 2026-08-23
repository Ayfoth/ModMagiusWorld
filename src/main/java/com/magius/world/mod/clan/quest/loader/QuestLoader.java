package com.magius.world.mod.clan.quest.loader;

import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidFirstQuest;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidUnexpectedGuestQuest;
import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidForgottenHomeQuest;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulFirstQuest;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulTaiaQuest;

public final class QuestLoader {

    private QuestLoader() {
    }

    public static void registerQuests() {

        QuestRegistry.register(
                new DragonmaidFirstQuest()
        );

        QuestRegistry.register(
                new SwordsoulFirstQuest()
        );

        QuestRegistry.register(
                new SwordsoulMoYeQuest()
        );
        QuestRegistry.register(
                new SwordsoulTaiaQuest()
        );


        QuestRegistry.register(
                new DragonmaidUnexpectedGuestQuest()
        );

        QuestRegistry.register(
                new DragonmaidForgottenHomeQuest()
        );
    }
}
