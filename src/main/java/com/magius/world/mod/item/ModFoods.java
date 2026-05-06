package com.magius.world.mod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();
    public static final FoodProperties RUBY_MUSHROOM = new FoodProperties.Builder()
            .nutrition(3)
            .alwaysEat()
            .saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 0), 0.25f)
            .build();
    public static final FoodProperties WITHER_MUSHROOM = new FoodProperties.Builder()
            .nutrition(2)
            .alwaysEat()
            .saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 100, 0), 0.5F)
            .build();
    public static final FoodProperties WITHER_SOUP = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 80, 0), 0.35F)
            .alwaysEat()
            .build();
    public static final FoodProperties CORRUPTED_STEW = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(0.8F)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 120, 1), 0.5F)
            .alwaysEat()
            .build();
}
