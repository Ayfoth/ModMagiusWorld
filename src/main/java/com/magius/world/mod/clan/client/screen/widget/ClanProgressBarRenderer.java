package com.magius.world.mod.clan.client.screen.widget;

import net.minecraft.client.gui.GuiGraphics;

public final class ClanProgressBarRenderer {

    private ClanProgressBarRenderer() {
    }

    public static void render(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int width,
            int height,
            int current,
            int maximum,
            int backgroundColor,
            int fillColor,
            int borderColor
    ) {
        int safeMaximum = Math.max(1, maximum);
        int safeCurrent = Math.max(0, Math.min(current, safeMaximum));

        float progress = (float) safeCurrent / safeMaximum;
        int filledWidth = Math.round((width - 4) * progress);

        guiGraphics.fill(
                x,
                y,
                x + width,
                y + height,
                borderColor
        );

        guiGraphics.fill(
                x + 1,
                y + 1,
                x + width - 1,
                y + height - 1,
                backgroundColor
        );

        if (filledWidth > 0) {
            guiGraphics.fill(
                    x + 2,
                    y + 2,
                    x + 2 + filledWidth,
                    y + height - 2,
                    fillColor
            );
        }
    }
}