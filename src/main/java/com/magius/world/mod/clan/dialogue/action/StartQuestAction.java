package com.magius.world.mod.clan.dialogue.action;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.StartQuestC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class StartQuestAction implements DialogueAction {

    private final ResourceLocation questId;

    public StartQuestAction(ResourceLocation questId) {
        this.questId = questId;
    }

    @Override
    public void execute(Player player) {
        ModMessages.sendToServer(
                new StartQuestC2SPacket(this.questId)
        );

        Minecraft.getInstance().setScreen(null);
    }
}
