package com.magius.world.mod.clan.quest.command;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.Quest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidFirstQuestEvents;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class QuestCommand {

    private static final ResourceLocation FIRST_CONTACT =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );
    private static final ResourceLocation UNEXPECTED_GUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );
    private static final ResourceLocation FORGOTTEN_HOME =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    private QuestCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {

        dispatcher.register(
                Commands.literal("quest")

                        // =============================================
                        // INFO
                        // =============================================

                        .then(
                                Commands.literal("info")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            var data =
                                                    optionalData.get();

                                            QuestStatus status =
                                                    QuestManager.getStatus(
                                                            data,
                                                            FIRST_CONTACT
                                                    );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§6=== Informations de quête ==="
                                                    )
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§eQuête : §fPremier contact"
                                                    )
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§eÉtat : §f"
                                                                    + status.name()
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        // =============================================
                        // LIST
                        // =============================================

                        .then(
                                Commands.literal("list")
                                        .executes(context -> {

                                            context.getSource()
                                                    .sendSystemMessage(
                                                            Component.literal(
                                                                    "§6=== Quêtes enregistrées ==="
                                                            )
                                                    );

                                            for (
                                                    Quest quest :
                                                    QuestRegistry.getAll()
                                            ) {

                                                context.getSource()
                                                        .sendSystemMessage(
                                                                Component.literal(
                                                                        "§e- "
                                                                                + quest.getTitle()
                                                                                + " §7("
                                                                                + quest.getId()
                                                                                + ")"
                                                                )
                                                        );
                                            }

                                            return 1;
                                        })
                        )

                        // =============================================
                        // START
                        // =============================================

                        .then(
                                Commands.literal("start")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            boolean success =
                                                    QuestManager.startQuest(
                                                            optionalData.get(),
                                                            FIRST_CONTACT
                                                    );

                                            if (success) {

                                                /*
                                                 * Si le joueur possédait déjà le grimoire avant
                                                 * d'accepter la quête, elle peut être validée
                                                 * immédiatement.
                                                 */
                                                DragonmaidFirstQuestEvents.checkInventory(player);

                                                /*
                                                 * checkInventory() synchronise déjà si la quête
                                                 * vient d'être terminée.
                                                 *
                                                 * Sinon on synchronise simplement son passage
                                                 * à IN_PROGRESS.
                                                 */
                                                QuestSyncManager.sync(player);

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§aQuête démarrée : §fPremier contact"
                                                        )
                                                );

                                                return 1;
                                            }

                                            QuestStatus status =
                                                    QuestManager.getStatus(
                                                            optionalData.get(),
                                                            FIRST_CONTACT
                                                    );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§cImpossible de démarrer la quête. État actuel : §f"
                                                                    + status.name()
                                                    )
                                            );

                                            return 0;
                                        })
                        )

                        // =============================================
// START QUEST 2
// =============================================

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

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            var data =
                                                    optionalData.get();

                                            boolean success =
                                                    QuestManager.startQuest(
                                                            data,
                                                            UNEXPECTED_GUEST
                                                    );

                                            if (success) {

                                                QuestSyncManager.sync(player);

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§aQuête démarrée : §fUne invitée inattendue"
                                                        )
                                                );

                                                return 1;
                                            }

                                            QuestStatus quest2Status =
                                                    QuestManager.getStatus(
                                                            data,
                                                            UNEXPECTED_GUEST
                                                    );

                                            QuestStatus firstContactStatus =
                                                    QuestManager.getStatus(
                                                            data,
                                                            FIRST_CONTACT
                                                    );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§cImpossible de démarrer Une invitée inattendue."
                                                    )
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§7État quête 2 : §f"
                                                                    + quest2Status.name()
                                                    )
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§7Premier Contact : §f"
                                                                    + firstContactStatus.name()
                                                    )
                                            );

                                            return 0;
                                        })
                        )
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

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            var data =
                                                    optionalData.get();

                                            boolean success =
                                                    QuestManager.startQuest(
                                                            data,
                                                            FORGOTTEN_HOME
                                                    );

                                            if (success) {

                                                QuestSyncManager.sync(player);

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§aQuête démarrée : §fLe foyer oublié"
                                                        )
                                                );

                                                return 1;
                                            }

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§cImpossible de démarrer Le foyer oublié."
                                                    )
                                            );

                                            return 0;
                                        })
                        )
                        .then(
                                Commands.literal("reset3")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            var data =
                                                    optionalData.get();

                                            data.setStatus(
                                                    FORGOTTEN_HOME,
                                                    QuestStatus.NOT_STARTED
                                            );

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aQuête réinitialisée : §fLe foyer oublié"
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        // =============================================
                        // COMPLETE
                        // =============================================

                        .then(
                                Commands.literal("complete")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            boolean success =
                                                    QuestManager.completeQuest(
                                                            optionalData.get(),
                                                            FIRST_CONTACT
                                                    );

                                            if (success) {

                                                QuestSyncManager.sync(player);

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§aQuête terminée : §fPremier contact"
                                                        )
                                                );

                                                return 1;
                                            }

                                            QuestStatus status =
                                                    QuestManager.getStatus(
                                                            optionalData.get(),
                                                            FIRST_CONTACT
                                                    );

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§cImpossible de terminer la quête. État actuel : §f"
                                                                    + status.name()
                                                    )
                                            );

                                            return 0;
                                        })
                        )

                        .then(
                                Commands.literal("sync")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aDonnées de quêtes synchronisées."
                                                    )
                                            );

                                            return 1;
                                        })
                        )

                        .then(
                                Commands.literal("reset")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource()
                                                            .getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player)
                                                            .resolve();

                                            if (optionalData.isEmpty()) {

                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );

                                                return 0;
                                            }

                                            var data =
                                                    optionalData.get();

                                            data.setStatus(
                                                    FIRST_CONTACT,
                                                    QuestStatus.NOT_STARTED
                                            );
                                            data.setStatus(
                                                    UNEXPECTED_GUEST,
                                                    QuestStatus.NOT_STARTED
                                            );

                                            QuestSyncManager.sync(player);

                                            player.sendSystemMessage(
                                                    Component.literal(
                                                            "§aQuêtes Dragonmaid réinitialisées."
                                                    )
                                            );

                                            return 1;
                                        })
                        )
        );
    }
}