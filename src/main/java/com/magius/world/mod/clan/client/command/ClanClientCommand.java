package com.magius.world.mod.clan.client.command;

import com.magius.world.mod.clan.client.screen.ClanMainScreen;
import com.magius.world.mod.clan.manager.ClanManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class ClanClientCommand {

    private ClanClientCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        /*
         * Commande principale générique.
         */
        var clanCommand =
                dispatcher.register(
                        Commands.literal("clan")
                                .executes(context ->
                                        openActiveClanDashboard()
                                )
                );

        /*
         * Ancien nom conservé comme alias afin de ne pas
         * casser les habitudes des joueurs alpha.
         *
         * /dragonmaid effectue désormais exactement
         * la même action que /clan.
         */
        dispatcher.register(
                Commands.literal("dragonmaid")
                        .redirect(clanCommand)
        );
    }

    private static int openActiveClanDashboard() {
        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player == null) {
            return 0;
        }

        var clanDataOptional =
                ClanManager.get(
                        minecraft.player
                ).resolve();

        if (clanDataOptional.isEmpty()) {
            minecraft.player.sendSystemMessage(
                    Component.literal(
                            "§cImpossible de charger les données de clan."
                    )
            );
            return 0;
        }

        ResourceLocation activeClanId =
                clanDataOptional.get()
                        .getActiveClanId();

        if (activeClanId == null) {
            minecraft.player.sendSystemMessage(
                    Component.literal(
                            "§7Vous n'avez aucun clan actif."
                    )
            );
            return 0;
        }

        minecraft.setScreen(
                new ClanMainScreen(
                        activeClanId
                )
        );

        return 1;
    }
}