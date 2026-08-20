package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.quest.data.PlayerQuestCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CQuestDataPacket {

    private final CompoundTag data;

    public S2CQuestDataPacket(
            CompoundTag data
    ) {
        this.data = data.copy();
    }

    public S2CQuestDataPacket(
            FriendlyByteBuf buffer
    ) {
        CompoundTag tag = buffer.readNbt();

        this.data =
                tag != null
                        ? tag
                        : new CompoundTag();
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeNbt(data);
    }

    public static void handle(
            S2CQuestDataPacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {

        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() -> {

            Minecraft minecraft =
                    Minecraft.getInstance();

            if (minecraft.player == null) {
                return;
            }

            minecraft.player
                    .getCapability(
                            PlayerQuestCapability.INSTANCE
                    )
                    .ifPresent(
                            questData ->
                                    questData.loadNBT(
                                            packet.data
                                    )
                    );
        });

        context.setPacketHandled(true);
    }
}
