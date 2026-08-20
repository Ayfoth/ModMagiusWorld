package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.dialogue.DialogueData;
import com.magius.world.mod.clan.theme.ClanTheme;
import com.magius.world.mod.clan.theme.ClanThemeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.gui.components.Button;
import com.magius.world.mod.clan.dialogue.DialogueOption;

public class ClanDialogueScreen extends Screen {

    public ClanDialogueScreen(DialogueData dialogue) {
        super(dialogue.getTitle());
        this.dialogue = dialogue;

        visibleCharacters = 0;
        lastCharacterTime = System.currentTimeMillis();
    }
    private final DialogueData dialogue;

    private int visibleCharacters = 0;
    private long lastCharacterTime = 0;
    private static final int CHARACTER_DELAY_MS = 35;

    private static final ResourceLocation ERUDITE_PORTRAIT =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite.png"
            );

    @Override
    protected void init() {
        super.init();

        int panelWidth = 420;
        int panelHeight = 240;

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

                                        option.execute(Minecraft.getInstance().player);
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

        long now = System.currentTimeMillis();

        if (visibleCharacters < dialogue.getText().getString().length()
                && now - lastCharacterTime > CHARACTER_DELAY_MS) {

            visibleCharacters++;
            lastCharacterTime = now;
        }

        int panelWidth = 420;
        int panelHeight = 240;

        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        ClanTheme theme = ClanThemeRegistry.get(
                ResourceLocation.fromNamespaceAndPath(
                        MagiusWorldMod.MOD_ID,
                        "dragonmaid"
                )
        ).orElseThrow(() ->
                new IllegalStateException(
                        "Le thème Dragonmaid n'est pas enregistré."
                )
        );

        ClanThemeRenderer.renderPanel(
                guiGraphics,
                theme,
                x,
                y,
                panelWidth,
                panelHeight
        );
        int contentY = y + 50;
        int contentHeight = 130;

        int portraitX = x + 12;
        int portraitWidth = 110;

        int dialogueX = portraitX + portraitWidth + 10;
        int dialogueWidth = panelWidth - portraitWidth - 34;

        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                portraitX,
                contentY,
                portraitWidth,
                contentHeight
        );


        int padding = 2;

        guiGraphics.blit(
                getPortraitForEmotion(),
                portraitX + padding,
                contentY + padding,
                0,
                0,
                portraitWidth - padding * 2,
                contentHeight - padding * 2,
                128,
                128
        );


        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                dialogueX,
                contentY,
                dialogueWidth,
                contentHeight
        );

        // Titre du clan
        guiGraphics.drawCenteredString(
                this.font,
                dialogue.getTitle(),
                this.width / 2,
                y + 22,
                0xFFFFD978
        );

        int textX = dialogueX + 10;
        int textY = contentY + 10;
        int textWidth = dialogueWidth - 20;

        guiGraphics.drawString(
                this.font,
                dialogue.getSpeaker(),
                textX,
                textY,
                0xFFFFD978
        );

        guiGraphics.fill(
                textX,
                textY + 13,
                textX + textWidth,
                textY + 14,
                0xFFD5A63A
        );

        String fullText = dialogue.getText().getString();

        String visibleText = fullText.substring(
                0,
                Math.min(visibleCharacters, fullText.length())
        );

        guiGraphics.drawWordWrap(
                this.font,
                net.minecraft.network.chat.Component.literal(visibleText),
                textX,
                textY + 24,
                textWidth,
                0xFFF3E8D0
        );



        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    private static final ResourceLocation ERUDITE_NORMAL =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite.png"
            );

    private static final ResourceLocation ERUDITE_HAPPY =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite_happy.png"
            );

    private static final ResourceLocation ERUDITE_SAD =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite_sad.png"
            );

    private static final ResourceLocation ERUDITE_ANGRY =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite_angry.png"
            );

    private static final ResourceLocation ERUDITE_SURPRISED =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/gui/clan/dragonmaid/erudite_surprised.png"
            );
    private ResourceLocation getPortraitForEmotion() {
        return switch (dialogue.getEmotion()) {
            case HAPPY -> ERUDITE_HAPPY;
            case SAD -> ERUDITE_SAD;
            case ANGRY -> ERUDITE_ANGRY;
            case SURPRISED -> ERUDITE_SURPRISED;
            case NORMAL -> ERUDITE_NORMAL;
        };
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
