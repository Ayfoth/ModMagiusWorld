package com.magius.world.mod.clan.command;

import com.magius.world.mod.clan.api.Clan;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanRegistry;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.arguments.StringArgumentType;

public class ClanCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("clan")

                        .then(
                                Commands.literal("prestige")
                                        .requires(source -> source.hasPermission(2))
                                        .then(
                                                Commands.literal("add")
                                                        .then(
                                                                Commands.argument(
                                                                                "amount",
                                                                                IntegerArgumentType.integer(1)
                                                                        )
                                                                        .executes(context -> {

                                                                            ServerPlayer player =
                                                                                    context.getSource()
                                                                                            .getPlayerOrException();

                                                                            var optionalData =
                                                                                    ClanManager.get(player).resolve();

                                                                            if (optionalData.isEmpty()) {
                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§cImpossible de charger les données de clan."
                                                                                        )
                                                                                );
                                                                                return 0;
                                                                            }

                                                                            var data = optionalData.get();

                                                                            if (!data.hasClan()) {
                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§cTu dois appartenir à un clan."
                                                                                        )
                                                                                );
                                                                                return 0;
                                                                            }

                                                                            int amount =
                                                                                    IntegerArgumentType.getInteger(
                                                                                            context,
                                                                                            "amount"
                                                                                    );

                                                                            int oldRank = data.getRank();

                                                                            ClanManager.addPrestige(
                                                                                    data,
                                                                                    amount
                                                                            );

                                                                            var clanOptional =
                                                                                    ClanManager.getClan(data);

                                                                            if (clanOptional.isEmpty()) {
                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§cClan introuvable."
                                                                                        )
                                                                                );
                                                                                return 0;
                                                                            }

                                                                            Clan clan = clanOptional.get();

                                                                            player.sendSystemMessage(
                                                                                    Component.literal(
                                                                                            "§a+" + amount
                                                                                                    + " prestige"
                                                                                    )
                                                                            );

                                                                            player.sendSystemMessage(
                                                                                    Component.literal(
                                                                                            "§ePrestige total : §f"
                                                                                                    + data.getPrestige()
                                                                                    )
                                                                            );

                                                                            if (data.getRank() > oldRank) {
                                                                                String rankName =
                                                                                        clan.getRanks()
                                                                                                .get(data.getRank())
                                                                                                .getName();

                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§6========================"
                                                                                        )
                                                                                );

                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§aPromotion ! Nouveau rang : §e"
                                                                                                        + rankName
                                                                                        )
                                                                                );

                                                                                player.sendSystemMessage(
                                                                                        Component.literal(
                                                                                                "§6========================"
                                                                                        )
                                                                                );
                                                                            }

                                                                            return 1;
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                Commands.literal("join")
                                        .then(
                                                Commands.argument("clan", StringArgumentType.word())
                                                        .suggests((context, builder) -> {
                                                            for (Clan clan : ClanRegistry.getAll()) {
                                                                builder.suggest(clan.getId().getPath());
                                                            }

                                                            return builder.buildFuture();
                                                        })
                                                        .executes(context -> {

                                                            ServerPlayer player =
                                                                    context.getSource().getPlayerOrException();

                                                            String clanName = StringArgumentType.getString(
                                                                    context,
                                                                    "clan"
                                                            );

                                                            var optionalData = ClanManager.get(player).resolve();

                                                            if (optionalData.isEmpty()) {
                                                                player.sendSystemMessage(
                                                                        Component.literal(
                                                                                "§cImpossible de charger les données de clan."
                                                                        )
                                                                );
                                                                return 0;
                                                            }

                                                            var data = optionalData.get();

                                                            if (data.hasClan()) {
                                                                player.sendSystemMessage(
                                                                        Component.literal(
                                                                                "§cTu appartiens déjà à un clan."
                                                                        )
                                                                );
                                                                return 0;
                                                            }

                                                            var clanOptional = ClanRegistry.get(clanName);

                                                            if (clanOptional.isEmpty()) {
                                                                player.sendSystemMessage(
                                                                        Component.literal(
                                                                                "§cClan inconnu : " + clanName
                                                                        )
                                                                );
                                                                return 0;
                                                            }

                                                            Clan clan = clanOptional.get();

                                                            boolean joined = ClanManager.joinClan(
                                                                    data,
                                                                    clanName
                                                            );

                                                            if (!joined) {
                                                                player.sendSystemMessage(
                                                                        Component.literal(
                                                                                "§cImpossible de rejoindre ce clan."
                                                                        )
                                                                );
                                                                return 0;
                                                            }

                                                            player.sendSystemMessage(
                                                                    Component.literal(
                                                                            "§6=========================="
                                                                    )
                                                            );

                                                            player.sendSystemMessage(
                                                                    Component.literal(
                                                                            "§aTu as rejoint le clan §e"
                                                                                    + clan.getName()
                                                                                    + "§a !"
                                                                    )
                                                            );

                                                            player.sendSystemMessage(
                                                                    Component.literal(
                                                                            "§7Bienvenue parmi ses membres."
                                                                    )
                                                            );

                                                            player.sendSystemMessage(
                                                                    Component.literal(
                                                                            "§6=========================="
                                                                    )
                                                            );

                                                            return 1;
                                                        })
                                        )
                        )
                        .then(
                                Commands.literal("leave")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayerOrException();

                                            var optionalData = ClanManager.get(player).resolve();

                                            if (optionalData.isEmpty()) {
                                                player.sendSystemMessage(
                                                        Component.literal("§cImpossible de charger les données de clan.")
                                                );
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            if (!data.hasClan()) {
                                                player.sendSystemMessage(
                                                        Component.literal("§7Tu n'appartiens à aucun clan.")
                                                );
                                                return 0;
                                            }

                                            String clanName = ClanManager.getClan(data)
                                                    .map(Clan::getName)
                                                    .orElse("Inconnu");

                                            ClanManager.leaveClan(data);

                                            player.sendSystemMessage(
                                                    Component.literal("§6========================")
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§cTu as quitté le clan §f" + clanName)
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§6========================")
                                            );

                                            return 1;
                                        })
                        )
                        .then(
                                Commands.literal("list")
                                        .executes(context -> {

                                            context.getSource().sendSystemMessage(
                                                    Component.literal("§6=== Clans enregistrés ===")
                                            );

                                            for (Clan clan : ClanRegistry.getAll()) {
                                                context.getSource().sendSystemMessage(
                                                        Component.literal("§e- " + clan.getName())
                                                );
                                            }

                                            return 1;
                                        })
                        )

                        .then(
                                Commands.literal("info")
                                        .executes(context -> {

                                            ServerPlayer player =
                                                    context.getSource().getPlayerOrException();

                                            var optionalData = ClanManager.get(player).resolve();

                                            if (optionalData.isEmpty()) {
                                                player.sendSystemMessage(
                                                        Component.literal("§cImpossible de charger les données de clan.")
                                                );
                                                return 0;
                                            }

                                            var data = optionalData.get();

                                            if (!data.hasClan()) {
                                                player.sendSystemMessage(
                                                        Component.literal("§7Tu n'appartiens à aucun clan.")
                                                );
                                                return 1;
                                            }

                                            var clanOptional = ClanManager.getClan(data);

                                            if (clanOptional.isEmpty()) {
                                                player.sendSystemMessage(
                                                        Component.literal("§cClan introuvable.")
                                                );
                                                return 0;
                                            }

                                            Clan clan = clanOptional.get();

                                            String rankName = "Inconnu";

                                            if (data.getRank() >= 0 && data.getRank() < clan.getRanks().size()) {
                                                rankName = clan.getRanks().get(data.getRank()).getName();
                                            }

                                            player.sendSystemMessage(
                                                    Component.literal("§6========================")
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§eClan : §f" + clan.getName())
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§eRang : §f" + rankName)
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§ePrestige : §f" + data.getPrestige())
                                            );

                                            player.sendSystemMessage(
                                                    Component.literal("§6========================")
                                            );

                                            return 1;


                                        })
                        )
        );
    }
}