package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedTwinsQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartUnchainedTwinsQuestPacket {

    public C2SStartUnchainedTwinsQuestPacket() {
    }

    public C2SStartUnchainedTwinsQuestPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SStartUnchainedTwinsQuestPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            if (!QuestRegistry.contains(UnchainedTwinsQuest.ID)) {
                player.sendSystemMessage(Component.literal(
                        "§cErreur : la quête Les Jumeaux de la Destruction n'est pas enregistrée."
                ));
                return;
            }

            var questData = QuestManager.get(player).resolve();
            if (questData.isEmpty()) {
                player.sendSystemMessage(Component.literal(
                        "§cErreur : les données de quête du joueur sont absentes."
                ));
                return;
            }

            boolean started = QuestManager.startQuest(
                    questData.get(),
                    UnchainedTwinsQuest.ID
            );

            if (!started) {
                player.sendSystemMessage(Component.literal(
                        "§cLa quête Les Jumeaux de la Destruction ne peut pas être démarrée."
                ));
                return;
            }

            QuestSyncManager.sync(player);
            player.sendSystemMessage(Component.literal(
                    "§4Nouvelle quête : §fLes Jumeaux de la Destruction"
            ));
        });

        context.setPacketHandled(true);
    }
}
