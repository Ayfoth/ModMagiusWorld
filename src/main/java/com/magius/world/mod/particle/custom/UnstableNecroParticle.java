package com.magius.world.mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class UnstableNecroParticle extends TextureSheetParticle {

    protected UnstableNecroParticle(ClientLevel level, double x, double y, double z,
                                    double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.lifetime = 25;
        this.quadSize = 0.12f;
        this.hasPhysics = false;

        this.setColor(0.75f, 0.25f, 1.0f);
    }

    @Override
    public void tick() {
        super.tick();

        this.yd += 0.002;
        this.alpha = 1.0f - ((float) this.age / (float) this.lifetime);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            UnstableNecroParticle particle =
                    new UnstableNecroParticle(level, x, y, z, xd, yd, zd);

            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
