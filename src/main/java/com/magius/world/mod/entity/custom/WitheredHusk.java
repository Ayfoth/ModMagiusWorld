package com.magius.world.mod.entity.custom;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WitheredHusk extends Husk {

    public WitheredHusk(EntityType<? extends Husk> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity entity) {
        boolean success = super.doHurtTarget(entity);

        if (success && entity instanceof Player player) {
            CorruptionHelper.addCorruption(player, 1);
        }

        return success;
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.random.nextFloat() < 0.4f) {

                double x = this.getX() + (this.random.nextDouble() - 0.5);
                double y = this.getY() + this.random.nextDouble() * 2;
                double z = this.getZ() + (this.random.nextDouble() - 0.5);

                this.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.02, 0);
                this.level().addParticle(ParticleTypes.ASH, x, y, z, 0, 0.01, 0);
            }
        }
    }
}
