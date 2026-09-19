package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.quest.ancientgear.AncientGearFirstQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SStartAncientGearFirstQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AncientGearDialogueScreen extends Screen {

    private static final int PANEL_WIDTH = 320;
    private static final int PANEL_HEIGHT = 130;
    private QuestStatus questStatus = QuestStatus.NOT_STARTED;

    public AncientGearDialogueScreen() {
        super(Component.literal("Émissaire mécaniste"));
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
                            AncientGearFirstQuest.ID
                    )
            );
        }

        String buttonLabel = switch (questStatus) {
            case NOT_STARTED -> "Réveiller la cité";
            case IN_PROGRESS -> "Je retrouverai Geartown";
            case COMPLETED -> "Le générateur est réparé";
            case REWARDED -> "Les rouages sont réveillés";
        };

        addRenderableWidget(
                Button.builder(
                                Component.literal(buttonLabel),
                                button -> {
                                    if (questStatus == QuestStatus.NOT_STARTED) {
                                        ModMessages.sendToServer(
                                                new C2SStartAncientGearFirstQuestPacket()
                                        );
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

        Component firstLine = Component.literal(
                "Ces mécanismes sont restés silencieux pendant des siècles."
        );
        Component secondLine = Component.literal(
                "Le cœur de Geartown bat encore sous la rouille."
        );
        Component thirdLine = Component.literal(
                "Aide-moi bientôt à réveiller son générateur."
        );

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
