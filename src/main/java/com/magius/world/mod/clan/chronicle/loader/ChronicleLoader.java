package com.magius.world.mod.clan.chronicle.loader;

import com.google.gson.*;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.chronicle.data.ChronicleDefinition;
import com.magius.world.mod.clan.chronicle.data.ChronicleRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ChronicleLoader {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    private static final String DIRECTORY =
            "clan_chronicles";

    private ChronicleLoader() {
    }

    public static void reload(
            ResourceManager resourceManager
    ) {

        ChronicleRegistry.clear();

        Map<ResourceLocation, Resource> resources =
                resourceManager.listResources(
                        DIRECTORY,
                        location ->
                                location.getPath()
                                        .endsWith(".json")
                );

        for (
                Map.Entry<ResourceLocation, Resource> entry :
                resources.entrySet()
        ) {

            ResourceLocation fileId =
                    entry.getKey();

            Resource resource =
                    entry.getValue();

            try (
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            resource.open(),
                                            StandardCharsets.UTF_8
                                    )
                            )
            ) {

                JsonObject root =
                        GSON.fromJson(
                                reader,
                                JsonObject.class
                        );

                ChronicleDefinition definition =
                        parseChronicle(
                                root,
                                fileId
                        );

                ChronicleRegistry.register(
                        definition
                );

                MagiusWorldMod.LOGGER.info(
                        "Chronique chargée : {}",
                        definition.getId()
                );

            } catch (Exception exception) {

                MagiusWorldMod.LOGGER.error(
                        "Erreur lors du chargement de la chronique {}",
                        fileId,
                        exception
                );
            }
        }

        MagiusWorldMod.LOGGER.info(
                "{} chronique(s) chargée(s).",
                ChronicleRegistry.size()
        );
    }

    private static ChronicleDefinition parseChronicle(
            JsonObject root,
            ResourceLocation fileId
    ) {

        ResourceLocation id =
                ResourceLocation.parse(
                        root.get("id").getAsString()
                );

        ResourceLocation clanId =
                ResourceLocation.parse(
                        root.get("clan").getAsString()
                );

        int order =
                root.get("order").getAsInt();

        String title =
                root.get("title").getAsString();

        String shortTitle =
                root.get("short_title").getAsString();

        String description =
                root.get("description").getAsString();

        ResourceLocation icon =
                ResourceLocation.parse(
                        root.get("icon").getAsString()
                );

        ChronicleDefinition.UnlockCondition unlock =
                parseUnlock(root);

        List<ChronicleDefinition.Page> pages =
                parsePages(root);

        return new ChronicleDefinition(
                id,
                clanId,
                order,
                title,
                shortTitle,
                description,
                icon,
                unlock,
                pages
        );
    }

    private static ChronicleDefinition.UnlockCondition parseUnlock(
            JsonObject root
    ) {

        if (
                !root.has("unlock")
                        || root.get("unlock").isJsonNull()
        ) {

            return null;
        }

        JsonObject unlockObject =
                root.getAsJsonObject("unlock");

        String type =
                unlockObject
                        .get("type")
                        .getAsString();

        ResourceLocation target =
                ResourceLocation.parse(
                        unlockObject
                                .get("target")
                                .getAsString()
                );

        return new ChronicleDefinition.UnlockCondition(
                type,
                target
        );
    }

    private static List<ChronicleDefinition.Page> parsePages(
            JsonObject root
    ) {

        List<ChronicleDefinition.Page> pages =
                new ArrayList<>();

        JsonArray pageArray =
                root.getAsJsonArray("pages");

        for (JsonElement pageElement : pageArray) {

            JsonObject pageObject =
                    pageElement.getAsJsonObject();

            String title =
                    pageObject
                            .get("title")
                            .getAsString();

            List<String> text =
                    new ArrayList<>();

            JsonArray textArray =
                    pageObject
                            .getAsJsonArray("text");

            for (JsonElement textElement : textArray) {
                text.add(
                        textElement.getAsString()
                );
            }

            pages.add(
                    new ChronicleDefinition.Page(
                            title,
                            text
                    )
            );
        }

        return pages;
    }
}
