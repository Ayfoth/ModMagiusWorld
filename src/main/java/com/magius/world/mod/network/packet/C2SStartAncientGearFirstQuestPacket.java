package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.data.PlayerClanCapability;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearFirstQuest;
import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartAncientGearFirstQuestPacket {

    public C2SStartAncientGearFirstQuestPacket() {
    }

    public C2SStartAncientGearFirstQuestPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SStartAncientGearFirstQuestPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            if (!QuestRegistry.contains(AncientGearFirstQuest.ID)) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cErreur : la quête Rouages Ancients n'est pas enregistrée."
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
                    AncientGearFirstQuest.ID
            );

            if (!started) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cLa quête Les Rouages endormis ne peut pas être démarrée."
                        )
                );
                return;
            }

            QuestSyncManager.sync(player);
            player.sendSystemMessage(
                    Component.literal(
                            "§6Nouvelle quête : §fLes Rouages endormis"
                    )
            );
        });

        context.setPacketHandled(true);
    }
}
