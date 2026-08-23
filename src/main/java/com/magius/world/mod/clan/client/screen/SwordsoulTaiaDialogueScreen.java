package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulTaiaQuest;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimSwordsoulTaiaQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SJoinSwordsoulClanPacket;
import com.magius.world.mod.network.packet.C2SStartSwordsoulTaiaQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulTaiaDialogueScreen extends Screen {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private QuestStatus moYeQuestStatus =
            QuestStatus.NOT_STARTED;

    private QuestStatus taiaQuestStatus =
            QuestStatus.NOT_STARTED;

    private boolean swordsoulActive = false;

    public SwordsoulTaiaDialogueScreen() {
        super(Component.literal("Taia"));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player != null) {
            QuestManager.get(minecraft.player)
                    .ifPresent(data -> {
                        moYeQuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        SwordsoulMoYeQuest.ID
                                );

                        taiaQuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        SwordsoulTaiaQuest.ID
                                );
                    });

            ClanManager.get(minecraft.player)
                    .ifPresent(clanData ->
                            swordsoulActive =
                                    SWORDSOUL_ID.equals(
                                            clanData.getActiveClanId()
                                    )
                    );
        }

        if (moYeQuestStatus != QuestStatus.REWARDED) {
            addCloseButton(
                    centerX,
                    centerY,
                    "Je reviendrai"
            );
            return;
        }

        switch (taiaQuestStatus) {
            case NOT_STARTED ->
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal(
                                                    "Suivre la voie de Taia"
                                            ),
                                            button -> {
                                                ModMessages.sendToServer(
                                                        new C2SStartSwordsoulTaiaQuestPacket()
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
                    addCloseButton(
                            centerX,
                            centerY,
                            "J'infuserai ma lame"
                    );

            case COMPLETED -> {
                if (!swordsoulActive) {
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal(
                                                    "Activer le clan Swordsoul"
                                            ),
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
                                            Component.literal(
                                                    "Achever l'enseignement"
                                            ),
                                            button -> {
                                                ModMessages.sendToServer(
                                                        new C2SClaimSwordsoulTaiaQuestRewardPacket()
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
                    addCloseButton(
                            centerX,
                            centerY,
                            "À bientôt, Taia"
                    );
        }
    }

    private void addCloseButton(
            int centerX,
            int centerY,
            String label
    ) {
        addRenderableWidget(
                Button.builder(
                                Component.literal(label),
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

    private Component getDialogue() {
        if (moYeQuestStatus != QuestStatus.REWARDED) {
            return Component.literal(
                    "« Tu n'es pas encore prêt à suivre ma voie. "
                            + "Écoute d'abord l'enseignement de Mo Ye "
                            + "et synchronise ton esprit avec ta lame. »"
            );
        }

        return switch (taiaQuestStatus) {
            case NOT_STARTED ->
                    Component.literal(
                            "« Une lame synchronisée n'est que le commencement. "
                                    + "Le sceau reçu de Mo Ye porte l'une des Sept Voies. "
                                    + "Unis ce sceau à ta lame dans la Forge spirituelle. »"
                    );

            case IN_PROGRESS ->
                    Component.literal(
                            "« Présente à la forge ta lame synchronisée, "
                                    + "un jeton de niveau identique et ton sceau d'attribut. "
                                    + "Lorsque leurs esprits ne feront plus qu'un, reviens me voir. »"
                    );

            case COMPLETED ->
                    Component.literal(
                            "« Ta lame porte désormais une voie spirituelle. "
                                    + "Tu ne manies plus une arme empruntée : "
                                    + "tu commences à révéler ta propre maîtrise. »"
                    );

            case REWARDED ->
                    Component.literal(
                            "« Chaque attribut impose un rythme différent au combat. "
                                    + "Comprends ta voie, mais ne laisse jamais "
                                    + "son pouvoir décider à ta place. »"
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
        Font font =
                Minecraft.getInstance().font;

        guiGraphics.fill(
                0,
                0,
                width,
                height,
                0x99000000
        );

        int panelWidth = 280;
        int panelHeight = 170;

        int x = (width - panelWidth) / 2;
        int y = (height - panelHeight) / 2;

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x88000000
        );

        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF02080D
        );

        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFF8DD9F2
        );

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF315C88
        );

        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF071521
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Taia"),
                width / 2,
                y + 15,
                0xFF8DD9F2
        );

        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF315C88
        );

        var lines =
                font.split(
                        getDialogue(),
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
