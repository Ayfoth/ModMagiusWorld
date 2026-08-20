package com.magius.world.mod.clan.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class ClanProgressData {

    private int prestige = 0;
    private int rank = 0;
    private int currency = 0;

    private final Set<String> claimedRewards =
            new HashSet<>();

    public Set<String> getClaimedRewards() {
        return Set.copyOf(
                claimedRewards
        );
    }


    // =========================================================
    // PRESTIGE
    // =========================================================

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige =
                Math.max(0, prestige);
    }

    public void addPrestige(int amount) {

        if (amount <= 0) {
            return;
        }

        prestige += amount;
    }


    // =========================================================
    // RANG
    // =========================================================

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank =
                Math.max(0, rank);
    }


    // =========================================================
    // MONNAIE DU CLAN
    // =========================================================

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency =
                Math.max(0, currency);
    }

    public void addCurrency(int amount) {

        if (amount <= 0) {
            return;
        }

        currency += amount;
    }

    public boolean hasCurrency(int amount) {

        return amount >= 0
                && currency >= amount;
    }

    public boolean removeCurrency(int amount) {

        if (amount <= 0) {
            return false;
        }

        if (currency < amount) {
            return false;
        }

        currency -= amount;

        return true;
    }


    // =========================================================
    // RÉCOMPENSES
    // =========================================================

    public boolean hasClaimedReward(
            ResourceLocation rewardId
    ) {

        if (rewardId == null) {
            return false;
        }

        return claimedRewards.contains(
                rewardId.toString()
        );
    }

    public boolean claimReward(
            ResourceLocation rewardId
    ) {

        if (rewardId == null) {
            return false;
        }

        return claimedRewards.add(
                rewardId.toString()
        );
    }


    // =========================================================
    // SAUVEGARDE
    // =========================================================

    public CompoundTag saveNBT() {

        CompoundTag tag =
                new CompoundTag();

        tag.putInt(
                "Prestige",
                prestige
        );

        tag.putInt(
                "Rank",
                rank
        );

        tag.putInt(
                "Currency",
                currency
        );

        ListTag rewardsTag =
                new ListTag();

        for (String reward : claimedRewards) {

            rewardsTag.add(
                    StringTag.valueOf(reward)
            );
        }

        tag.put(
                "ClaimedRewards",
                rewardsTag
        );

        return tag;
    }


    // =========================================================
    // CHARGEMENT
    // =========================================================

    public void loadNBT(
            CompoundTag tag
    ) {

        prestige =
                Math.max(
                        0,
                        tag.getInt("Prestige")
                );

        rank =
                Math.max(
                        0,
                        tag.getInt("Rank")
                );

        currency =
                Math.max(
                        0,
                        tag.getInt("Currency")
                );

        claimedRewards.clear();

        ListTag rewardsTag =
                tag.getList(
                        "ClaimedRewards",
                        Tag.TAG_STRING
                );

        for (
                int i = 0;
                i < rewardsTag.size();
                i++
        ) {

            claimedRewards.add(
                    rewardsTag.getString(i)
            );
        }
    }
}