package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimSwordsoulFirstQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SJoinSwordsoulClanPacket;
import com.magius.world.mod.network.packet.C2SStartSwordsoulFirstQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulDialogueScreen extends Screen {

    public SwordsoulDialogueScreen() {
        super(
                Component.literal(
                        "Émissaire Swordsoul"
                )
        );
    }
    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private QuestStatus questStatus =
            QuestStatus.NOT_STARTED;

    private boolean swordsoulActive = false;

    @Override
    protected void init() {

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player != null) {

            QuestManager.get(minecraft.player)
                    .ifPresent(data -> {

                        questStatus =
                                QuestManager.getStatus(
                                        data,
                                        QUEST_ID
                                );
                    });

            ClanManager.get(minecraft.player)
                    .ifPresent(clanData -> {

                        swordsoulActive =
                                SWORDSOUL_ID.equals(
                                        clanData.getActiveClanId()
                                );
                    });
        }

        switch (questStatus) {

            case NOT_STARTED ->
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal("Accepter la quête"),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SStartSwordsoulFirstQuestPacket()
                                                );

                                                onClose();
                                            }
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );

            case IN_PROGRESS ->
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal("Je retrouverai la lame"),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );

            case COMPLETED -> {

                if (!swordsoulActive) {

                    addRenderableWidget(
                            Button.builder(
                                            Component.literal("Rejoindre Swordsoul"),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SJoinSwordsoulClanPacket()
                                                );

                                                onClose();
                                            }
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );

                } else {

                    addRenderableWidget(
                            Button.builder(
                                            Component.literal("Recevoir la récompense"),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SClaimSwordsoulFirstQuestRewardPacket()
                                                );

                                                onClose();
                                            }
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
            }

            case REWARDED ->
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal("À bientôt"),
                                            button -> onClose()
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

        
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();

        Font font =
                minecraft.font;

        guiGraphics.fill(
                0,
                0,
                this.width,
                this.height,
                0x99000000
        );

        int panelWidth = 280;
        int panelHeight = 170;

        int x =
                (this.width - panelWidth) / 2;

        int y =
                (this.height - panelHeight) / 2;

        // Ombre
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x88000000
        );

        // Contour sombre
        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF02080D
        );

        // Bleu glacier
        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFF8DD9F2
        );

        // Bleu acier
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF315C88
        );

        // Fond bleu nuit
        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF071521
        );

        // Titre
        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Émissaire Swordsoul"
                ),
                this.width / 2,
                y + 15,
                0xFF8DD9F2
        );

        // Séparateur
        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF315C88
        );

        Component dialogue =
                Component.literal(
                        "« Ton pas est lourd, voyageur... mais ton esprit "
                                + "ne l'est peut-être pas. Au-delà des hauteurs "
                                + "repose un sanctuaire où l'acier répond à l'âme. "
                                + "Si tu souhaites comprendre notre voie, cherche Mo Ye. »"
                );

        var lines =
                font.split(
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
                    0xFFDCECF2,
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