package com.magius.world.mod.worldgen.biome.surface;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.sound.ModSounds;
import com.magius.world.mod.worldgen.ModPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {
    public static final ResourceKey<Biome> TEST_BIOME = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "test_biome"));
    public static final ResourceKey<Biome> RUBY_BIOME = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_biome"));

    public static void bootstrap(BootstapContext<Biome> context){
        context.register(TEST_BIOME, testBiome(context));
        context.register(RUBY_BIOME, rubyBiome(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome testBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.RHINO.get(), 2, 3, 5));

        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        //biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
       // biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINE_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get())).build())
                .build();
    }
    public static Biome rubyBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(
                        context.lookup(Registries.PLACED_FEATURE),
                        context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);

        // minerais normaux + rubis
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);

        biomeBuilder.addFeature(
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.RUBIS_ORE_PLACED_KEY
        );

        // pics de rubis
        biomeBuilder.addFeature(
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                ModPlacedFeatures.RUBY_SPIKE_PLACED_KEY
        );

        // arbres cristallisés
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_TREE_PLACED_KEY
        );
        // buisson rubis
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_BUSH_PLACED_KEY
        );
//        biomeBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.RED_GRASS_PLACED_KEY
//        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.WILD_RED_WHEAT_PLACED_KEY
        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_MUSHROOM_PLACED_KEY
        );
//        biomeBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.RUBY_FLOWER_PLACED_KEY
//        );
//        biomeBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.CRYSTAL_SHARD_PLACED_KEY
//        );
//        biomeBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.RUBY_SHRUB_PLACED_KEY
//        );
//        biomeBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.DARK_RED_GRASS_PLACED_KEY
//        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_GRASS_PATCH_PLACED_KEY
        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_VEGETATION_PATCH_PLACED_KEY
        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.RUBY_NATURAL_TREE_PLACED_KEY
        );

//        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
//        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);

        biomeBuilder.addFeature(
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                ModPlacedFeatures.RUBY_ROCK_PLACED_KEY
        );
        biomeBuilder.addFeature(
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                ModPlacedFeatures.RUBY_CRYSTAL_CLUSTER_PLACED_KEY
        );

        biomeBuilder.addFeature(
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                ModPlacedFeatures.RUBY_CLIFF_ORE_PLACED_KEY
        );

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.2f)
                .downfall(0.0f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .waterColor(0x7a0f14)
                        .waterFogColor(0x4a0508)
                        .skyColor(0xd11f3a)
                        .fogColor(0x3a0d0d)
                        .grassColorOverride(0x5c1a1a)
                        .foliageColorOverride(0x7a2222)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
}
