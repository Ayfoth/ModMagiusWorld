package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulFirstQuestEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartSwordsoulFirstQuestPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    public C2SStartSwordsoulFirstQuestPacket() {
    }

    public C2SStartSwordsoulFirstQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SStartSwordsoulFirstQuestPacket packet,
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
             * Initialise la progression Swordsoul.
             *
             * Cela ne signifie PAS que Swordsoul devient
             * automatiquement le clan actif.
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
                                            "§cLa quête L'Épée sans maître ne peut pas être démarrée."
                                    )
                            );

                            return;
                        }

                        /*
                         * Cas où le joueur possède déjà
                         * la Lame spirituelle brisée.
                         */
                        SwordsoulFirstQuestEvents
                                .checkInventory(player);

                        QuestSyncManager.sync(player);

                        player.sendSystemMessage(
                                Component.literal(
                                        "§bNouvelle quête : §fL'Épée sans maître"
                                )
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}