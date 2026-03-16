package com.magius.world.mod.worldgen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.worldgen.biome.surface.ModBiomes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_WITHER_ORE = registerKey("add_wither_ore");
    public static final ResourceKey<BiomeModifier> ADD_RUBIS_ORE = registerKey("add_rubis_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_RUBIS_ORE = registerKey("add_nether_rubis_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_RUBIS_ORE = registerKey("add_end_rubis_ore");
//    public static final ResourceKey<BiomeModifier> ADD_RUBIS_ORE_TO_RUBY_BIOME = registerKey("add_rubis_ore_to_ruby_biome");
//    public static final ResourceKey<BiomeModifier> ADD_RUBY_SPIKE = registerKey("add_ruby_spike");
//    public static final ResourceKey<BiomeModifier> ADD_RUBY_TREE = registerKey("add_ruby_tree");
  //  public static final ResourceKey<BiomeModifier> ADD_NETHER_WITHER_ORE = registerKey("add_nether_wither_ore");

    public static final ResourceKey<BiomeModifier> ADD_TREE_PINE = registerKey("add_tree_pine");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);


//        context.register(ADD_WITHER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WITHER_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));
//        context.register(ADD_RUBIS_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RUBIS_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));
//        context.register(ADD_NETHER_RUBIS_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//               biomes.getOrThrow(BiomeTags.IS_NETHER),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_RUBIS_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));
//      context.register(ADD_END_RUBIS_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                biomes.getOrThrow(Tags.Biomes.IS_HOT_END),
//           HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_RUBIS_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));
//
//      context.register(ADD_TREE_PINE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//              biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
//              HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PINE_PLACED_KEY)),
//              GenerationStep.Decoration.VEGETAL_DECORATION));
//        context.register(ADD_RUBIS_ORE_TO_RUBY_BIOME, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RUBIS_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));
//
//        context.register(ADD_RUBY_SPIKE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(ModBiomes.RUBY_BIOME)),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RUBY_SPIKE_PLACED_KEY)),
//                GenerationStep.Decoration.LOCAL_MODIFICATIONS));
//
//        context.register(ADD_RUBY_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(ModBiomes.RUBY_BIOME)),
//                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RUBY_TREE_PLACED_KEY)),
//                GenerationStep.Decoration.VEGETAL_DECORATION));


    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name));
    }
}
