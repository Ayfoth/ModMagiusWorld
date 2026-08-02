package com.magius.world.mod.client.gui;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SOpenRubyScholarTradePacket;
import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class RubyScholarMenuScreen extends Screen {

    private static final int PANEL_WIDTH = 340;
    private static final int PANEL_HEIGHT = 220;
    private static final int SCREEN_MARGIN = 12;

    private static final int PANEL_BACKGROUND = 0xF0120709;
    private static final int HEADER_BACKGROUND = 0xFF2A0B10;
    private static final int CARD_BACKGROUND = 0xD91B0D10;

    private static final int RUBY_BORDER = 0xFF651725;
    private static final int RUBY_ACCENT = 0xFFD13B52;
    private static final int GOLD_BORDER = 0xFFD09A4A;
    private static final int GOLD_TEXT = 0xFFF0C56A;
    private static final int TITLE_COLOR = 0xFFFF6473;
    private static final int TEXT_COLOR = 0xFFE9D9C5;
    private static final int MUTED_TEXT_COLOR = 0xFFB99F91;
    private static final int SUCCESS_COLOR = 0xFF79C96B;
    private static final int ACTIVE_COLOR = 0xFFF2B84B;

    private static final int BUTTON_HEIGHT = 20;
    private static final int INPUT_COOLDOWN_TICKS = 10;

    private final int villagerId;
    private final QuestState questState;
    private Button questsButton;
    private Button tradeButton;
    private int inputCooldown;

    public RubyScholarMenuScreen(int villagerId, QuestState questState) {
        super(Component.literal("Érudit Rubis"));
        this.villagerId = villagerId;
        this.questState = questState;
    }

    @Override
    protected void init() {
        int panelLeft = getPanelLeft();
        int panelTop = getPanelTop();
        int panelRight = panelLeft + getPanelWidth();
        int panelBottom = panelTop + getPanelHeight();
        int centerX = (panelLeft + panelRight) / 2;
        int gap = 8;
        int cardLeft = panelLeft + 12;
        int cardRight = panelRight - 12;
        int cardWidth = (cardRight - cardLeft - gap) / 2;
        int cardTop = panelTop + 80;
        int closeY = panelBottom - BUTTON_HEIGHT - 11;
        int cardBottom = closeY - 12;
        int optionButtonY = cardBottom - BUTTON_HEIGHT - 7;
        int optionButtonWidth = Math.min(130, cardWidth - 16);
        this.inputCooldown = INPUT_COOLDOWN_TICKS;

        this.questsButton = addRenderableWidget(Button.builder(
                Component.literal("Quêtes"),
                button -> openQuests()
        ).bounds(
                cardLeft + (cardWidth - optionButtonWidth) / 2,
                optionButtonY,
                optionButtonWidth,
                BUTTON_HEIGHT
        ).build());

        this.tradeButton = addRenderableWidget(Button.builder(
                Component.literal("Commerce"),
                button -> openTrade()
        ).bounds(
                cardLeft + cardWidth + gap
                        + (cardWidth - optionButtonWidth) / 2,
                optionButtonY,
                optionButtonWidth,
                BUTTON_HEIGHT
        ).build());

        this.questsButton.active = false;
        this.tradeButton.active = false;

        addRenderableWidget(Button.builder(
                Component.translatable(
                        "dialogue.magiusworldmod.button.close"
                ),
                button -> onClose()
        ).bounds(centerX - 55, closeY, 110, BUTTON_HEIGHT).build());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.inputCooldown > 0) {
            this.inputCooldown--;
        }

        if (this.inputCooldown == 0) {
            this.questsButton.active = true;
            this.tradeButton.active = true;
        }
    }

    private void openQuests() {
        if (this.inputCooldown == 0 && minecraft != null) {
            minecraft.setScreen(
                    new RubyScholarDialogueScreen(
                            villagerId,
                            questState
                    )
            );
        }
    }

    private void openTrade() {
        if (this.inputCooldown != 0) {
            return;
        }

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

        int panelLeft = getPanelLeft();
        int panelTop = getPanelTop();
        int panelRight = panelLeft + getPanelWidth();
        int panelBottom = panelTop + getPanelHeight();
        int centerX = (panelLeft + panelRight) / 2;

        renderJournalPanel(
                guiGraphics,
                panelLeft,
                panelTop,
                panelRight,
                panelBottom
        );

        guiGraphics.drawString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.ruby_scholar.name"
                ),
                panelLeft + 14,
                panelTop + 10,
                TITLE_COLOR,
                false
        );

        guiGraphics.drawString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.ruby_scholar.role"
                ),
                panelLeft + 14,
                panelTop + 23,
                MUTED_TEXT_COLOR,
                false
        );

        guiGraphics.drawCenteredString(
                this.font,
                Component.literal("Que souhaitez-vous faire ?"),
                centerX,
                panelTop + 63,
                GOLD_TEXT
        );

        renderOptionCards(
                guiGraphics,
                panelLeft,
                panelTop,
                panelRight,
                panelBottom
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void renderJournalPanel(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom
    ) {
        guiGraphics.fill(left, top, right, bottom, PANEL_BACKGROUND);
        drawBorder(guiGraphics, left, top, right, bottom, RUBY_BORDER);
        drawBorder(
                guiGraphics,
                left + 2,
                top + 2,
                right - 2,
                bottom - 2,
                GOLD_BORDER
        );

        guiGraphics.fill(
                left + 4,
                top + 4,
                right - 4,
                top + 55,
                HEADER_BACKGROUND
        );
        guiGraphics.fill(
                left + 4,
                top + 53,
                right - 4,
                top + 55,
                RUBY_ACCENT
        );

        renderCornerOrnament(guiGraphics, left + 5, top + 5, false);
        renderCornerOrnament(guiGraphics, right - 5, top + 5, true);
        renderCornerOrnament(guiGraphics, left + 5, bottom - 5, false);
        renderCornerOrnament(guiGraphics, right - 5, bottom - 5, true);
    }

    private void renderOptionCards(
            GuiGraphics guiGraphics,
            int panelLeft,
            int panelTop,
            int panelRight,
            int panelBottom
    ) {
        int gap = 8;
        int cardLeft = panelLeft + 12;
        int cardRight = panelRight - 12;
        int cardWidth = (cardRight - cardLeft - gap) / 2;
        int cardTop = panelTop + 80;
        int closeY = panelBottom - BUTTON_HEIGHT - 11;
        int cardBottom = closeY - 12;

        renderQuestCard(
                guiGraphics,
                cardLeft,
                cardTop,
                cardLeft + cardWidth,
                cardBottom
        );
        renderTradeCard(
                guiGraphics,
                cardLeft + cardWidth + gap,
                cardTop,
                cardRight,
                cardBottom
        );
    }

    private void renderQuestCard(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom
    ) {
        renderCardBackground(
                guiGraphics,
                left,
                top,
                right,
                bottom,
                RUBY_BORDER
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Quêtes"),
                left + 8,
                top + 8,
                RUBY_ACCENT,
                false
        );
        renderQuestStatus(guiGraphics, top, right);

        guiGraphics.drawString(
                this.font,
                Component.literal("Consulter vos quêtes"),
                left + 8,
                top + 27,
                TEXT_COLOR,
                false
        );
        guiGraphics.drawString(
                this.font,
                Component.literal("et votre progression."),
                left + 8,
                top + 38,
                MUTED_TEXT_COLOR,
                false
        );
    }

    private void renderTradeCard(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom
    ) {
        renderCardBackground(
                guiGraphics,
                left,
                top,
                right,
                bottom,
                GOLD_BORDER
        );

        guiGraphics.drawString(
                this.font,
                Component.literal("Commerce"),
                left + 8,
                top + 8,
                GOLD_TEXT,
                false
        );
        guiGraphics.drawString(
                this.font,
                Component.literal("Échanger des ressources"),
                left + 8,
                top + 27,
                TEXT_COLOR,
                false
        );
        guiGraphics.drawString(
                this.font,
                Component.literal("avec l'Érudit rubis."),
                left + 8,
                top + 38,
                MUTED_TEXT_COLOR,
                false
        );
    }

    private void renderCardBackground(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom,
            int borderColor
    ) {
        guiGraphics.fill(left, top, right, bottom, CARD_BACKGROUND);
        drawBorder(guiGraphics, left, top, right, bottom, borderColor);
    }

    private void renderQuestStatus(
            GuiGraphics guiGraphics,
            int cardTop,
            int cardRight
    ) {
        Component status = getQuestStatusText();
        int statusWidth = this.font.width(status) + 8;
        int statusLeft = cardRight - statusWidth - 6;
        int statusTop = cardTop + 6;
        int statusColor = getQuestStatusColor();

        guiGraphics.fill(
                statusLeft,
                statusTop,
                cardRight - 6,
                statusTop + 15,
                0xD90A0708
        );
        drawBorder(
                guiGraphics,
                statusLeft,
                statusTop,
                cardRight - 6,
                statusTop + 15,
                statusColor
        );
        guiGraphics.drawCenteredString(
                this.font,
                status,
                statusLeft + statusWidth / 2,
                statusTop + 4,
                statusColor
        );
    }

    private Component getQuestStatusText() {
        return switch (questState) {
            case NOT_STARTED -> Component.translatable(
                    "dialogue.magiusworldmod.status.available"
            );
            case STARTED -> Component.translatable(
                    "dialogue.magiusworldmod.status.active"
            );
            case COMPLETED -> Component.translatable(
                    "dialogue.magiusworldmod.status.completed"
            );
        };
    }

    private int getQuestStatusColor() {
        return switch (questState) {
            case NOT_STARTED -> GOLD_TEXT;
            case STARTED -> ACTIVE_COLOR;
            case COMPLETED -> SUCCESS_COLOR;
        };
    }

    private void renderCornerOrnament(
            GuiGraphics guiGraphics,
            int x,
            int y,
            boolean pointsLeft
    ) {
        int direction = pointsLeft ? -1 : 1;
        int horizontalEnd = x + direction * 9;
        int verticalEnd = y + (y < this.height / 2 ? 9 : -9);

        guiGraphics.fill(
                Math.min(x, horizontalEnd),
                y - 1,
                Math.max(x, horizontalEnd) + 1,
                y + 1,
                GOLD_BORDER
        );
        guiGraphics.fill(
                x - 1,
                Math.min(y, verticalEnd),
                x + 1,
                Math.max(y, verticalEnd) + 1,
                GOLD_BORDER
        );
    }

    private void drawBorder(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom,
            int color
    ) {
        guiGraphics.fill(left, top, right, top + 1, color);
        guiGraphics.fill(left, bottom - 1, right, bottom, color);
        guiGraphics.fill(left, top, left + 1, bottom, color);
        guiGraphics.fill(right - 1, top, right, bottom, color);
    }

    private int getPanelWidth() {
        return Math.min(PANEL_WIDTH, this.width - SCREEN_MARGIN * 2);
    }

    private int getPanelHeight() {
        return Math.min(PANEL_HEIGHT, this.height - SCREEN_MARGIN * 2);
    }

    private int getPanelLeft() {
        return (this.width - getPanelWidth()) / 2;
    }

    private int getPanelTop() {
        return (this.height - getPanelHeight()) / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
