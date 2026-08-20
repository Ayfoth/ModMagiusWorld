package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DragonmaidDialogueScreen extends Screen {

    private static final ResourceLocation FIRST_CONTACT =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );
    private static final ResourceLocation UNEXPECTED_GUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );
    private static final ResourceLocation FORGOTTEN_HOME =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );
    private static final ResourceLocation DRAGONMAID_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    private Button actionButton;
    private Button leaveButton;

    private QuestStatus firstContactStatus =
            QuestStatus.NOT_STARTED;

    private QuestStatus unexpectedGuestStatus =
            QuestStatus.NOT_STARTED;

    private QuestStatus forgottenHomeStatus =
            QuestStatus.NOT_STARTED;

    private boolean dragonmaidActive = false;

    public DragonmaidDialogueScreen() {
        super(
                Component.literal(
                        "Émissaire Dragonmaid"
                )
        );
    }

    // =========================================================
    // INITIALISATION
    // =========================================================

    @Override
    protected void init() {

        /*
         * La progression ayant été synchronisée côté client,
         * on peut lire directement la capability du joueur.
         */
        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player != null) {

            QuestManager.get(minecraft.player)
                    .ifPresent(data -> {

                        firstContactStatus =
                                QuestManager.getStatus(
                                        data,
                                        FIRST_CONTACT
                                );

                        unexpectedGuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        UNEXPECTED_GUEST
                                );
                        forgottenHomeStatus =
                                QuestManager.getStatus(
                                        data,
                                        FORGOTTEN_HOME
                                );
                    });
            ClanManager.get(minecraft.player)
                    .ifPresent(clanData -> {

                        dragonmaidActive =
                                DRAGONMAID_ID.equals(
                                        clanData.getClanId()
                                );
                    });
        }

        int centerX =
                this.width / 2;

        int centerY =
                this.height / 2;

        /*
         * Le premier bouton dépend maintenant
         * de l'état de Premier Contact.
         */
        if (firstContactStatus != QuestStatus.REWARDED) {

            switch (firstContactStatus) {

                case NOT_STARTED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Accepter la quête",
                                        this::onAcceptFirstContact
                                );

                case IN_PROGRESS ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Je le retrouverai",
                                        this::onClose
                                );

                case COMPLETED -> {

                    if (!dragonmaidActive) {

                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Rejoindre Dragonmaid",
                                        this::onJoinDragonmaid
                                );

                    } else {

                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Recevoir la récompense",
                                        this::onClaimFirstReward
                                );
                    }
                }

                case REWARDED -> {
                    // Impossible ici à cause du if.
                }
            }

        } else if (unexpectedGuestStatus != QuestStatus.REWARDED) {

            switch (unexpectedGuestStatus) {

                case NOT_STARTED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Une nouvelle mission",
                                        this::onAcceptUnexpectedGuest
                                );

                case IN_PROGRESS ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Je retrouverai Nurse",
                                        this::onClose
                                );

                case COMPLETED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Recevoir la récompense",
                                        this::onClaimUnexpectedGuestReward
                                );

                case REWARDED -> {
                }
            }

        } else {

            switch (forgottenHomeStatus) {

                case NOT_STARTED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Accepter la quête",
                                        this::onAcceptForgottenHome
                                );

                case IN_PROGRESS ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Je réveillerai le Foyer",
                                        this::onClose
                                );

                case COMPLETED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "Recevoir la récompense",
                                        this::onClaimForgottenHomeReward
                                );

                case REWARDED ->
                        actionButton =
                                createActionButton(
                                        centerX,
                                        centerY,
                                        "À bientôt",
                                        this::onClose
                                );
            }
        }

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

        addRenderableWidget(
                actionButton
        );

        addRenderableWidget(
                leaveButton
        );
    }

    private void onJoinDragonmaid() {

        if (
                firstContactStatus
                        != QuestStatus.COMPLETED
        ) {

            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SJoinDragonmaidClanPacket()
        );

        onClose();
    }

    private void onAcceptForgottenHome() {

        if (
                unexpectedGuestStatus != QuestStatus.REWARDED
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

    private void onAcceptUnexpectedGuest() {

        if (
                firstContactStatus != QuestStatus.REWARDED
                        || unexpectedGuestStatus
                        != QuestStatus.NOT_STARTED
        ) {

            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SStartUnexpectedGuestQuestPacket()
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

        /*
         * On conserve la correction qui a supprimé
         * les crashs précédents.
         */
        Minecraft minecraft =
                Minecraft.getInstance();

        Font font =
                minecraft.font;

        /*
         * Fond assombri.
         *
         * Toujours pas de renderBackground().
         */
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

        // =====================================================
        // OMBRE
        // =====================================================

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x88000000
        );

        // =====================================================
        // CONTOUR SOMBRE
        // =====================================================

        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF120607
        );

        // =====================================================
        // CONTOUR DORÉ
        // =====================================================

        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFFD5A63A
        );

        // =====================================================
        // CONTOUR DRAGONMAID
        // =====================================================

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF741C28
        );

        // =====================================================
        // FOND INTÉRIEUR
        // =====================================================

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
                        "Émissaire Dragonmaid"
                ),
                this.width / 2,
                y + 15,
                0xFFD5A63A
        );

        // =====================================================
        // SÉPARATEUR
        // =====================================================

        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF741C28
        );

        // =====================================================
        // DIALOGUE SELON LA QUÊTE
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

        /*
         * Affichage discret du statut.
         * Pratique pendant le développement.
         */
        QuestStatus displayedStatus;

        if (firstContactStatus != QuestStatus.REWARDED) {

            displayedStatus =
                    firstContactStatus;

        } else if (unexpectedGuestStatus != QuestStatus.REWARDED) {

            displayedStatus =
                    unexpectedGuestStatus;

        } else {

            displayedStatus =
                    forgottenHomeStatus;
        }

        String statusText =
                switch (displayedStatus) {

                    case NOT_STARTED ->
                            "Quête disponible";

                    case IN_PROGRESS ->
                            "Quête en cours";

                    case COMPLETED ->
                            "Quête terminée";

                    case REWARDED ->
                            "Quête accomplie";
                };

        guiGraphics.drawCenteredString(
                font,
                Component.literal(statusText),
                this.width / 2,
                y + 103,
                0xFFB7833A
        );

        /*
         * Rendu des boutons.
         */
        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    private Button createActionButton(
            int centerX,
            int centerY,
            String text,
            Runnable action
    ) {

        return Button.builder(
                        Component.literal(text),
                        button -> action.run()
                )
                .bounds(
                        centerX - 100,
                        centerY + 45,
                        200,
                        20
                )
                .build();
    }

    // =========================================================
    // TEXTE DU DIALOGUE
    // =========================================================

    private Component getDialogue() {

        /*
         * Tant que Premier Contact n'est pas entièrement
         * récompensée, l'Émissaire parle de la quête 1.
         */
        if (firstContactStatus != QuestStatus.REWARDED) {

            return switch (firstContactStatus) {

                case NOT_STARTED ->
                        Component.literal(
                                "« Un ancien grimoire appartenant à notre clan "
                                        + "a disparu. Retrouvez-le et rapportez "
                                        + "avec vous la mémoire qu'il renferme. »"
                        );

                case IN_PROGRESS ->
                        Component.literal(
                                "« Le Grimoire Dragonmaid est toujours quelque part. "
                                        + "Ne sous-estimez pas ce que vous pourriez "
                                        + "découvrir en le retrouvant. »"
                        );

                case COMPLETED -> {

                    if (!dragonmaidActive) {

                        yield Component.literal(
                                "« Vous avez retrouvé le Grimoire et rendu une part "
                                        + "de notre mémoire au Foyer. Vous avez fait vos preuves. "
                                        + "Si vous le souhaitez, une place parmi les Dragonmaids "
                                        + "vous est désormais ouverte. »"
                        );

                    }

                    yield Component.literal(
                            "« Vous l'avez retrouvé... Alors les anciennes "
                                    + "paroles peuvent de nouveau être entendues. »"
                    );
                }

                case REWARDED ->
                        Component.empty();
            };
        }

        /*
         * Premier Contact est terminé :
         * l'Émissaire parle maintenant de la quête 2.
         */
        if (unexpectedGuestStatus != QuestStatus.REWARDED) {

            return switch (unexpectedGuestStatus) {

                case NOT_STARTED ->
                        Component.literal(
                                "« Le Grimoire a réagi à votre présence... "
                                        + "Une ancienne servante s'est manifestée. "
                                        + "Je pense qu'il est temps de la retrouver. »"
                        );

                case IN_PROGRESS ->
                        Component.literal(
                                "« Cherchez Nurse Dragonmaid. Si elle s'est réellement "
                                        + "réveillée, elle aura certainement ressenti "
                                        + "l'appel du Grimoire. »"
                        );

                case COMPLETED ->
                        Component.literal(
                                "« Nurse est donc revenue... C'est une nouvelle que "
                                        + "je n'espérais plus entendre. Vous avez "
                                        + "accompli votre mission. »"
                        );

                case REWARDED ->
                        Component.empty();
            };
        }

        return switch (forgottenHomeStatus) {

            case NOT_STARTED ->
                    Component.literal(
                            "« Le retour de Nurse confirme ce que je craignais... "
                                    + "Le Grimoire cherche à reconstituer notre ancien Foyer. "
                                    + "Retrouvez ses ruines et réveillez le Cœur du Foyer. »"
                    );

            case IN_PROGRESS ->
                    Component.literal(
                            "« Le Cœur du Foyer dort encore. "
                                    + "Apportez-lui l'Éclat du Foyer et réveillez sa flamme. »"
                    );

            case COMPLETED ->
                    Component.literal(
                            "« Tinkhec est revenue... Alors le Foyer vit réellement de nouveau. "
                                    + "Vous avez accompli bien plus qu'une simple mission. »"
                    );

            case REWARDED ->
                    Component.literal(
                            "« Le Foyer renaît, et avec lui une part de votre propre pouvoir. "
                                    + "Le Réveil Draconique vous appartient désormais. »"
                    );
        };
    }

    // =========================================================
    // ACCEPTER
    // =========================================================

    private void onAcceptFirstContact() {

        if (firstContactStatus != QuestStatus.NOT_STARTED) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SStartDragonmaidQuestPacket()
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

    private void onClaimFirstReward() {

        if (firstContactStatus != QuestStatus.COMPLETED) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SClaimDragonmaidQuestRewardPacket()
        );

        onClose();
    }

    private void onClaimUnexpectedGuestReward() {

        if (unexpectedGuestStatus != QuestStatus.COMPLETED) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SClaimUnexpectedGuestRewardPacket()
        );

        onClose();
    }
    private void onClaimForgottenHomeReward() {

        if (forgottenHomeStatus != QuestStatus.COMPLETED) {
            onClose();
            return;
        }

        ModMessages.sendToServer(
                new C2SClaimForgottenHomeRewardPacket()
        );

        onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}