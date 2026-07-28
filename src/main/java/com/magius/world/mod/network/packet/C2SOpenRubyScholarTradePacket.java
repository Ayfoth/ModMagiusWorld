package com.magius.world.mod.network.packet;

import com.magius.world.mod.villager.ModVillagers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SOpenRubyScholarTradePacket {

    private static final double MAX_INTERACTION_DISTANCE_SQUARED = 64.0D;

    private final int villagerId;

    public C2SOpenRubyScholarTradePacket(int villagerId) {
        this.villagerId = villagerId;
    }

    public C2SOpenRubyScholarTradePacket(FriendlyByteBuf buffer) {
        this.villagerId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(villagerId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();

        if (player == null) {
            context.setPacketHandled(true);
            return;
        }

        context.enqueueWork(() -> openTrade(player));
        context.setPacketHandled(true);
    }

    private void openTrade(ServerPlayer player) {
        Entity entity = player.level().getEntity(villagerId);

        if (!(entity instanceof Villager villager)
                || !villager.isAlive()
                || villager.isBaby()
                || villager.getVillagerData().getProfession()
                != ModVillagers.RUBY_SCHOLAR.get()
                || player.distanceToSqr(villager)
                > MAX_INTERACTION_DISTANCE_SQUARED) {
            return;
        }

        if (villager.getTradingPlayer() != null
                && villager.getTradingPlayer() != player) {
            return;
        }

        // Initialise les offres avant l'ouverture, comme l'interaction vanilla.
        villager.getOffers();
        villager.setTradingPlayer(player);
        villager.openTradingScreen(
                player,
                villager.getDisplayName(),
                villager.getVillagerData().getLevel()
        );
    }
}
