package com.magius.world.mod.clan.quest.dragonmaid;

import com.magius.world.mod.clan.client.screen.NurseDragonmaidDialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class NurseDragonmaidNpcEvents {

    private static final String NURSE_TAG =
            "nurse_dragonmaid_npc";

    private NurseDragonmaidNpcEvents() {
    }

    @Mod.EventBusSubscriber(
            value = Dist.CLIENT
    )
    public static final class ClientEvents {

        private ClientEvents() {
        }

        @SubscribeEvent
        public static void onInteract(
                PlayerInteractEvent.EntityInteract event
        ) {

            if (!(event.getTarget() instanceof Villager villager)) {
                return;
            }

            if (!villager.getTags().contains(NURSE_TAG)) {
                return;
            }

            /*
             * Empêche l'ouverture du commerce vanilla.
             */
            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            Minecraft minecraft =
                    Minecraft.getInstance();

            /*
             * Important :
             * on diffère l'ouverture de l'écran sur le
             * thread client principal.
             */
            minecraft.execute(() -> {

                minecraft.setScreen(
                        new NurseDragonmaidDialogueScreen()
                );
            });
        }
    }
}