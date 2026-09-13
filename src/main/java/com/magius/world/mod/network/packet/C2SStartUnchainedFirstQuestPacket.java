package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.data.PlayerClanCapability;
import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedPrisonQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartUnchainedFirstQuestPacket {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained"
            );

    public C2SStartUnchainedFirstQuestPacket() {
    }

    public C2SStartUnchainedFirstQuestPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SStartUnchainedFirstQuestPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            ClanManager.get(player).ifPresent(clanData -> {
                if (!clanData.hasJoinedClan(CLAN_ID)) {
                    clanData.getProgress(CLAN_ID);
                    ClanSyncManager.sync(player);
                }
            });

            if (!QuestRegistry.contains(UnchainedPrisonQuest.ID)) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cErreur : la quête Unchained n'est pas enregistrée."
                        )
                );
                return;
            }

            var questData = QuestManager.get(player).resolve();

            if (questData.isEmpty()) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cErreur capability : questRegistered="
                                        + PlayerQuestCapability.INSTANCE.isRegistered()
                                        + ", clanRegistered="
                                        + PlayerClanCapability.INSTANCE.isRegistered()
                                        + ", clanPresent="
                                        + ClanManager.get(player).isPresent()
                        )
                );
                return;
            }

            boolean started = QuestManager.startQuest(
                    questData.get(),
                    UnchainedPrisonQuest.ID
            );

            if (!started) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cLa quête La Prison de l'Abomination ne peut pas être démarrée."
                        )
                );
                return;
            }

            QuestSyncManager.sync(player);
            player.sendSystemMessage(
                    Component.literal(
                            "§4Nouvelle quête : §fLa Prison de l'Abomination"
                    )
            );
        });

        context.setPacketHandled(true);
    }
}
