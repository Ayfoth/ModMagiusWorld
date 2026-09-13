package com.magius.world.mod.clan.quest.unchained;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class UnchainedQuestCommand {

    private UnchainedQuestCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        dispatcher.register(
                Commands.literal("unchainedquest")
                        .requires(source -> source.hasPermission(2))

                        /*
                         * /unchainedquest start1
                         */
                        .then(
                                Commands.literal("start1")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean started =
                                                    QuestManager.startQuest(
                                                            data,
                                                            UnchainedPrisonQuest.ID
                                                    );

                                            if (!started) {

                                                QuestStatus status =
                                                        QuestManager.getStatus(
                                                                data,
                                                                UnchainedPrisonQuest.ID
                                                        );

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de démarrer la quête. État : §f"
                                                                        + status.name()
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aQuête démarrée : §f"
                                                                    + "La Prison de l'Abomination"
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        /*
                         * /unchainedquest reward1
                         */
                        .then(
                                Commands.literal("reward1")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean rewarded =
                                                    QuestManager.rewardQuest(
                                                            data,
                                                            UnchainedPrisonQuest.ID
                                                    );

                                            if (!rewarded) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cLa quête doit être terminée "
                                                                        + "avant de recevoir sa récompense."
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§dRécompense de la quête 1 récupérée."
                                                    )
                                            );

                                            return 1;
                                        })
                        )
                        /*
                         * /unchainedquest start2
                         */
                        .then(
                                Commands.literal("start2")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean started =
                                                    QuestManager.startQuest(
                                                            data,
                                                            UnchainedTwinsQuest.ID
                                                    );

                                            if (!started) {

                                                QuestStatus status =
                                                        QuestManager.getStatus(
                                                                data,
                                                                UnchainedTwinsQuest.ID
                                                        );

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de démarrer la quête 2. État : §f"
                                                                        + status.name()
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aQuête démarrée : §f"
                                                                    + "Les Jumeaux de la Destruction"
                                                    )
                                            );

                                            return 1;
                                        })
                        )

/*
 * /unchainedquest reward2
 */
                        .then(
                                Commands.literal("reward2")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean rewarded =
                                                    QuestManager.rewardQuest(
                                                            data,
                                                            UnchainedTwinsQuest.ID
                                                    );

                                            if (!rewarded) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cLa quête 2 doit être terminée "
                                                                        + "avant de recevoir sa récompense."
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§dRécompense de la quête 2 récupérée."
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        /*
                         * /unchainedquest start3
                         */
                        .then(
                                Commands.literal("start3")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean started =
                                                    QuestManager.startQuest(
                                                            data,
                                                            UnchainedChainReactionQuest.ID
                                                    );

                                            if (!started) {

                                                QuestStatus status =
                                                        QuestManager.getStatus(
                                                                data,
                                                                UnchainedChainReactionQuest.ID
                                                        );

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de démarrer la quête 3. État : §f"
                                                                        + status.name()
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aQuête démarrée : §f"
                                                                    + "La Réaction en chaîne"
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        /*
                         * /unchainedquest reward3
                         */
                        .then(
                                Commands.literal("reward3")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            boolean rewarded =
                                                    QuestManager.rewardQuest(
                                                            data,
                                                            UnchainedChainReactionQuest.ID
                                                    );

                                            if (!rewarded) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cLa quête 3 doit être terminée "
                                                                        + "avant de recevoir sa récompense."
                                                        )
                                                );

                                                return 0;
                                            }

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§dRécompense de la quête 3 récupérée."
                                                    )
                                            );

                                            return 1;
                                        })
                        )
        );
    }
}
