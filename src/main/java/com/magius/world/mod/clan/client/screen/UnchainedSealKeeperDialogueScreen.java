package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedChainReactionQuest;
import com.magius.world.mod.clan.quest.unchained.UnchainedTwinsQuest;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimUnchainedChainReactionQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SClaimUnchainedTwinsQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SStartUnchainedChainReactionQuestPacket;
import com.magius.world.mod.network.packet.C2SStartUnchainedTwinsQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UnchainedSealKeeperDialogueScreen extends Screen {

    private QuestStatus twinsStatus = QuestStatus.NOT_STARTED;
    private QuestStatus chainReactionStatus = QuestStatus.NOT_STARTED;

    public UnchainedSealKeeperDialogueScreen() {
        super(Component.literal("Gardien des Sceaux"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null) {
            QuestManager.get(minecraft.player).ifPresent(data ->
            {
                twinsStatus = QuestManager.getStatus(
                        data,
                        UnchainedTwinsQuest.ID
                );
                chainReactionStatus = QuestManager.getStatus(
                        data,
                        UnchainedChainReactionQuest.ID
                );
            }
            );
        }

        if (twinsStatus != QuestStatus.REWARDED) {
            initTwinsButton(centerX, centerY);
        } else {
            initChainReactionButton(centerX, centerY);
        }
    }

    private void initTwinsButton(int centerX, int centerY) {
        switch (twinsStatus) {
            case NOT_STARTED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Affronter les sceaux jumeaux",
                    button -> {
                        ModMessages.sendToServer(
                                new C2SStartUnchainedTwinsQuestPacket()
                        );
                        onClose();
                    }
            );

            case IN_PROGRESS -> addDialogueButton(
                    centerX,
                    centerY,
                    "Je briserai leurs chaînes",
                    button -> onClose()
            );

            case COMPLETED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Recevoir la récompense",
                    button -> {
                        ModMessages.sendToServer(
                                new C2SClaimUnchainedTwinsQuestRewardPacket()
                        );
                        onClose();
                    }
            );

            case REWARDED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Les jumeaux sont libres",
                    button -> onClose()
            );
        }
    }

    private void initChainReactionButton(int centerX, int centerY) {
        switch (chainReactionStatus) {
            case NOT_STARTED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Déclencher la Réaction en chaîne",
                    button -> {
                        ModMessages.sendToServer(
                                new C2SStartUnchainedChainReactionQuestPacket()
                        );
                        onClose();
                    }
            );

            case IN_PROGRESS -> addDialogueButton(
                    centerX,
                    centerY,
                    "Je préparerai les quatre sceaux",
                    button -> onClose()
            );

            case COMPLETED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Recevoir la récompense",
                    button -> {
                        ModMessages.sendToServer(
                                new C2SClaimUnchainedChainReactionQuestRewardPacket()
                        );
                        onClose();
                    }
            );

            case REWARDED -> addDialogueButton(
                    centerX,
                    centerY,
                    "La destruction est maîtrisée",
                    button -> onClose()
            );
        }
    }

    private void addDialogueButton(
            int centerX,
            int centerY,
            String label,
            Button.OnPress onPress
    ) {
        addRenderableWidget(
                Button.builder(Component.literal(label), onPress)
                        .bounds(centerX - 110, centerY + 45, 220, 20)
                        .build()
        );
    }

    private Component getDialogue() {
        if (twinsStatus != QuestStatus.REWARDED) {
            return getTwinsDialogue();
        }

        return switch (chainReactionStatus) {
            case NOT_STARTED -> Component.literal(
                    "« Aruha et Rakea peuvent désormais façonner leurs sceaux. "
                            + "Il est temps d'en libérer toute la puissance. "
                            + "Entoure un Sceau du Désastre de trois autres sceaux. »"
            );
            case IN_PROGRESS -> Component.literal(
                    "« Place au moins trois sceaux à moins de quatre blocs du "
                            + "Sceau du Désastre, puis brise ce dernier. »"
            );
            case COMPLETED -> Component.literal(
                    "« La réaction a dévoré toutes les chaînes à la fois. "
                            + "Tu as survécu à la véritable force des Déchaînés. »"
            );
            case REWARDED -> Component.literal(
                    "« Tu sais désormais provoquer la destruction sans "
                            + "te laisser consumer par elle. »"
            );
        };
    }

    private Component getTwinsDialogue() {
        return switch (twinsStatus) {
            case NOT_STARTED -> Component.literal(
                    "« Le premier verrou est tombé. Mais deux chaînes "
                            + "retiennent encore Aruha et Rakea. Trouve leurs "
                            + "sceaux et libère leur pouvoir destructeur. »"
            );
            case IN_PROGRESS -> Component.literal(
                    "« Aruha et Rakea sont liés l'un à l'autre. Tant que "
                            + "leurs deux sceaux subsistent, leur puissance "
                            + "reste prisonnière. »"
            );
            case COMPLETED -> Component.literal(
                    "« Les deux chaînes ont cédé. Tu as survécu à leur "
                            + "réveil et prouvé que tu pouvais supporter "
                            + "la destruction des Déchaînés. »"
            );
            case REWARDED -> Component.literal(
                    "« Les Jumeaux de la Destruction sont libres. Leur "
                            + "puissance répond désormais à l'appel du clan. »"
            );
        };
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.fill(0, 0, this.width, this.height, 0xB0000000);

        int panelWidth = 300;
        int panelHeight = 170;
        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        guiGraphics.fill(
                x + 4, y + 4,
                x + panelWidth + 4, y + panelHeight + 4,
                0x99000000
        );
        guiGraphics.fill(
                x, y,
                x + panelWidth, y + panelHeight,
                0xFF020102
        );
        guiGraphics.fill(
                x + 2, y + 2,
                x + panelWidth - 2, y + panelHeight - 2,
                0xFF812D2D
        );
        guiGraphics.fill(
                x + 4, y + 4,
                x + panelWidth - 4, y + panelHeight - 4,
                0xFF1A090C
        );
        guiGraphics.fill(
                x + 6, y + 6,
                x + panelWidth - 6, y + panelHeight - 6,
                0xFF030204
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Gardien des Sceaux"),
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
        int textY = y + 43;

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    x + 15,
                    textY + i * 11,
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
