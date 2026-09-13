package com.magius.world.mod.network.packet;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.unchained.UnchainedTwinsQuest;
import com.magius.world.mod.entity.unchained.UnchainedAruhaEntity;
import com.magius.world.mod.entity.unchained.UnchainedRakeaEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SBuyUnchainedSealPacket {

    private static final double MAX_DISTANCE_SQUARED = 64.0D;

    private final int merchantId;
    private final SealTrade trade;

    public C2SBuyUnchainedSealPacket(
            int merchantId,
            SealTrade trade
    ) {
        this.merchantId = merchantId;
        this.trade = trade;
    }

    public C2SBuyUnchainedSealPacket(FriendlyByteBuf buffer) {
        this.merchantId = buffer.readVarInt();
        this.trade = buffer.readEnum(SealTrade.class);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(merchantId);
        buffer.writeEnum(trade);
    }

    public static void handle(
            C2SBuyUnchainedSealPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            Entity merchant = player.level().getEntity(packet.merchantId);

            if (!isValidMerchant(player, merchant, packet.trade)) {
                return;
            }

            QuestManager.get(player).ifPresent(questData -> {
                if (QuestManager.getStatus(
                        questData,
                        UnchainedTwinsQuest.ID
                ) != QuestStatus.REWARDED) {
                    player.sendSystemMessage(Component.literal(
                            "§cLibérez d'abord les deux jumeaux et récupérez leur récompense."
                    ));
                    return;
                }

                Item currency = packet.trade.currency();
                int price = packet.trade.price();

                if (countItem(player, currency) < price) {
                    player.sendSystemMessage(Component.literal(
                            "§cIl vous manque la monnaie nécessaire pour cet échange."
                    ));
                    return;
                }

                removeItems(player, currency, price);

                ItemStack seal = new ItemStack(
                        packet.trade == SealTrade.ARUHA
                                ? ModBlocks.UNCHAINED_ARUHA_SEAL.get()
                                : ModBlocks.UNCHAINED_RAKEA_SEAL.get()
                );

                if (!player.getInventory().add(seal)) {
                    player.drop(seal, false);
                }

                player.containerMenu.broadcastChanges();
                player.sendSystemMessage(Component.literal(
                        packet.trade == SealTrade.ARUHA
                                ? "§4Aruha vous confie l'un de ses Sceaux."
                                : "§4Rakea vous confie l'un de ses Sceaux."
                ));
            });
        });

        context.setPacketHandled(true);
    }

    private static boolean isValidMerchant(
            ServerPlayer player,
            Entity merchant,
            SealTrade trade
    ) {
        if (merchant == null
                || !merchant.isAlive()
                || player.distanceToSqr(merchant) > MAX_DISTANCE_SQUARED) {
            return false;
        }

        return trade == SealTrade.ARUHA
                ? merchant instanceof UnchainedAruhaEntity
                : merchant instanceof UnchainedRakeaEntity;
    }

    private static int countItem(
            ServerPlayer player,
            Item item
    ) {
        int count = 0;

        for (int slot = 0;
             slot < player.getInventory().getContainerSize();
             slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            if (stack.is(item)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    private static void removeItems(
            ServerPlayer player,
            Item item,
            int amount
    ) {
        int remaining = amount;

        for (int slot = 0;
             slot < player.getInventory().getContainerSize()
                     && remaining > 0;
             slot++) {
            ItemStack stack = player.getInventory().getItem(slot);

            if (!stack.is(item)) {
                continue;
            }

            int removed = Math.min(remaining, stack.getCount());
            stack.shrink(removed);
            remaining -= removed;
        }
    }

    public enum SealTrade {
        ARUHA(Items.ROTTEN_FLESH, 16),
        RAKEA(Items.BLAZE_ROD, 4);

        private final Item currency;
        private final int price;

        SealTrade(Item currency, int price) {
            this.currency = currency;
            this.price = price;
        }

        public Item currency() {
            return currency;
        }

        public int price() {
            return price;
        }
    }
}
