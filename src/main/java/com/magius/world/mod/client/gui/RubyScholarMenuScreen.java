package com.magius.world.mod.client.gui;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SOpenRubyScholarTradePacket;
import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class RubyScholarMenuScreen extends Screen {

    private static final int PANEL_WIDTH = 250;
    private static final int PANEL_HEIGHT = 135;
    private static final int TITLE_COLOR = 0xE04444;

    private final int villagerId;
    private final QuestState questState;

    public RubyScholarMenuScreen(int villagerId, QuestState questState) {
        super(Component.literal("Érudit Rubis"));
        this.villagerId = villagerId;
        this.questState = questState;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int topY = this.height / 2 - PANEL_HEIGHT / 2;

        addRenderableWidget(Button.builder(
                Component.literal("Quêtes"),
                button -> openQuests()
        ).bounds(centerX - 105, topY + 48, 100, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Commerce"),
                button -> openTrade()
        ).bounds(centerX + 5, topY + 48, 100, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Fermer"),
                button -> onClose()
        ).bounds(centerX - 50, topY + 88, 100, 20).build());
    }

    private void openQuests() {
        if (minecraft != null) {
            minecraft.setScreen(
                    new RubyScholarDialogueScreen(villagerId, questState)
            );
        }
    }

    private void openTrade() {
        onClose();
        ModMessages.sendToServer(
                new C2SOpenRubyScholarTradePacket(villagerId)
        );
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        renderBackground(guiGraphics);

        int centerX = this.width / 2;
        int topY = this.height / 2 - PANEL_HEIGHT / 2;

        guiGraphics.fill(
                centerX - PANEL_WIDTH / 2,
                topY,
                centerX + PANEL_WIDTH / 2,
                topY + PANEL_HEIGHT,
                0xE0100A0A
        );

        guiGraphics.drawCenteredString(
                this.font,
                this.title,
                centerX,
                topY + 16,
                TITLE_COLOR
        );

        guiGraphics.drawCenteredString(
                this.font,
                Component.literal("Que souhaitez-vous faire ?"),
                centerX,
                topY + 31,
                0xE8D8C0
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
