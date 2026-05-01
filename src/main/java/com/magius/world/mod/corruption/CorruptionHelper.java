package com.magius.world.mod.corruption;

import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.echo.SyncCorruptionS2CPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class CorruptionHelper {

    public static int getCorruption(Player player) {
        return player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .map(PlayerCorruption::getCorruption)
                .orElse(0);
    }

    public static void setCorruption(Player player, int amount) {
        player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .ifPresent(data -> data.setCorruption(amount));
    }

    public static void addCorruption(Player player, int amount) {
        player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .ifPresent(data -> {
                    CorruptionLevel oldLevel = data.getLevel();

                    data.addCorruption(amount);
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(
                                new SyncCorruptionS2CPacket(data.getCorruption()),
                                serverPlayer
                        );
                    }

                    CorruptionLevel newLevel = data.getLevel();

                    if (oldLevel != newLevel) {
                        sendLevelUpMessage(player, newLevel);
                    }
                });
    }
    private static void sendLevelUpMessage(Player player, CorruptionLevel level) {
        switch (level) {
            case EXPOSED:
                player.sendSystemMessage(Component.literal("Vous êtes désormais Exposé."));
                break;
            case INFECTED:
                player.sendSystemMessage(Component.literal("La corruption s'installe : Infecté."));
                break;
            case MUTATED:
                player.sendSystemMessage(Component.literal("Votre corps mute."));
                break;
            case CORRUPTED:
                player.sendSystemMessage(Component.literal("Instabilité critique : Corrompu."));
                break;
            case ASSIMILATED:
                player.sendSystemMessage(Component.literal("Assimilation complète."));
                break;
            default:
                break;
        }
    }
    public static void removeCorruption(Player player, int amount) {
        player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .ifPresent(data -> {
                    CorruptionLevel oldLevel = data.getLevel();

                    data.removeCorruption(amount);
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(
                                new SyncCorruptionS2CPacket(data.getCorruption()),
                                serverPlayer
                        );
                    }

                    CorruptionLevel newLevel = data.getLevel();

                    if (oldLevel != newLevel) {
                        sendLevelDownMessage(player, newLevel);
                    }
                });
    }
    private static void sendLevelDownMessage(Player player, CorruptionLevel level) {
        switch (level) {
            case PURE:
                player.sendSystemMessage(Component.literal("Vous êtes redevenu Pur."));
                break;
            case EXPOSED:
                player.sendSystemMessage(Component.literal("La corruption faiblit : Exposé."));
                break;
            case INFECTED:
                player.sendSystemMessage(Component.literal("Vous redescendez au stade Infecté."));
                break;
            case MUTATED:
                player.sendSystemMessage(Component.literal("Votre mutation se stabilise."));
                break;
            case CORRUPTED:
                player.sendSystemMessage(Component.literal("Vous n'êtes plus assimilé."));
                break;
            default:
                break;
        }
    }

    public static CorruptionLevel getLevel(Player player) {
        return player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .map(PlayerCorruption::getLevel)
                .orElse(CorruptionLevel.PURE);
    }
}
