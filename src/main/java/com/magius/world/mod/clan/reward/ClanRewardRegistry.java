package com.magius.world.mod.clan.reward;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ClanRewardRegistry {

    private static final Map<ResourceLocation, ClanReward> REWARDS =
            new LinkedHashMap<>();

    private ClanRewardRegistry() {
    }

    public static void register(
            ClanReward reward
    ) {

        if (reward == null) {
            return;
        }

        REWARDS.put(
                reward.getId(),
                reward
        );
    }

    public static ClanReward get(
            ResourceLocation rewardId
    ) {
        return REWARDS.get(rewardId);
    }

    public static List<ClanReward> getForClan(
            ResourceLocation clanId
    ) {

        List<ClanReward> result =
                new ArrayList<>();

        for (ClanReward reward : REWARDS.values()) {

            if (
                    reward.getClanId()
                            .equals(clanId)
            ) {

                result.add(reward);
            }
        }

        result.sort(
                java.util.Comparator.comparingInt(
                        ClanReward::getRequiredPrestige
                )
        );

        return Collections.unmodifiableList(
                result
        );
    }
}