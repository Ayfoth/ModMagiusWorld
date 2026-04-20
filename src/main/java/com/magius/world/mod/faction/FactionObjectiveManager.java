package com.magius.world.mod.faction;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.Set;

public class FactionObjectiveManager {

    private FactionObjectiveManager() {}

    public static void handleObjective(ServerPlayer player, String objectiveId) {
        String factionId = getPlayerFactionId(player);
        if (factionId == null) return;

        FactionProgressData data = FactionProgressData.get(player.serverLevel());

        if (!data.isObjectiveCompleted(factionId, objectiveId)) {
            data.completeObjective(factionId, objectiveId);
            notifyFactionCompletion(player, factionId, objectiveId);
        }

        tryGrantReward(player, factionId, objectiveId, data);
    }

    public static void grantPendingRewardsOnLogin(ServerPlayer player) {
        String factionId = getPlayerFactionId(player);
        if (factionId == null) return;

        FactionProgressData data = FactionProgressData.get(player.serverLevel());
        Set<String> completed = data.getCompletedObjectives(factionId);

        for (String objectiveId : completed) {
            tryGrantReward(player, factionId, objectiveId, data);
        }
    }

    private static void tryGrantReward(ServerPlayer player, String factionId, String objectiveId, FactionProgressData data) {
        if (data.hasClaimedReward(factionId, objectiveId, player.getUUID())) return;

        FactionRewardRegistry.grantReward(player, objectiveId);
        data.markRewardClaimed(factionId, objectiveId, player.getUUID());

        player.sendSystemMessage(
                Component.literal("✔ Récompense obtenue : ")
                        .append(Component.literal(FactionRewardRegistry.getDisplayName(objectiveId)))
                        .withStyle(ChatFormatting.GOLD)
        );
    }

    private static void notifyFactionCompletion(ServerPlayer source, String factionId, String objectiveId) {
        Component message = Component.literal("★ Objectif de faction accompli : ")
                .append(Component.literal(FactionRewardRegistry.getDisplayName(objectiveId)))
                .withStyle(ChatFormatting.LIGHT_PURPLE);

        for (ServerPlayer player : source.server.getPlayerList().getPlayers()) {
            String team = getPlayerFactionId(player);
            if (factionId.equals(team)) {
                player.sendSystemMessage(message);
            }
        }
    }
    public static void addProgress(ServerPlayer player, String objectiveId, int amount) {
        String factionId = getPlayerFactionId(player);
        if (factionId == null) return;

        FactionObjectiveDefinition def = FactionObjectiveRegistry.get(objectiveId);
        if (def == null) return;

        FactionProgressData data = FactionProgressData.get(player.serverLevel());

        if (!isObjectiveUnlocked(data, factionId, def)) {
            return;
        }

        if (data.isObjectiveCompleted(factionId, objectiveId)) {
            tryGrantReward(player, factionId, objectiveId, data);
            return;
        }

        int current = data.addObjectiveProgress(factionId, objectiveId, amount);

        if (current >= def.getTargetValue()) {
            data.completeObjective(factionId, objectiveId);
            notifyFactionCompletion(player, factionId, objectiveId);
            checkCategoryAndFinalRewards(player, factionId, data);
        }

        tryGrantReward(player, factionId, objectiveId, data);
    }
    private static boolean isObjectiveUnlocked(FactionProgressData data, String factionId, FactionObjectiveDefinition def) {
        String parentId = def.getParentObjectiveId();
        return parentId == null || data.isObjectiveCompleted(factionId, parentId);
    }

