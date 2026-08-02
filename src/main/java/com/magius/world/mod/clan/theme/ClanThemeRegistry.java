package com.magius.world.mod.clan.theme;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class ClanThemeRegistry {

    private static final Map<ResourceLocation, ClanTheme> THEMES =
            new LinkedHashMap<>();

    private ClanThemeRegistry() {
    }

    public static void register(ResourceLocation id, ClanTheme theme) {

        if (THEMES.containsKey(id)) {
            throw new IllegalStateException(
                    "Le thème existe déjà : " + id
            );
        }

        THEMES.put(id, theme);
    }

    public static Optional<ClanTheme> get(ResourceLocation id) {
        return Optional.ofNullable(THEMES.get(id));
    }
}
