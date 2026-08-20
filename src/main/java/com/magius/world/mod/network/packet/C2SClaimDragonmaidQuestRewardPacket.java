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

public class C2SClaimDragonmaidQuestRewardPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );

    private static final int PRESTIGE_REWARD = 50;

    public C2SClaimDragonmaidQuestRewardPacket() {
    }

    public C2SClaimDragonmaidQuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimDragonmaidQuestRewardPacket packet,
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

                        /*
                         * Sécurité serveur :
                         * la récompense n'est accordée
                         * QUE si la quête est COMPLETED.
                         *
                         * Une quête REWARDED ne peut donc
                         * jamais redonner les 50 points.
                         */
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
                                     * Attribution du prestige.
                                     */
                                    ClanManager.addPrestige(
                                            clanData,
                                            PRESTIGE_REWARD
                                    );
                                    ClanSyncManager.sync(player);

                                    /*
                                     * La quête passe :
                                     *
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

                                    /*
                                     * Synchronisation des quêtes.
                                     */
                                    QuestSyncManager.sync(
                                            player
                                    );

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§6Premier contact accompli ! §a+"
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