package com.magius.world.mod.clan.client.command;

import com.magius.world.mod.clan.client.screen.ClanDialogueScreen;
import com.magius.world.mod.clan.client.screen.ClanMainScreen;
import com.magius.world.mod.clan.dialogue.DialogueEmotion;
import com.magius.world.mod.clan.manager.ClanManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.magius.world.mod.clan.dialogue.DialogueData;
import net.minecraft.network.chat.Component;
import java.util.List;
import com.magius.world.mod.clan.dialogue.DialogueOption;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.dialogue.action.StartQuestAction;
import net.minecraft.resources.ResourceLocation;



public final class ClanClientCommand {

    private ClanClientCommand() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        DialogueData dialogue4 = new DialogueData(
                Component.literal("Dragonmaid"),
                Component.literal("Érudite"),
                Component.literal("Acceptes-tu de rejoindre notre ordre ?"),
                DialogueEmotion.SURPRISED,
                List.of(
                        new DialogueOption(
                                Component.literal("Accepter"),
                                null,
                                new StartQuestAction(
                                        ResourceLocation.fromNamespaceAndPath(
                                                MagiusWorldMod.MOD_ID,
                                                "dragonmaid_first_contact"
                                        )
                                )
                        ),
                        new DialogueOption(
                                Component.literal("Refuser"),
                                null,
                                player -> Minecraft.getInstance().setScreen(null)
                        )
                )
        );
        DialogueData dialogue3 = new DialogueData(
                Component.literal("Dragonmaid"),
                Component.literal("Érudite"),
                Component.literal("Depuis des siècles nous attendions ton arrivée."),
                DialogueEmotion.NORMAL,
                List.of(
                        new DialogueOption(
                                Component.literal("Continuer"),
                                dialogue4,
                                player -> {}
                        )
                )
        );
        DialogueData dialogue2 = new DialogueData(
                Component.literal("Dragonmaid"),
                Component.literal("Érudite"),
                Component.literal("Nous sommes les gardiennes d'un ancien héritage draconique."),
                DialogueEmotion.HAPPY,
                List.of(
                        new DialogueOption(
                                Component.literal("Pourquoi moi ?"),
                                dialogue3,
                                player -> {}
                        )
                )
        );
        DialogueData dialogue1 = new DialogueData(
                Component.literal("Dragonmaid"),
                Component.literal("Érudite"),
                Component.literal("Bienvenue, voyageur."),
                DialogueEmotion.NORMAL,
                List.of(
                        new DialogueOption(
                                Component.literal("Qui êtes-vous ?"),
                                dialogue2,
                                player -> {}
                        )
                )
        );
        dispatcher.register(
                Commands.literal("dragonmaid")
                        .executes(context -> {
                            DialogueData whoAreYouDialogue = new DialogueData(
                                    Component.literal("Dragonmaid"),
                                    Component.literal("Érudite"),
                                    Component.literal(
                                            "Nous sommes les gardiennes d'un ancien héritage draconique."
                                    ),
                                    DialogueEmotion.NORMAL,
                                    List.of(
                                            new DialogueOption(
                                                    Component.literal("Retour"),
                                                    null,
                                                    player -> Minecraft.getInstance().setScreen(null)
                                            )
                                    )
                            );
//                            Minecraft.getInstance().setScreen(
//                                    new ClanDialogueScreen(dialogue1)
//                            );

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

                            var clanData =
                                    clanDataOptional.get();

                            ResourceLocation activeClanId =
                                    clanData.getActiveClanId();

                            if (activeClanId == null) {

                                minecraft.player.sendSystemMessage(
                                        Component.literal(
                                                "§7Aucun clan actif."
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


                        })
        );
    }
}