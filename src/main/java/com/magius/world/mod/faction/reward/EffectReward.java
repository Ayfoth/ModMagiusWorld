package com.magius.world.mod.faction.reward;

import net.minecraft.world.effect.MobEffect;

public class EffectReward {

    private final MobEffect effect;
    private final int durationTicks;
    private final int amplifier;

    public EffectReward(MobEffect effect, int durationTicks, int amplifier) {
        this.effect = effect;
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
    }

    public MobEffect getEffect() {
        return effect;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public int getAmplifier() {
        return amplifier;
    }
}
