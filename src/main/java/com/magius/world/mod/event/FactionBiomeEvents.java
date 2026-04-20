package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.faction.FactionRewardRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionBiomeEvents {

    private static final ResourceLocation RUBY_BIOME_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome"); // remplace si ton id exact diffère

    private static final Map<UUID, ResourceLocation> LAST_BIOMES = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;

        ResourceLocation currentBiomeId = player.level()
                .getBiome(player.blockPosition())
                .unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        if (currentBiomeId == null) return;

        UUID playerId = player.getUUID();
        ResourceLocation previousBiomeId = LAST_BIOMES.get(playerId);

        if (currentBiomeId.equals(previousBiomeId)) {
            return;
        }

        LAST_BIOMES.put(playerId, currentBiomeId);

        if (RUBY_BIOME_ID.equals(currentBiomeId)) {
            FactionObjectiveManager.completeObjective(player, FactionObjectiveRegistry.DISCOVER_RUBY_BIOME);
        }
    }
}
