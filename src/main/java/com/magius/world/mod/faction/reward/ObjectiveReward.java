package com.magius.world.mod.faction.reward;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveReward {

    private final int experience;
    private final List<ItemReward> items;
    private final List<EffectReward> effects;

    public ObjectiveReward(int experience, List<ItemReward> items, List<EffectReward> effects) {
        this.experience = experience;
        this.items = items;
        this.effects = effects;
    }

    public int getExperience() {
        return experience;
    }

    public List<ItemReward> getItems() {
        return items;
    }

    public List<EffectReward> getEffects() {
        return effects;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int experience = 0;
        private final List<ItemReward> items = new ArrayList<>();
        private final List<EffectReward> effects = new ArrayList<>();

        public Builder xp(int amount) {
            this.experience = amount;
            return this;
        }

        public Builder item(Item item, int count) {
            this.items.add(new ItemReward(item, count));
            return this;
        }

        public Builder effect(MobEffect effect, int durationTicks, int amplifier) {
            this.effects.add(new EffectReward(effect, durationTicks, amplifier));
            return this;
        }

        public ObjectiveReward build() {
            return new ObjectiveReward(experience, items, effects);
        }
    }
}