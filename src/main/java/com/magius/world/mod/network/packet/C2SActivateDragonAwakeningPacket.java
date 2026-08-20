package com.magius.world.mod.network.packet;

import com.magius.world.mod.clan.manager.ClanSyncManager;
import com.magius.world.mod.network.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import com.magius.world.mod.clan.manager.ClanManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.magius.world.mod.network.packet.S2CDragonAwakeningStatePacket;

import java.util.function.Supplier;

public class C2SActivateDragonAwakeningPacket {

    public C2SActivateDragonAwakeningPacket() {
    }

    public C2SActivateDragonAwakeningPacket(
            FriendlyByteBuf buffer
    ) {
    }

    public void encode(
            FriendlyByteBuf buffer
    ) {
    }

    public static void handle(
            C2SActivateDragonAwakeningPacket packet,
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

            ClanManager.get(player)
                    .ifPresent(clanData -> {

                        /*
                         * =====================================================
                         * COMPÉTENCE DÉBLOQUÉE ?
                         * =====================================================
                         */
                        if (!clanData.isDragonAwakeningUnlocked()) {

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cRéveil Draconique n'est pas encore débloqué."
                                    )
                            );

                            return;
                        }

                        /*
                         * =====================================================
                         * COOLDOWN
                         * =====================================================
                         */
                        if (clanData.isDragonAwakeningOnCooldown()) {

                            long remainingMillis =
                                    clanData.getDragonAwakeningRemainingCooldownMillis();

                            long remainingSeconds =
                                    (remainingMillis + 999L) / 1000L;

                            long minutes =
                                    remainingSeconds / 60L;

                            long seconds =
                                    remainingSeconds % 60L;

                            player.sendSystemMessage(
                                    Component.literal(
                                            "§cRéveil Draconique en recharge : §f"
                                                    + minutes
                                                    + "m "
                                                    + seconds
                                                    + "s"
                                    )
                            );

                            return;
                        }

                        /*
                         * Réveil Draconique
                         * Durée : 20 secondes = 400 ticks
                         *
                         * Amplifier 0 = niveau I
                         */
                        player.getPersistentData().putInt(
                                "DragonAwakeningTicks",
                                20 * 20
                        );

                        int duration = 20 * 20;

                        player.addEffect(
                                new MobEffectInstance(
                                        MobEffects.DAMAGE_BOOST,
                                        duration,
                                        0,
                                        false,
                                        true,
                                        true
                                )
                        );

                        player.addEffect(
                                new MobEffectInstance(
                                        MobEffects.DAMAGE_RESISTANCE,
                                        duration,
                                        0,
                                        false,
                                        true,
                                        true
                                )
                        );

                        player.addEffect(
                                new MobEffectInstance(
                                        MobEffects.MOVEMENT_SPEED,
                                        duration,
                                        0,
                                        false,
                                        true,
                                        true
                                )
                        );

                        player.sendSystemMessage(
                                Component.literal(
                                        "§d✦ Réveil Draconique activé ! §f20 secondes"
                                )
                        );
                        clanData.startDragonAwakeningCooldown(
                                2L * 60L * 1000L
                        );

                        ClanSyncManager.sync(player);
                        ModMessages.sendToPlayer(
                                new S2CDragonAwakeningStatePacket(
                                        20 * 20,
                                        clanData.getDragonAwakeningCooldownEnd()
                                ),
                                player
                        );
                    });
        });

        context.setPacketHandled(true);
    }
}
