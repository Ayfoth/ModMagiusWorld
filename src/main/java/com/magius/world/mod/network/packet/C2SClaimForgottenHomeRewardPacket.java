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

public class C2SClaimForgottenHomeRewardPacket {

    private static final ResourceLocation QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );

    private static final int PRESTIGE_REWARD = 125;

    public C2SClaimForgottenHomeRewardPacket() {
    }

    public C2SClaimForgottenHomeRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimForgottenHomeRewardPacket packet,
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
                                     * +125 prestige.
                                     */
                                    ClanManager.addPrestige(
                                            clanData,
                                            PRESTIGE_REWARD
                                    );

                                    /*
                                     * Déblocage permanent :
                                     * Réveil Draconique.
                                     */
                                    clanData.unlockDragonAwakening();

                                    /*
                                     * La quête passe COMPLETED -> REWARDED.
                                     */
                                    boolean rewarded =
                                            QuestManager.rewardQuest(
                                                    questData,
                                                    QUEST_ID
                                            );

                                    if (!rewarded) {
                                        return;
                                    }

                                    ClanSyncManager.sync(player);
                                    QuestSyncManager.sync(player);

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§6Le foyer oublié accompli ! §a+"
                                                            + PRESTIGE_REWARD
                                                            + " prestige"
                                            )
                                    );

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§dCompétence débloquée : §fRéveil Draconique"
                                            )
                                    );
                                });
                    });
        });

        context.setPacketHandled(true);
    }
}