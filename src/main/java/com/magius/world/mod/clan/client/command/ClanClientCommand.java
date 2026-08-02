package com.magius.world.mod.clan.client.command;

import com.magius.world.mod.clan.client.screen.ClanDialogueScreen;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.magius.world.mod.clan.dialogue.DialogueData;
import net.minecraft.network.chat.Component;
import java.util.List;
import com.magius.world.mod.clan.dialogue.DialogueOption;



public final class ClanClientCommand {

    private ClanClientCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        dispatcher.register(
                Commands.literal("dragonmaid")
                        .executes(context -> {
                            DialogueData whoAreYouDialogue = new DialogueData(
                                    Component.literal("Dragonmaid"),
                                    Component.literal("Érudite"),
                                    Component.literal(
                                            "Nous sommes les gardiennes d'un ancien héritage draconique."
                                    ),
                                    List.of(
                                            new DialogueOption(
                                                    Component.literal("Retour"),
                                                    null,
                                                    () -> Minecraft.getInstance().setScreen(null)
                                            )
                                    )
                            );
                            Minecraft.getInstance().setScreen(
                                    new ClanDialogueScreen(
                                            new DialogueData(
                                                    Component.literal("Dragonmaid"),
                                                    Component.literal("Érudite"),
                                                    Component.literal("Bienvenue..."),
                                                    List.of(
                                                            new DialogueOption(
                                                                    Component.literal("Qui êtes-vous ?"),
                                                                    whoAreYouDialogue,
                                                                    null
                                                            ),
                                                            new DialogueOption(
                                                                    Component.literal("Accepter"),
                                                                    null,
                                                                    () -> {
                                                                        if (Minecraft.getInstance().player != null) {
                                                                            Minecraft.getInstance().player.sendSystemMessage(
                                                                                    Component.literal("Quête acceptée.")
                                                                            );
                                                                        }
                                                                    }
                                                            ),
                                                            new DialogueOption(
                                                                    Component.literal("Refuser"),
                                                                    null,
                                                                    () -> Minecraft.getInstance().setScreen(null)
                                                            )
                                                    )
                                            )
                                    )
                            );

                            return 1;
                        })
        );
    }
}