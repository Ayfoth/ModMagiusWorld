package com.magius.world.mod.entity.custom;

import com.magius.world.mod.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class RubySheepEntity extends Sheep {

    public RubySheepEntity(EntityType<? extends Sheep> type, Level level) {
        super(type, level);
        this.setColor(DyeColor.RED);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getColor() != DyeColor.RED) {
            this.setColor(DyeColor.RED);
        }
        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.CRIMSON_SPORE,
                    this.getX(),
                    this.getY() + 1,
                    this.getZ(),
                    0, 0.01, 0
            );
        }
    }

    @Override
    public Sheep getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        RubySheepEntity baby = ModEntities.RUBY_SHEEP.get().create(level);
        if (baby != null) {
            baby.setColor(DyeColor.RED);
        }
        return baby;
    }

    @Override
    public void shear(SoundSource source) {
        this.level().playSound(null, this, net.minecraft.sounds.SoundEvents.SHEEP_SHEAR, source, 1.0F, 1.0F);

        if (!this.level().isClientSide) {
            this.setSheared(true);

            int count = (1 + this.random.nextInt(3)) * 2; // 2 à 6 laines

            for (int i = 0; i < count; i++) {
                ItemEntity item = this.spawnAtLocation(Blocks.RED_WOOL, 1);
                if (item != null) {
                    item.setDeltaMovement(
                            item.getDeltaMovement().add(
                                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                                    this.random.nextFloat() * 0.05F,
                                    (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                            )
                    );
                }
            }
        }
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Sheep.createAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D);
    }
}
