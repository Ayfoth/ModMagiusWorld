package com.magius.world.mod.clan.api;

public class ClanRank {

    private final String name;
    private final int requiredPrestige;

    public ClanRank(String name, int requiredPrestige) {
        this.name = name;
        this.requiredPrestige = requiredPrestige;
    }

    public String getName() {
        return name;
    }

    public int getRequiredPrestige() {
        return requiredPrestige;
    }
}