package com.magius.world.mod.client;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = MagiusWorldMod.MOD_ID,
        value = Dist.CLIENT
)
public final class DragonAwakeningHud {

    private DragonAwakeningHud() {
    }

    @SubscribeEvent
    public static void onRenderGui(
            RenderGuiOverlayEvent.Post event
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        boolean active =
                DragonAwakeningClientData.isActive();

        boolean cooldown =
                DragonAwakeningClientData.isOnCooldown();

        if (!active && !cooldown) {
            return;
        }

        GuiGraphics guiGraphics =
                event.getGuiGraphics();

        Font font =
                minecraft.font;

        int x = 10;
        int y = 10;

        int width = 150;
        int height = 42;

        /*
         * Fond.
         */
        guiGraphics.fill(
                x,
                y,
                x + width,
                y + height,
                0xCC16080B
        );

        /*
         * Bordure dorée.
         */
        guiGraphics.fill(
                x,
                y,
                x + width,
                y + 1,
                0xFFD5A63A
        );

        guiGraphics.fill(
                x,
                y + height - 1,
                x + width,
                y + height,
                0xFFD5A63A
        );

        guiGraphics.fill(
                x,
                y,
                x + 1,
                y + height,
                0xFFD5A63A
        );

        guiGraphics.fill(
                x + width - 1,
                y,
                x + width,
                y + height,
                0xFFD5A63A
        );

        guiGraphics.drawString(
                font,
                "Réveil Draconique",
                x + 8,
                y + 7,
                0xFFD5A63A,
                false
        );

        if (active) {

            renderActive(
                    guiGraphics,
                    font,
                    x,
                    y
            );

        } else {

            renderCooldown(
                    guiGraphics,
                    font,
                    x,
                    y
            );
        }
    }

    private static void renderActive(
            GuiGraphics guiGraphics,
            Font font,
            int x,
            int y
    ) {

        int ticks =
                DragonAwakeningClientData
                        .getActiveTicks();

        int seconds =
                (ticks + 19) / 20;

        guiGraphics.drawString(
                font,
                "Actif : " + seconds + "s",
                x + 8,
                y + 19,
                0xFFFFE8CC,
                false
        );

        /*
         * Barre de durée.
         */
        int barX =
                x + 8;

        int barY =
                y + 31;

        int barWidth =
                134;

        int barHeight =
                5;

        guiGraphics.fill(
                barX,
                barY,
                barX + barWidth,
                barY + barHeight,
                0xFF3A161B
        );

        float progress =
                ticks / 400.0F;

        int filledWidth =
                (int) (
                        barWidth * progress
                );

        guiGraphics.fill(
                barX,
                barY,
                barX + filledWidth,
                barY + barHeight,
                0xFFB52A3A
        );
    }

    private static void renderCooldown(
            GuiGraphics guiGraphics,
            Font font,
            int x,
            int y
    ) {

        long remainingMillis =
                DragonAwakeningClientData
                        .getRemainingCooldownMillis();

        long remainingSeconds =
                (remainingMillis + 999L)
                        / 1000L;

        long minutes =
                remainingSeconds / 60L;

        long seconds =
                remainingSeconds % 60L;

        String cooldownText =
                String.format(
                        "Recharge : %d:%02d",
                        minutes,
                        seconds
                );

        guiGraphics.drawString(
                font,
                cooldownText,
                x + 8,
                y + 22,
                0xFFD7C6C9,
                false
        );
    }
}
