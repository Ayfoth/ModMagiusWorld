package com.magius.world.mod.clan.client.screen.widget;

import com.magius.world.mod.clan.api.ClanRank;
import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanRegistry;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class ClanHeaderRenderer {

    private ClanHeaderRenderer() {
    }

    public static void render(
            GuiGraphics graphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {
        var font = Minecraft.getInstance().font;
        var player =
                Minecraft.getInstance().player;

        int prestige = 0;
        int rankIndex = 0;

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

                /*
                 * Le header ne reçoit pas directement clanId,
                 * contrairement à HomeTab.
                 *
                 * On utilise donc l'identifiant du thème actuellement
                 * affiché pour retrouver Dragonmaid.
                 */
                var clanOptional =
                        ClanRegistry.get("dragonmaid");

                if (clanOptional.isPresent()) {

                    var ranks =
                            clanOptional.get()
                                    .getRanks();

                    if (!ranks.isEmpty()) {

                        /*
                         * Calcul du rang directement depuis
                         * le prestige réel.
                         */
                        rankIndex = 0;

                        for (int i = 0; i < ranks.size(); i++) {

                            if (prestige >=
                                    ranks.get(i)
                                            .getRequiredPrestige()) {

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

        boolean compact = height <= 72;

        ClanThemeRenderer.renderInnerBox(
                graphics,
                theme,
                x,
                y,
                width,
                height
        );

        int padding = compact ? 5 : 7;

        // =====================================================
        // EMBLÈME
        // =====================================================

        int emblemSize = height - padding * 2;

        if (compact) {
            emblemSize = Math.min(emblemSize, 58);
        }

        int emblemX = x + padding;
        int emblemY = y + (height - emblemSize) / 2;

        graphics.fill(
                emblemX,
                emblemY,
                emblemX + emblemSize,
                emblemY + emblemSize,
                theme.getTitleColor()
        );

        graphics.fill(
                emblemX + 2,
                emblemY + 2,
                emblemX + emblemSize - 2,
                emblemY + emblemSize - 2,
                theme.getAccentColor()
        );

        graphics.fill(
                emblemX + 4,
                emblemY + 4,
                emblemX + emblemSize - 4,
                emblemY + emblemSize - 4,
                theme.getButtonColor()
        );

        ClanThemeRenderer.renderIcon(
                graphics,
                theme,
                emblemX + 6,
                emblemY + 6,
                Math.max(8, emblemSize - 12)
        );

        // =====================================================
        // STATS DROITE
        // =====================================================

        int statsWidth = compact ? 105 : 145;

        int statsX =
                x + width
                        - statsWidth
                        - padding;

        int separatorX = statsX - 6;

        // =====================================================
        // INFORMATIONS CENTRALES
        // =====================================================

        int infoX =
                emblemX
                        + emblemSize
                        + 10;

        int infoWidth =
                separatorX
                        - infoX
                        - 7;

        graphics.drawString(
                font,
                theme.getDisplayName(),
                infoX,
                y + 7,
                theme.getTitleColor(),
                false
        );

        graphics.drawString(
                font,
                Component.literal(
                        theme.getReputationName().getString()
                                + " : "
                                + prestige
                                + (
                                prestigeTarget > prestigeStart
                                        ? " / " + prestigeTarget
                                        : " — MAX"
                        )
                ),
                infoX,
                y + 21,
                theme.getTextColor(),
                false
        );

        ClanProgressBarRenderer.render(
                graphics,
                infoX,
                y + 35,
                Math.max(25, infoWidth),
                8,
                progressValue,
                progressMaximum,
                0xFF180B0C,
                theme.getAccentColor(),
                theme.getTitleColor()
        );

        graphics.drawString(
                font,
                Component.literal(
                        prestigePercent + " %"
                ),
                infoX,
                y + 47,
                theme.getAccentColor(),
                false
        );

        // Description seulement en mode normal
        if (!compact) {
            graphics.drawString(
                    font,
                    Component.literal(
                            "Les gardiennes d'un ancien héritage draconique."
                    ),
                    infoX,
                    y + 62,
                    theme.getTextColor(),
                    false
            );
        }

        // =====================================================
        // SÉPARATEUR
        // =====================================================

        graphics.fill(
                separatorX,
                y + 6,
                separatorX + 1,
                y + height - 6,
                theme.getAccentColor()
        );

        // =====================================================
        // STATISTIQUES
        // =====================================================

        int statY = y + 7;

        graphics.drawString(
                font,
                Component.literal(
                        "★ Rang : " + rankName
                ),
                statsX,
                statY,
                theme.getTextColor(),
                false
        );

        graphics.drawString(
                font,
                Component.literal(
                        "• Niveau : " + (rankIndex + 1)
                ),
                statsX,
                statY + 13,
                theme.getTextColor(),
                false
        );

        if (!compact) {

            graphics.drawString(
                    font,
                    Component.literal("Membres : 0 / 12"),
                    statsX,
                    statY + 27,
                    theme.getTextColor(),
                    false
            );

            graphics.drawString(
                    font,
                    Component.literal("Chroniques : 1 / 20"),
                    statsX,
                    statY + 40,
                    theme.getTextColor(),
                    false
            );

            graphics.drawString(
                    font,
                    Component.literal("Quêtes : 2 / 18"),
                    statsX,
                    statY + 53,
                    theme.getTextColor(),
                    false
            );
        }
    }
}
