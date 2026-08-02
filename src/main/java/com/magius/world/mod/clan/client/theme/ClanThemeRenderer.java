package com.magius.world.mod.clan.client.theme;

import net.minecraft.client.gui.GuiGraphics;

public final class ClanThemeRenderer {

    private ClanThemeRenderer() {
    }

    public static void renderPanel(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int width,
            int height
    ) {

        // Fond
        guiGraphics.fill(
                x,
                y,
                x + width,
                y + height,
                0xCC202020
        );

        // Bordure haute
        guiGraphics.fill(
                x,
                y,
                x + width,
                y + 2,
                0xFFE8C96A
        );

        // Bordure basse
        guiGraphics.fill(
                x,
                y + height - 2,
                x + width,
                y + height,
                0xFFE8C96A
        );

        // Bordure gauche
        guiGraphics.fill(
                x,
                y,
                x + 2,
                y + height,
                0xFFE8C96A
        );

        // Bordure droite
        guiGraphics.fill(
                x + width - 2,
                y,
                x + width,
                y + height,
                0xFFE8C96A
        );
    }
}