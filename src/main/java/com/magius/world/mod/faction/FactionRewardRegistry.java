package com.magius.world.mod.faction;

import com.magius.world.mod.faction.reward.EffectReward;
import com.magius.world.mod.faction.reward.ItemReward;
import com.magius.world.mod.faction.reward.ObjectiveReward;
import com.magius.world.mod.item.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FactionRewardRegistry {

    private static final Map<String, ObjectiveReward> REWARDS = new HashMap<>();

    static {
        // =========================
        // EXPLORATION
        // =========================
        REWARDS.put(FactionObjectiveRegistry.DISCOVER_RUBY_BIOME,
                ObjectiveReward.builder()
                        .xp(250)
                        .item(ModItems.RUBIS.get(), 3)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.EXPLORE_RUBY_ZONES,
                ObjectiveReward.builder()
                        .xp(600)
                        .item(ModItems.RUBIS.get(), 2)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.DISCOVER_RUBY_ALTAR,
                ObjectiveReward.builder()
                        .xp(800)
                        .item(ModItems.CORRUPTED_RUBY.get(), 1)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.DISCOVER_RUBY_HAMLET,
                ObjectiveReward.builder()
                        .xp(900)
                        .item(ModItems.RUBIS.get(), 4)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.EXPLORE_RUBY_DEPTHS,
                ObjectiveReward.builder()
                        .xp(700)
                        .item(ModItems.RUBY_KEY.get(), 5)
                        .effect(MobEffects.DIG_SPEED, 20 * 30, 0)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.REACH_RUBY_BIOME_CENTER,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.RUBIS.get(), 6)
                        .build()
        );

        // =========================
        // COMBAT
        // =========================
        REWARDS.put(FactionObjectiveRegistry.KILL_1_RUBY_BIOME,
                ObjectiveReward.builder()
                        .xp(400)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.KILL_25_RUBY_BIOME,
                ObjectiveReward.builder()
                        .xp(800)
                        .effect(MobEffects.DAMAGE_BOOST, 20 * 20, 0)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.KILL_100_RUBY_BIOME,
                ObjectiveReward.builder()
                        .xp(1500)
                        .item(ModItems.RUBIS.get(), 8)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.KILL_15_WITH_RUBY_WEAPON,
                ObjectiveReward.builder()
                        .xp(900)
                        .item(ModItems.RUBIS.get(), 4)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.KILL_10_RUBY_WISPS,
                ObjectiveReward.builder()
                        .xp(1100)
                        .item(ModItems.CORRUPTED_RUBY.get(), 3)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.KILL_3_RUBY_MOB_TYPES,
                ObjectiveReward.builder()
                        .xp(900)
                        .effect(MobEffects.MOVEMENT_SPEED, 20 * 20, 0)
                        .build()
        );

        // =========================
        // RÉCOLTE
        // =========================
        REWARDS.put(FactionObjectiveRegistry.MINE_1_RUBY_ORE,
                ObjectiveReward.builder()
                        .xp(300)
                        .item(ModItems.RUBIS.get(), 2)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.MINE_32_RUBY_ORE,
                ObjectiveReward.builder()
                        .xp(700)
                        .item(ModItems.RUBIS.get(), 6)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.MINE_128_RUBY_ORE,
                ObjectiveReward.builder()
                        .xp(1600)
                        .item(ModItems.RUBIS.get(), 12)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.HARVEST_16_RUBY_PLANTS,
                ObjectiveReward.builder()
                        .xp(500)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.HARVEST_32_RED_WHEAT,
                ObjectiveReward.builder()
                        .xp(700)
                        .item(ModItems.RED_WHEAT.get(), 4)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.MINE_DEEPSLATE_RUBIS,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.CORRUPTED_RUBY.get(), 2)
                        .build()
        );

        // =========================
        // ARTISANAT
        // =========================
        REWARDS.put(FactionObjectiveRegistry.CRAFT_RUBY_BLOCK,
                ObjectiveReward.builder()
                        .xp(600)
                        .item(ModItems.RUBIS.get(), 4)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.CRAFT_RUBY_SWORD,
                ObjectiveReward.builder()
                        .xp(450)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.CRAFT_FULL_RUBY_ARMOR,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.RUBY_HORSE_ARMOR.get(), 6)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.OBTAIN_RUBY_RELIC,
                ObjectiveReward.builder()
                        .xp(1500)
                        .item(ModItems.CORRUPTED_RUBY.get(), 2)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.CRAFT_RUBY_PICKAXE,
                ObjectiveReward.builder()
                        .xp(500)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.CRAFT_ALL_RUBY_TOOLS,
                ObjectiveReward.builder()
                        .xp(1500)
                        .item(ModItems.RUBIS.get(), 4)
                        .build()
        );

        // =========================
        // COMMERCE
        // =========================


        REWARDS.put(FactionObjectiveRegistry.MEET_ALL_RUBY_MERCHANTS,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.SCARLET_NETWORK_CONTRACT.get(),  4)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.TRADE_WITH_RUBY_MERCHANT,
                ObjectiveReward.builder()
                        .xp(500)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.TRADE_10_RUBY_MERCHANTS,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.RUBIS.get(), 6)
                        .build()
        );
        REWARDS.put(FactionObjectiveRegistry.MAX_RUBY_KEEPER_LEVEL,
                ObjectiveReward.builder()
                        .xp(1000)
                        .item(ModItems.RUBIS.get(), 3)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.MAX_CORRUPTED_PRIEST_LEVEL,
                ObjectiveReward.builder()
                        .xp(1000)
                        .item(ModItems.CORRUPTED_RUBY.get(), 2)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.MAX_RUBY_SCHOLAR_LEVEL,
                ObjectiveReward.builder()
                        .xp(1000)
                        .item(ModItems.RUBIS.get(), 2)
                        .build()
        );

        // =========================
        // RÉCOMPENSES DE CATÉGORIE
        // =========================
        REWARDS.put(FactionObjectiveRegistry.REWARD_EXPLORATION_RUBY,
                ObjectiveReward.builder()
                        .xp(1000)
                        .item(ModItems.RUBY_LOCATOR.get(), 1)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.REWARD_COMBAT_RUBY,
                ObjectiveReward.builder()
                        .xp(1200)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.REWARD_GATHERING_RUBY,
                ObjectiveReward.builder()
                        .xp(1000)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.REWARD_CRAFTING_RUBY,
                ObjectiveReward.builder()
                        .xp(1200)
                        .item(ModItems.RUBY_FIRE_CORE_PLAN.get(), 1)
                        .item(ModItems.RUBY_WAND_PLAN.get(), 1)
                        .build()
        );

        REWARDS.put(FactionObjectiveRegistry.REWARD_COMMERCE_RUBY,
                ObjectiveReward.builder()
                        .xp(1000)
                        .item(ModItems.SCARLET_NETWORK_CONTRACT.get(), 1)
                        .build()
        );

        // =========================
        // RÉCOMPENSE FINALE
        // =========================
        REWARDS.put(FactionObjectiveRegistry.RUBY_MASTERY,
                ObjectiveReward.builder()
                        .xp(3000)
                        .item(ModItems.RED_KEY.get(), 1)
                        .build()
        );
    }

    private FactionRewardRegistry() {}

    public static void grantReward(ServerPlayer player, String objectiveId) {
        ObjectiveReward reward = REWARDS.get(objectiveId);
        if (reward == null) {
            return;
        }

        if (reward.getExperience() > 0) {
            player.giveExperiencePoints(reward.getExperience());
        }

        for (ItemReward itemReward : reward.getItems()) {
            player.getInventory().placeItemBackInInventory(
                    new ItemStack(itemReward.getItem(), itemReward.getCount())
            );
        }

        for (EffectReward effectReward : reward.getEffects()) {
            player.addEffect(new MobEffectInstance(
                    effectReward.getEffect(),
                    effectReward.getDurationTicks(),
                    effectReward.getAmplifier()
            ));
        }
    }

    public static String getDisplayName(String objectiveId) {
        FactionObjectiveDefinition def = FactionObjectiveRegistry.get(objectiveId);
        return def != null ? def.getDisplayName() : objectiveId;
    }
}