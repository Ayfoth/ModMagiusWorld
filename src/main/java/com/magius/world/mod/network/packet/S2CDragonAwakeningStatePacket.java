package com.magius.world.mod.network.packet;

import com.magius.world.mod.client.DragonAwakeningClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CDragonAwakeningStatePacket {

    private final int activeTicks;
    private final long cooldownEnd;

    public S2CDragonAwakeningStatePacket(
            int activeTicks,
            long cooldownEnd
    ) {
        this.activeTicks = activeTicks;
        this.cooldownEnd = cooldownEnd;
    }

    public S2CDragonAwakeningStatePacket(
            FriendlyByteBuf buffer
    ) {
        this.activeTicks =
                buffer.readInt();

        this.cooldownEnd =
                buffer.readLong();
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
        buffer.writeInt(activeTicks);
        buffer.writeLong(cooldownEnd);
    }

    public static void handle(
            S2CDragonAwakeningStatePacket packet,
            Supplier<NetworkEvent.Context> contextSupplier
    ) {

        NetworkEvent.Context context =
                contextSupplier.get();

        context.enqueueWork(() -> {

            DragonAwakeningClientData.set(
                    packet.activeTicks,
                    packet.cooldownEnd
            );
        });

        context.setPacketHandled(true);
    }
}
