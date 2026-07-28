package com.magius.world.mod.network.packet;

import com.magius.world.mod.client.gui.RubyScholarMenuScreen;
import com.magius.world.mod.quest.QuestState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Opens the Ruby Scholar action menu on the client.
 *
 * <p>This packet is deliberately separate from the old dialogue packet so
 * opening the action menu can no longer invoke the dialogue screen handler.</p>
 */
public final class S2COpenRubyScholarMenuPacket {

    private final int villagerId;
    private final QuestState questState;

    public S2COpenRubyScholarMenuPacket(
            int villagerId,
            QuestState questState
    ) {
        this.villagerId = villagerId;
        this.questState = questState;
    }

    public S2COpenRubyScholarMenuPacket(FriendlyByteBuf buffer) {
        this.villagerId = buffer.readVarInt();
        this.questState = QuestState.fromName(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(villagerId);
        buffer.writeUtf(questState.name());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> Minecraft.getInstance().setScreen(
                new RubyScholarMenuScreen(villagerId, questState)
        ));

        context.setPacketHandled(true);
    }
}
