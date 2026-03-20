package com.magius.world.mod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class CrystalFragmentParticle extends TextureSheetParticle {

    protected CrystalFragmentParticle(ClientLevel level, double x, double y, double z,
                                      double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.gravity = -0.001f; // très légère montée
        this.friction = 0.98f;
        this.lifetime = 50 + this.random.nextInt(20);

        this.quadSize = 0.25f + this.random.nextFloat() * 0.15f;
        this.alpha = 0.9f;

        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 240;
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