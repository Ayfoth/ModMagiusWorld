package com.magius.world.mod.clan.quest.swordsoul;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class SwordsoulSpiritTokenDropEvents {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    /*
     * 10 % par créature hostile.
     * En moyenne, environ 40 monstres pour obtenir
     * les quatre Jetons II nécessaires à la quête.
     */
    private static final float DROP_CHANCE = 0.10F;

    private SwordsoulSpiritTokenDropEvents() {
    }

    @SubscribeEvent
    public static void onLivingDrops(
            LivingDropsEvent event
    ) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        /*
         * Uniquement les créatures hostiles.
         */
        if (!(event.getEntity() instanceof Enemy)) {
            return;
        }

        /*
         * Le coup fatal doit provenir d'un joueur.
         */
        if (!(event.getSource().getEntity()
                instanceof ServerPlayer player)) {
            return;
        }

        ClanManager.get(player)
                .ifPresent(clanData -> {

                    /*
                     * Le joueur doit avoir Swordsoul
                     * comme clan actif.
                     */
                    if (!SWORDSOUL_ID.equals(
                            clanData.getActiveClanId()
                    )) {
                        return;
                    }

                    if (player.getRandom().nextFloat()
                            >= DROP_CHANCE) {
                        return;
                    }

                    ItemStack token =
                            new ItemStack(
                                    ModItems.SWORDSOUL_SPIRIT_TOKEN_II.get()
                            );

                    ItemEntity droppedToken =
                            new ItemEntity(
                                    event.getEntity().level(),
                                    event.getEntity().getX(),
                                    event.getEntity().getY(),
                                    event.getEntity().getZ(),
                                    token
                            );

                    event.getDrops().add(
                            droppedToken
                    );
                });
    }
}