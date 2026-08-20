package com.magius.world.mod.clan.chronicle.data;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ChronicleDefinition {

    private final ResourceLocation id;
    private final ResourceLocation clanId;

    private final int order;

    private final String title;
    private final String shortTitle;
    private final String description;

    private final ResourceLocation icon;

    private final UnlockCondition unlock;

    private final List<Page> pages;

    public ChronicleDefinition(
            ResourceLocation id,
            ResourceLocation clanId,
            int order,
            String title,
            String shortTitle,
            String description,
            ResourceLocation icon,
            UnlockCondition unlock,
            List<Page> pages
    ) {
        this.id = id;
        this.clanId = clanId;
        this.order = order;
        this.title = title;
        this.shortTitle = shortTitle;
        this.description = description;
        this.icon = icon;
        this.unlock = unlock;
        this.pages = List.copyOf(pages);
    }

    public ResourceLocation getId() {
        return id;
    }

    public ResourceLocation getClanId() {
        return clanId;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getDescription() {
        return description;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public UnlockCondition getUnlock() {
        return unlock;
    }

    public List<Page> getPages() {
        return pages;
    }

    public static class Page {

        private final String title;
        private final List<String> text;

        public Page(
                String title,
                List<String> text
        ) {
            this.title = title;
            this.text = List.copyOf(text);
        }

        public String getTitle() {
            return title;
        }

        public List<String> getText() {
            return text;
        }
    }

    public static class UnlockCondition {

        private final String type;
        private final ResourceLocation target;

        public UnlockCondition(
                String type,
                ResourceLocation target
        ) {
            this.type = type;
            this.target = target;
        }

        public String getType() {
            return type;
        }

        public ResourceLocation getTarget() {
            return target;
        }
    }
}
