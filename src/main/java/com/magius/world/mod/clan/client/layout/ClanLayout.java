package com.magius.world.mod.clan.client.layout;

import net.minecraft.client.gui.navigation.ScreenRectangle;

public final class ClanLayout {

    private final ScreenRectangle header;
    private final ScreenRectangle sidebar;
    private final ScreenRectangle content;
    private final ScreenRectangle footer;

    private final boolean compact;

    public ClanLayout(
            int panelX,
            int panelY,
            int panelWidth,
            int panelHeight,
            boolean compact
    ) {
        this.compact = compact;

        /*
         * =====================================================
         * VALEURS SELON LE MODE
         * =====================================================
         */

        int padding = compact ? 4 : 8;
        int gap = compact ? 3 : 6;

        int headerHeight = compact ? 72 : 96;
        int footerHeight = compact ? 18 : 28;

        int sidebarWidth = compact ? 112 : 145;

        /*
         * Sécurité si jamais la fenêtre devient vraiment petite.
         */
        headerHeight = Math.min(
                headerHeight,
                Math.max(50, panelHeight / 3)
        );

        footerHeight = Math.min(
                footerHeight,
                Math.max(16, panelHeight / 10)
        );

        sidebarWidth = Math.min(
                sidebarWidth,
                Math.max(92, panelWidth / 4)
        );

        /*
         * =====================================================
         * ZONE INTÉRIEURE
         * =====================================================
         */

        int innerX = panelX + padding;
        int innerY = panelY + padding;

        int innerWidth =
                Math.max(
                        1,
                        panelWidth - padding * 2
                );

        int innerHeight =
                Math.max(
                        1,
                        panelHeight - padding * 2
                );

        /*
         * =====================================================
         * HEADER
         * =====================================================
         */

        header = new ScreenRectangle(
                innerX,
                innerY,
                innerWidth,
                headerHeight
        );

        /*
         * =====================================================
         * FOOTER
         * =====================================================
         */

        int footerY =
                innerY
                        + innerHeight
                        - footerHeight;

        footer = new ScreenRectangle(
                innerX,
                footerY,
                innerWidth,
                footerHeight
        );

        /*
         * =====================================================
         * BODY
         * =====================================================
         */

        int bodyY =
                innerY
                        + headerHeight
                        + gap;

        int bodyHeight =
                footerY
                        - gap
                        - bodyY;

        bodyHeight =
                Math.max(
                        1,
                        bodyHeight
                );

        /*
         * =====================================================
         * SIDEBAR
         * =====================================================
         */

        sidebar = new ScreenRectangle(
                innerX,
                bodyY,
                sidebarWidth,
                bodyHeight
        );

        /*
         * =====================================================
         * CONTENU
         * =====================================================
         */

        int contentX =
                innerX
                        + sidebarWidth
                        + gap;

        int contentWidth =
                innerWidth
                        - sidebarWidth
                        - gap;

        content = new ScreenRectangle(
                contentX,
                bodyY,
                Math.max(1, contentWidth),
                bodyHeight
        );
    }

    public ScreenRectangle header() {
        return header;
    }

    public ScreenRectangle sidebar() {
        return sidebar;
    }

    public ScreenRectangle content() {
        return content;
    }

    public ScreenRectangle footer() {
        return footer;
    }

    public boolean isCompact() {
        return compact;
    }
}
