package com.magius.world.mod.clan.client.screen.widget;

import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.BooleanSupplier;

public class ClanSidebarButton extends AbstractButton {

    private final ClanTheme theme;
    private final BooleanSupplier selectedSupplier;
    private final Runnable pressAction;

    /*
     * Icône Minecraft affichée à gauche.
     */
    private final ItemStack icon;

    public ClanSidebarButton(
            int x,
            int y,
            int width,
            int height,
            Component label,
            ItemStack icon,
            ClanTheme theme,
            BooleanSupplier selectedSupplier,
            Runnable pressAction
    ) {
        super(x, y, width, height, label);

        this.icon = icon;
        this.theme = theme;
        this.selectedSupplier = selectedSupplier;
        this.pressAction = pressAction;
    }

    @Override
    public void onPress() {
        pressAction.run();
    }

    @Override
    protected void renderWidget(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        boolean selected =
                selectedSupplier.getAsBoolean();

        boolean hovered =
                isHovered();

        /*
         * =====================================================
         * COULEURS
         * =====================================================
         */

        int borderColor;

        if (selected) {
            borderColor = theme.getTitleColor();
        } else {
            borderColor = theme.getAccentColor();
        }

        int backgroundColor;

        if (selected) {

            backgroundColor =
                    theme.getAccentColor();

        } else if (hovered) {

            backgroundColor =
                    lighten(
                            theme.getButtonColor(),
                            18
                    );

        } else {

            backgroundColor =
                    theme.getButtonColor();
        }

        /*
         * =====================================================
         * BORDURE
         * =====================================================
         */

        guiGraphics.fill(
                getX(),
                getY(),
                getX() + width,
                getY() + height,
                borderColor
        );

        /*
         * =====================================================
         * FOND
         * =====================================================
         */

        guiGraphics.fill(
                getX() + 2,
                getY() + 2,
                getX() + width - 2,
                getY() + height - 2,
                backgroundColor
        );

        /*
         * =====================================================
         * MARQUEUR DE SÉLECTION
         * =====================================================
         *
         * Petite barre dorée à gauche.
         */

        if (selected) {

            guiGraphics.fill(
                    getX() + 2,
                    getY() + 2,
                    getX() + 5,
                    getY() + height - 2,
                    theme.getTitleColor()
            );
        }

        /*
         * =====================================================
         * ICÔNE
         * =====================================================
         */

        int iconX =
                getX() + 9;

        int iconY =
                getY()
                        + (height - 16) / 2;

        if (!icon.isEmpty()) {

            guiGraphics.renderItem(
                    icon,
                    iconX,
                    iconY
            );
        }

        /*
         * =====================================================
         * TEXTE
         * =====================================================
         */

        int textColor =
                selected
                        ? theme.getTitleColor()
                        : theme.getTextColor();

        int textX =
                getX() + 31;

        int textY =
                getY()
                        + (height - 8) / 2;

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                getMessage(),
                textX,
                textY,
                textColor,
                false
        );
    }

    /*
     * =========================================================
     * ÉCLAIRCISSEMENT DU BOUTON AU SURVOL
     * =========================================================
     */

    private static int lighten(
            int color,
            int amount
    ) {

        int alpha =
                (color >> 24) & 0xFF;

        int red =
                (color >> 16) & 0xFF;

        int green =
                (color >> 8) & 0xFF;

        int blue =
                color & 0xFF;

        red = Math.min(
                255,
                red + amount
        );

        green = Math.min(
                255,
                green + amount
        );

        blue = Math.min(
                255,
                blue + amount
        );

        return (alpha << 24)
                | (red << 16)
                | (green << 8)
                | blue;
    }

    @Override
    protected void updateWidgetNarration(
            NarrationElementOutput narrationElementOutput
    ) {

        this.defaultButtonNarrationText(
                narrationElementOutput
        );
    }
}
