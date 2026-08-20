package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartUnexpectedGuestQuestPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );

    public C2SStartUnexpectedGuestQuestPacket() {
    }

    public C2SStartUnexpectedGuestQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartUnexpectedGuestQuestPacket packet,
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

            QuestManager.get(player)
                    .ifPresent(data -> {

                        boolean started =
                                QuestManager.startQuest(
                                        data,
                                        QUEST_ID
                                );

                        if (!started) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cLa quête Une invitée inattendue ne peut pas être démarrée."
                                    )
                            );

                            return;
                        }

                        /*
                         * Synchronisation immédiate avec le client.
                         */
                        QuestSyncManager.sync(
                                player
                        );

                        player.sendSystemMessage(
                                Component.literal(
                                        "§aNouvelle quête : §fUne invitée inattendue"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
