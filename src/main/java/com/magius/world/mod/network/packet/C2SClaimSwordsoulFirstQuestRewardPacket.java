package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimSwordsoulFirstQuestRewardPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private static final int PRESTIGE_REWARD = 50;

    public C2SClaimSwordsoulFirstQuestRewardPacket() {
    }

    public C2SClaimSwordsoulFirstQuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimSwordsoulFirstQuestRewardPacket packet,
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

            QuestManager.get(player)
                    .ifPresent(questData -> {

                        QuestStatus status =
                                QuestManager.getStatus(
                                        questData,
                                        QUEST_ID
                                );

                        if (status != QuestStatus.COMPLETED) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cCette récompense ne peut pas être récupérée."
                                    )
                            );

                            return;
                        }

                        ClanManager.get(player)
                                .ifPresent(clanData -> {

                                    /*
                                     * Sécurité :
                                     * Swordsoul doit être le clan actif.
                                     */
                                    if (!SWORDSOUL_ID.equals(
                                            clanData.getActiveClanId()
                                    )) {

                                        player.sendSystemMessage(
                                                Component.literal(
                                                        "§cSwordsoul doit être votre clan actif pour recevoir cette récompense."
                                                )
                                        );

                                        return;
                                    }

                                    /*
                                     * +50 prestige Swordsoul.
                                     */
                                    ClanManager.addPrestige(
                                            clanData,
                                            PRESTIGE_REWARD
                                    );

                                    ClanSyncManager.sync(
                                            player
                                    );

                                    /*
                                     * COMPLETED -> REWARDED
                                     */
                                    boolean rewarded =
                                            QuestManager.rewardQuest(
                                                    questData,
                                                    QUEST_ID
                                            );

                                    if (!rewarded) {
                                        return;
                                    }

                                    QuestSyncManager.sync(
                                            player
                                    );

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§bL'Épée sans maître accomplie ! §a+"
                                                            + PRESTIGE_REWARD
                                                            + " prestige"
                                            )
                                    );
                                });
                    });
        });

        context.setPacketHandled(true);
    }
}