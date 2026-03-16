package com.magius.world.mod.worldgen.feature;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.worldgen.feature.custom.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, MagiusWorldMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_SPIKE =
            FEATURES.register("ruby_spike",
                    () -> new RubySpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_TREE =
            FEATURES.register("ruby_tree",
                    () -> new RubyTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_ROCK =
            FEATURES.register("ruby_rock",
                    () -> new RubyRockFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_BUSH =
            FEATURES.register("ruby_bush",
                    () -> new RubyBushFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RED_GRASS =
            FEATURES.register("red_grass",
                    () -> new RedGrassFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WILD_RED_WHEAT =
            FEATURES.register("wild_red_wheat",
                    () -> new WildRedWheatFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_FLOWER =
            FEATURES.register("ruby_flower",
                    () -> new RubyFlowerFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTAL_SHARD =
            FEATURES.register("crystal_shard",
                    () -> new CrystalShardFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_SHRUB =
            FEATURES.register("ruby_shrub",
                    () -> new RubyShrubFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> DARK_RED_GRASS =
            FEATURES.register("dark_red_grass",
                    () -> new DarkRedGrassFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_GRASS_PATCH =
            FEATURES.register("ruby_grass_patch",
                    () -> new RubyGrassPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_VEGETATION_PATCH =
            FEATURES.register("ruby_vegetation_patch",
                    () -> new RubyVegetationPatchFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_MUSHROOM =
            FEATURES.register("ruby_mushroom",
                    () -> new RubyMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_CRYSTAL_CLUSTER =
            FEATURES.register("ruby_crystal_cluster",
                    () -> new RubyCrystalClusterFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_CLIFF_ORE =
            FEATURES.register("ruby_cliff_ore",
                    () -> new RubyCliffOreFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUBY_NATURAL_TREE =
            FEATURES.register("ruby_natural_tree",
                    () -> new RubyNaturalTreeFeature(NoneFeatureConfiguration.CODEC));
}
