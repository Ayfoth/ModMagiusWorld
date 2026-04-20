package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class ExplorationObjectiveEvents {

    private static final ResourceLocation RUBY_BIOME =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    // tracking exploration zones
    private static final Set<String> visitedZones = new HashSet<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        String factionId = FactionObjectiveManager.getPlayerFactionId(player);
        if (factionId == null) return;

        if (!isInRubyBiome(player)) return;

        // 1️⃣ Découverte du biome
        FactionObjectiveManager.addProgress(
                player,
                FactionObjectiveRegistry.DISCOVER_RUBY_BIOME,
                1
        );

        // 2️⃣ Exploration de zones (grille)
        trackZoneExploration(player);

        // 5️⃣ Profondeur
        if (player.getY() < 40) {
            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.EXPLORE_RUBY_DEPTHS,
                    1
            );
        }
    }

    private static boolean isInRubyBiome(ServerPlayer player) {
        Holder<Biome> biomeHolder = player.serverLevel().getBiome(player.blockPosition());
        ResourceLocation biomeId = biomeHolder.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        return RUBY_BIOME.equals(biomeId);
    }

    private static void trackZoneExploration(ServerPlayer player) {
        BlockPos pos = player.blockPosition();

        // découpe en zones 32x32
        int zoneX = pos.getX() >> 5;
        int zoneZ = pos.getZ() >> 5;

        String key = player.getUUID() + "_" + zoneX + "_" + zoneZ;

        if (!visitedZones.contains(key)) {
            visitedZones.add(key);

            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.EXPLORE_RUBY_ZONES,
                    1
            );
        }
    }
}
