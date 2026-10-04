package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearFirstQuest;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.entity.ancientgear.AncientGearEmissaryEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimAncientGearFirstQuestRewardPacket {

    private static final ResourceLocation CLAN_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ancient_gear");

    public C2SClaimAncientGearFirstQuestRewardPacket() {
    }

    public C2SClaimAncientGearFirstQuestRewardPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SClaimAncientGearFirstQuestRewardPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }

            // Le bouton ne peut servir que lors du dialogue avec l'émissaire.
            if (player.level().getEntitiesOfClass(
                    AncientGearEmissaryEntity.class,
                    player.getBoundingBox().inflate(8.0D)
            ).isEmpty()) {
                return;
            }

            var clanData = ClanManager.get(player).resolve();
            var questData = QuestManager.get(player).resolve();
            if (clanData.isEmpty() || questData.isEmpty()) {
                return;
            }

            // rewardQuest refuse tout état autre que COMPLETED : plusieurs clics
            // ou paquets envoyés en double ne rapportent qu'une seule fois.
            if (!QuestManager.rewardQuest(questData.get(), AncientGearFirstQuest.ID)) {
                return;
            }

            clanData.get().setActiveClan(CLAN_ID);
            clanData.get().getProgress(CLAN_ID)
                    .addPrestige(new AncientGearFirstQuest().getPrestigeReward());
            QuestSyncManager.sync(player);
            ClanSyncManager.sync(player);
            player.sendSystemMessage(Component.literal(
                    "§6Les Rouages endormis : §f+50 prestige Ancien Gear."
            ));
        });
        context.setPacketHandled(true);
    }
}
