package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.api.ClanRank;
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

import java.util.List;

public class PrestigeTab implements ClanTab {

    private static final ItemStack PRESTIGE_ICON =
            new ItemStack(Items.NETHER_STAR);
    private int rankScrollOffset = 0;

    @Override
    public Component getTitle() {
        return Component.literal("Prestige");
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

        var minecraft =
                Minecraft.getInstance();

        var font =
                minecraft.font;

        var player =
                minecraft.player;

        int prestige = 0;

        if (player != null) {

            prestige =
                    ClanManager.get(player)
                            .resolve()
                            .map(data ->
                                    data.getPrestige()
                            )
                            .orElse(0);
        }

        var clanOptional =
                ClanRegistry.get(clanId);

        if (clanOptional.isEmpty()) {

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            "Clan introuvable."
                    ),
                    x + 10,
                    y + 10,
                    theme.getTextColor(),
                    false
            );

            return;
        }

        List<ClanRank> ranks =
                clanOptional.get()
                        .getRanks();

        if (ranks.isEmpty()) {
            return;
        }

        /*
         * =====================================================
         * RANG ACTUEL
         * =====================================================
         */

        int currentRankIndex = 0;

        for (int i = 0; i < ranks.size(); i++) {

            if (
                    prestige
                            >= ranks.get(i)
                            .getRequiredPrestige()
            ) {

                currentRankIndex = i;

            } else {

                break;
            }
        }

        ClanRank currentRank =
                ranks.get(currentRankIndex);

        ClanRank nextRank =
                currentRankIndex + 1 < ranks.size()
                        ? ranks.get(currentRankIndex + 1)
                        : null;

        /*
         * =====================================================
         * CARTE PRINCIPALE
         * =====================================================
         */

        int mainHeight =
                Math.min(
                        100,
                        Math.max(
                                90,
                                height / 3
                        )
                );

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                y,
                width,
                mainHeight,
                Component.literal(
                        "Prestige du clan"
                )
        );

        guiGraphics.renderItem(
                PRESTIGE_ICON,
                x + 12,
                y + 29
        );



        guiGraphics.drawString(
                font,
                Component.literal(
                        currentRank.getName()
                ),
                x + 35,
                y + 28,
                theme.getTitleColor(),
                false
        );

        guiGraphics.drawString(
                font,
                Component.literal(
                        prestige + " Prestige"
                ),
                x + 35,
                y + 42,
                theme.getTextColor(),
                false
        );

        /*
         * =====================================================
         * PROGRESSION VERS LE PROCHAIN RANG
         * =====================================================
         */

        int barX =
                x + 12;

        int barY =
                y + mainHeight - 14;

        int barWidth =
                width - 24;

        if (nextRank != null) {

            int start =
                    currentRank.getRequiredPrestige();

            int target =
                    nextRank.getRequiredPrestige();

            int value =
                    Math.max(
                            0,
                            prestige - start
                    );

            int maximum =
                    Math.max(
                            1,
                            target - start
                    );

            ClanProgressBarRenderer.render(
                    guiGraphics,
                    barX,
                    barY,
                    barWidth,
                    8,
                    value,
                    maximum,
                    0xFF180B0C,
                    theme.getAccentColor(),
                    theme.getTitleColor()
            );

            String progressText =
                    prestige
                            + " / "
                            + target
                            + " → "
                            + nextRank.getName();

            guiGraphics.drawCenteredString(
                    font,
                    Component.literal(
                            progressText
                    ),
                    x + width / 2,
                    barY - 13,
                    theme.getTextColor()
            );

        } else {

            ClanProgressBarRenderer.render(
                    guiGraphics,
                    barX,
                    barY,
                    barWidth,
                    8,
                    1,
                    1,
                    0xFF180B0C,
                    theme.getAccentColor(),
                    theme.getTitleColor()
            );

            guiGraphics.drawCenteredString(
                    font,
                    Component.literal(
                            "Rang maximum atteint"
                    ),
                    x + width / 2,
                    barY - 11,
                    theme.getTitleColor()
            );
        }

        /*
         * =====================================================
         * LISTE DES RANGS
         * =====================================================
         */

        int listY =
                y + mainHeight + 7;

        int remainingHeight =
                height
                        - mainHeight
                        - 7;

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                listY,
                width,
                remainingHeight,
                Component.literal(
                        "Rangs"
                )
        );

        int listContentTop =
                listY + 24;

        int listContentBottom =
                listY + remainingHeight - 6;

        int visibleHeight =
                Math.max(
                        0,
                        listContentBottom - listContentTop
                );

        int lineHeight = 15;

        int totalHeight =
                ranks.size() * lineHeight;

        int maxScroll =
                Math.max(
                        0,
                        totalHeight - visibleHeight
                );

        rankScrollOffset =
                Math.max(
                        0,
                        Math.min(
                                rankScrollOffset,
                                maxScroll
                        )
                );

        guiGraphics.enableScissor(
                x,
                listContentTop,
                x + width,
                listContentBottom
        );

        int lineY =
                listContentTop - rankScrollOffset;

        for (ClanRank rank : ranks) {

            boolean unlocked =
                    prestige
                            >= rank.getRequiredPrestige();

            String marker =
                    unlocked
                            ? "✓ "
                            : "🔒 ";

            String text =
                    marker
                            + rank.getName()
                            + " — "
                            + rank.getRequiredPrestige()
                            + " Prestige";

            int color =
                    unlocked
                            ? theme.getTitleColor()
                            : theme.getTextColor();

            guiGraphics.drawString(
                    font,
                    Component.literal(text),
                    x + 12,
                    lineY,
                    color,
                    false
            );

            lineY += lineHeight;
        }

        guiGraphics.disableScissor();

        /*
         * Barre de défilement
         */
        if (maxScroll > 0) {

            int scrollbarX =
                    x + width - 7;

            int scrollbarHeight =
                    visibleHeight;

            int thumbHeight =
                    Math.max(
                            12,
                            visibleHeight * visibleHeight / totalHeight
                    );

            int thumbTravel =
                    scrollbarHeight - thumbHeight;

            int thumbY =
                    listContentTop
                            + rankScrollOffset
                            * thumbTravel
                            / maxScroll;

            guiGraphics.fill(
                    scrollbarX,
                    listContentTop,
                    scrollbarX + 3,
                    listContentBottom,
                    0xFF180B0C
            );

            guiGraphics.fill(
                    scrollbarX,
                    thumbY,
                    scrollbarX + 3,
                    thumbY + thumbHeight,
                    theme.getAccentColor()
            );
        }
    }
    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double delta,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {

        int mainHeight =
                Math.min(
                        100,
                        Math.max(
                                90,
                                height / 3
                        )
                );

        int listY =
                y + mainHeight + 7;

        int remainingHeight =
                height - mainHeight - 7;

        boolean hovered =
                mouseX >= x
                        && mouseX < x + width
                        && mouseY >= listY
                        && mouseY < listY + remainingHeight;

        if (!hovered) {
            return false;
        }

        var clanOptional =
                ClanRegistry.get(clanId);

        if (clanOptional.isEmpty()) {
            return false;
        }

        int visibleHeight =
                Math.max(
                        0,
                        remainingHeight - 30
                );

        int totalHeight =
                clanOptional.get()
                        .getRanks()
                        .size() * 15;

        int maxScroll =
                Math.max(
                        0,
                        totalHeight - visibleHeight
                );

        if (maxScroll == 0) {
            return false;
        }

        rankScrollOffset -=
                (int) Math.signum(delta) * 15;

        rankScrollOffset =
                Math.max(
                        0,
                        Math.min(
                                rankScrollOffset,
                                maxScroll
                        )
                );

        return true;
    }
}