package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.dragonmaid.DragonmaidFirstQuestEvents;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartDragonmaidQuestPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );
    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid"
            );

    public C2SStartDragonmaidQuestPacket() {
    }

    public C2SStartDragonmaidQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartDragonmaidQuestPacket packet,
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

            /*
             * =========================================
             * ADHÉSION AU CLAN DRAGONMAID
             * =========================================
             */

            ClanManager.get(player)
                    .ifPresent(clanData -> {

                        if (!clanData.hasJoinedClan(CLAN_ID)) {

                            clanData.getProgress(
                                    CLAN_ID
                            );

                            ClanSyncManager.sync(
                                    player
                            );
                        }
                    });

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
                                            "§cLa quête Premier contact ne peut pas être démarrée."
                                    )
                            );

                            return;
                        }

                        /*
                         * Cas où le joueur possède déjà
                         * le Grimoire Dragonmaid.
                         */
                        DragonmaidFirstQuestEvents
                                .checkInventory(player);

                        /*
                         * Synchronisation client.
                         */
                        QuestSyncManager.sync(player);

                        player.sendSystemMessage(
                                Component.literal(
                                        "§aNouvelle quête : §fPremier contact"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
