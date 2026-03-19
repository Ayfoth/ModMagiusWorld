package com.magius.world.mod.entity.custom;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;

public class RubyWispEntity extends Blaze {

    public RubyWispEntity(EntityType<? extends Blaze> type, Level level) {
        super(type, level);
        this.xpReward = 8;
    }
    private int rubyAttackCooldown = 0;

    public static AttributeSupplier.Builder createAttributes() {
        return Blaze.createAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.CRIMSON_SPORE,
                    this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                    this.getY() + 0.5D + this.random.nextDouble() * this.getBbHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                    0.0D, 0.01D, 0.0D
            );

            this.level().addParticle(
                    ParticleTypes.SMALL_FLAME,
                    this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                    this.getY() + 0.5D + this.random.nextDouble() * this.getBbHeight(),
                    this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                    0.0D, 0.01D, 0.0D
            );

        }
        if (!this.level().isClientSide && this.getTarget() != null) {
            rubyAttackCooldown--;

            if (rubyAttackCooldown <= 0 && this.hasLineOfSight(this.getTarget())) {
                double dx = this.getTarget().getX() - this.getX();
                double dy = this.getTarget().getY(0.5D) - this.getY(0.5D);
                double dz = this.getTarget().getZ() - this.getZ();

                RubyBoltEntity bolt = new RubyBoltEntity(this.level(), this);
                bolt.shoot(dx, dy, dz, 1.2F, 0.0F);

                bolt.setPos(this.getX(), this.getY() + 0.5D, this.getZ());
                this.level().addFreshEntity(bolt);

                this.playSound(net.minecraft.sounds.SoundEvents.BLAZE_SHOOT, 1.0F, 1.2F);

                rubyAttackCooldown = 40;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (!this.level().isClientSide) {
            int count = 1 + this.random.nextInt(2 + looting);
            this.spawnAtLocation(ModItems.RUBY_ESSENCE.get(), count);
        }
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
