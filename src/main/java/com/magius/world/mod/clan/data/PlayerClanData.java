package com.magius.world.mod.clan.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;


public class PlayerClanData {

    /**
     * Clan actuel du joueur.
     * Null = aucun clan.
     */
    private ResourceLocation clanId;

    /**
     * Prestige du joueur dans son clan.
     */
    private int prestige;

    /**
     * Rang actuel.
     */
    private int rank;

    public ResourceLocation getClanId() {
        return clanId;
    }

    public void setClanId(ResourceLocation clanId) {
        this.clanId = clanId;
    }

    public boolean hasClan() {
        return clanId != null;
    }

    public int getPrestige() {
        return prestige;
    }

    public void setPrestige(int prestige) {
        this.prestige = Math.max(0, prestige);
    }

    public void addPrestige(int amount) {
        if (amount > 0) {
            prestige += amount;
        }
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = Math.max(0, rank);
    }

    public void promote() {
        rank++;
    }

    public void reset() {
        clanId = null;
        prestige = 0;
        rank = 0;
    }
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();

        if (clanId != null) {
            tag.putString("ClanId", clanId.toString());
        }

        tag.putInt("Prestige", prestige);
        tag.putInt("Rank", rank);

        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        reset();

        if (tag.contains("ClanId")) {
            clanId = ResourceLocation.tryParse(tag.getString("ClanId"));
        }

        prestige = Math.max(0, tag.getInt("Prestige"));
        rank = Math.max(0, tag.getInt("Rank"));
    }
}
