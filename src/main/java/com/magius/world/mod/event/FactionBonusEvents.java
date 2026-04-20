package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.faction.FactionObjectiveManager;
import com.magius.world.mod.faction.FactionObjectiveRegistry;
import com.magius.world.mod.faction.FactionProgressData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class FactionBonusEvents {

    private static final ResourceLocation RUBY_BIOME_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        String factionId = FactionObjectiveManager.getPlayerFactionId(player);
        if (factionId == null) {
            return;
        }

        if (!isInRubyBiome(player)) {
            return;
        }

        FactionProgressData data = FactionProgressData.get(player.serverLevel());

        if (data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_COMBAT_RUBY)) {
            applyCombatBonus(player);
        }

        if (data.isObjectiveCompleted(factionId, FactionObjectiveRegistry.REWARD_GATHERING_RUBY)) {
            applyGatheringBonus(player);

            // scan un peu moins souvent pour éviter de trop charger
            if (player.tickCount % 20 == 0) {
                highlightNearbyRubyOre(player);
            }
        }
    }

    private static boolean isInRubyBiome(ServerPlayer player) {
        Holder<Biome> biomeHolder = player.serverLevel().getBiome(player.blockPosition());
        ResourceLocation biomeId = biomeHolder.unwrapKey()
                .map(ResourceKey::location)
                .orElse(null);

        return RUBY_BIOME_ID.equals(biomeId);
    }

    private static void applyCombatBonus(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_BOOST,
                40,
                0,
                true,
                false,
                true
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE,
                40,
                0,
                true,
                false,
                true
        ));
    }

    private static void applyGatheringBonus(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(
                MobEffects.DIG_SPEED,
                40,
                0,
                true,
                false,
                true
        ));
    }

    private static void highlightNearbyRubyOre(ServerPlayer player) {
        BlockPos origin = player.blockPosition();
        int radius = 8;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = origin.offset(x, y, z);

                    if (player.serverLevel().getBlockState(pos).is(ModBlocks.RUBIS_ORE.get())) {
                        player.serverLevel().sendParticles(
                                ParticleTypes.END_ROD,
                                pos.getX() + 0.5D,
                                pos.getY() + 0.5D,
                                pos.getZ() + 0.5D,
                                2,
                                0.15D,
                                0.15D,
                                0.15D,
                                0.0D
                        );
                    }
                }
            }
        }
    }
}
