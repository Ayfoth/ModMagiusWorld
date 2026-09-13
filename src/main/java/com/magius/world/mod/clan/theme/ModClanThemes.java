package com.magius.world.mod.clan.theme;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class ModClanThemes {

    private ModClanThemes() {
    }

    public static void register() {

        ClanThemeRegistry.register(

                ResourceLocation.fromNamespaceAndPath(
                        MagiusWorldMod.MOD_ID,
                        "dragonmaid"
                ),

                new ClanTheme(

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/dragonmaid/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/dragonmaid/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/dragonmaid/emblem.png"
                        ),

                        Component.literal("Dragonmaid"),
                        Component.literal("Confiance"),

                        0xFF741C28,
                        0xFFD5A63A,
                        0xFFFFFFFF,
                        0xFF26090D,

                        null,
                        null
                )
        );
        ClanThemeRegistry.register(

                ResourceLocation.fromNamespaceAndPath(
                        MagiusWorldMod.MOD_ID,
                        "swordsoul"
                ),

                new ClanTheme(

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/swordsoul/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/swordsoul/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clans/swordsoul.png"
                        ),

                        Component.literal("Swordsoul"),
                        Component.literal("Harmonie"),

                        // Accent : bleu acier
                        0xFF315C88,

                        // Titres : bleu glacier
                        0xFF8DD9F2,

                        // Texte
                        0xFFFFFFFF,

                        // Fond : bleu nuit
                        0xFF071521,

                        null,
                        null
                )
        );
        ClanThemeRegistry.register(
                ResourceLocation.fromNamespaceAndPath(
                        MagiusWorldMod.MOD_ID,
                        "unchained"
                ),

                new ClanTheme(
                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/unchained/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clan/unchained/frame.png"
                        ),

                        ResourceLocation.fromNamespaceAndPath(
                                MagiusWorldMod.MOD_ID,
                                "textures/gui/clans/unchained.png"
                        ),

                        Component.literal("Les Déchaînés"),
                        Component.literal("Rupture"),

                        // Accent : charbon rouge très sombre
                        0xFF1A090C,

                        // Titres : rouge sang assombri, sans rose
                        0xFF812D2D,

                        // Texte : gris cendre neutre
                        0xFFD2CED0,

                        // Fond : noir charbon légèrement violacé
                        0xFF030204,

                        null,
                        null
                )
        );
    }
}
