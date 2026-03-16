package com.magius.world.mod.worldgen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.worldgen.feature.ModFeatures;
import com.magius.world.mod.worldgen.tree.custom.PineFoliagePlacer;
import com.magius.world.mod.worldgen.tree.custom.PineTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_WITHER_ORE_KEY = registerKey("wither_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_RUBIS_ORE_KEY = registerKey("rubis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_RUBIS_ORE_KEY = registerKey("nether_rubis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_RUBIS_ORE_KEY = registerKey("end_rubis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_SPIKE_KEY = registerKey("ruby_spike");
 public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ROCK_KEY = registerKey("ruby_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_BUSH_KEY = registerKey("ruby_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_GRASS_KEY = registerKey("red_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_RED_WHEAT_KEY = registerKey("wild_red_wheat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_FLOWER_KEY = registerKey("ruby_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_SHARD_KEY = registerKey("crystal_shard");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_SHRUB_KEY =
            registerKey("ruby_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_RED_GRASS_KEY = registerKey("dark_red_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_VEGETATION_PATCH_KEY = registerKey("ruby_vegetation_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_KEY = registerKey("pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_TREE_KEY = registerKey("ruby_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_GRASS_PATCH_KEY = registerKey("ruby_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_MUSHROOM_KEY = registerKey("ruby_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_CRYSTAL_CLUSTER_KEY = registerKey("ruby_crystal_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_CLIFF_ORE_KEY = registerKey("ruby_cliff_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_NATURAL_TREE_KEY = registerKey("ruby_natural_tree");



    public static void boostrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceable = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceable = new BlockMatchTest(Blocks.END_STONE);


        List<OreConfiguration.TargetBlockState> overworldWitherOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.WITHER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_WITHER_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> overworldRubisOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.RUBIS_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_RUBIS_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_WITHER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldWitherOres, 8));
        register(context, OVERWORLD_RUBIS_ORE_KEY, Feature.ORE, new OreConfiguration(overworldRubisOres, 5));
        register(context, NETHER_RUBIS_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceable,
                ModBlocks.NETHER_RUBIS_ORE.get().defaultBlockState(), 10));
        register(context, END_RUBIS_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceable,
                ModBlocks.END_STONE_RUBIS_ORE.get().defaultBlockState(), 10));

        register(context, PINE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PINE_LOG.get()),
                new PineTrunkPlacer(2, 4,3),

                BlockStateProvider.simple(ModBlocks.PINE_LEAVES.get()),
                new PineFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),

                new TwoLayersFeatureSize(1,0,2)).build());
        register(context, RUBY_SPIKE_KEY, ModFeatures.RUBY_SPIKE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_TREE_KEY, ModFeatures.RUBY_TREE.get(), NoneFeatureConfiguration.INSTANCE);
      register(context, RUBY_ROCK_KEY, ModFeatures.RUBY_ROCK.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_BUSH_KEY, ModFeatures.RUBY_BUSH.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RED_GRASS_KEY, ModFeatures.RED_GRASS.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, WILD_RED_WHEAT_KEY, ModFeatures.WILD_RED_WHEAT.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_FLOWER_KEY, ModFeatures.RUBY_FLOWER.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, CRYSTAL_SHARD_KEY, ModFeatures.CRYSTAL_SHARD.get(), NoneFeatureConfiguration.INSTANCE);
        register(context,
                RUBY_SHRUB_KEY,
                ModFeatures.RUBY_SHRUB.get(),
                NoneFeatureConfiguration.INSTANCE);
        register(context, DARK_RED_GRASS_KEY, ModFeatures.DARK_RED_GRASS.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_GRASS_PATCH_KEY, ModFeatures.RUBY_GRASS_PATCH.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_VEGETATION_PATCH_KEY, ModFeatures.RUBY_VEGETATION_PATCH.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_MUSHROOM_KEY, ModFeatures.RUBY_MUSHROOM.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_CRYSTAL_CLUSTER_KEY, ModFeatures.RUBY_CRYSTAL_CLUSTER.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_CLIFF_ORE_KEY, ModFeatures.RUBY_CLIFF_ORE.get(), NoneFeatureConfiguration.INSTANCE);
        register(context, RUBY_NATURAL_TREE_KEY, ModFeatures.RUBY_NATURAL_TREE.get(), NoneFeatureConfiguration.INSTANCE);

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
