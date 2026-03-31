package com.magius.world.mod.entity.custom;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import java.util.List;

public class RubyBossEntity extends Zombie {

    public RubyBossEntity(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setCustomName(Component.translatable("entity.name.ruby_boss"));
        this.setCustomNameVisible(true);
    }
    private boolean isPhase2 = false;

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }
    private void performSlamAttack() {
        this.level().playSound(
                null,
                this.blockPosition(),
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE,
                1.5F,
                0.8F
        );

        // Rayon de l'attaque
        double radius = 4.0D;

        // Récupérer les entités autour
        List<LivingEntity> targets = this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(radius),
                entity -> entity != this
        );

        for (LivingEntity target : targets) {
            // Dégâts
            target.hurt(this.damageSources().mobAttack(this), 8.0F);

            // Knockback (projection)
            double dx = target.getX() - this.getX();
            double dz = target.getZ() - this.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);

            if (distance > 0) {
                target.push(dx / distance * 1.5, 0.5, dz / distance * 1.5);
            }
        }

        // Particules
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    30,
                    1.0, 0.5, 1.0,
                    0.1
            );
        }
        float damage = isPhase2 ? 14.0F : 8.0F;
    }
    private final ServerBossEvent bossBar = new ServerBossEvent(
            Component.translatable("entity.bar.ruby_boss"),
            BossEvent.BossBarColor.RED,
            BossEvent.BossBarOverlay.NOTCHED_10
    );
    @Override
    public void aiStep() {
        super.aiStep();


        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        if (!isPhase2 && this.getHealth() < this.getMaxHealth() / 2) {
            isPhase2 = true;

            // Effet visuel + feedback
            this.level().explode(null, this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.NONE);
        }
        if (isPhase2) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
            this.setSecondsOnFire(1);
        }
        if (isPhase2 && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    this.getX(),
                    this.getY() + 1,
                    this.getZ(),
                    5,
                    0.5, 0.5, 0.5,
                    0.01
            );
        }
        if (isPhase2) {
            this.bossBar.setColor(BossEvent.BossBarColor.PURPLE);
        }
        // Si proche d'une cible → slam
        if (!this.level().isClientSide && this.getTarget() != null) {

            int cooldown = isPhase2 ? 20 : 40; // phase 2 = 2x plus rapide

            if (this.distanceTo(this.getTarget()) < 3.5F && this.tickCount % cooldown == 0) {
                performSlamAttack();
            }
        }
    }
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }
    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (!this.level().isClientSide) {
            this.spawnAtLocation(ModItems.BOSS_RUBY_KEY.get());
            this.spawnAtLocation(ModItems.RUBY_HORSE_ARMOR.get());
            this.spawnAtLocation(ModItems.CORRUPTED_RUBY.get());
        }
    }
    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }
    @Override
    public boolean fireImmune() {
        return true;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }
}
