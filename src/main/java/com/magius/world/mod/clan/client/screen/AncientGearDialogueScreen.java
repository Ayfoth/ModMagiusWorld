package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.quest.ancientgear.AncientGearFirstQuest;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearSecondQuest;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearThirdQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimAncientGearFirstQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SStartAncientGearFirstQuestPacket;
import com.magius.world.mod.network.packet.C2SStartAncientGearSecondQuestPacket;
import com.magius.world.mod.network.packet.C2SClaimAncientGearSecondQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SStartAncientGearThirdQuestPacket;
import com.magius.world.mod.network.packet.C2SClaimAncientGearThirdQuestRewardPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AncientGearDialogueScreen extends Screen {

    private static final int PANEL_WIDTH = 320;
    private static final int PANEL_HEIGHT = 130;
    private QuestStatus questStatus = QuestStatus.NOT_STARTED;
    private QuestStatus secondQuestStatus = QuestStatus.NOT_STARTED;
    private QuestStatus thirdQuestStatus = QuestStatus.NOT_STARTED;

    public AncientGearDialogueScreen() {
        super(Component.literal("Émissaire mécaniste"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player != null) {
            QuestManager.get(minecraft.player).ifPresent(data -> {
                    questStatus = QuestManager.getStatus(
                            data,
                            AncientGearFirstQuest.ID
                    );
                    secondQuestStatus = QuestManager.getStatus(data, AncientGearSecondQuest.ID);
                    thirdQuestStatus = QuestManager.getStatus(data, AncientGearThirdQuest.ID);
            });
        }

        String buttonLabel = switch (questStatus) {
            case NOT_STARTED -> "Réveiller la cité";
            case IN_PROGRESS -> "Je retrouverai Geartown";
            case COMPLETED -> "Recevoir 50 prestige";
            case REWARDED -> switch (secondQuestStatus) {
                case NOT_STARTED -> "Réveiller le premier automate";
                case IN_PROGRESS -> "Je construirai l'automate";
                case COMPLETED -> "Recevoir 100 prestige";
                case REWARDED -> switch (thirdQuestStatus) {
                    case NOT_STARTED -> "Rallumer le cœur de Geartown";
                    case IN_PROGRESS -> "Je réactiverai le réacteur";
                    case COMPLETED -> "Recevoir 150 prestige";
                    case REWARDED -> "Geartown est alimentée";
                };
            };
        };

        addRenderableWidget(
                Button.builder(
                                Component.literal(buttonLabel),
                                button -> {
                                    if (questStatus == QuestStatus.NOT_STARTED) {
                                        ModMessages.sendToServer(
                                                new C2SStartAncientGearFirstQuestPacket()
                                        );
                                    } else if (questStatus == QuestStatus.COMPLETED) {
                                        ModMessages.sendToServer(
                                                new C2SClaimAncientGearFirstQuestRewardPacket()
                                        );
                                    } else if (questStatus == QuestStatus.REWARDED) {
                                        if (secondQuestStatus == QuestStatus.NOT_STARTED) {
                                            ModMessages.sendToServer(new C2SStartAncientGearSecondQuestPacket());
                                        } else if (secondQuestStatus == QuestStatus.COMPLETED) {
                                            ModMessages.sendToServer(new C2SClaimAncientGearSecondQuestRewardPacket());
                                        } else if (secondQuestStatus == QuestStatus.REWARDED) {
                                            if (thirdQuestStatus == QuestStatus.NOT_STARTED) {
                                                ModMessages.sendToServer(new C2SStartAncientGearThirdQuestPacket());
                                            } else if (thirdQuestStatus == QuestStatus.COMPLETED) {
                                                ModMessages.sendToServer(new C2SClaimAncientGearThirdQuestRewardPacket());
                                            }
                                        }
                                    }
                                    onClose();
                                }
                        )
                        .bounds(centerX - 100, centerY + 40, 200, 20)
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
        renderBackground(guiGraphics);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int left = centerX - PANEL_WIDTH / 2;
        int top = centerY - PANEL_HEIGHT / 2;

        guiGraphics.fill(
                left,
                top,
                left + PANEL_WIDTH,
                top + PANEL_HEIGHT,
                0xEE120D09
        );
        guiGraphics.renderOutline(
                left,
                top,
                PANEL_WIDTH,
                PANEL_HEIGHT,
                0xFFC88A42
        );

        guiGraphics.drawCenteredString(
                this.font,
                this.title,
                centerX,
                top + 14,
                0xFFC88A42
        );

        Component firstLine;
        Component secondLine;
        Component thirdLine;
        if (questStatus == QuestStatus.COMPLETED) {
            firstLine = Component.literal("Le générateur de Geartown tourne à nouveau.");
            secondLine = Component.literal("Tu as réveillé les rouages oubliés.");
            thirdLine = Component.literal("Accepte tes 50 points de prestige.");
        } else if (questStatus == QuestStatus.REWARDED) {
            if (secondQuestStatus == QuestStatus.COMPLETED) {
                firstLine = Component.literal("Le premier automate de Geartown est réparé.");
                secondLine = Component.literal("La cité accueille son premier habitant mécanique.");
                thirdLine = Component.literal("Accepte tes 100 points de prestige.");
            } else if (secondQuestStatus == QuestStatus.REWARDED) {
                if (thirdQuestStatus == QuestStatus.COMPLETED) {
                    firstLine = Component.literal("Le réacteur alimente de nouveau Geartown.");
                    secondLine = Component.literal("Son cœur énergétique pulse dans les profondeurs.");
                    thirdLine = Component.literal("Accepte tes 150 points de prestige.");
                } else if (thirdQuestStatus == QuestStatus.REWARDED) {
                    firstLine = Component.literal("Geartown s'éveille à nouveau.");
                    secondLine = Component.literal("Ses rouages et son réacteur fonctionnent ensemble.");
                    thirdLine = Component.empty();
                } else {
                    firstLine = Component.literal("Le grand réacteur de Geartown reste silencieux.");
                    secondLine = Component.literal("Forge un cœur avec cuivre, redstone et améthyste.");
                    thirdLine = Component.literal("Installe-le au centre du réacteur antique.");
                }
            } else {
                firstLine = Component.literal("Assemble un automate avec les pièces antiques.");
                secondLine = Component.literal("Active-le sur le générateur réparé.");
                thirdLine = Component.literal("Un lingot de fer stabilisera ses mécanismes.");
            }
        } else {
            firstLine = Component.literal("Ces mécanismes sont restés silencieux pendant des siècles.");
            secondLine = Component.literal("Le cœur de Geartown bat encore sous la rouille.");
            thirdLine = Component.literal("Aide-moi à réveiller son générateur.");
        }

        guiGraphics.drawCenteredString(
                this.font,
                firstLine,
                centerX,
                top + 39,
                0xFFE4D8C4
        );
        guiGraphics.drawCenteredString(
                this.font,
                secondLine,
                centerX,
                top + 53,
                0xFFE4D8C4
        );
        guiGraphics.drawCenteredString(
                this.font,
                thirdLine,
                centerX,
                top + 67,
                0xFFE4D8C4
        );

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
