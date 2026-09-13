package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedChainReactionQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimUnchainedChainReactionQuestRewardPacket {

    private static final ResourceLocation UNCHAINED_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "unchained"
            );

    private static final int PRESTIGE_REWARD = 75;

    public C2SClaimUnchainedChainReactionQuestRewardPacket() {
    }

    public C2SClaimUnchainedChainReactionQuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(
            C2SClaimUnchainedChainReactionQuestRewardPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            QuestManager.get(player).ifPresent(questData -> {
                if (QuestManager.getStatus(
                        questData,
                        UnchainedChainReactionQuest.ID
                ) != QuestStatus.COMPLETED) {
                    player.sendSystemMessage(Component.literal(
                            "§cCette récompense ne peut pas être récupérée."
                    ));
                    return;
                }

                ClanManager.get(player).ifPresent(clanData -> {
                    if (!UNCHAINED_ID.equals(clanData.getActiveClanId())) {
                        player.sendSystemMessage(Component.literal(
                                "§cLes Déchaînés doivent être votre clan actif pour recevoir cette récompense."
                        ));
                        return;
                    }

                    if (!QuestManager.rewardQuest(
                            questData,
                            UnchainedChainReactionQuest.ID
                    )) {
                        return;
                    }

                    ClanManager.addPrestige(clanData, PRESTIGE_REWARD);
                    ClanSyncManager.sync(player);
                    QuestSyncManager.sync(player);

                    player.sendSystemMessage(Component.literal(
                            "§4La Réaction en chaîne accomplie ! §a+"
                                    + PRESTIGE_REWARD
                                    + " prestige"
                    ));
                });
            });
        });

        context.setPacketHandled(true);
    }
}
