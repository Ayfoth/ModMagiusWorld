package com.magius.world.mod.entity.custom;

import com.magius.world.mod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RubyBoarEntity extends Pig {
    private boolean charging = false;

    public RubyBoarEntity(EntityType<? extends Pig> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Pig.createAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 2.50D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 14.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2,
                new NearestAttackableTargetGoal<>(this, Player.class, 5, true, false,
                        player -> this.distanceTo(player) < 5.0F));
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        if (!this.level().isClientSide) {
            this.spawnAtLocation(ModItems.RUBY_SHARD.get(), 1);

            if (this.random.nextFloat() < 0.35F + (0.1F * looting)) {
                this.spawnAtLocation(ModItems.RUBY_SHARD.get(), 1);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 10.0F) {
            charging = true;

            if (this.level().isClientSide) {
                this.level().addParticle(
                        ParticleTypes.CRIMSON_SPORE,
                        this.getX(),
                        this.getY() + 0.8D,
                        this.getZ(),
                        0.0D, 0.02D, 0.0D
                );
            }
        } else {
            charging = false;
        }

        if (charging) {
            if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.38D);
            }
        } else {
            if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28D);
            }
        }
    }
}