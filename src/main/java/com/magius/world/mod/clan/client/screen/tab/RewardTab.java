package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.client.screen.widget.ClanCardRenderer;
import com.magius.world.mod.clan.data.PlayerClanData;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.reward.ClanReward;
import com.magius.world.mod.clan.reward.ClanRewardRegistry;
import com.magius.world.mod.clan.theme.ClanTheme;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimClanRewardPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class RewardTab implements ClanTab {

    @Override
    public Component getTitle() {
        return Component.literal("Récompenses");
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

        Minecraft minecraft =
                Minecraft.getInstance();

        var font =
                minecraft.font;

        var player =
                minecraft.player;

        int prestige = 0;

        Optional<PlayerClanData> clanDataOptional =
                Optional.empty();

        if (player != null) {

            clanDataOptional =
                    ClanManager.get(player)
                            .resolve();
        }

        if (clanDataOptional.isPresent()) {

            prestige =
                    clanDataOptional.get()
                            .getPrestige();
        }

        List<ClanReward> rewards =
                ClanRewardRegistry.getForClan(
                        clanId
                );

        if (rewards.isEmpty()) {

            guiGraphics.drawCenteredString(
                    font,
                    Component.literal(
                            "Aucune récompense disponible pour ce clan."
                    ),
                    x + width / 2,
                    y + 20,
                    theme.getTextColor()
            );

            return;
        }

        int gap = 3;

        int cardHeight =
                (height - gap * (rewards.size() - 1))
                        / rewards.size();

        int currentY = y;

        for (ClanReward reward : rewards) {

            boolean unlocked =
                    prestige
                            >= reward.getRequiredPrestige();

            boolean claimed =
                    clanDataOptional.isPresent()
                            && clanDataOptional
                            .get()
                            .hasClaimedClanReward(
                                    reward.getId()
                            );

            /*
             * =============================================
             * CARTE
             * =============================================
             */

            ClanCardRenderer.render(
                    guiGraphics,
                    theme,
                    x,
                    currentY,
                    width,
                    cardHeight,
                    Component.literal(
                            reward.getTitle()
                    )
            );

            String stateText;
            int stateColor;

            if (claimed) {

                stateText =
                        "✓ Récupéré";

                stateColor =
                        theme.getTitleColor();

            } else if (unlocked) {

                stateText =
                        "★ Disponible";

                stateColor =
                        theme.getAccentColor();

            } else {

                stateText =
                        "🔒 "
                                + reward.getRequiredPrestige()
                                + " Prestige";

                stateColor =
                        theme.getTextColor();
            }

            int stateWidth =
                    font.width(
                            stateText
                    );

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            stateText
                    ),
                    x
                            + width
                            - stateWidth
                            - 10,
                    currentY + 8,
                    stateColor,
                    false
            );

            /*
             * =============================================
             * TYPE DE RÉCOMPENSE
             * =============================================
             */

            String typeText =
                    switch (reward.getType()) {

                        case ITEMS ->
                                "Récompense d'objets";

                        case UNLOCK ->
                                "Déblocage permanent";

                        case RECIPES ->
                                "Nouvelles recettes";

                        case SPECIAL ->
                                "Récompense spéciale";
                    };

            guiGraphics.drawString(
                    font,
                    Component.literal(typeText),
                    x + 10,
                    currentY + 20     ,
                    unlocked
                            ? theme.getTextColor()
                            : 0xFF777777,
                    false
            );

            /*
             * =============================================
             * INFOBULLE AU SURVOL
             * =============================================
             */

            boolean hovered =
                    mouseX >= x
                            && mouseX < x + width
                            && mouseY >= currentY
                            && mouseY < currentY + cardHeight;

            if (hovered) {

                guiGraphics.renderTooltip(
                        font,
                        font.split(
                                Component.literal(
                                        reward.getDescription()
                                ),
                                260
                        ),
                        mouseX,
                        mouseY
                );
            }

            currentY +=
                    cardHeight + gap;

            if (
                    currentY + cardHeight
                            > y + height + cardHeight
            ) {
                break;
            }
        }
    }
    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {

        if (button != 0) {
            return false;
        }

        Minecraft minecraft =
                Minecraft.getInstance();

        var player =
                minecraft.player;

        if (player == null) {
            return false;
        }

        Optional<PlayerClanData> clanDataOptional =
                ClanManager.get(player)
                        .resolve();

        if (clanDataOptional.isEmpty()) {
            return false;
        }

        PlayerClanData clanData =
                clanDataOptional.get();

        List<ClanReward> rewards =
                ClanRewardRegistry.getForClan(
                        clanId
                );

        if (rewards.isEmpty()) {
            return false;
        }

        int gap = 3;

        int cardHeight =
                (height - gap * (rewards.size() - 1))
                        / rewards.size();

        int currentY = y;

        for (ClanReward reward : rewards) {

            boolean hovered =
                    mouseX >= x
                            && mouseX < x + width
                            && mouseY >= currentY
                            && mouseY < currentY + cardHeight;

            if (hovered) {

                boolean unlocked =
                        clanData.getPrestige()
                                >= reward.getRequiredPrestige();

                boolean claimed =
                        clanData.hasClaimedClanReward(
                                reward.getId()
                        );

                if (!unlocked || claimed) {
                    return true;
                }

                ModMessages.sendToServer(
                        new C2SClaimClanRewardPacket(
                                reward.getId()
                        )
                );

                return true;
            }

            currentY +=
                    cardHeight + gap;
        }

        return false;
    }
}