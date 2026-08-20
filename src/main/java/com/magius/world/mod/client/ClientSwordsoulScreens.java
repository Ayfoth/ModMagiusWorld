package com.magius.world.mod.client;

import com.magius.world.mod.client.gui.SwordsoulSpiritForgeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class ClientSwordsoulScreens {

    private ClientSwordsoulScreens() {
    }

    public static void openSpiritForge(
            BlockPos terminalPos
    ) {

        Minecraft minecraft =
                Minecraft.getInstance();

        minecraft.execute(() ->
                minecraft.setScreen(
                        new SwordsoulSpiritForgeScreen(
                                terminalPos
                        )
                )
        );
    }
}
