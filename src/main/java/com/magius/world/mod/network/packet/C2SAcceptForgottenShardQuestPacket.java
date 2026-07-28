package com.magius.world.mod.network.packet;

import com.magius.world.mod.quest.QuestIds;
import com.magius.world.mod.quest.QuestManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SAcceptForgottenShardQuestPacket {

    public C2SAcceptForgottenShardQuestPacket() {
    }

    public C2SAcceptForgottenShardQuestPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();

        context.enqueueWork(() -> {
            if (player != null) {
                QuestManager.startQuest(
                        player,
                        QuestIds.FORGOTTEN_SHARD
                );
            }
        });

        context.setPacketHandled(true);
    }
}
