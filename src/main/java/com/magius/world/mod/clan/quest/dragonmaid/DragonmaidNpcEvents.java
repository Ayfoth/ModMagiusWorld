package com.magius.world.mod.clan.quest.dragonmaid;

import com.magius.world.mod.clan.client.screen.DragonmaidDialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class DragonmaidNpcEvents {

    private static final String DRAGONMAID_TAG =
            "dragonmaid_npc";

    private DragonmaidNpcEvents() {
    }

    @Mod.EventBusSubscriber(
            value = Dist.CLIENT
    )
    public static final class ClientEvents {

        private ClientEvents() {
        }

        @SubscribeEvent
        public static void onEntityInteract(
                PlayerInteractEvent.EntityInteract event
        ) {

            if (!(event.getTarget() instanceof Villager villager)) {
                return;
            }

            if (!villager.getTags().contains(DRAGONMAID_TAG)) {
                return;
            }

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            Minecraft minecraft =
                    Minecraft.getInstance();

            /*
             * IMPORTANT :
             *
             * On ne crée pas/changement pas l'écran directement
             * depuis l'événement d'interaction.
             *
             * execute() le programme sur le thread client
             * principal, après l'événement courant.
             */
            minecraft.execute(() -> {

                minecraft.setScreen(
                        new DragonmaidDialogueScreen()
                );
            });
        }
    }
}