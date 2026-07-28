package com.magius.world.mod.client.gui;

import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class RubyScholarDialogueScreen extends Screen {

    private static final int TEXT_WIDTH = 280;
    private static final int TEXT_COLOR = 0xE8D8C0;
    private static final int TITLE_COLOR = 0xE04444;

    private final QuestState questState;

    public RubyScholarDialogueScreen(QuestState questState) {
        super(Component.translatable(
                "quest.magiusworldmod.forgotten_shard.title"
        ));
        this.questState = questState;
    }

    @Override
    protected void init() {
        int buttonY = this.height / 2 + 65;

        if (questState == QuestState.NOT_STARTED) {
            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.accept"
                    ),
                    button -> acceptQuest()
            ).bounds(this.width / 2 - 105, buttonY, 100, 20).build());

            addRenderableWidget(Button.builder(
                    Component.translatable(
                            "dialogue.magiusworldmod.button.later"
                    ),
                    button -> onClose()
            ).bounds(this.width / 2 + 5, buttonY, 100, 20).build());
            return;
        }

        addRenderableWidget(Button.builder(
                Component.translatable(
                        "dialogue.magiusworldmod.button.close"
                ),
                button -> onClose()
        ).bounds(this.width / 2 - 50, buttonY, 100, 20).build());
    }

    private void acceptQuest() {
        if (minecraft != null
                && minecraft.player != null
                && minecraft.player.connection != null) {
            minecraft.player.connection.sendCommand("magiusquest start");
        }

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

        int centerX = this.width / 2;
        int topY = this.height / 2 - 90;

        guiGraphics.fill(
                centerX - 160,
                topY,
                centerX + 160,
                topY + 175,
                0xE0100A0A
        );

        guiGraphics.drawCenteredString(
                this.font,
                Component.translatable(
                        "dialogue.magiusworldmod.ruby_scholar.name"
                ),
                centerX,
                topY + 14,
                TITLE_COLOR
        );

        guiGraphics.drawCenteredString(
                this.font,
                this.title,
                centerX,
                topY + 32,
                0xFFFFFF
        );

        Component dialogue = getDialogueText();
        List<FormattedCharSequence> lines =
                this.font.split(dialogue, TEXT_WIDTH);

        int textY = topY + 55;
        for (FormattedCharSequence line : lines) {
            guiGraphics.drawCenteredString(
                    this.font,
                    line,
                    centerX,
                    textY,
                    TEXT_COLOR
            );
            textY += 11;
        }

        if (questState == QuestState.STARTED) {
            guiGraphics.drawCenteredString(
                    this.font,
                    Component.translatable(
                            "quest.magiusworldmod.forgotten_shard.objective"
                    ),
                    centerX,
                    topY + 120,
                    0xFFCB6B
            );
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
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
}
