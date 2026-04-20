package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionMerchantEvents {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        Entity target = event.getTarget();
        String merchantKey = getRubyMerchantKey(target);

        if (merchantKey == null) {
            return;
        }

        // Objectif 1 : Réseau Écarlate
        FactionObjectiveManager.addUniqueProgress(
                player,
                FactionObjectiveRegistry.MEET_ALL_RUBY_MERCHANTS,
                merchantKey
        );

        // Objectifs 2 et 3 : version simple basée sur l'interaction
        FactionObjectiveManager.completeObjective(
                player,
                FactionObjectiveRegistry.TRADE_WITH_RUBY_MERCHANT
        );

        FactionObjectiveManager.addProgress(
                player,
                FactionObjectiveRegistry.TRADE_10_RUBY_MERCHANTS,
                1
        );

        // Objectifs 4, 5, 6 : niveau max
        if (target instanceof Villager villager) {
            int level = villager.getVillagerData().getLevel();

            if (level >= 5) {
                switch (merchantKey) {
                    case "ruby_keeper" -> FactionObjectiveManager.completeObjective(
                            player,
                            FactionObjectiveRegistry.MAX_RUBY_KEEPER_LEVEL
                    );
                    case "corrupted_priest" -> FactionObjectiveManager.completeObjective(
                            player,
                            FactionObjectiveRegistry.MAX_CORRUPTED_PRIEST_LEVEL
                    );
                    case "ruby_scholar" -> FactionObjectiveManager.completeObjective(
                            player,
                            FactionObjectiveRegistry.MAX_RUBY_SCHOLAR_LEVEL
                    );
                }
            }
        }
    }
    private static String getRubyMerchantKey(Entity entity) {
        String path = entity.getType().builtInRegistryHolder().key().location().getPath();

        if (path.equals("ruby_keeper")) {
            return "ruby_keeper";
        }
        if (path.equals("corrupted_priest")) {
            return "corrupted_priest";
        }
        if (path.equals("ruby_scholar")) {
            return "ruby_scholar";
        }

        return null;
    }
}