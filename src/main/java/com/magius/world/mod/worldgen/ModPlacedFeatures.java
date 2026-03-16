package com.magius.world.mod.worldgen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> WITHER_ORE_PLACED_KEY = registerKey("wither_ore_placed");
    public static final ResourceKey<PlacedFeature> RARE_WITHER_ORE_PLACED_KEY = registerKey("rare_wither_ore_placed");
    public static final ResourceKey<PlacedFeature> RUBIS_ORE_PLACED_KEY = registerKey("rubis_ore_placed");
    public static final ResourceKey<PlacedFeature> RARE_RUBIS_ORE_PLACED_KEY = registerKey("rare_rubis_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_RUBIS_ORE_PLACED_KEY = registerKey("nether_rubis_ore_placed");
    public static final ResourceKey<PlacedFeature> END_RUBIS_ORE_PLACED_KEY = registerKey("end_rubis_ore_placed");
    public static final ResourceKey<PlacedFeature> RUBY_SPIKE_PLACED_KEY = registerKey("ruby_spike_placed");
   public static final ResourceKey<PlacedFeature> RUBY_ROCK_PLACED_KEY = registerKey("ruby_rock_placed");
    public static final ResourceKey<PlacedFeature> RUBY_BUSH_PLACED_KEY = registerKey("ruby_bush_placed");
    public static final ResourceKey<PlacedFeature> RED_GRASS_PLACED_KEY = registerKey("red_grass_placed");
    public static final ResourceKey<PlacedFeature> WILD_RED_WHEAT_PLACED_KEY = registerKey("wild_red_wheat_placed");
    public static final ResourceKey<PlacedFeature> RUBY_FLOWER_PLACED_KEY = registerKey("ruby_flower_placed");
    public static final ResourceKey<PlacedFeature> CRYSTAL_SHARD_PLACED_KEY = registerKey("crystal_shard_placed");
    public static final ResourceKey<PlacedFeature> RUBY_SHRUB_PLACED_KEY =
            registerKey("ruby_shrub_placed");
    public static final ResourceKey<PlacedFeature> DARK_RED_GRASS_PLACED_KEY = registerKey("dark_red_grass_placed");
    public static final ResourceKey<PlacedFeature> RUBY_GRASS_PATCH_PLACED_KEY = registerKey("ruby_grass_patch_placed");
    public static final ResourceKey<PlacedFeature> RUBY_VEGETATION_PATCH_PLACED_KEY =
            registerKey("ruby_vegetation_patch_placed");
    public static final ResourceKey<PlacedFeature> RUBY_NATURAL_TREE_PLACED_KEY =
            registerKey("ruby_natural_tree_placed");


    public static final ResourceKey<PlacedFeature> PINE_PLACED_KEY = registerKey("pine_placed");
    public static final ResourceKey<PlacedFeature> RUBY_TREE_PLACED_KEY = registerKey("ruby_tree_placed");
    public static final ResourceKey<PlacedFeature> RUBY_MUSHROOM_PLACED_KEY = registerKey("ruby_mushroom_placed");
    public static final ResourceKey<PlacedFeature> RUBY_CRYSTAL_CLUSTER_PLACED_KEY = registerKey("ruby_crystal_cluster_placed");
    public static final ResourceKey<PlacedFeature> RUBY_CLIFF_ORE_PLACED_KEY = registerKey("ruby_cliff_ore_placed");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, WITHER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_WITHER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(15))));
        register(context, RARE_WITHER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_WITHER_ORE_KEY),
                ModOrePlacement.rareOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-32))));
       register(context, RUBIS_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_RUBIS_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-14))));
        register(context, RARE_RUBIS_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_RUBIS_ORE_KEY),
                ModOrePlacement.rareOrePlacement(1,
                       HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-32))));
        register(context, NETHER_RUBIS_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_RUBIS_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-14))));
        register(context, END_RUBIS_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_RUBIS_ORE_KEY),
                ModOrePlacement.commonOrePlacement(2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-14))));

        register(context, PINE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PINE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3,0.1f,2),
                        ModBlocks.PINE_SAPLING.get()));


        register(context, RUBY_SPIKE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_SPIKE_KEY),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_TREE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_TREE_KEY),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_NATURAL_TREE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_NATURAL_TREE_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_ROCK_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_ROCK_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_BUSH_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_BUSH_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RED_GRASS_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_GRASS_KEY),
                List.of(
                        CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, WILD_RED_WHEAT_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.WILD_RED_WHEAT_KEY),
                List.of(
                        CountPlacement.of(5),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_FLOWER_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_FLOWER_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, CRYSTAL_SHARD_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.CRYSTAL_SHARD_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context,
                RUBY_SHRUB_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_SHRUB_KEY),
                List.of(
                        CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, DARK_RED_GRASS_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.DARK_RED_GRASS_KEY),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_GRASS_PATCH_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_GRASS_PATCH_KEY),
                List.of(
                        CountPlacement.of(6),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_VEGETATION_PATCH_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_VEGETATION_PATCH_KEY),
                List.of(
                        CountPlacement.of(5),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_MUSHROOM_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_MUSHROOM_KEY),
                List.of(
                        CountPlacement.of(5),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_CRYSTAL_CLUSTER_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_CRYSTAL_CLUSTER_KEY),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));
        register(context, RUBY_CLIFF_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_CLIFF_ORE_KEY),
                List.of(
                        CountPlacement.of(4),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                ));



    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
