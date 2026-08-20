package com.magius.world.mod.clan.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class SwordsoulSoulEvents {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private SwordsoulSoulEvents() {
    }

    @SubscribeEvent
    public static void onLivingDeath(
            LivingDeathEvent event
    ) {

        /*
         * Seuls les monstres hostiles donnent des Âmes.
         */
        if (!(event.getEntity() instanceof Monster)) {
            return;
        }

        /*
         * Le coup fatal doit provenir directement
         * d'un joueur.
         */
        if (!(event.getSource().getEntity()
                instanceof ServerPlayer player)) {
            return;
        }

        ClanManager.get(player)
                .ifPresent(data -> {

                    /*
                     * Swordsoul doit être le clan actif.
                     */
                    if (!SWORDSOUL_ID.equals(
                            data.getActiveClanId()
                    )) {
                        return;
                    }

                    /*
                     * +1 Âme Swordsoul.
                     */
                    data.addClanCurrency(
                            SWORDSOUL_ID,
                            1
                    );

                    ClanSyncManager.sync(
                            player
                    );
                });
    }
}
