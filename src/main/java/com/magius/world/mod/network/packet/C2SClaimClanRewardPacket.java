package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.clan.reward.ClanReward;
import com.magius.world.mod.clan.reward.ClanRewardRegistry;
import com.magius.world.mod.clan.reward.ClanRewardType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SClaimClanRewardPacket {

    private final ResourceLocation rewardId;

    public C2SClaimClanRewardPacket(
            ResourceLocation rewardId
    ) {
        this.rewardId = rewardId;
    }

    public C2SClaimClanRewardPacket(
            FriendlyByteBuf buffer
    ) {
        this.rewardId =
                buffer.readResourceLocation();
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeResourceLocation(
                rewardId
        );
    }

    public static void handle(
            C2SClaimClanRewardPacket packet,
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

            /*
             * La récompense demandée doit réellement
             * exister dans le registre du serveur.
             */
            ClanReward reward =
                    ClanRewardRegistry.get(
                            packet.rewardId
                    );

            if (reward == null) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cRécompense de clan inconnue."
                        )
                );
                return;
            }

            ClanManager.get(player).ifPresent(clanData -> {
                /*
                 * La récompense doit appartenir
                 * au clan actuellement sélectionné.
                 */
                if (clanData.getClanId() == null
                        || !clanData.getClanId().equals(
                        reward.getClanId()
                )) {

                    player.sendSystemMessage(
                            Component.literal(
                                    "§cCette récompense n'appartient pas à votre clan."
                            )
                    );
                    return;
                }

                /*
                 * Le prestige est toujours contrôlé
                 * côté serveur.
                 */
                if (clanData.getPrestige()
                        < reward.getRequiredPrestige()) {

                    player.sendSystemMessage(
                            Component.literal(
                                    "§cPrestige insuffisant."
                            )
                    );
                    return;
                }

                /*
                 * Empêche de récupérer plusieurs fois
                 * la même récompense.
                 */
                if (clanData.hasClaimedClanReward(
                        reward.getId()
                )) {
                    player.sendSystemMessage(
                            Component.literal(
                                    "§eCette récompense a déjà été récupérée."
                            )
                    );
                    return;
                }

                /*
                 * Pour l'instant, seuls les objets ont
                 * un véritable traitement côté serveur.
                 *
                 * Une récompense non implémentée ne doit
                 * surtout pas être marquée comme récupérée.
                 */
                if (reward.getType()
                        != ClanRewardType.ITEMS) {

                    player.sendSystemMessage(
                            Component.literal(
                                    "§cCe type de récompense n'est pas encore disponible."
                            )
                    );
                    return;
                }

                /*
                 * Marque la récompense avant de donner
                 * les objets afin d'éviter une duplication.
                 */
                boolean claimed =
                        clanData.claimClanReward(
                                reward.getId()
                        );

                if (!claimed) {
                    return;
                }

                /*
                 * Donne tous les objets enregistrés.
                 */
                for (ItemStack rewardStack
                        : reward.getItems()) {

                    ItemStack stack =
                            rewardStack.copy();

                    player.getInventory().add(
                            stack
                    );

                    /*
                     * Si l'inventaire est plein, le reste
                     * est déposé aux pieds du joueur.
                     */
                    if (!stack.isEmpty()) {
                        player.drop(
                                stack,
                                false
                        );
                    }
                }

                ClanSyncManager.sync(
                        player
                );

                player.sendSystemMessage(
                        Component.literal(
                                "§6Récompense obtenue : §f"
                                        + reward.getTitle()
                        )
                );
            });
        });

        context.setPacketHandled(true);
    }
}