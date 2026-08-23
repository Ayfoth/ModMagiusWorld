package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulTaiaQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartSwordsoulTaiaQuestPacket {

    public C2SStartSwordsoulTaiaQuestPacket() {
    }

    public C2SStartSwordsoulTaiaQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartSwordsoulTaiaQuestPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player =
                    context.getSender();

            if (player == null) {
                return;
            }

            QuestManager.get(player).ifPresent(data -> {
                QuestStatus moYeStatus =
                        QuestManager.getStatus(
                                data,
                                SwordsoulMoYeQuest.ID
                        );

                if (moYeStatus != QuestStatus.REWARDED) {
                    player.sendSystemMessage(
                            Component.literal(
                                    "§cVous devez d'abord achever l'enseignement de Mo Ye."
                            )
                    );
                    return;
                }

                boolean started =
                        QuestManager.startQuest(
                                data,
                                SwordsoulTaiaQuest.ID
                        );

                if (!started) {
                    player.sendSystemMessage(
                            Component.literal(
                                    "§cLa quête de Taia ne peut pas être démarrée."
                            )
                    );
                    return;
                }

                QuestSyncManager.sync(player);

                player.sendSystemMessage(
                        Component.literal(
                                "§bNouvelle quête : §fLa Voie de Taia"
                        )
                );
            });
        });

        context.setPacketHandled(true);
    }
}