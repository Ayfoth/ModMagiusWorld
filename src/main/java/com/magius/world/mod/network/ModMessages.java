package com.magius.world.mod.network;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.network.packet.C2SRequestFactionProgressPacket;
import com.magius.world.mod.network.packet.S2CFactionProgressDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "messages"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(C2SRequestFactionProgressPacket.class, id())
                .encoder(C2SRequestFactionProgressPacket::encode)
                .decoder(C2SRequestFactionProgressPacket::new)
                .consumerMainThread(C2SRequestFactionProgressPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2CFactionProgressDataPacket.class, id())
                .encoder(S2CFactionProgressDataPacket::encode)
                .decoder(S2CFactionProgressDataPacket::new)
                .consumerMainThread(S2CFactionProgressDataPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, net.minecraft.server.level.ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
