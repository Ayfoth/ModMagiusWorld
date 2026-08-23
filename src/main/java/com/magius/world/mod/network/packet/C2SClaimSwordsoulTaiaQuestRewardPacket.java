package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulTaiaQuest;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimSwordsoulTaiaQuestRewardPacket {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private static final int PRESTIGE_REWARD = 75;

    public C2SClaimSwordsoulTaiaQuestRewardPacket() {
    }

    public C2SClaimSwordsoulTaiaQuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimSwordsoulTaiaQuestRewardPacket packet,
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

            QuestManager.get(player).ifPresent(questData -> {
                QuestStatus status =
                        QuestManager.getStatus(
                                questData,
                                SwordsoulTaiaQuest.ID
                        );

                if (status != QuestStatus.COMPLETED) {
                    player.sendSystemMessage(
                            Component.literal(
                                    "§cLa récompense de Taia ne peut pas être récupérée."
                            )
                    );
                    return;
                }

                ClanManager.get(player).ifPresent(clanData -> {
                    if (!SWORDSOUL_ID.equals(
                            clanData.getActiveClanId()
                    )) {
                        player.sendSystemMessage(
                                Component.literal(
                                        "§cSwordsoul doit être votre clan actif."
                                )
                        );
                        return;
                    }

                    /*
                     * La quête passe d'abord à REWARDED
                     * pour empêcher toute double récompense.
                     */
                    boolean rewarded =
                            QuestManager.rewardQuest(
                                    questData,
                                    SwordsoulTaiaQuest.ID
                            );

                    if (!rewarded) {
                        return;
                    }

                    ClanManager.addPrestige(
                            clanData,
                            PRESTIGE_REWARD
                    );

                    ClanSyncManager.sync(player);
                    QuestSyncManager.sync(player);

                    player.sendSystemMessage(
                            Component.literal(
                                    "§bLa Voie de Taia accomplie ! §a+"
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