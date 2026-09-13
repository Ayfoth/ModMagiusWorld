package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedTwinsQuest;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SBuyUnchainedSealPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UnchainedTwinMerchantScreen extends Screen {

    private final int merchantId;
    private final C2SBuyUnchainedSealPacket.SealTrade trade;
    private boolean tradeUnlocked;

    public UnchainedTwinMerchantScreen(
            int merchantId,
            C2SBuyUnchainedSealPacket.SealTrade trade
    ) {
        super(Component.literal(
                trade == C2SBuyUnchainedSealPacket.SealTrade.ARUHA
                        ? "Aruha"
                        : "Rakea"
        ));
        this.merchantId = merchantId;
        this.trade = trade;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null) {
            QuestManager.get(minecraft.player).ifPresent(data ->
                    tradeUnlocked = QuestManager.getStatus(
                            data,
                            UnchainedTwinsQuest.ID
                    ) == QuestStatus.REWARDED
            );
        }

        if (tradeUnlocked) {
            addRenderableWidget(
                    Button.builder(
                                    Component.literal(getTradeLabel()),
                                    button -> ModMessages.sendToServer(
                                            new C2SBuyUnchainedSealPacket(
                                                    merchantId,
                                                    trade
                                            )
                                    )
                            )
                            .bounds(centerX - 110, centerY + 30, 220, 20)
                            .build()
            );
        }

        addRenderableWidget(
                Button.builder(
                                Component.literal("Quitter"),
                                button -> onClose()
                        )
                        .bounds(centerX - 110, centerY + 55, 220, 20)
                        .build()
        );
    }

    private String getTradeLabel() {
        return trade == C2SBuyUnchainedSealPacket.SealTrade.ARUHA
                ? "16 chairs putréfiées → Sceau d'Aruha"
                : "4 bâtons de Blaze → Sceau de Rakea";
    }

    private Component getDialogue() {
        if (!tradeUnlocked) {
            return Component.literal(
                    "« Nos chaînes viennent à peine de céder. "
                            + "Reçois d'abord la reconnaissance du Gardien. »"
            );
        }

        return trade == C2SBuyUnchainedSealPacket.SealTrade.ARUHA
                ? Component.literal(
                        "« La chair des morts nourrit mes chaînes. "
                                + "Apporte-m'en et je façonnerai un nouveau Sceau. »"
                )
                : Component.literal(
                        "« Les flammes des Blazes alimentent ma destruction. "
                                + "Leurs bâtons paieront le prix de mon Sceau. »"
                );
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xB0000000);

        int panelWidth = 320;
        int panelHeight = 180;
        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        guiGraphics.fill(x + 4, y + 4,
                x + panelWidth + 4, y + panelHeight + 4,
                0x99000000);
        guiGraphics.fill(x, y,
                x + panelWidth, y + panelHeight,
                0xFF020102);
        guiGraphics.fill(x + 2, y + 2,
                x + panelWidth - 2, y + panelHeight - 2,
                0xFF812D2D);
        guiGraphics.fill(x + 4, y + 4,
                x + panelWidth - 4, y + panelHeight - 4,
                0xFF1A090C);
        guiGraphics.fill(x + 6, y + 6,
                x + panelWidth - 6, y + panelHeight - 6,
                0xFF030204);

        Font font = Minecraft.getInstance().font;
        String merchantName =
                trade == C2SBuyUnchainedSealPacket.SealTrade.ARUHA
                        ? "Aruha — Commerce des Sceaux"
                        : "Rakea — Commerce des Sceaux";

        guiGraphics.drawCenteredString(
                font,
                Component.literal(merchantName),
                this.width / 2,
                y + 15,
                0xFFB34646
        );
        guiGraphics.fill(
                x + 15, y + 30,
                x + panelWidth - 15, y + 31,
                0xFF5A1D27
        );

        var lines = font.split(getDialogue(), panelWidth - 30);

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    x + 15,
                    y + 43 + i * 11,
                    0xFFD2CED0,
                    false
            );
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
