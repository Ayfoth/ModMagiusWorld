package com.magius.world.mod.clan.quest.command;

import com.magius.world.mod.clan.quest.api.Quest;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class QuestCommand {

    private QuestCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        dispatcher.register(
                Commands.literal("quest")
                        .then(
                                Commands.literal("info")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayerOrException();

                                            var optionalData =
                                                    QuestManager.get(player).resolve();

                                            if (optionalData.isEmpty()) {
                                                player.sendSystemMessage(
                                                        Component.literal(
                                                                "§cImpossible de charger les données de quête."
                                                        )
                                                );
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            ResourceLocation questId =
                                                    ResourceLocation.fromNamespaceAndPath(
                                                            MagiusWorldMod.MOD_ID,
                                                            "dragonmaid_first_contact"
                                                    );

                                            QuestStatus status =
                                                    QuestManager.getStatus(data, questId);

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
                                                            "§eÉtat : §f" + status.name()
                                                    )
                                            );

                                            return 1;
                                        })
                        )
                        .then(
                                Commands.literal("list")
                                        .executes(context -> {

                                            context.getSource().sendSystemMessage(
                                                    Component.literal(
                                                            "§6=== Quêtes enregistrées ==="
                                                    )
                                            );

                                            for (Quest quest : QuestRegistry.getAll()) {
                                                context.getSource().sendSystemMessage(
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
        );
    }
}
