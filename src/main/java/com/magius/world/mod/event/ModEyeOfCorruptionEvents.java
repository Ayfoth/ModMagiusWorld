package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEyeOfCorruptionEvents {
    public static final String NBT_ACTIVE_TICKS = "magiusworld.eye_of_corruption_active_ticks";
    public static final String NBT_END_PENALTY_APPLIED = "magiusworld.eye_of_corruption_end_penalty_applied";

    private static final int SCAN_INTERVAL = 10; // tous les 10 ticks
    private static final int BLOCK_SCAN_RADIUS = 18;
    private static final int MOB_REVEAL_RADIUS = 20;
    private static final int MAX_HIGHLIGHTED_BLOCKS = 24;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        int activeTicks = player.getPersistentData().getInt(NBT_ACTIVE_TICKS);
        if (activeTicks <= 0) return;

        activeTicks--;
        player.getPersistentData().putInt(NBT_ACTIVE_TICKS, activeTicks);

        // Maintient un peu la vision nocturne pour éviter le clignotement
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 0, false, false, true));

        // Reveal périodique des ennemis
        if (player.tickCount % 20 == 0) {
            List<LivingEntity> enemies = level.getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(MOB_REVEAL_RADIUS),
                    entity -> entity instanceof Enemy && entity.isAlive()
            );

            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, true));
            }
        }

        // Scan des blocs spéciaux
        if (player.tickCount % SCAN_INTERVAL == 0) {
            List<BlockPos> foundBlocks = findTaggedBlocks(level, player.blockPosition(), BLOCK_SCAN_RADIUS, MAX_HIGHLIGHTED_BLOCKS);

            for (BlockPos pos : foundBlocks) {
                level.sendParticles(ParticleTypes.PORTAL,
                        pos.getX() + 0.5D, pos.getY() + 1.1D, pos.getZ() + 0.5D,
                        6, 0.2D, 0.15D, 0.2D, 0.01D);

                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                        pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.5D,
                        3, 0.15D, 0.05D, 0.15D, 0.005D);
            }

            if (!foundBlocks.isEmpty() && player.tickCount % 40 == 0) {
                level.playSound(null, player.blockPosition(),
                        SoundEvents.RESPAWN_ANCHOR_CHARGE,
                        SoundSource.PLAYERS,
                        0.5F,
                        1.35F);

                player.displayClientMessage(
                        Component.translatable("item.magiusworld.eye_of_corruption.found", foundBlocks.size()),
                        true
                );
            }
        }

        // Fin d'effet + contrepartie
        if (activeTicks == 0 && !player.getPersistentData().getBoolean(NBT_END_PENALTY_APPLIED)) {
            player.getPersistentData().putBoolean(NBT_END_PENALTY_APPLIED, true);

            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 8, 1, false, true, true));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 5, 0, false, true, true));

            level.playSound(null, player.blockPosition(),
                    SoundEvents.WARDEN_HEARTBEAT,
                    SoundSource.PLAYERS,
                    0.65F,
                    0.8F);

            level.sendParticles(ParticleTypes.SMOKE,
                    player.getX(), player.getY() + 1.0D, player.getZ(),
                    20, 0.3D, 0.5D, 0.3D, 0.01D);
        }
    }

    private static List<BlockPos> findTaggedBlocks(ServerLevel level, BlockPos center, int radius, int maxResults) {
        List<BlockPos> results = new ArrayList<>();

        int minX = center.getX() - radius;
        int maxX = center.getX() + radius;
        int minY = Math.max(level.getMinBuildHeight(), center.getY() - 8);
        int maxY = Math.min(level.getMaxBuildHeight() - 1, center.getY() + 8);
        int minZ = center.getZ() - radius;
        int maxZ = center.getZ() + radius;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    mutablePos.set(x, y, z);
                    BlockState state = level.getBlockState(mutablePos);

                    if (state.is(ModTags.Blocks.EYE_OF_CORRUPTION_DETECTABLE)) {
                        results.add(mutablePos.immutable());

                        if (results.size() >= maxResults) {
                            return results;
                        }
                    }
                }
            }
        }

        return results;
    }
}