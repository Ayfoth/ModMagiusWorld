package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.client.screen.SwordsoulTaiaDialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenSwordsoulTaiaDialoguePacket {

    public S2COpenSwordsoulTaiaDialoguePacket() {
    }

    public S2COpenSwordsoulTaiaDialoguePacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public void handle(
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() ->
                Minecraft.getInstance().setScreen(
                        new SwordsoulTaiaDialogueScreen()
                )
        );

        context.setPacketHandled(true);
    }
}