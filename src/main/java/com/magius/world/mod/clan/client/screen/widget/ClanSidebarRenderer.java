package com.magius.world.mod.clan.client.screen.widget;

import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;

public final class ClanSidebarRenderer {

    private ClanSidebarRenderer() {
    }

    public static void render(
            GuiGraphics guiGraphics,
            ScreenRectangle area,
            ClanTheme theme
    ) {
        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                area.left(),
                area.top(),
                area.width(),
                area.height()
        );
    }
}
