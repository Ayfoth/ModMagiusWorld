package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class ExplorationObjectiveEvents {

    private static final ResourceLocation RUBY_BIOME =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    private static final ResourceLocation RUBY_ALTAR_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_fire_shrine");

    // tracking exploration zones
    private static final Set<String> visitedZones = new HashSet<>();

    private static boolean hasCompletedRubyAltar(ServerPlayer player) {
        return false;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        if (player.tickCount % 40 != 0) return;

        if (!hasCompletedRubyAltar(player) && isInsideRubyAltar(player)) {
            grantRubyAltarObjective(player);
        }

        String factionId = FactionObjectiveManager.getPlayerFactionId(player);
        if (factionId == null) return;

        if (!isInRubyBiome(player)) return;

        FactionObjectiveManager.addProgress(
                player,
                FactionObjectiveRegistry.DISCOVER_RUBY_BIOME,
                1
        );

        trackZoneExploration(player);

        if (player.getY() < 40) {
            FactionObjectiveManager.addProgress(
                    player,
                    FactionObjectiveRegistry.EXPLORE_RUBY_DEPTHS,
                    1
            );
        }
    }

    private static boolean isInsideRubyAltar(ServerPlayer player) {
        var registry = player.serverLevel()
                .registryAccess()
                .registryOrThrow(Registries.STRUCTURE);

        Structure rubyAltar = registry.get(RUBY_ALTAR_ID);
        if (rubyAltar == null) {
            return false;
        }

        StructureStart start = player.serverLevel()
                .structureManager()
                .getStructureWithPieceAt(player.blockPosition(), rubyAltar);

        return start != null && start.isValid();
    }

    private static void grantRubyAltarObjective(ServerPlayer player) {
        FactionObjectiveManager.addProgress(
                player,
                FactionObjectiveRegistry.DISCOVER_RUBY_ALTAR,
                1
        );
    }

    private static boolean isInRubyBiome(ServerPlayer player) {
        Holder<Biome> biomeHolder = player.serverLevel().getBiome(player.blockPosition());
        ResourceLocation biomeId = biomeHolder.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        return RUBY_BIOME.equals(biomeId);
    }

    private static void trackZoneExploration(ServerPlayer player) {
        var pos = player.blockPosition();

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
