package com.magius.world.mod.corruption;

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
                .ifPresent(data -> data.addCorruption(amount));
    }

    public static CorruptionLevel getLevel(Player player) {
        return player.getCapability(PlayerCorruptionProvider.PLAYER_CORRUPTION)
                .map(PlayerCorruption::getLevel)
                .orElse(CorruptionLevel.PURE);
    }
}
