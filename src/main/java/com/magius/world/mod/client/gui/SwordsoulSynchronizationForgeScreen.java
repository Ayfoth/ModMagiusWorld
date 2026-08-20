package com.magius.world.mod.client.gui;

import com.magius.world.mod.screen.SwordsoulSpiritForgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SwordsoulSynchronizationForgeScreen
        extends AbstractContainerScreen<SwordsoulSpiritForgeMenu> {

    public SwordsoulSynchronizationForgeScreen(
            SwordsoulSpiritForgeMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(
                menu,
                inventory,
                title
        );

        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        titleLabelY = 6;

        /*
         * Le libellé de l'inventaire est masqué
         * pour laisser la place au bouton.
         */
        inventoryLabelY = 10000;

        addRenderableWidget(
                Button.builder(
                                Component.literal(
                                        "Synchroniser"
                                ),
                                button -> {

                                    if (minecraft != null
                                            && minecraft.gameMode != null) {

                                        minecraft.gameMode
                                                .handleInventoryButtonClick(
                                                        menu.containerId,
                                                        0
                                                );
                                    }
                                }
                        )
                        .bounds(
                                leftPos + 50,
                                topPos + 59,
                                76,
                                18
                        )
                        .build()
        );
    }

    @Override
    protected void renderBg(
            GuiGraphics guiGraphics,
            float partialTick,
            int mouseX,
            int mouseY
    ) {
        int x = leftPos;
        int y = topPos;

        /*
         * Fond principal.
         */
        guiGraphics.fill(
                x,
                y,
                x + imageWidth,
                y + imageHeight,
                0xF0121B26
        );

        /*
         * Bordure extérieure.
         */
        guiGraphics.renderOutline(
                x,
                y,
                imageWidth,
                imageHeight,
                0xFF55D9FF
        );

        /*
         * Zone supérieure de synchronisation.
         */
        guiGraphics.fill(
                x + 7,
                y + 18,
                x + 169,
                y + 80,
                0xFF182A3A
        );

        guiGraphics.renderOutline(
                x + 7,
                y + 18,
                162,
                62,
                0xFF327A9E
        );

        /*
         * Emplacement de la Lame sans maître.
         */
        drawSlot(
                guiGraphics,
                x + 25,
                y + 34,
                0xFF4D9FC5
        );

        /*
         * Emplacement du Jeton spirituel.
         */
        drawSlot(
                guiGraphics,
                x + 61,
                y + 34,
                0xFF55D9FF
        );

        /*
         * Emplacement du catalyseur.
         */
        drawSlot(
                guiGraphics,
                x + 97,
                y + 34,
                0xFFB38CFF
        );

        /*
         * Emplacement du résultat.
         */
        drawSlot(
                guiGraphics,
                x + 142,
                y + 34,
                0xFF8FFFEA
        );

        /*
         * Lame + Jeton + Catalyseur.
         */
        guiGraphics.drawCenteredString(
                font,
                Component.literal("+"),
                x + 53,
                y + 39,
                0xFFBDEFFF
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("+"),
                x + 89,
                y + 39,
                0xFFBDEFFF
        );

        /*
         * Flèche vers le résultat.
         */
        guiGraphics.drawString(
                font,
                Component.literal("→"),
                x + 124,
                y + 39,
                0xFFBDEFFF,
                false
        );
    }

    private void drawSlot(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int borderColor
    ) {
        guiGraphics.fill(
                x,
                y,
                x + 18,
                y + 18,
                0xFF09121B
        );

        guiGraphics.renderOutline(
                x,
                y,
                18,
                18,
                borderColor
        );
    }

    @Override
    protected void renderLabels(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY
    ) {
        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Forge de synchronisation"
                ),
                imageWidth / 2,
                6,
                0xFFBDEFFF
        );

//        guiGraphics.drawString(
//                font,
//                Component.translatable(
//                        "container.inventory"
//                ),
//                8,
//                inventoryLabelY,
//                0xFFD8E5EC,
//                false
//        );
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        renderBackground(guiGraphics);

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );

        renderTooltip(
                guiGraphics,
                mouseX,
                mouseY
        );
    }
}