package com.magius.world.mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class CrystalFragmentParticle extends TextureSheetParticle {

    protected CrystalFragmentParticle(ClientLevel level, double x, double y, double z,
                                      double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.friction = 0.98f;
        this.gravity = 0.0f;

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.lifetime = 40;

        this.quadSize = 0.8f; // très grand pour test

        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;

        this.alpha = 1.0f;
    }
    @Override
    public int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age > this.lifetime / 2) {
            this.alpha = 1.0f - ((float)(this.age - this.lifetime / 2) / (this.lifetime / 2));
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            CrystalFragmentParticle particle = new CrystalFragmentParticle(level, x, y, z, xd, yd, zd);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
