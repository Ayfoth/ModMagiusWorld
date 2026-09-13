package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedPrisonQuest;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimUnchainedFirstQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SJoinUnchainedClanPacket;
import com.magius.world.mod.network.packet.C2SStartUnchainedFirstQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class UnchainedDialogueScreen extends Screen {

    private static final ResourceLocation UNCHAINED_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained"
            );

    private QuestStatus questStatus = QuestStatus.NOT_STARTED;
    private boolean unchainedActive = false;

    public UnchainedDialogueScreen() {
        super(Component.literal("Émissaire déchaîné"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null) {
            QuestManager.get(minecraft.player).ifPresent(data ->
                    questStatus = QuestManager.getStatus(
                            data,
                            UnchainedPrisonQuest.ID
                    )
            );

            ClanManager.get(minecraft.player).ifPresent(clanData ->
                    unchainedActive = UNCHAINED_ID.equals(
                            clanData.getActiveClanId()
                    )
            );
        }

        switch (questStatus) {
            case NOT_STARTED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Briser le premier sceau",
                    button -> {
                        ModMessages.sendToServer(
                                new C2SStartUnchainedFirstQuestPacket()
                        );
                        onClose();
                    }
            );

            case IN_PROGRESS -> addDialogueButton(
                    centerX,
                    centerY,
                    "Je trouverai ce sceau",
                    button -> onClose()
            );

            case COMPLETED -> {
                if (!unchainedActive) {
                    addDialogueButton(
                            centerX,
                            centerY,
                            "Rejoindre les Déchaînés",
                            button -> {
                                ModMessages.sendToServer(
                                        new C2SJoinUnchainedClanPacket()
                                );
                                onClose();
                            }
                    );
                } else {
                    addDialogueButton(
                            centerX,
                            centerY,
                            "Recevoir la récompense",
                            button -> {
                                ModMessages.sendToServer(
                                        new C2SClaimUnchainedFirstQuestRewardPacket()
                                );
                                onClose();
                            }
                    );
                }
            }

            case REWARDED -> addDialogueButton(
                    centerX,
                    centerY,
                    "Les chaînes sont rompues",
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
                Button.builder(
                                Component.literal(label),
                                onPress
                        )
                        .bounds(
                                centerX - 100,
                                centerY + 45,
                                200,
                                20
                        )
                        .build()
        );
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.fill(
                0,
                0,
                this.width,
                this.height,
                0xB0000000
        );

        int panelWidth = 280;
        int panelHeight = 170;
        int x = (this.width - panelWidth) / 2;
        int y = (this.height - panelHeight) / 2;

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x99000000
        );

        // Contour noir charbon.
        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF020102
        );

        // Liseré rouge sang.
        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFF812D2D
        );

        // Cadre bordeaux noirci.
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF1A090C
        );

        // Fond noir violacé.
        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF030204
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Émissaire déchaîné"),
                this.width / 2,
                y + 15,
                0xFFB34646
        );

        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF5A1D27
        );

        Component dialogue = Component.literal(
                "« Écoute... Sous ces terres sommeille une abomination "
                        + "que nul n'a osé libérer. Trouve le premier Sceau "
                        + "enchaîné et brise-le. Si tu survis à ce que tu "
                        + "auras réveillé, les Déchaînés t'accepteront. »"
        );

        var lines = font.split(
                dialogue,
                panelWidth - 30
        );

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

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
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
