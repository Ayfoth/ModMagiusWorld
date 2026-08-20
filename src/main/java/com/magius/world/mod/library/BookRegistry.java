package com.magius.world.mod.library;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class BookRegistry {

    private static final Map<ResourceLocation, BookData> BOOKS =
            new LinkedHashMap<>();

    private BookRegistry() {
    }

    public static void register(BookData book) {
        if (book == null) {
            throw new IllegalArgumentException(
                    "Le livre ne peut pas être nul."
            );
        }

        ResourceLocation id = book.getId();

        if (id == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du livre ne peut pas être nul."
            );
        }

        if (BOOKS.containsKey(id)) {
            throw new IllegalStateException(
                    "Un livre possède déjà l'identifiant : " + id
            );
        }

        BOOKS.put(id, book);
    }

    public static Optional<BookData> get(ResourceLocation id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(BOOKS.get(id));
    }

    public static Collection<BookData> getAll() {
        return Collections.unmodifiableCollection(BOOKS.values());
    }

    public static boolean contains(ResourceLocation id) {
        return id != null && BOOKS.containsKey(id);
    }

    public static int size() {
        return BOOKS.size();
    }
}