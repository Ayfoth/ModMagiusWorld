package com.magius.world.mod.network.packet;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimSwordsoulMoYeQuestRewardPacket {

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private static final int PRESTIGE_REWARD = 50;

    public C2SClaimSwordsoulMoYeQuestRewardPacket() {
    }

    public C2SClaimSwordsoulMoYeQuestRewardPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SClaimSwordsoulMoYeQuestRewardPacket packet,
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
                                        SwordsoulMoYeQuest.ID
                                );

                        if (status != QuestStatus.COMPLETED) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cLa récompense de Mo Ye ne peut pas être récupérée."
                                    )
                            );

                            return;
                        }

                        ClanManager.get(player)
                                .ifPresent(clanData -> {

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
                                                    SwordsoulMoYeQuest.ID
                                            );

                                    if (!rewarded) {
                                        return;
                                    }

                                    ItemStack sealReward =
                                            createRandomSeal(player);

                                    boolean added =
                                            player.getInventory().add(
                                                    sealReward
                                            );

                                    if (!added) {
                                        player.drop(
                                                sealReward,
                                                false
                                        );
                                    }

                                    ClanManager.addPrestige(
                                            clanData,
                                            PRESTIGE_REWARD
                                    );

                                    ClanSyncManager.sync(player);
                                    QuestSyncManager.sync(player);

                                    player.sendSystemMessage(
                                            Component.literal(
                                                    "§bL'Éveil de Mo Ye accompli ! §a+"
                                                            + PRESTIGE_REWARD
                                                            + " prestige §7• §d"
                                            ).append(
                                                    sealReward.getHoverName()
                                            )
                                    );
                                });
                    });
        });

        context.setPacketHandled(true);
    }

    private static ItemStack createRandomSeal(
            ServerPlayer player
    ) {
        int roll =
                player.getRandom().nextInt(100);

        if (roll < 16) {
            return new ItemStack(
                    ModItems.SWORDSOUL_WATER_SEAL.get()
            );
        }

        if (roll < 32) {
            return new ItemStack(
                    ModItems.SWORDSOUL_FIRE_SEAL.get()
            );
        }

        if (roll < 48) {
            return new ItemStack(
                    ModItems.SWORDSOUL_WIND_SEAL.get()
            );
        }

        if (roll < 64) {
            return new ItemStack(
                    ModItems.SWORDSOUL_EARTH_SEAL.get()
            );
        }

        if (roll < 80) {
            return new ItemStack(
                    ModItems.SWORDSOUL_LIGHT_SEAL.get()
            );
        }

        if (roll < 96) {
            return new ItemStack(
                    ModItems.SWORDSOUL_DARK_SEAL.get()
            );
        }

        /*
         * Résultat 96 à 99 : 4 %.
         */
        return new ItemStack(
                ModItems.SWORDSOUL_DIVINE_SEAL.get()
        );
    }
}
