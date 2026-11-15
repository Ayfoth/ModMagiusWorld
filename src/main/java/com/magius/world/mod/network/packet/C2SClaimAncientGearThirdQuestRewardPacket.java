package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearThirdQuest;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.entity.ancientgear.AncientGearEmissaryEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimAncientGearThirdQuestRewardPacket {
    private static final ResourceLocation CLAN_ID = ResourceLocation.fromNamespaceAndPath(
            MagiusWorldMod.MOD_ID, "ancient_gear"
    );

    public C2SClaimAncientGearThirdQuestRewardPacket() {
    }

    public C2SClaimAncientGearThirdQuestRewardPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(C2SClaimAncientGearThirdQuestRewardPacket packet,
                              Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null || player.level().getEntitiesOfClass(
                    AncientGearEmissaryEntity.class, player.getBoundingBox().inflate(8.0D)
            ).isEmpty()) {
                return;
            }

            var clanData = ClanManager.get(player).resolve();
            var questData = QuestManager.get(player).resolve();
            if (clanData.isEmpty() || questData.isEmpty()
                    || !clanData.get().hasJoinedClan(CLAN_ID)) {
                return;
            }

            if (!QuestManager.rewardQuest(questData.get(), AncientGearThirdQuest.ID)) {
                return;
            }

            clanData.get().getProgress(CLAN_ID)
                    .addPrestige(new AncientGearThirdQuest().getPrestigeReward());
            QuestSyncManager.sync(player);
            ClanSyncManager.sync(player);
            player.sendSystemMessage(Component.literal(
                    "§6Le cœur de Geartown : §f+150 prestige Anciens Gear."
            ));
        });
        context.setPacketHandled(true);
    }
}
