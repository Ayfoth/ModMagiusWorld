package com.magius.world.mod.client.hud;

import com.magius.world.mod.client.ClientCorruptionData;
import com.magius.world.mod.corruption.CorruptionHelper;
import com.magius.world.mod.corruption.CorruptionLevel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CorruptionHudOverlay {

    public static final IGuiOverlay HUD_CORRUPTION = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;
        if (minecraft.options.hideGui) return;

        int corruption = ClientCorruptionData.get();
        CorruptionLevel level = ClientCorruptionData.getLevel();

        if (corruption <= 0) return;

        int x = 10;
        int y = 10;

        guiGraphics.drawString(
                minecraft.font,
                "Corruption : " + corruption + " / 100",
                x,
                y,
                0xAA55FF,
                true
        );

        guiGraphics.drawString(
                minecraft.font,
                "Niveau : " + level.name(),
                x,
                y + 10,
                0x8844CC,
                true
        );
    };
}
