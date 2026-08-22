package com.magius.world.mod.network;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.network.packet.*;
import com.magius.world.mod.network.echo.SyncCorruptionS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import com.magius.world.mod.network.packet.C2SCompleteNurseDragonmaidQuestPacket;

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

        INSTANCE.messageBuilder(SyncCorruptionS2CPacket.class, id())
                .encoder(SyncCorruptionS2CPacket::toBytes)
                .decoder(SyncCorruptionS2CPacket::new)
                .consumerMainThread(SyncCorruptionS2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2COpenRubyScholarDialoguePacket.class, id())
                .encoder(S2COpenRubyScholarDialoguePacket::encode)
                .decoder(S2COpenRubyScholarDialoguePacket::new)
                .consumerMainThread(S2COpenRubyScholarDialoguePacket::handle)
                .add();

        INSTANCE.messageBuilder(S2COpenRubyScholarMenuPacket.class, id())
                .encoder(S2COpenRubyScholarMenuPacket::encode)
                .decoder(S2COpenRubyScholarMenuPacket::new)
                .consumerMainThread(S2COpenRubyScholarMenuPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SOpenRubyScholarTradePacket.class, id())
                .encoder(C2SOpenRubyScholarTradePacket::encode)
                .decoder(C2SOpenRubyScholarTradePacket::new)
                .consumerMainThread(C2SOpenRubyScholarTradePacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SAcceptForgottenShardQuestPacket.class, id())
                .encoder(C2SAcceptForgottenShardQuestPacket::encode)
                .decoder(C2SAcceptForgottenShardQuestPacket::new)
                .consumerMainThread(C2SAcceptForgottenShardQuestPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SCompleteForgottenShardQuestPacket.class, id())
                .encoder(C2SCompleteForgottenShardQuestPacket::encode)
                .decoder(C2SCompleteForgottenShardQuestPacket::new)
                .consumerMainThread(C2SCompleteForgottenShardQuestPacket::handle)
                .add();

        INSTANCE.messageBuilder(StartQuestC2SPacket.class, id())
                .encoder(StartQuestC2SPacket::encode)
                .decoder(StartQuestC2SPacket::new)
                .consumerMainThread(StartQuestC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        S2CQuestDataPacket.class,
                        id()
                )
                .encoder(S2CQuestDataPacket::encode)
                .decoder(S2CQuestDataPacket::new)
                .consumerMainThread(S2CQuestDataPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        C2SStartDragonmaidQuestPacket.class,
                        id()
                )
                .encoder(C2SStartDragonmaidQuestPacket::encode)
                .decoder(C2SStartDragonmaidQuestPacket::new)
                .consumerMainThread(C2SStartDragonmaidQuestPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        C2SClaimDragonmaidQuestRewardPacket.class,
                        id()
                )
                .encoder(C2SClaimDragonmaidQuestRewardPacket::encode)
                .decoder(C2SClaimDragonmaidQuestRewardPacket::new)
                .consumerMainThread(C2SClaimDragonmaidQuestRewardPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        S2CClanDataPacket.class,
                        id()
                )
                .encoder(S2CClanDataPacket::encode)
                .decoder(S2CClanDataPacket::new)
                .consumerMainThread(S2CClanDataPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        C2SCompleteNurseDragonmaidQuestPacket.class,
                        id()
                )
                .encoder(C2SCompleteNurseDragonmaidQuestPacket::encode)
                .decoder(C2SCompleteNurseDragonmaidQuestPacket::new)
                .consumerMainThread(C2SCompleteNurseDragonmaidQuestPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SClaimUnexpectedGuestRewardPacket.class,
                        id()
                )
                .encoder(C2SClaimUnexpectedGuestRewardPacket::encode)
                .decoder(C2SClaimUnexpectedGuestRewardPacket::new)
                .consumerMainThread(C2SClaimUnexpectedGuestRewardPacket::handle)
                .add();

        INSTANCE.messageBuilder(
                        C2SStartUnexpectedGuestQuestPacket.class,
                        id()
                )
                .encoder(C2SStartUnexpectedGuestQuestPacket::encode)
                .decoder(C2SStartUnexpectedGuestQuestPacket::new)
                .consumerMainThread(C2SStartUnexpectedGuestQuestPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SClaimForgottenHomeRewardPacket.class,
                        id()
                )
                .encoder(C2SClaimForgottenHomeRewardPacket::encode)
                .decoder(C2SClaimForgottenHomeRewardPacket::new)
                .consumerMainThread(C2SClaimForgottenHomeRewardPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SStartForgottenHomeQuestPacket.class,
                        id()
                )
                .encoder(C2SStartForgottenHomeQuestPacket::encode)
                .decoder(C2SStartForgottenHomeQuestPacket::new)
                .consumerMainThread(C2SStartForgottenHomeQuestPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SActivateDragonAwakeningPacket.class,
                        id()
                )
                .encoder(C2SActivateDragonAwakeningPacket::encode)
                .decoder(C2SActivateDragonAwakeningPacket::new)
                .consumerMainThread(C2SActivateDragonAwakeningPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        S2CDragonAwakeningStatePacket.class,
                        id()
                )
                .encoder(S2CDragonAwakeningStatePacket::encode)
                .decoder(S2CDragonAwakeningStatePacket::new)
                .consumerMainThread(S2CDragonAwakeningStatePacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SClaimClanRewardPacket.class,
                        id()
                )
                .encoder(C2SClaimClanRewardPacket::encode)
                .decoder(C2SClaimClanRewardPacket::new)
                .consumerMainThread(C2SClaimClanRewardPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SJoinDragonmaidClanPacket.class,
                        id()
                )
                .encoder(C2SJoinDragonmaidClanPacket::encode)
                .decoder(C2SJoinDragonmaidClanPacket::new)
                .consumerMainThread(C2SJoinDragonmaidClanPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SJoinSwordsoulClanPacket.class,
                        id()
                )
                .encoder(C2SJoinSwordsoulClanPacket::encode)
                .decoder(C2SJoinSwordsoulClanPacket::new)
                .consumerMainThread(C2SJoinSwordsoulClanPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SStartSwordsoulFirstQuestPacket.class,
                        id()
                )
                .encoder(C2SStartSwordsoulFirstQuestPacket::encode)
                .decoder(C2SStartSwordsoulFirstQuestPacket::new)
                .consumerMainThread(C2SStartSwordsoulFirstQuestPacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SUnlockSwordsoulSpiritForgePacket.class,
                        id()
                )
                .encoder(C2SUnlockSwordsoulSpiritForgePacket::encode)
                .decoder(C2SUnlockSwordsoulSpiritForgePacket::new)
                .consumerMainThread(C2SUnlockSwordsoulSpiritForgePacket::handle)
                .add();
        INSTANCE.messageBuilder(
                        C2SClaimSwordsoulFirstQuestRewardPacket.class,
                        id()
                )
                .encoder(
                        C2SClaimSwordsoulFirstQuestRewardPacket::encode
                )
                .decoder(
                        C2SClaimSwordsoulFirstQuestRewardPacket::new
                )
                .consumerMainThread(
                        C2SClaimSwordsoulFirstQuestRewardPacket::handle
                )
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, net.minecraft.server.level.ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
