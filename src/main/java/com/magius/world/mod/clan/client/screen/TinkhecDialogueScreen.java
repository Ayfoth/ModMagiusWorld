package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TinkhecDialogueScreen extends Screen {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    private QuestStatus questStatus =
            QuestStatus.NOT_STARTED;

    private Button actionButton;

    public TinkhecDialogueScreen() {

        super(
                Component.literal(
                        "Tinkhec Dragonmaid"
                )
        );
    }

    @Override
    protected void init() {

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player != null) {

            QuestManager.get(minecraft.player)
                    .ifPresent(data ->
                            questStatus =
                                    QuestManager.getStatus(
                                            data,
                                            QUEST_ID
                                    )
                    );
        }

        int centerX =
                this.width / 2;

        int centerY =
                this.height / 2;

        actionButton =
                Button.builder(
                                Component.literal(
                                        questStatus == QuestStatus.COMPLETED
                                                ? "Je préviendrai l'Émissaire"
                                                : "À bientôt"
                                ),
                                button ->
                                        onClose()
                        )
                        .bounds(
                                centerX - 100,
                                centerY + 55,
                                200,
                                20
                        )
                        .build();

        addRenderableWidget(
                actionButton
        );
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

        /*
         * Fond assombri.
         */
        guiGraphics.fill(
                0,
                0,
                this.width,
                this.height,
                0x99000000
        );

        int panelWidth =
                280;

        int panelHeight =
                180;

        int x =
                (this.width - panelWidth) / 2;

        int y =
                (this.height - panelHeight) / 2;

        /*
         * Ombre.
         */
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x88000000
        );

        /*
         * Contour sombre.
         */
        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF120607
        );

        /*
         * Contour doré.
         */
        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFFD5A63A
        );

        /*
         * Bordure Dragonmaid.
         */
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF741C28
        );

        /*
         * Fond intérieur.
         */
        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF26090D
        );

        /*
         * Titre.
         */
        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Tinkhec Dragonmaid"
                ),
                this.width / 2,
                y + 15,
                0xFFD5A63A
        );

        /*
         * Séparateur.
         */
        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF741C28
        );

        Component dialogue =
                getDialogue();

        var lines =
                font.split(
                        dialogue,
                        panelWidth - 30
                );

        int textY =
                y + 43;

        for (
                int i = 0;
                i < lines.size();
                i++
        ) {

            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    x + 15,
                    textY + i * 11,
                    0xFFD7C6C9,
                    false
            );
        }

        String statusText =
                switch (questStatus) {

                    case NOT_STARTED ->
                            "Le Foyer dort encore";

                    case IN_PROGRESS ->
                            "Le Cœur attend son réveil";

                    case COMPLETED ->
                            "Tinkhec est revenue";

                    case REWARDED ->
                            "Le Foyer renaît";
                };

        guiGraphics.drawCenteredString(
                font,
                Component.literal(statusText),
                this.width / 2,
                y + 112,
                0xFFB7833A
        );

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    private Component getDialogue() {

        return switch (questStatus) {

            case NOT_STARTED ->
                    Component.literal(
                            "« Nous n'aurions pas dû nous rencontrer ainsi. "
                                    + "Le Foyer sommeille encore et son appel "
                                    + "ne s'est pas fait entendre. »"
                    );

            case IN_PROGRESS ->
                    Component.literal(
                            "« Le Cœur est proche de son réveil. "
                                    + "Lorsque sa flamme renaîtra, "
                                    + "les anciennes servantes l'entendront. »"
                    );

            case COMPLETED ->
                    Component.literal(
                            "« Cette chaleur... Je croyais le Foyer perdu "
                                    + "à jamais. C'est donc toi qui as réveillé "
                                    + "son Cœur. Je suis Tinkhec. Retourne auprès "
                                    + "de l'Émissaire et dis-lui que je suis revenue. »"
                    );

            case REWARDED ->
                    Component.literal(
                            "« Le Foyer brûle de nouveau. Tant que sa flamme "
                                    + "subsistera, notre histoire ne disparaîtra pas. "
                                    + "D'autres Dragonmaids pourraient encore revenir. »"
                    );
        };
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