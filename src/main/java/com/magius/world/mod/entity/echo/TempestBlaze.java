package com.magius.world.mod.entity.echo;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;

public class TempestBlaze extends Blaze {

    public TempestBlaze(EntityType<? extends Blaze> type, Level level) {
        super(type, level);
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.random.nextFloat() < 0.4f) {

                double x = this.getX() + (this.random.nextDouble() - 0.5);
                double y = this.getY() + this.random.nextDouble() * 1.5;
                double z = this.getZ() + (this.random.nextDouble() - 0.5);

                this.level().addParticle(
                        net.minecraft.core.particles.ParticleTypes.PORTAL,
                        x, y, z,
                        0, 0.02, 0
                );
            }
        }
    }
    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);

        if (result && target instanceof net.minecraft.world.entity.player.Player player) {
            CorruptionHelper.addCorruption(player, 10);
        }

        return result;
    }
    @Override
    public void aiStep() {
        super.aiStep();

        this.setSecondsOnFire(0); // blaze violet → pas de feu visible
    }
}