    public static String getPlayerFactionId(ServerPlayer player) {
        var team = player.getTeam();
        return team != null ? team.getName() : null;
    }
    private static void checkCategoryAndFinalRewards(ServerPlayer player, String factionId, FactionProgressData data) {
        tryCompleteCategoryReward(player, factionId, "Exploration", FactionObjectiveRegistry.REWARD_EXPLORATION_RUBY, data);
        tryCompleteCategoryReward(player, factionId, "Combat", FactionObjectiveRegistry.REWARD_COMBAT_RUBY, data);
        tryCompleteCategoryReward(player, factionId, "Récolte", FactionObjectiveRegistry.REWARD_GATHERING_RUBY, data);
        tryCompleteCategoryReward(player, factionId, "Artisanat", FactionObjectiveRegistry.REWARD_CRAFTING_RUBY, data);
        tryCompleteCategoryReward(player, factionId, "Commerce", FactionObjectiveRegistry.REWARD_COMMERCE_RUBY, data);

        boolean allCategoryRewardsDone =
                data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_EXPLORATION_RUBY) &&
                        data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_COMBAT_RUBY) &&
                        data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_GATHERING_RUBY) &&
                        data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_CRAFTING_RUBY) &&
                        data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_COMMERCE_RUBY);

        if (allCategoryRewardsDone && !data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.RUBY_MASTERY)) {
            data.completeObjective(factionId, FactionObjectiveRegistry.RUBY_MASTERY);
            notifyFactionCompletion(player, factionId, FactionObjectiveRegistry.RUBY_MASTERY);
            playFinalMasteryEffects(player);
        }

        grantIfCompleted(player, factionId, FactionObjectiveRegistry.REWARD_EXPLORATION_RUBY, data);
        grantIfCompleted(player, factionId, FactionObjectiveRegistry.REWARD_COMBAT_RUBY, data);
        grantIfCompleted(player, factionId, FactionObjectiveRegistry.REWARD_GATHERING_RUBY, data);
        grantIfCompleted(player, factionId, FactionObjectiveRegistry.REWARD_CRAFTING_RUBY, data);
        grantIfCompleted(player, factionId, FactionObjectiveRegistry.REWARD_COMMERCE_RUBY, data);
        grantIfCompleted(player, factionId, FactionObjectiveRegistry.RUBY_MASTERY, data);
    }
    private static void grantIfCompleted(ServerPlayer player, String factionId, String objectiveId, FactionProgressData data) {
        if (data.isObjectiveCompleted(factionId, objectiveId)) {
            tryGrantReward(player, factionId, objectiveId, data);
        }
    }
    private static void playCategoryCompletionEffects(ServerPlayer player) {
        ServerLevel level = player.serverLevel();

        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                SoundSource.PLAYERS,
                0.9F,
                1.0F
        );

        level.sendParticles(
                ParticleTypes.END_ROD,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                25,
                0.5D,
                0.6D,
                0.5D,
                0.02D
        );

        level.sendParticles(
                ParticleTypes.ENCHANT,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                30,
                0.8D,
                0.8D,
                0.8D,
                0.05D
        );
    }
    private static void playFinalMasteryEffects(ServerPlayer player) {
        ServerLevel level = player.serverLevel();

        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                SoundSource.PLAYERS,
                1.2F,
                0.8F
        );

        level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.BEACON_ACTIVATE,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        level.sendParticles(
                ParticleTypes.TOTEM_OF_UNDYING,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                40,
                0.6D,
                0.8D,
                0.6D,
                0.1D
        );

        level.sendParticles(
                ParticleTypes.END_ROD,
                player.getX(),
                player.getY() + 1.2D,
                player.getZ(),
                50,
                0.9D,
                1.0D,
                0.9D,
                0.03D
        );
    }

    private static void tryCompleteCategoryReward(ServerPlayer player, String factionId, String category,
                                                  String rewardObjectiveId, FactionProgressData data) {
        if (data.isObjectiveCompleted(factionId, rewardObjectiveId)) {
            return;
        }

        for (FactionObjectiveDefinition def : FactionObjectiveRegistry.getAll().values()) {

            if (!category.equals(def.getCategory())) continue;

            // Ignore les objectifs de récompense
            if (def.getId().startsWith("reward_") || def.getId().equals(FactionObjectiveRegistry.RUBY_MASTERY)) {
                continue;
            }

            if (!data.isObjectiveCompleted(factionId, def.getId())) {
                return;
            }
        }

        data.setObjectiveProgress(factionId, rewardObjectiveId, 1); // 👈 IMPORTANT
        data.completeObjective(factionId, rewardObjectiveId);
        notifyFactionCompletion(player, factionId, rewardObjectiveId);
        playCategoryCompletionEffects(player);
    }

    public static void completeObjective(ServerPlayer player, String objectiveId) {
        addProgress(player, objectiveId, 1);
    }
    public static void addUniqueProgress(ServerPlayer player, String objectiveId, String uniqueKey) {
        String factionId = getPlayerFactionId(player);
        if (factionId == null) return;

        FactionObjectiveDefinition def = FactionObjectiveRegistry.get(objectiveId);
        if (def == null) return;

        FactionProgressData data = FactionProgressData.get(player.serverLevel());

        if (data.isObjectiveCompleted(factionId, objectiveId)) {
            tryGrantReward(player, factionId, objectiveId, data);
            return;
        }

        boolean added = data.addUniqueProgressKey(factionId, objectiveId, uniqueKey);
        if (!added) {
            tryGrantReward(player, factionId, objectiveId, data);
            return;
        }

        int progress = data.getUniqueProgressCount(factionId, objectiveId);
        data.setObjectiveProgress(factionId, objectiveId, progress);

        if (progress >= def.getTargetValue()) {
            data.completeObjective(factionId, objectiveId);
            notifyFactionCompletion(player, factionId, objectiveId);
            checkCategoryAndFinalRewards(player, factionId, data);
        }

        tryGrantReward(player, factionId, objectiveId, data);
    }
}