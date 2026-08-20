package com.magius.world.mod.network.packet;


import com.magius.world.mod.clan.quest.manager.QuestManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartQuestC2SPacket {

    private final ResourceLocation questId;

    public StartQuestC2SPacket(ResourceLocation questId) {
        this.questId = questId;
    }

    public StartQuestC2SPacket(FriendlyByteBuf buffer) {
        this.questId = buffer.readResourceLocation();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.questId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();

        context.enqueueWork(() -> {
            if (player == null) {
                return;
            }

            QuestManager.get(player).ifPresent(data -> {
                boolean started = QuestManager.startQuest(
                        data,
                        this.questId
                );

                if (started) {
                    System.out.println(
                            "[MagiusWorld] Quête démarrée côté serveur : "
                                    + this.questId
                    );
                } else {
                    System.out.println(
                            "[MagiusWorld] Impossible de démarrer la quête : "
                                    + this.questId
                    );
                }
            });
        });

        context.setPacketHandled(true);
    }
}
