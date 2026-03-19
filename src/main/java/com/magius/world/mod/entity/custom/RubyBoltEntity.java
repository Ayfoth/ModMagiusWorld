package com.magius.world.mod.entity.custom;

import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RubyBoltEntity extends ThrowableItemProjectile {

    public RubyBoltEntity(EntityType<? extends RubyBoltEntity> type, Level level) {
        super(type, level);
    }

    public RubyBoltEntity(Level level, LivingEntity shooter) {
        super(ModEntities.RUBY_BOLT.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.RUBY_BOLT_ITEM.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.CRIMSON_SPORE,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0, 0, 0
            );
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!this.level().isClientSide) {
            this.level().explode(
                    this,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    1.0F,
                    Level.ExplosionInteraction.NONE
            );
        } else {
            for (int i = 0; i < 16; i++) {
                this.level().addParticle(
                        ParticleTypes.CRIMSON_SPORE,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (this.random.nextDouble() - 0.5D) * 0.35D,
                        (this.random.nextDouble() - 0.5D) * 0.35D,
                        (this.random.nextDouble() - 0.5D) * 0.35D
                );

                this.level().addParticle(
                        ParticleTypes.SMALL_FLAME,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        (this.random.nextDouble() - 0.5D) * 0.2D,
                        (this.random.nextDouble() - 0.5D) * 0.2D,
                        (this.random.nextDouble() - 0.5D) * 0.2D
                );
            }
        }

        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().magic(), 6.0F);
            target.setSecondsOnFire(2);
        }
    }
}
