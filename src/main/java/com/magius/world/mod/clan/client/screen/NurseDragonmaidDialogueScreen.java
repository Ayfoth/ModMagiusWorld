package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.network.packet.C2SStartForgottenHomeQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SCompleteNurseDragonmaidQuestPacket;

public class NurseDragonmaidDialogueScreen extends Screen {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );
    private static final ResourceLocation FORGOTTEN_HOME =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );


    private Button actionButton;
    private Button leaveButton;

    private QuestStatus questStatus =
            QuestStatus.NOT_STARTED;
    private QuestStatus forgottenHomeStatus =
            QuestStatus.NOT_STARTED;

    public NurseDragonmaidDialogueScreen() {

        super(
                Component.literal(
                        "Nurse Dragonmaid"
                )
        );
    }

    // =========================================================
    // INITIALISATION
    // =========================================================

    @Override
    protected void init() {

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

                        forgottenHomeStatus =
                                QuestManager.getStatus(
                                        data,
                                        FORGOTTEN_HOME
                                );
                    });
        }

        // Le reste de ton init() continue ici...

        int centerX =
                this.width / 2;

        int centerY =
                this.height / 2;

        if (questStatus != QuestStatus.REWARDED) {

            switch (questStatus) {

                case NOT_STARTED -> {

                    actionButton =
                            Button.builder(
                                            Component.literal("Partir"),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case IN_PROGRESS -> {

                    actionButton =
                            Button.builder(
                                            Component.literal("Continuer"),
                                            button -> onContinue()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case COMPLETED -> {

                    actionButton =
                            Button.builder(
                                            Component.literal(
                                                    "Je vais retourner le voir"
                                            ),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case REWARDED -> {
                }
            }

        } else {

            switch (forgottenHomeStatus) {

                case NOT_STARTED -> {

                    actionButton =
                            Button.builder(
                                            Component.literal(
                                                    "Parle-moi du Foyer"
                                            ),
                                            button ->
                                                    onAcceptForgottenHome()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case IN_PROGRESS -> {

                    actionButton =
                            Button.builder(
                                            Component.literal(
                                                    "Je réveillerai le Cœur"
                                            ),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case COMPLETED -> {

                    actionButton =
                            Button.builder(
                                            Component.literal(
                                                    "Le Cœur s'est réveillé"
                                            ),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }

                case REWARDED -> {

                    actionButton =
                            Button.builder(
                                            Component.literal(
                                                    "À bientôt"
                                            ),
                                            button -> onClose()
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build();
                }
            }
        }

        /*
         * NOT_STARTED possède déjà un bouton Partir.
         * Pour les autres états on ajoute le bouton secondaire.
         */
        if (questStatus != QuestStatus.NOT_STARTED) {

            leaveButton =
                    Button.builder(
                                    Component.literal(
                                            "Partir"
                                    ),
                                    button ->
                                            onClose()
                            )
                            .bounds(
                                    centerX - 100,
                                    centerY + 70,
                                    200,
                                    20
                            )
                            .build();
        }

        addRenderableWidget(
                actionButton
        );

        if (leaveButton != null) {

            addRenderableWidget(
                    leaveButton
            );
        }
    }
    private void onAcceptForgottenHome() {

        if (
                questStatus != QuestStatus.REWARDED
                        || forgottenHomeStatus != QuestStatus.NOT_STARTED
        ) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SStartForgottenHomeQuestPacket()
        );

        onClose();
    }

    // =========================================================
    // RENDU
    // =========================================================

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

        int panelWidth =
                260;

        int panelHeight =
                170;

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
                0xFF120607
        );

        // Contour doré
        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFFD5A63A
        );

        // Contour Dragonmaid
        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF741C28
        );

        // Fond intérieur
        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF26090D
        );

        // =====================================================
        // TITRE
        // =====================================================

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        "Nurse Dragonmaid"
                ),
                this.width / 2,
                y + 15,
                0xFFD5A63A
        );

        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF741C28
        );

        // =====================================================
        // DIALOGUE
        // =====================================================

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

        QuestStatus displayedStatus =
                questStatus != QuestStatus.REWARDED
                        ? questStatus
                        : forgottenHomeStatus;

        String statusText;

        if (questStatus != QuestStatus.REWARDED) {

            statusText =
                    switch (displayedStatus) {

                        case NOT_STARTED ->
                                "Une présence mystérieuse";

                        case IN_PROGRESS ->
                                "Nurse retrouvée";

                        case COMPLETED ->
                                "Retournez voir l'Émissaire";

                        case REWARDED ->
                                "Rencontre accomplie";
                    };

        } else {

            statusText =
                    switch (displayedStatus) {

                        case NOT_STARTED ->
                                "Le Foyer oublié";

                        case IN_PROGRESS ->
                                "Réveillez le Cœur";

                        case COMPLETED ->
                                "Le Cœur s'est réveillé";

                        case REWARDED ->
                                "Le Foyer renaît";
                    };
        }

        guiGraphics.drawCenteredString(
                font,
                Component.literal(statusText),
                this.width / 2,
                y + 103,
                0xFFB7833A
        );

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    // =========================================================
    // DIALOGUES
    // =========================================================

    private Component getDialogue() {

        if (questStatus != QuestStatus.REWARDED) {

            return switch (questStatus) {

                case NOT_STARTED ->
                        Component.literal(
                                "« Nous ne nous connaissons pas encore... "
                                        + "Peut-être nos chemins se croiseront-ils "
                                        + "lorsque le moment sera venu. »"
                        );

                case IN_PROGRESS ->
                        Component.literal(
                                "« Ainsi... c'est toi que le Grimoire a choisi. "
                                        + "Je sentais son énergie depuis mon réveil. "
                                        + "Dis à l'Émissaire que Nurse est revenue. »"
                        );

                case COMPLETED ->
                        Component.literal(
                                "« Tu m'as retrouvée. Retourne maintenant auprès "
                                        + "de l'Émissaire. Il doit savoir que les "
                                        + "anciennes servantes commencent à revenir. »"
                        );

                case REWARDED ->
                        Component.empty();
            };
        }

        return switch (forgottenHomeStatus) {

            case NOT_STARTED ->
                    Component.literal(
                            "« Regarde autour de toi... Ce lieu était autrefois "
                                    + "notre Foyer. Il ne reste presque rien, mais "
                                    + "le Cœur est toujours là. S'il pouvait être "
                                    + "réveillé, peut-être que le village pourrait renaître. »"
                    );

            case IN_PROGRESS ->
                    Component.literal(
                            "« Le Cœur du Foyer dort encore au centre des ruines. "
                                    + "Apporte-lui l'Éclat du Foyer et réveille sa flamme. »"
                    );

            case COMPLETED ->
                    Component.literal(
                            "« Je l'ai senti... le Cœur s'est réveillé. "
                                    + "Et Tinkhec a répondu à son appel. "
                                    + "Le Foyer n'est peut-être pas perdu après tout. »"
                    );

            case REWARDED ->
                    Component.literal(
                            "« La flamme brûle de nouveau. "
                                    + "Ce n'est encore qu'une ruine, mais désormais "
                                    + "nous pouvons commencer à reconstruire notre foyer. »"
                    );
        };
    }

    // =========================================================
    // CONTINUER
    // =========================================================

    private void onContinue() {

        if (questStatus != QuestStatus.IN_PROGRESS) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SCompleteNurseDragonmaidQuestPacket()
        );

        onClose();
    }

    // =========================================================
    // FERMETURE
    // =========================================================

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
