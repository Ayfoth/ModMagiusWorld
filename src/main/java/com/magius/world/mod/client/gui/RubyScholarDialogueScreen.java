package com.magius.world.mod.client.gui;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SAcceptForgottenShardQuestPacket;
import com.magius.world.mod.network.packet.C2SCompleteForgottenShardQuestPacket;
import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class RubyScholarDialogueScreen extends Screen {

    private static final int PANEL_WIDTH = 340;
    private static final int PANEL_HEIGHT = 220;
    private static final int SCREEN_MARGIN = 12;

    private static final int PANEL_BACKGROUND = 0xF0120709;
    private static final int HEADER_BACKGROUND = 0xFF2A0B10;
    private static final int SECTION_BACKGROUND = 0xD91B0D10;
    private static final int INNER_BACKGROUND = 0xB30A0708;

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
    private static final int BUTTON_BOTTOM_MARGIN = 10;

    private final int villagerId;
    private final QuestState questState;

    public RubyScholarDialogueScreen(QuestState questState) {
        this(-1, questState);
    }

    public RubyScholarDialogueScreen(
            int villagerId,
            QuestState questState
    ) {
        super(Component.translatable(
                "quest.magiusworldmod.forgotten_shard.title"
        ));
        this.villagerId = villagerId;
        this.questState = questState;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int buttonY = getPanelTop() + getPanelHeight()
                - BUTTON_HEIGHT - BUTTON_BOTTOM_MARGIN;

        if (questState == QuestState.NOT_STARTED) {
            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.accept"
                    ),
                    button -> acceptQuest()
            ).bounds(centerX - 108, buttonY, 102, BUTTON_HEIGHT).build());

            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.later"
                    ),
                    button -> onClose()
            ).bounds(centerX + 6, buttonY, 102, BUTTON_HEIGHT).build());
            return;
        }

        if (questState == QuestState.STARTED) {
            int totalWidth = 272;
            int buttonX = centerX - totalWidth / 2;

            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.submit_rubies"
                    ),
                    button -> submitRubies()
            ).bounds(buttonX, buttonY, 130, BUTTON_HEIGHT).build());

            addRenderableWidget(Button.builder(
                    Component.translatable("gui.back"),
                    button -> returnToMenu()
            ).bounds(buttonX + 136, buttonY, 65, BUTTON_HEIGHT).build());

            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.close"
                    ),
                    button -> onClose()
            ).bounds(buttonX + 207, buttonY, 65, BUTTON_HEIGHT).build());
            return;
        }

        addRenderableWidget(Button.builder(
                Component.translatable("gui.back"),
                button -> returnToMenu()
        ).bounds(centerX - 108, buttonY, 102, BUTTON_HEIGHT).build());

        addRenderableWidget(Button.builder(
                Component.translatable(
                        "dialogue.magiusworldmod.button.close"
                ),
                button -> onClose()
        ).bounds(centerX + 6, buttonY, 102, BUTTON_HEIGHT).build());
    }

    private void returnToMenu() {
        if (minecraft == null || villagerId < 0) {
            onClose();
            return;
        }

        minecraft.setScreen(
                new RubyScholarMenuScreen(villagerId, questState)
        );
    }

    private void acceptQuest() {
        ModMessages.sendToServer(
                new C2SAcceptForgottenShardQuestPacket()
        );
        onClose();
    }

    private void submitRubies() {
        if (villagerId < 0) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SCompleteForgottenShardQuestPacket(villagerId)
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

        int panelWidth = getPanelWidth();
        int panelHeight = getPanelHeight();
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;
        int panelRight = panelLeft + panelWidth;
        int panelBottom = panelTop + panelHeight;

        renderJournalPanel(
                guiGraphics,
                panelLeft,
                panelTop,
                panelRight,
                panelBottom
        );
        renderHeader(guiGraphics, panelLeft, panelTop, panelRight);
        renderDialogueSection(
                guiGraphics,
                panelLeft,
                panelTop,
                panelRight
        );
        renderQuestDetails(
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

    private void renderHeader(
            GuiGraphics guiGraphics,
            int panelLeft,
            int panelTop,
            int panelRight
    ) {
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

        renderStateBadge(guiGraphics, panelTop, panelRight);

        guiGraphics.drawCenteredString(
                this.font,
                this.title,
                (panelLeft + panelRight) / 2,
                panelTop + 40,
                GOLD_TEXT
        );
    }

    private void renderStateBadge(
            GuiGraphics guiGraphics,
            int panelTop,
            int panelRight
    ) {
        Component status = getStatusText();
        int statusWidth = this.font.width(status) + 12;
        int statusLeft = panelRight - statusWidth - 12;
        int statusTop = panelTop + 11;
        int statusColor = getStatusColor();

        guiGraphics.fill(
                statusLeft,
                statusTop,
                panelRight - 12,
                statusTop + 15,
                0xD90A0708
        );
        drawBorder(
                guiGraphics,
                statusLeft,
                statusTop,
                panelRight - 12,
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

    private void renderDialogueSection(
            GuiGraphics guiGraphics,
            int panelLeft,
            int panelTop,
            int panelRight
    ) {
        int sectionLeft = panelLeft + 12;
        int sectionTop = panelTop + 62;
        int sectionRight = panelRight - 12;
        int sectionBottom = panelTop + 124;

        guiGraphics.fill(
                sectionLeft,
                sectionTop,
                sectionRight,
                sectionBottom,
                SECTION_BACKGROUND
        );
        drawBorder(
                guiGraphics,
                sectionLeft,
                sectionTop,
                sectionRight,
                sectionBottom,
                RUBY_BORDER
        );

        guiGraphics.drawString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.section.dialogue"
                ),
                sectionLeft + 8,
                sectionTop + 6,
                RUBY_ACCENT,
                false
        );

        Component dialogue = getDialogueText();
        List<FormattedCharSequence> lines = this.font.split(
                dialogue,
                sectionRight - sectionLeft - 16
        );

        int textY = sectionTop + 19;
        int maxTextY = sectionBottom - 10;

        for (FormattedCharSequence line : lines) {
            if (textY > maxTextY) {
                break;
            }

            guiGraphics.drawString(
                    this.font,
                    line,
                    sectionLeft + 8,
                    textY,
                    TEXT_COLOR,
                    false
            );
            textY += 10;
        }
    }

    private void renderQuestDetails(
            GuiGraphics guiGraphics,
            int panelLeft,
            int panelTop,
            int panelRight,
            int panelBottom
    ) {
        int detailsTop = panelTop + 132;
        int detailsBottom = panelBottom
                - BUTTON_HEIGHT - BUTTON_BOTTOM_MARGIN - 8;
        int rewardWidth = 82;
        int gap = 8;
        int objectiveLeft = panelLeft + 12;
        int rewardRight = panelRight - 12;
        int rewardLeft = rewardRight - rewardWidth;
        int objectiveRight = rewardLeft - gap;

        renderObjectiveBox(
                guiGraphics,
                objectiveLeft,
                detailsTop,
                objectiveRight,
                detailsBottom
        );
        renderRewardBox(
                guiGraphics,
                rewardLeft,
                detailsTop,
                rewardRight,
                detailsBottom
        );
    }

    private void renderObjectiveBox(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom
    ) {
        guiGraphics.fill(left, top, right, bottom, INNER_BACKGROUND);
        drawBorder(guiGraphics, left, top, right, bottom, RUBY_BORDER);

        guiGraphics.drawString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.section.objective"
                ),
                left + 7,
                top + 6,
                GOLD_TEXT,
                false
        );

        List<FormattedCharSequence> objectiveLines = this.font.split(
                Component.translatable(
                        "quest.magiusworldmod.forgotten_shard.objective"
                ),
                right - left - 14
        );

        int textY = top + 19;
        for (FormattedCharSequence line : objectiveLines) {
            if (textY > bottom - 10) {
                break;
            }

            guiGraphics.drawString(
                    this.font,
                    line,
                    left + 7,
                    textY,
                    TEXT_COLOR,
                    false
            );
            textY += 10;
        }
    }

    private void renderRewardBox(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int right,
            int bottom
    ) {
        guiGraphics.fill(left, top, right, bottom, INNER_BACKGROUND);
        drawBorder(guiGraphics, left, top, right, bottom, GOLD_BORDER);

        int centerX = (left + right) / 2;
        guiGraphics.drawCenteredString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.section.reward"
                ),
                centerX,
                top + 6,
                GOLD_TEXT
        );
        guiGraphics.drawCenteredString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.reward.forgotten_shard"
                ),
                centerX,
                top + 24,
                SUCCESS_COLOR
        );
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

    private Component getStatusText() {
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

    private int getStatusColor() {
        return switch (questState) {
            case NOT_STARTED -> GOLD_TEXT;
            case STARTED -> ACTIVE_COLOR;
            case COMPLETED -> SUCCESS_COLOR;
        };
    }

    private Component getDialogueText() {
        return switch (questState) {
            case NOT_STARTED -> Component.translatable(
                    "dialogue.magiusworldmod.ruby_scholar.intro"
            );
            case STARTED -> Component.translatable(
                    "dialogue.magiusworldmod.ruby_scholar.active"
            );
            case COMPLETED -> Component.translatable(
                    "dialogue.magiusworldmod.ruby_scholar.completed"
            );
        };
    }

    private int getPanelWidth() {
        return Math.min(PANEL_WIDTH, this.width - SCREEN_MARGIN * 2);
    }

    private int getPanelHeight() {
        return Math.min(PANEL_HEIGHT, this.height - SCREEN_MARGIN * 2);
    }

    private int getPanelTop() {
        return (this.height - getPanelHeight()) / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
