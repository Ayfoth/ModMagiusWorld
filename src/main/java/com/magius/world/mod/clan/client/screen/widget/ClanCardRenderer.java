package com.magius.world.mod.clan.client.screen.widget;

import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class ClanCardRenderer {

    private static final int HEADER_HEIGHT = 18;

    private ClanCardRenderer() {
    }

    public static void render(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height,
            Component title
    ) {

        // Cadre principal
        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                x,
                y,
                width,
                height
        );

        // Bandeau du titre
        guiGraphics.fill(
                x + 2,
                y + 2,
                x + width - 2,
                y + HEADER_HEIGHT,
                theme.getButtonColor()
        );

        // Ligne de séparation
        guiGraphics.fill(
                x + 2,
                y + HEADER_HEIGHT,
                x + width - 2,
                y + HEADER_HEIGHT + 1,
                theme.getAccentColor()
        );

        // Titre
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                title,
                x + 6,
                y + 6,
                theme.getTitleColor(),
                false
        );
    }
}
