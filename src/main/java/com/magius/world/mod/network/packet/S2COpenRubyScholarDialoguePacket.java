package com.magius.world.mod.network.packet;

import com.magius.world.mod.client.gui.RubyScholarDialogueScreen;
import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2COpenRubyScholarDialoguePacket {

    private final QuestState questState;

    public S2COpenRubyScholarDialoguePacket(QuestState questState) {
        this.questState = questState;
    }

    public S2COpenRubyScholarDialoguePacket(FriendlyByteBuf buffer) {
        this.questState = QuestState.fromName(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(questState.name());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() ->
                Minecraft.getInstance().setScreen(
                        new RubyScholarDialogueScreen(questState)
                )
        );

        context.setPacketHandled(true);
    }
}

