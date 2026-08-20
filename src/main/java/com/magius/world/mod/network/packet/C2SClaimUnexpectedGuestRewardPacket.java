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

public class C2SClaimUnexpectedGuestRewardPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );

    private static final int PRESTIGE_REWARD = 75;

    public C2SClaimUnexpectedGuestRewardPacket() {
    }

    public C2SClaimUnexpectedGuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimUnexpectedGuestRewardPacket packet,
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
                         * La quête doit avoir été terminée
                         * grâce au dialogue avec Nurse.
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
                                     * +75 prestige.
                                     */
                                    ClanManager.addPrestige(
                                            clanData,
                                            PRESTIGE_REWARD
                                    );

                                    /*
                                     * La quête devient REWARDED.
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
                                     * Synchronisation des deux
                                     * capabilities.
                                     */
                                    ClanSyncManager.sync(player);

                                    QuestSyncManager.sync(player);

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§6Une invitée inattendue accomplie ! §a+"
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
