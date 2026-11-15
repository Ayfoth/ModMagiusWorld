package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.quest.ancientgear.AncientGearThirdQuest;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearSecondQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SStartAncientGearThirdQuestPacket {
    public C2SStartAncientGearThirdQuestPacket() {
    }

    public C2SStartAncientGearThirdQuestPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(C2SStartAncientGearThirdQuestPacket packet,
                              Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            QuestManager.get(player).ifPresent(data -> {
                QuestStatus secondStatus = QuestManager.getStatus(
                        data, AncientGearSecondQuest.ID
                );
                QuestStatus thirdStatus = QuestManager.getStatus(
                        data, AncientGearThirdQuest.ID
                );

                if (thirdStatus != QuestStatus.NOT_STARTED) {
                    player.sendSystemMessage(Component.literal(
                            "§eLa quête Le cœur de Geartown est déjà démarrée ou terminée."
                    ));
                    return;
                }

                if (secondStatus != QuestStatus.REWARDED) {
                    player.sendSystemMessage(Component.literal(
                            "§cLa récompense du Premier automate doit d'abord être réclamée. "
                                    + "État actuel : " + secondStatus
                    ));
                    return;
                }

                // L'état est posé directement côté serveur après vérification du
                // prérequis, afin d'éviter une divergence de synchronisation au
                // moment où la récompense de la deuxième quête vient d'être prise.
                data.setStatus(AncientGearThirdQuest.ID, QuestStatus.IN_PROGRESS);
                QuestSyncManager.sync(player);
                player.sendSystemMessage(Component.literal(
                        "§6Nouvelle quête : §fLe cœur de Geartown"
                ));
            });
        });
        context.setPacketHandled(true);
    }
}
