package com.magius.world.mod.clan.manager;

import com.magius.world.mod.clan.api.Clan;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class ClanRegistry {

    private static final Map<ResourceLocation, Clan> CLANS = new LinkedHashMap<>();

    private ClanRegistry() {
    }

    public static void register(Clan clan) {
        if (clan == null) {
            throw new IllegalArgumentException("Le clan ne peut pas être null.");
        }

        ResourceLocation clanId = clan.getId();

        if (clanId == null) {
            throw new IllegalArgumentException("L'identifiant du clan ne peut pas être null.");
        }

        if (CLANS.containsKey(clanId)) {
            throw new IllegalStateException(
                    "Un clan avec l'identifiant " + clanId + " est déjà enregistré."
            );
        }

        CLANS.put(clanId, clan);
    }

    public static Optional<Clan> get(ResourceLocation clanId) {
        if (clanId == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(CLANS.get(clanId));
    }

    public static Collection<Clan> getAll() {
        return Collections.unmodifiableCollection(CLANS.values());
    }

    public static boolean contains(ResourceLocation clanId) {
        return clanId != null && CLANS.containsKey(clanId);
    }

    public static int size() {
        return CLANS.size();
    }
    public static Optional<Clan> get(String id) {
        return CLANS.values().stream()
                .filter(clan -> clan.getId().getPath().equalsIgnoreCase(id))
                .findFirst();
    }
}