package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.faction.FactionRewardRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionCombatEvents {

    private static final ResourceLocation RUBY_BIOME =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;

        LivingEntity target = event.getEntity();

        var biome = target.level().getBiome(target.blockPosition())
                .unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        if (RUBY_BIOME.equals(biome)) {
            FactionObjectiveManager.completeObjective(player, FactionObjectiveRegistry.KILL_1_RUBY_BIOME);
            FactionObjectiveManager.addProgress(player, FactionObjectiveRegistry.KILL_25_RUBY_BIOME, 1);
        }
    }
}
