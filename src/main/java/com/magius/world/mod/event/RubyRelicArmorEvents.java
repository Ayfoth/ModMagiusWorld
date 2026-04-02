package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RubyRelicArmorEvents {

    public static final String NBT_ACTIVE_TICKS = "magiusworldmod.ruby_relic_armor_active_ticks";
    public static final String NBT_COOLDOWN_TICKS = "magiusworldmod.ruby_relic_armor_cooldown_ticks";
    public static final String NBT_PENALTY_APPLIED = "magiusworldmod.ruby_relic_armor_penalty_applied";

    private static final int ACTIVE_DURATION = 20 * 15;
    private static final int COOLDOWN_DURATION = 20 * 60;

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        int activeTicks = player.getPersistentData().getInt(NBT_ACTIVE_TICKS);
        int cooldownTicks = player.getPersistentData().getInt(NBT_COOLDOWN_TICKS);

        boolean canActivate = chest.is(ModItems.RUBY_RELIC_ARMOR.get())
                && hasAllRelics(player)
                && player.isShiftKeyDown()
                && activeTicks <= 0
                && cooldownTicks <= 0;

        if (!canActivate) return;

        player.getPersistentData().putInt(NBT_ACTIVE_TICKS, ACTIVE_DURATION);
        player.getPersistentData().putBoolean(NBT_PENALTY_APPLIED, false);

        level.playSound(null, player.blockPosition(),
                SoundEvents.BEACON_ACTIVATE,
                SoundSource.PLAYERS,
                1.0F, 1.1F);

        spawnRubyWave(level, player, 2.5D);
        damageAndKnockbackEnemies(level, player, 4.0D, 6.0F, 0.8D);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!(player.level() instanceof ServerLevel level)) return;

        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

        int activeTicks = player.getPersistentData().getInt(NBT_ACTIVE_TICKS);
        int cooldownTicks = player.getPersistentData().getInt(NBT_COOLDOWN_TICKS);

        if (cooldownTicks > 0) {
            player.getPersistentData().putInt(NBT_COOLDOWN_TICKS, cooldownTicks - 1);
        }

        if (!chest.is(ModItems.RUBY_RELIC_ARMOR.get())) {
            if (activeTicks > 0) {
                if (player.tickCount % 20 == 0) {
                    spawnRubyWave(level, player, 1.5D);
                    damageAndKnockbackEnemies(level, player, 3.0D, 2.0F, 0.35D);
                }
                player.getPersistentData().putInt(NBT_ACTIVE_TICKS, 0);
            }
            return;
        }

        if (activeTicks > 0) {
            player.getPersistentData().putInt(NBT_ACTIVE_TICKS, activeTicks - 1);

            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, false, false, true));

            if (player.tickCount % 10 == 0) {
                level.sendParticles(ParticleTypes.FLAME,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        6, 0.25D, 0.4D, 0.25D, 0.01D);

                level.sendParticles(ParticleTypes.PORTAL,
                        player.getX(), player.getY() + 1.0D, player.getZ(),
                        4, 0.2D, 0.3D, 0.2D, 0.02D);
            }

            if (activeTicks - 1 <= 0) {
                player.getPersistentData().putInt(NBT_COOLDOWN_TICKS, COOLDOWN_DURATION);

                if (!player.getPersistentData().getBoolean(NBT_PENALTY_APPLIED)) {
                    player.getPersistentData().putBoolean(NBT_PENALTY_APPLIED, true);

                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 6, 0, false, true, true));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * 5, 0, false, true, true));

                    level.playSound(null, player.blockPosition(),
                            SoundEvents.BEACON_DEACTIVATE,
                            SoundSource.PLAYERS,
                            0.8F, 0.8F);
                }
            }
        }
    }

    private static boolean hasAllRelics(Player player) {
        return hasItem(player, ModItems.RUBY_HEART.get())
                && hasItem(player, ModItems.RUBY_EYE.get())
                && hasItem(player, ModItems.RUBY_BLOOD.get())
                && hasItem(player, ModItems.RUBY_CORE_RELIC.get());
    }

    private static boolean hasItem(Player player, Item item) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                return true;
            }
        }
        return false;
    }
    private static void damageAndKnockbackEnemies(ServerLevel level, ServerPlayer player, double radius, float damage, double knockbackStrength) {
        List<LivingEntity> enemies = level.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(radius),
                entity -> entity instanceof Enemy && entity.isAlive()
        );

        for (LivingEntity enemy : enemies) {
            enemy.hurt(level.damageSources().playerAttack(player), damage);
            enemy.setSecondsOnFire(4);

            Vec3 push = enemy.position().subtract(player.position()).normalize().scale(knockbackStrength);
            enemy.push(push.x, 0.2D, push.z);
        }

        if (!enemies.isEmpty()) {
            level.playSound(null, player.blockPosition(),
                    SoundEvents.BLAZE_SHOOT,
                    SoundSource.PLAYERS,
                    0.8F, 0.9F);
        }
    }
    private static void spawnRubyWave(ServerLevel level, ServerPlayer player, double radius) {
        double centerX = player.getX();
        double centerY = player.getY() + 0.15D;
        double centerZ = player.getZ();

        for (int i = 0; i < 24; i++) {
            double angle = (Math.PI * 2D / 24D) * i;
            double x = centerX + Math.cos(angle) * radius;
            double z = centerZ + Math.sin(angle) * radius;

            level.sendParticles(ParticleTypes.FLAME,
                    x, centerY, z,
                    1, 0.0D, 0.05D, 0.0D, 0.0D);

            level.sendParticles(ParticleTypes.PORTAL,
                    x, centerY + 0.1D, z,
                    1, 0.0D, 0.05D, 0.0D, 0.0D);

            level.sendParticles(ParticleTypes.CRIT,
                    x, centerY + 0.05D, z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
