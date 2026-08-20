package com.magius.world.mod.clan.chronicle.data;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ChronicleRegistry {

    private static final Map<ResourceLocation, ChronicleDefinition> CHRONICLES =
            new LinkedHashMap<>();

    private ChronicleRegistry() {
    }

    public static void clear() {
        CHRONICLES.clear();
    }

    public static void register(
            ChronicleDefinition chronicle
    ) {
        ResourceLocation id =
                chronicle.getId();

        if (CHRONICLES.containsKey(id)) {
            throw new IllegalStateException(
                    "La chronique existe déjà : " + id
            );
        }

        CHRONICLES.put(
                id,
                chronicle
        );
    }

    public static Optional<ChronicleDefinition> get(
            ResourceLocation id
    ) {
        return Optional.ofNullable(
                CHRONICLES.get(id)
        );
    }

    public static List<ChronicleDefinition> getForClan(
            ResourceLocation clanId
    ) {
        List<ChronicleDefinition> result =
                new ArrayList<>();

        for (
                ChronicleDefinition chronicle :
                CHRONICLES.values()
        ) {

            if (
                    chronicle
                            .getClanId()
                            .equals(clanId)
            ) {
                result.add(chronicle);
            }
        }

        result.sort(
                Comparator.comparingInt(
                        ChronicleDefinition::getOrder
                )
        );

        return List.copyOf(result);
    }

    public static List<ChronicleDefinition> getAll() {
        return List.copyOf(
                CHRONICLES.values()
        );
    }

    public static int size() {
        return CHRONICLES.size();
    }
}
