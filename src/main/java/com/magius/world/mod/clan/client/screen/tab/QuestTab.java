package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class QuestTab implements ClanTab {

    @Override
    public Component getTitle() {
        return Component.literal("Quêtes");
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

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                Component.literal("Quêtes du clan"),
                x + 10,
                y + 10,
                theme.getTitleColor(),
                false
        );

        guiGraphics.drawString(
                Minecraft.getInstance().font,
                Component.literal("Les quêtes actives apparaîtront ici."),
                x + 10,
                y + 30,
                theme.getTextColor(),
                false
        );

    }
}