package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.client.screen.widget.ClanCardRenderer;
import com.magius.world.mod.clan.client.screen.widget.ClanProgressBarRenderer;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanRegistry;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.magius.world.mod.clan.api.ClanRank;
import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.chronicle.data.ChronicleDefinition;
import com.magius.world.mod.clan.chronicle.data.ChronicleRegistry;
import com.magius.world.mod.clan.chronicle.unlock.ChronicleUnlockManager;

import java.util.List;

public class HomeTab implements ClanTab {

    private static final int GAP = 5;

    private static final ResourceLocation FIRST_CONTACT_QUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );
    private static final ResourceLocation UNEXPECTED_GUEST_QUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );
    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private static final ItemStack RANK_ICON =
            new ItemStack(Items.NETHER_STAR);

    private static final ItemStack QUEST_ICON =
            new ItemStack(Items.WRITABLE_BOOK);

    private static final ItemStack REWARD_ICON =
            new ItemStack(Items.CHEST);

    private static final ItemStack CHRONICLE_ICON =
            new ItemStack(Items.ENCHANTED_BOOK);

    private static final ItemStack ACTIVITY_ICON =
            new ItemStack(Items.CLOCK);

    @Override
    public Component getTitle() {
        return Component.literal("Accueil");
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        var font = Minecraft.getInstance().font;
        var player =
                Minecraft.getInstance().player;
        QuestStatus firstContactStatus =
                QuestStatus.NOT_STARTED;

        if (player != null) {

            firstContactStatus =
                    QuestManager.get(player)
                            .map(data ->
                                    QuestManager.getStatus(
                                            data,
                                            FIRST_CONTACT_QUEST
                                    )
                            )
                            .orElse(
                                    QuestStatus.NOT_STARTED
                            );
        }
        QuestStatus unexpectedGuestStatus =
                QuestStatus.NOT_STARTED;

        if (player != null) {

            unexpectedGuestStatus =
                    QuestManager.get(player)
                            .map(data ->
                                    QuestManager.getStatus(
                                            data,
                                            UNEXPECTED_GUEST_QUEST
                                    )
                            )
                            .orElse(
                                    QuestStatus.NOT_STARTED
                            );
        }

        int prestige = 0;
        int rankIndex = 0;
        int souls = 0;

        String rankName =
                "Novice";

        int prestigeStart = 0;
        int prestigeTarget = 100;

        if (player != null) {

            var clanDataOptional =
                    ClanManager.get(player)
                            .resolve();

            if (clanDataOptional.isPresent()) {

                var clanData =
                        clanDataOptional.get();

                prestige =
                        clanData.getPrestige();
                if (SWORDSOUL_ID.equals(clanId)) {
                    souls =
                            clanData.getClanCurrency(
                                    SWORDSOUL_ID
                            );
                }



                var clanOptional =
                        ClanRegistry.get(
                                clanId
                        );

                if (clanOptional.isPresent()) {

                    var ranks =
                            clanOptional.get()
                                    .getRanks();

                    if (!ranks.isEmpty()) {

                        /*
                         * On détermine le rang directement
                         * à partir du prestige réel.
                         */
                        rankIndex = 0;

                        for (int i = 0; i < ranks.size(); i++) {

                            if (prestige >= ranks.get(i).getRequiredPrestige()) {
                                rankIndex = i;
                            } else {
                                break;
                            }
                        }

                        ClanRank currentRank =
                                ranks.get(rankIndex);

                        rankName =
                                currentRank.getName();

                        prestigeStart =
                                currentRank.getRequiredPrestige();

                        if (rankIndex + 1 < ranks.size()) {

                            prestigeTarget =
                                    ranks.get(
                                            rankIndex + 1
                                    ).getRequiredPrestige();

                        } else {

                            prestigeTarget =
                                    prestigeStart;
                        }


                    }
                }
            }
        }

        /*
         * Progression À L'INTÉRIEUR du rang actuel.
         */
        int progressValue;
        int progressMaximum;
        int prestigePercent;

        if (prestigeTarget > prestigeStart) {

            progressValue =
                    Math.max(
                            0,
                            prestige - prestigeStart
                    );

            progressMaximum =
                    prestigeTarget - prestigeStart;

            prestigePercent =
                    Math.min(
                            100,
                            progressValue * 100
                                    / progressMaximum
                    );

        } else {

            /*
             * Rang maximum.
             */
            progressValue = 1;
            progressMaximum = 1;
            prestigePercent = 100;
        }

        boolean compact =
                height < 190;

        /*
         * =====================================================
         * DERNIÈRE CHRONIQUE DÉBLOQUÉE
         * =====================================================
         */

        List<ChronicleDefinition> chronicles =
                ChronicleRegistry.getForClan(clanId);

        ChronicleDefinition lastUnlockedChronicle =
                null;

        if (player != null) {

            for (ChronicleDefinition chronicle : chronicles) {

                if (
                        ChronicleUnlockManager.isUnlocked(
                                player,
                                chronicle
                        )
                ) {

                    /*
                     * On conserve la dernière chronique
                     * débloquée trouvée dans le registre.
                     */
                    lastUnlockedChronicle =
                            chronicle;
                }
            }
        }

        boolean hasUnlockedChronicle =
                lastUnlockedChronicle != null;

        String chronicleName =
                hasUnlockedChronicle
                        ? lastUnlockedChronicle.getTitle()
                        : "Aucune chronique";

        String chronicleCompactName =
                hasUnlockedChronicle
                        ? lastUnlockedChronicle.getShortTitle()
                        : "Aucune chronique";

        String chronicleDescription =
                hasUnlockedChronicle
                        ? "Chronique découverte"
                        : "Terminez des quêtes pour en découvrir";

        int leftWidth =
                (width - GAP) / 2;

        int rightWidth =
                width - leftWidth - GAP;

        int availableHeight =
                height - GAP * 2;

        int rowHeight =
                availableHeight / 3;

        int lastRowHeight =
                availableHeight - rowHeight * 2;

        int leftX = x;
        int rightX = x + leftWidth + GAP;

        int row1Y = y;
        int row2Y = row1Y + rowHeight + GAP;
        int row3Y = row2Y + rowHeight + GAP;

        // =====================================================
        // STATUT DU CLAN
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                leftX,
                row1Y,
                leftWidth,
                rowHeight,
                Component.literal("Statut du clan")
        );

        if (compact) {

            int iconX = leftX + 8;
            int iconY = row1Y + 25;

            guiGraphics.renderItem(
                    RANK_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(rankName),
                    iconX + 21,
                    row1Y + 29,
                    theme.getTextColor(),
                    false
            );
            if (SWORDSOUL_ID.equals(clanId)) {

//                guiGraphics.drawString(
//                        font,
//                        Component.literal(
//                                "Âmes : " + souls
//                        ),
//                        iconX + 23,
//                        row1Y + 51,
//                        theme.getAccentColor(),
//                        false
//                );
            }

        } else {

            int iconX = leftX + 10;
            int iconY = row1Y + 27;

            guiGraphics.renderItem(
                    RANK_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(rankName),
                    iconX + 23,
                    row1Y + 27,
                    theme.getTitleColor(),
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal("Rang actuel"),
                    iconX + 23,
                    row1Y + 39,
                    theme.getTextColor(),
                    false
            );
//            if (SWORDSOUL_ID.equals(clanId)) {
//
//                guiGraphics.drawString(
//                        font,
//                        Component.literal(
//                                "Âmes : " + souls
//                        ),
//                        iconX + 23,
//                        row1Y + 51,
//                        theme.getAccentColor(),
//                        false
//                );
//            }


            String levelText =
                    "Niveau " + (rankIndex + 1);

            int levelWidth =
                    font.width(levelText);

            guiGraphics.drawString(
                    font,
                    Component.literal(levelText),
                    leftX
                            + leftWidth
                            - levelWidth
                            - 10,
                    row1Y + 31,
                    theme.getTextColor(),
                    false
            );
        }

        // =====================================================
        // PROGRESSION
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                rightX,
                row1Y,
                rightWidth,
                rowHeight,
                Component.literal("Progression")
        );

        if (compact) {

            int barX = rightX + 8;
            int barY = row1Y + 29;

            String percentText =
                    prestigePercent + "%";

            int percentWidth =
                    font.width(percentText);

            int barWidth =
                    rightWidth
                            - 24
                            - percentWidth;

            ClanProgressBarRenderer.render(
                    guiGraphics,
                    barX,
                    barY,
                    Math.max(20, barWidth),
                    8,
                    progressValue,
                    progressMaximum,
                    0xFF180B0C,
                    theme.getAccentColor(),
                    theme.getTitleColor()
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(percentText),
                    barX + barWidth + 6,
                    barY,
                    theme.getTitleColor(),
                    false
            );

        } else {

            int progressX = rightX + 10;
            int progressY = row1Y + 27;

            guiGraphics.drawString(
                    font,
                    Component.literal("Confiance"),
                    progressX,
                    progressY,
                    theme.getTextColor(),
                    false
            );

            String progressText =
                    prestigeTarget > prestigeStart
                            ? prestige + " / " + prestigeTarget
                            : prestige + " — MAX";

            int progressValueWidth =
                    font.width(progressText);

            guiGraphics.drawString(
                    font,
                    Component.literal(progressText),
                    rightX
                            + rightWidth
                            - progressValueWidth
                            - 10,
                    progressY,
                    theme.getTitleColor(),
                    false
            );

            int barY =
                    progressY + 14;

            ClanProgressBarRenderer.render(
                    guiGraphics,
                    progressX,
                    barY,
                    rightWidth - 20,
                    8,
                    progressValue,
                    progressMaximum,
                    0xFF180B0C,
                    theme.getAccentColor(),
                    theme.getTitleColor()
            );
        }


        String questCardTitle;
        String questName;
        String questDescription;

        /*
         * =====================================================
         * PREMIER CONTACT PAS ENCORE TERMINÉ
         * =====================================================
         */

        if (firstContactStatus != QuestStatus.REWARDED) {

            switch (firstContactStatus) {

                case NOT_STARTED -> {
                    questCardTitle =
                            "Quête disponible";

                    questName =
                            "Premier Contact";

                    questDescription =
                            "Parler à l'Émissaire Dragonmaid";
                }

                case IN_PROGRESS -> {
                    questCardTitle =
                            "Quête en cours";

                    questName =
                            "Premier Contact";

                    questDescription =
                            "Retrouver le Grimoire Dragonmaid";
                }

                case COMPLETED -> {
                    questCardTitle =
                            "Quête terminée";

                    questName =
                            "Premier Contact";

                    questDescription =
                            "Retournez voir l'Émissaire";
                }

                default -> {
                    questCardTitle =
                            "Quêtes";

                    questName =
                            "Aucune quête";

                    questDescription =
                            "";
                }
            }

            /*
             * =====================================================
             * QUÊTE 2
             * =====================================================
             */

        } else {

            switch (unexpectedGuestStatus) {

                case NOT_STARTED -> {
                    questCardTitle =
                            "Quête disponible";

                    questName =
                            "Une invitée inattendue";

                    questDescription =
                            "Parler à l'Émissaire Dragonmaid";
                }

                case IN_PROGRESS -> {
                    questCardTitle =
                            "Quête en cours";

                    questName =
                            "Une invitée inattendue";

                    questDescription =
                            "Retrouver Nurse Dragonmaid";
                }

                case COMPLETED -> {
                    questCardTitle =
                            "Quête terminée";

                    questName =
                            "Une invitée inattendue";

                    questDescription =
                            "Retournez voir l'Émissaire";
                }

                case REWARDED -> {
                    questCardTitle =
                            "Quêtes";

                    questName =
                            "Aucune quête en cours";

                    questDescription =
                            "De nouvelles missions arriveront bientôt";
                }

                default -> {
                    questCardTitle =
                            "Quêtes";

                    questName =
                            "Aucune quête en cours";

                    questDescription =
                            "";
                }
            }
        }

        // =====================================================
        // QUÊTE EN COURS
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                leftX,
                row2Y,
                leftWidth,
                rowHeight,
                Component.literal(questCardTitle)
        );

        if (compact) {

            int iconX = leftX + 8;
            int iconY = row2Y + 25;

            guiGraphics.renderItem(
                    QUEST_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(questName),
                    iconX + 21,
                    row2Y + 29,
                    theme.getTitleColor(),
                    false
            );

        } else {

            int iconX = leftX + 10;
            int iconY = row2Y + 27;

            guiGraphics.renderItem(
                    QUEST_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(questName),
                    iconX + 23,
                    row2Y + 27,
                    theme.getTitleColor(),
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            questDescription
                    ),
                    iconX + 23,
                    row2Y + 41,
                    theme.getTextColor(),
                    false
            );
        }

        String rewardCardTitle;
        String rewardMainText;
        String rewardSecondaryText;

        /*
         * =====================================================
         * RÉCOMPENSE — PREMIER CONTACT
         * =====================================================
         */

        if (firstContactStatus != QuestStatus.REWARDED) {

            switch (firstContactStatus) {

                case NOT_STARTED, IN_PROGRESS -> {
                    rewardCardTitle =
                            "Récompense de quête";

                    rewardMainText =
                            "+50 Prestige";

                    rewardSecondaryText =
                            "Les Origines Dragonmaid";
                }

                case COMPLETED -> {
                    rewardCardTitle =
                            "Récompense disponible";

                    rewardMainText =
                            "+50 Prestige";

                    rewardSecondaryText =
                            "Retournez voir l'Émissaire";
                }

                default -> {
                    rewardCardTitle =
                            "Récompense";

                    rewardMainText =
                            "";

                    rewardSecondaryText =
                            "";
                }
            }

            /*
             * =====================================================
             * RÉCOMPENSE — UNE INVITÉE INATTENDUE
             * =====================================================
             */

        } else {

            switch (unexpectedGuestStatus) {

                case NOT_STARTED, IN_PROGRESS -> {
                    rewardCardTitle =
                            "Récompense de quête";

                    rewardMainText =
                            "+75 Prestige";

                    rewardSecondaryText =
                            "Les Servantes du Dragon";
                }

                case COMPLETED -> {
                    rewardCardTitle =
                            "Récompense disponible";

                    rewardMainText =
                            "+75 Prestige";

                    rewardSecondaryText =
                            "Retournez voir l'Émissaire";
                }

                case REWARDED -> {
                    rewardCardTitle =
                            "Récompense obtenue";

                    rewardMainText =
                            "+75 Prestige obtenu";

                    rewardSecondaryText =
                            "Les Servantes du Dragon débloquée";
                }

                default -> {
                    rewardCardTitle =
                            "Récompense";

                    rewardMainText =
                            "Aucune récompense";

                    rewardSecondaryText =
                            "";
                }
            }
        }

        // =====================================================
        // PROCHAINE RÉCOMPENSE
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                rightX,
                row2Y,
                rightWidth,
                rowHeight,
                Component.literal(rewardCardTitle)
        );

        if (compact) {

            int iconX = rightX + 8;
            int iconY = row2Y + 25;

            guiGraphics.renderItem(
                    REWARD_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(rewardMainText),
                    iconX + 21,
                    row2Y + 29,
                    theme.getAccentColor(),
                    false
            );

        } else {

            int iconX = rightX + 10;
            int iconY = row2Y + 27;

            guiGraphics.renderItem(
                    REWARD_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(rewardMainText),
                    iconX + 23,
                    row2Y + 27,
                    theme.getAccentColor(),
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            rewardSecondaryText
                    ),
                    iconX + 23,
                    row2Y + 41,
                    theme.getTextColor(),
                    false
            );
        }

        // =====================================================
        // DERNIÈRE CHRONIQUE
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                leftX,
                row3Y,
                leftWidth,
                lastRowHeight,
                Component.literal("Dernière chronique")
        );

        if (compact) {

            int iconX = leftX + 8;
            int iconY = row3Y + 25;

            guiGraphics.renderItem(
                    CHRONICLE_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            chronicleCompactName
                    ),
                    iconX + 21,
                    row3Y + 29,
                    theme.getTitleColor(),
                    false
            );

        } else {

            int iconX = leftX + 10;
            int iconY = row3Y + 27;

            guiGraphics.renderItem(
                    CHRONICLE_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            chronicleName
                    ),
                    iconX + 23,
                    row3Y + 27,
                    theme.getTitleColor(),
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            chronicleDescription
                    ),
                    iconX + 23,
                    row3Y + 41,
                    theme.getTextColor(),
                    false
            );
        }

        String activityTitle =
                "Aucune activité récente";

        String activityDetail =
                "";

        /*
         * =====================================================
         * ACTIVITÉ RÉCENTE
         * =====================================================
         *
         * Tant que Premier Contact n'est pas récompensée,
         * on affiche son activité.
         *
         * Ensuite, la quête 2 devient l'activité principale.
         */

        if (firstContactStatus != QuestStatus.REWARDED) {

            switch (firstContactStatus) {

                case NOT_STARTED -> {
                    activityTitle =
                            "Aucune activité récente";

                    activityDetail =
                            "";
                }

                case IN_PROGRESS -> {
                    activityTitle =
                            "Quête acceptée";

                    activityDetail =
                            "Premier Contact";
                }

                case COMPLETED -> {
                    activityTitle =
                            "Quête terminée";

                    activityDetail =
                            "Premier Contact";
                }

                case REWARDED -> {
                    activityTitle =
                            "Récompense obtenue";

                    activityDetail =
                            "Premier Contact";
                }
            }

        } else {

            /*
             * Premier Contact est accompli.
             * On suit maintenant Une invitée inattendue.
             */

            switch (unexpectedGuestStatus) {

                case NOT_STARTED -> {
                    activityTitle =
                            "Quête accomplie";

                    activityDetail =
                            "Premier Contact";
                }

                case IN_PROGRESS -> {
                    activityTitle =
                            "Quête acceptée";

                    activityDetail =
                            "Une invitée inattendue";
                }

                case COMPLETED -> {
                    activityTitle =
                            "Quête terminée";

                    activityDetail =
                            "Une invitée inattendue";
                }

                case REWARDED -> {
                    activityTitle =
                            "Récompense obtenue";

                    activityDetail =
                            "Une invitée inattendue";
                }
            }
        }

        // =====================================================
        // ACTIVITÉ RÉCENTE
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                rightX,
                row3Y,
                rightWidth,
                lastRowHeight,
                Component.literal("Activité récente")
        );

        if (compact) {

            int iconX = rightX + 8;
            int iconY = row3Y + 25;

            guiGraphics.renderItem(
                    ACTIVITY_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(activityTitle),
                    iconX + 21,
                    row3Y + 29,
                    theme.getTextColor(),
                    false
            );

        } else {

            int iconX = rightX + 10;
            int iconY = row3Y + 27;

            guiGraphics.renderItem(
                    ACTIVITY_ICON,
                    iconX,
                    iconY
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(activityTitle),
                    iconX + 23,
                    row3Y + 27,
                    theme.getTitleColor(),
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal(activityDetail),
                    iconX + 23,
                    row3Y + 41,
                    theme.getTextColor(),
                    false
            );
        }
    }
}