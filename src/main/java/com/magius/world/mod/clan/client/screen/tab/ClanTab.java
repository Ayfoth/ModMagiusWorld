package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ClanTab {

    Component getTitle();

    void render(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTick
    );

    default boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {
        return false;
    }

    default boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double delta,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {
        return false;
    }
}
