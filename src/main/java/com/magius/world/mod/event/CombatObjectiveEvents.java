package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.RubyBoarEntity;
import com.magius.world.mod.entity.custom.RubySheepEntity;
import com.magius.world.mod.entity.custom.RubyWispEntity;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.item.ModItems;

import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.*;



@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class CombatObjectiveEvents {
    private static final Map<UUID, Set<String>> KILLED_RUBY_TYPES = new HashMap<>();

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {

        LivingEntity deadEntity = event.getEntity();


        if (!(deadEntity.getKillCredit() instanceof ServerPlayer player)) {
            return;
        }

        // =========================
        // OBJECTIF 4
        // Lame consacrée
        // =========================

        ItemStack weapon = player.getMainHandItem();

        if (weapon.is(ModItems.RUBIS_SWORD.get())) {

            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.KILL_15_WITH_RUBY_WEAPON,
                    1
            );
        }

        // =========================
        // OBJECTIF 5
        // Chasseur de Wisps
        // =========================

        if (deadEntity instanceof RubyWispEntity) {

            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.KILL_10_RUBY_WISPS,
                    1
            );
        }
        trackRubyMobTypes(player, deadEntity);
    }
    private static void trackRubyMobTypes(ServerPlayer player, LivingEntity deadEntity) {

        String mobType = null;

        if (deadEntity instanceof RubyWispEntity) {
            mobType = "ruby_wisp";
        }

        else if (deadEntity instanceof RubySheepEntity) {
            mobType = "ruby_sheep";
        }

        else if (deadEntity instanceof RubyBoarEntity) {
            mobType = "ruby_bolt";
        }

        if (mobType == null) {
            return;
        }

        Set<String> killedTypes =
                KILLED_RUBY_TYPES.computeIfAbsent(
                        player.getUUID(),
                        uuid -> new HashSet<>()
                );

        if (killedTypes.add(mobType)) {

            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.KILL_3_RUBY_MOB_TYPES,
                    1
            );
        }
    }
}