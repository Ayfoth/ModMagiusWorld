package com.magius.world.mod.network.packet;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestRegistry;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedChainReactionQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartUnchainedChainReactionQuestPacket {

    public C2SStartUnchainedChainReactionQuestPacket() {
    }

    public C2SStartUnchainedChainReactionQuestPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SStartUnchainedChainReactionQuestPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            if (!QuestRegistry.contains(
                    UnchainedChainReactionQuest.ID
            )) {
                player.sendSystemMessage(Component.literal(
                        "§cErreur : la quête La Réaction en chaîne n'est pas enregistrée."
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
                    UnchainedChainReactionQuest.ID
            );

            if (!started) {
                player.sendSystemMessage(Component.literal(
                        "§cLa quête La Réaction en chaîne ne peut pas être démarrée."
                ));
                return;
            }

            ItemStack disasterSeal = new ItemStack(
                    ModBlocks.UNCHAINED_DISASTER_SEAL.get()
            );

            if (!player.getInventory().add(disasterSeal)) {
                player.drop(disasterSeal, false);
            }

            player.containerMenu.broadcastChanges();

            QuestSyncManager.sync(player);
            player.sendSystemMessage(Component.literal(
                    "§4Nouvelle quête : §fLa Réaction en chaîne "
                            + "§8— §cLe Gardien vous confie un Sceau du Désastre."
            ));
        });

        context.setPacketHandled(true);
    }
}
