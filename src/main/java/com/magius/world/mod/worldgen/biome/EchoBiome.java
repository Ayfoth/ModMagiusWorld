package com.magius.world.mod.worldgen.biome;

import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class EchoBiome {

    public static Biome create(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();

      //  BiomeDefaultFeatures.commonSpawns(spawns);

// 🔥 Husk corrompu
        spawns.addSpawn(
                MobCategory.MONSTER,
                new MobSpawnSettings.SpawnerData(ModEntities.WITHERED_HUSK.get(), 80, 1, 3)
        );
        spawns.addSpawn(
                MobCategory.MONSTER,
                new MobSpawnSettings.SpawnerData(ModEntities.TEMPEST_BLAZE.get(), 15, 1, 1)
        );

        BiomeGenerationSettings.Builder generation =
                new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        BiomeDefaultFeatures.addDefaultCarversAndLakes(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        generation.addFeature(
                GenerationStep.Decoration.UNDERGROUND_ORES,
                placedFeatures.getOrThrow(ModPlacedFeatures.NECRO_STONE_ORE_PLACED_KEY)
        );
        generation.addFeature(
                GenerationStep.Decoration.UNDERGROUND_ORES,
                placedFeatures.getOrThrow(ModPlacedFeatures.NECRO_CAVE_PLACED_KEY)
        );
        generation.addFeature(
                GenerationStep.Decoration.UNDERGROUND_DECORATION,
                placedFeatures.getOrThrow(ModPlacedFeatures.NECRO_CAVE_FEATURE_PLACED_KEY)
        );
        generation.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                placedFeatures.getOrThrow(ModPlacedFeatures.WITHERED_TREE_PLACED_KEY)
        );
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        generation.addFeature(
                net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION,
                placedFeatures.getOrThrow(ModPlacedFeatures.CORRUPTED_SOIL_PATCH_PLACED_KEY)
        );

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.7f)
                .downfall(0.0f)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0x3a2f3f)
                        .waterColor(0x3a2f3f)
                        .waterFogColor(0x2a1f2f)
                        .skyColor(0x2a1f3a )
                        .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(generation.build())
                .build();
    }
}
