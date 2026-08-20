package com.magius.world.mod.clan.client.theme;

import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.gui.GuiGraphics;

public final class ClanThemeRenderer {

    private ClanThemeRenderer() {
    }

    /*
     * =========================================================
     * GRANDE FRAME PRINCIPALE
     * =========================================================
     *
     * Plus aucune texture étirée.
     *
     * La frame est construite avec plusieurs couches :
     *
     * noir sombre
     * or
     * bordeaux
     * or fin
     * fond
     *
     * Elle restera donc parfaitement nette quelle que soit
     * la taille de la fenêtre.
     */

    public static void renderPanel(
            GuiGraphics graphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {

        int darkOutline = 0xFF120607;
        int deepShadow = 0xFF26090D;

        int gold =
                theme.getTitleColor();

        int burgundy =
                theme.getAccentColor();

        int background =
                theme.getButtonColor();

        /*
         * Ombre extérieure
         */
        graphics.fill(
                x + 3,
                y + 3,
                x + width + 3,
                y + height + 3,
                0x88000000
        );

        /*
         * Contour noir
         */
        graphics.fill(
                x,
                y,
                x + width,
                y + height,
                darkOutline
        );

        /*
         * Bordure or principale
         */
        graphics.fill(
                x + 2,
                y + 2,
                x + width - 2,
                y + height - 2,
                gold
        );

        /*
         * Bordure bordeaux
         */
        graphics.fill(
                x + 4,
                y + 4,
                x + width - 4,
                y + height - 4,
                burgundy
        );

        /*
         * Deuxième contour sombre
         */
        graphics.fill(
                x + 6,
                y + 6,
                x + width - 6,
                y + height - 6,
                deepShadow
        );

        /*
         * Ligne intérieure dorée
         */
        graphics.fill(
                x + 7,
                y + 7,
                x + width - 7,
                y + height - 7,
                gold
        );

        /*
         * Fond central
         */
        graphics.fill(
                x + 9,
                y + 9,
                x + width - 9,
                y + height - 9,
                background
        );

        /*
         * =====================================================
         * COINS DÉCORATIFS
         * =====================================================
         */

        renderCorner(
                graphics,
                x + 2,
                y + 2,
                gold,
                burgundy
        );

        renderCorner(
                graphics,
                x + width - 10,
                y + 2,
                gold,
                burgundy
        );

        renderCorner(
                graphics,
                x + 2,
                y + height - 10,
                gold,
                burgundy
        );

        renderCorner(
                graphics,
                x + width - 10,
                y + height - 10,
                gold,
                burgundy
        );
    }

    /*
     * =========================================================
     * PETIT ORNEMENT DE COIN
     * =========================================================
     */

    private static void renderCorner(
            GuiGraphics graphics,
            int x,
            int y,
            int gold,
            int burgundy
    ) {

        /*
         * Carré sombre
         */
        graphics.fill(
                x,
                y,
                x + 8,
                y + 8,
                0xFF170607
        );

        /*
         * Or
         */
        graphics.fill(
                x + 1,
                y + 1,
                x + 7,
                y + 7,
                gold
        );

        /*
         * Bordeaux
         */
        graphics.fill(
                x + 3,
                y + 3,
                x + 5,
                y + 5,
                burgundy
        );
    }

    /*
     * =========================================================
     * PANNEAUX INTERNES
     * =========================================================
     */

    public static void renderInnerBox(
            GuiGraphics graphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {

        /*
         * Bordure sombre.
         */
        graphics.fill(
                x,
                y,
                x + width,
                y + height,
                0xFF160708
        );

        /*
         * Bordure dorée fine.
         */
        graphics.fill(
                x + 1,
                y + 1,
                x + width - 1,
                y + height - 1,
                theme.getTitleColor()
        );

        /*
         * Bordure secondaire.
         */
        graphics.fill(
                x + 3,
                y + 3,
                x + width - 3,
                y + height - 3,
                theme.getAccentColor()
        );

        /*
         * Fond intérieur.
         */
        graphics.fill(
                x + 4,
                y + 4,
                x + width - 4,
                y + height - 4,
                theme.getButtonColor()
        );
    }

    /*
     * =========================================================
     * BACKGROUND OPTIONNEL
     * =========================================================
     */

    public static void renderBackground(
            GuiGraphics graphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {

        if (theme.getBackgroundTexture() == null) {
            return;
        }

        graphics.blit(
                theme.getBackgroundTexture(),
                x,
                y,
                0,
                0,
                width,
                height,
                width,
                height
        );
    }

    /*
     * =========================================================
     * EMBLÈME DU CLAN
     * =========================================================
     */

    public static void renderIcon(
            GuiGraphics graphics,
            ClanTheme theme,
            int x,
            int y,
            int size
    ) {

        if (theme.getIcon() == null) {
            return;
        }

        graphics.blit(
                theme.getIcon(),
                x,
                y,
                0,
                0,
                size,
                size,
                size,
                size
        );
    }
}