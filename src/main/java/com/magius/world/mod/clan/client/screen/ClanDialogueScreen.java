package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.dialogue.DialogueData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import net.minecraft.client.gui.components.Button;
import com.magius.world.mod.clan.dialogue.DialogueOption;

public class ClanDialogueScreen extends Screen {

    public ClanDialogueScreen(DialogueData dialogue) {
        super(dialogue.getTitle());
        this.dialogue = dialogue;
    }
    private final DialogueData dialogue;

    @Override
    protected void init() {
        super.init();

        int panelWidth = 360;
        int panelHeight = 210;

        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        int buttonWidth = 90;
        int buttonHeight = 20;
        int spacing = 8;

        int optionCount = dialogue.getOptions().size();

        int totalWidth = optionCount * buttonWidth
                + Math.max(0, optionCount - 1) * spacing;

        int startX = this.width / 2 - totalWidth / 2;
        int buttonY = y + panelHeight - buttonHeight - 12;

        for (int i = 0; i < optionCount; i++) {
            DialogueOption option = dialogue.getOptions().get(i);

            int buttonX = startX + i * (buttonWidth + spacing);

            this.addRenderableWidget(
                    Button.builder(
                                    option.getText(),
                                    button -> {
                                        if (option.getNextDialogue() != null) {
                                            Minecraft.getInstance().setScreen(
                                                    new ClanDialogueScreen(
                                                            option.getNextDialogue()
                                                    )
                                            );
                                        }

                                        option.execute();
                                    }
                            )
                            .bounds(
                                    buttonX,
                                    buttonY,
                                    buttonWidth,
                                    buttonHeight
                            )
                            .build()
            );
        }
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        this.renderBackground(guiGraphics);

        int panelWidth = 300;
        int panelHeight = 170;

        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        ClanThemeRenderer.renderPanel(
                guiGraphics,
                x,
                y,
                panelWidth,
                panelHeight
        );

        // Titre du clan
        guiGraphics.drawCenteredString(
                this.font,
                dialogue.getTitle(),
                this.width / 2,
                y + 12,
                0xFFF4D67A
        );

        int textX = x + 16;
        int textY = y + 40;
        int textWidth = panelWidth - 32;
        int textHeight = 105;

        // Zone sombre du dialogue
        guiGraphics.fill(
                textX,
                textY,
                textX + textWidth,
                textY + textHeight,
                0x66000000
        );

        // Nom du PNJ
        guiGraphics.drawString(
                this.font,
                dialogue.getSpeaker(),
                textX + 8,
                textY + 8,
                0xFFF4D67A
        );

        // Texte avec retour automatique à la ligne
        guiGraphics.drawWordWrap(
                this.font,
                dialogue.getText(),
                textX + 8,
                textY + 24,
                textWidth - 16,
                0xFFFFFFFF
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
