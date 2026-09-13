package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.client.screen.DragonmaidDialogueScreen;
import com.magius.world.mod.clan.client.screen.NurseDragonmaidDialogueScreen;
import com.magius.world.mod.clan.client.screen.SwordsoulDialogueScreen;
import com.magius.world.mod.clan.client.screen.TinkhecDialogueScreen;
import com.magius.world.mod.clan.client.screen.UnchainedDialogueScreen;
import com.magius.world.mod.clan.client.screen.UnchainedSealKeeperDialogueScreen;
import com.magius.world.mod.clan.client.screen.UnchainedTwinMerchantScreen;
import com.magius.world.mod.entity.dragonmaid.DragonmaidEmissaryEntity;
import com.magius.world.mod.entity.dragonmaid.DragonmaidNurseEntity;
import com.magius.world.mod.entity.dragonmaid.DragonmaidTinkhecEntity;
import com.magius.world.mod.entity.swordsoul.SwordsoulEmissaryEntity;
import com.magius.world.mod.entity.unchained.UnchainedEmissaryEntity;
import com.magius.world.mod.entity.unchained.UnchainedAruhaEntity;
import com.magius.world.mod.entity.unchained.UnchainedRakeaEntity;
import com.magius.world.mod.entity.unchained.UnchainedSealKeeperEntity;
import com.magius.world.mod.network.packet.C2SBuyUnchainedSealPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        value = Dist.CLIENT
)
public final class DragonmaidClientInteractionEvents {

    private DragonmaidClientInteractionEvents() {
    }

    @SubscribeEvent
    public static void onEntityInteract(
            PlayerInteractEvent.EntityInteract event
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();

        if (event.getTarget() instanceof DragonmaidEmissaryEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new DragonmaidDialogueScreen()
                    )
            );

            return;
        }
        if (event.getTarget() instanceof SwordsoulEmissaryEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new SwordsoulDialogueScreen()
                    )
            );

            return;
        }

        if (event.getTarget() instanceof UnchainedEmissaryEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new UnchainedDialogueScreen()
                    )
            );

            return;
        }

        if (event.getTarget() instanceof UnchainedSealKeeperEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new UnchainedSealKeeperDialogueScreen()
                    )
            );

            return;
        }

        if (event.getTarget() instanceof UnchainedAruhaEntity aruha) {

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            minecraft.execute(() -> minecraft.setScreen(
                    new UnchainedTwinMerchantScreen(
                            aruha.getId(),
                            C2SBuyUnchainedSealPacket.SealTrade.ARUHA
                    )
            ));

            return;
        }

        if (event.getTarget() instanceof UnchainedRakeaEntity rakea) {

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);

            minecraft.execute(() -> minecraft.setScreen(
                    new UnchainedTwinMerchantScreen(
                            rakea.getId(),
                            C2SBuyUnchainedSealPacket.SealTrade.RAKEA
                    )
            ));

            return;
        }

        if (event.getTarget() instanceof DragonmaidNurseEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new NurseDragonmaidDialogueScreen()
                    )
            );

            return;
        }

        if (event.getTarget() instanceof DragonmaidTinkhecEntity) {

            event.setCanceled(true);

            event.setCancellationResult(
                    InteractionResult.SUCCESS
            );

            minecraft.execute(() ->
                    minecraft.setScreen(
                            new TinkhecDialogueScreen()
                    )
            );
        }
    }
}
