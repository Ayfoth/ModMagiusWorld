package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartForgottenHomeQuestPacket {

    private static final ResourceLocation PREVIOUS_QUEST =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    public C2SStartForgottenHomeQuestPacket() {
    }

    public C2SStartForgottenHomeQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartForgottenHomeQuestPacket packet,
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

                        /*
                         * La quête 2 doit obligatoirement
                         * avoir été récompensée.
                         */
                        QuestStatus previousStatus =
                                QuestManager.getStatus(
                                        data,
                                        PREVIOUS_QUEST
                                );

                        if (previousStatus != QuestStatus.REWARDED) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cVous devez d'abord terminer la mission précédente."
                                    )
                            );

                            return;
                        }

                        /*
                         * La quête 3 doit être encore vierge.
                         */
                        QuestStatus currentStatus =
                                QuestManager.getStatus(
                                        data,
                                        QUEST_ID
                                );

                        if (currentStatus != QuestStatus.NOT_STARTED) {
                            return;
                        }

                        boolean started =
                                QuestManager.startQuest(
                                        data,
                                        QUEST_ID
                                );

                        if (!started) {
                            return;
                        }

                        QuestSyncManager.sync(player);

                        player.sendSystemMessage(
                                Component.literal(
                                        "§6Nouvelle quête : §fLe foyer oublié"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
