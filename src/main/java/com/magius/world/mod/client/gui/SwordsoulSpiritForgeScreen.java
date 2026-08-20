package com.magius.world.mod.client.gui;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SUnlockSwordsoulSpiritForgePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulSpiritForgeScreen extends Screen {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );
    private final BlockPos terminalPos;

    private static final int SOUL_COST = 25;

    private int souls = 0;

    public SwordsoulSpiritForgeScreen(
            BlockPos terminalPos
    ) {
        super(Component.literal("Forge spirituelle"));
        this.terminalPos = terminalPos;
    }

    @Override
    protected void init() {

        super.init();

        /*
         * Récupération du solde synchronisé côté client.
         */
        if (minecraft != null && minecraft.player != null) {

            ClanManager.get(minecraft.player)
                    .resolve()
                    .ifPresent(data ->
                            souls = data.getClanCurrency(
                                    SWORDSOUL_ID
                            )
                    );
        }

        int centerX = width / 2;
        int centerY = height / 2;

        /*
         * ACTIVER
         */
        addRenderableWidget(
                Button.builder(
                                Component.literal("Activer"),
                                button -> activateForge()
                        )
                        .bounds(
                                centerX - 82,
                                centerY + 45,
                                78,
                                20
                        )
                        .build()
        );

        /*
         * ANNULER
         */
        addRenderableWidget(
                Button.builder(
                                Component.literal("Annuler"),
                                button -> onClose()
                        )
                        .bounds(
                                centerX + 4,
                                centerY + 45,
                                78,
                                20
                        )
                        .build()
        );
    }

    private void activateForge() {

        /*
         * Le serveur fera de nouveau toutes les vérifications.
         * On ne fait jamais confiance au solde affiché côté client.
         */
        ModMessages.sendToServer(
                new C2SUnlockSwordsoulSpiritForgePacket(
                        terminalPos
                )
        );

        onClose();
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        renderBackground(guiGraphics);

        int centerX = width / 2;
        int centerY = height / 2;

        /*
         * Fond sombre Swordsoul.
         */
        guiGraphics.fill(
                centerX - 105,
                centerY - 75,
                centerX + 105,
                centerY + 75,
                0xE6101822
        );

        /*
         * Bordure bleu glacé.
         */
        guiGraphics.renderOutline(
                centerX - 105,
                centerY - 75,
                210,
                150,
                0xFF64D8FF
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Forge spirituelle"),
                centerX,
                centerY - 58,
                0xFFBDEFFF
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Réactive une ancienne forge"
                ),
                centerX,
                centerY - 30,
                0xFFE0E8F0
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "alimentée par les Âmes Swordsoul."
                ),
                centerX,
                centerY - 18,
                0xFFE0E8F0
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Coût : " + SOUL_COST + " Âmes"
                ),
                centerX,
                centerY + 7,
                0xFF64D8FF
        );

        int balanceColor =
                souls >= SOUL_COST
                        ? 0xFF80FFB0
                        : 0xFFFF8080;

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Solde : " + souls + " Âmes"
                ),
                centerX,
                centerY + 20,
                balanceColor
        );

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
