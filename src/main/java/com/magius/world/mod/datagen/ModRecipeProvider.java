package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> WITHER_SMELTABLES = List.of(ModItems.WITHER.get(),
            ModBlocks.WITHER_ORE.get(), ModBlocks.DEEPSLATE_WITHER_ORE.get());
    private static final List<ItemLike> RUBIS_SMELTABLES = List.of(ModItems.RUBIS.get(),
            ModBlocks.RUBIS_ORE.get(), ModBlocks.DEEPSLATE_RUBIS_ORE.get(),
            ModBlocks.NETHER_RUBIS_ORE.get(), ModBlocks.END_STONE_RUBIS_ORE.get());
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(pWriter, WITHER_SMELTABLES, RecipeCategory.MISC, ModItems.WITHER.get(), 0.25f, 100, "wither");
        oreSmelting(pWriter, WITHER_SMELTABLES, RecipeCategory.MISC, ModItems.WITHER.get(), 0.25f, 200, "wither");
        oreBlasting(pWriter, RUBIS_SMELTABLES, RecipeCategory.MISC, ModItems.RUBIS.get(), 0.25f, 100, "rubis");
        oreSmelting(pWriter, RUBIS_SMELTABLES, RecipeCategory.MISC, ModItems.RUBIS.get(), 0.25f, 200, "rubis");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_PICKAXE.get())
                .pattern("WWW")
                .pattern(" B ")
                .pattern(" B ")
                .define('W', ModBlocks.WITHER_BLOCK.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FIRE_FOUNDERIE.get())
                .pattern("RRR")
                .pattern("ROR")
                .pattern("O O")
                .define('R', ModBlocks.RUBIS_BLOCK.get())
                .define('O', Blocks.OBSIDIAN)
                .unlockedBy(getHasName(ModBlocks.FIRE_FOUNDERIE.get()), has(ModBlocks.FIRE_FOUNDERIE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GEM_POLISHING_STATION.get())
                .pattern("RLR")
                .pattern("RRR")
                .pattern("B B")
                .define('R', ModBlocks.RUBIS_BLOCK.get())
                .define('L', ModBlocks.SAPPHIRE_DOOR.get())
                .define('B', ModBlocks.WHITE_LEGENDARY_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WHITE_LEGENDARY_BLOCK.get()), has(ModBlocks.WHITE_LEGENDARY_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_SWORD.get())
                .pattern(" R ")
                .pattern(" R ")
                .pattern(" B ")
                .define('R', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_WAND.get())
                .pattern("  R")
                .pattern(" B ")
                .pattern("B  ")
                .define('R', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_PICKAXE.get())
                .pattern("RRR")
                .pattern(" B ")
                .pattern(" B ")
                .define('R', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_AXE.get())
                .pattern("WW ")
                .pattern("WB ")
                .pattern(" B ")
                .define('W', ModBlocks.WITHER_BLOCK.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_AXE.get())
                .pattern("WW ")
                .pattern("WB ")
                .pattern(" B ")
                .define('W', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_HOE.get())
                .pattern("RR ")
                .pattern(" B ")
                .pattern(" B ")
                .define('R', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_SHOVEL.get())
                .pattern(" R ")
                .pattern(" B ")
                .pattern(" B ")
                .define('R', ModItems.RUBIS.get())
                .define('B', Items.STICK)
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_HELMET.get())
                .pattern("   ")
                .pattern("WWW")
                .pattern("W W")
                .define('W', ModItems.WITHER.get())
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_CHESTPLATE.get())
                .pattern("W W")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', ModItems.WITHER.get())
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_LEGGINGS.get())
                .pattern("WWW")
                .pattern("W W")
                .pattern("W W")
                .define('W', ModItems.WITHER.get())
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WITHER_BOOTS.get())
                .pattern("   ")
                .pattern("W W")
                .pattern("W W")
                .define('W', ModItems.WITHER.get())
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_HELMET.get())
                .pattern("   ")
                .pattern("WWW")
                .pattern("W W")
                .define('W', ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_CHESTPLATE.get())
                .pattern("W W")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_LEGGINGS.get())
                .pattern("WWW")
                .pattern("W W")
                .pattern("W W")
                .define('W', ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS_BOOTS.get())
                .pattern("   ")
                .pattern("W W")
                .pattern("W W")
                .define('W', ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        // Outils Wither

// Wither Block
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WITHER_BLOCK.get())
                .pattern("WWW")
                .pattern("WWW")
                .pattern("WWW")
                .define('W', ModItems.WITHER.get())
                .unlockedBy(getHasName(ModItems.WITHER.get()), has(ModItems.WITHER.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WITHER.get(), 9)
                .requires(ModBlocks.WITHER_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WITHER_BLOCK.get()), has(ModBlocks.WITHER_BLOCK.get()))
                .save(pWriter);

        // Rubis Block
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBIS_BLOCK.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBIS.get(), 9)
                .requires(ModBlocks.RUBIS_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RUBIS_BLOCK.get()), has(ModBlocks.RUBIS_BLOCK.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_LEGENDARY_BLOCK.get(), 1)
                .requires(Blocks.POLISHED_BASALT)
                .requires(Blocks.END_STONE_BRICKS)
                .requires(Blocks.CALCITE)
                .requires(Blocks.QUARTZ_BRICKS)
                .unlockedBy(getHasName(Blocks.END_STONE), has(Blocks.END_STONE))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_PLANKS.get(), 4)
                .requires(ModBlocks.RUBY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_LOG.get()), has(ModBlocks.RUBY_LOG.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_PLANKS.get(), 4)
                .requires(ModBlocks.STRIPPED_RUBY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_RUBY_LOG.get()), has(ModBlocks.STRIPPED_RUBY_LOG.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "ruby_planks_from_stripped_log"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBY_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.RUBY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_LOG.get()), has(ModBlocks.RUBY_LOG.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_RUBY_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.STRIPPED_RUBY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_RUBY_LOG.get()), has(ModBlocks.STRIPPED_RUBY_LOG.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.RUBY_BUTTON.get())
                .requires(ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.RUBY_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.RUBY_FENCE.get(), 3)
                .pattern("#S#")
                .pattern("#S#")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.RUBY_FENCE_GATE.get())
                .pattern("S#S")
                .pattern("S#S")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.RUBY_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.RUBY_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);
    }
        protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List< ItemLike > pIngredients, RecipeCategory
        pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
            oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
        }

        protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
            oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
        }

        protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe > pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
            Iterator var9 = pIngredients.iterator();

            while(var9.hasNext()) {
                ItemLike itemlike = (ItemLike)var9.next();
                SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                        .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                        .save(pFinishedRecipeConsumer, MagiusWorldMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
            }

        }

    }

