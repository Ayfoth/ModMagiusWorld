package com.magius.world.mod.library;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BookData {

    private final ResourceLocation id;
    private final BookCategory category;
    private final Component title;
    private final List<Component> pages;

    public BookData(ResourceLocation id,
                    BookCategory category,
                    Component title,
                    List<Component> pages) {

        this.id = id;
        this.category = category;
        this.title = title;
        this.pages = pages;
    }

    public ResourceLocation getId() {
        return id;
    }

    public BookCategory getCategory() {
        return category;
    }

    public Component getTitle() {
        return title;
    }

    public List<Component> getPages() {
        return pages;
    }

    public int getPageCount() {
        return pages.size();
    }
}
