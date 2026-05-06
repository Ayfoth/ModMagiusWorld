package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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
        // Echo du Premier
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.STONE)
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(ModItems.PURIFYING_HEART.get())
                .unlockedBy(getHasName(ModItems.PURIFYING_HEART.get()), has(ModItems.PURIFYING_HEART.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE.get())
                .requires(Items.STONE)
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy(getHasName(ModItems.ESSENCE_WITHER.get()), has(ModItems.ESSENCE_WITHER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE_BRICKS.get(), 4)
                .pattern("NN")
                .pattern("NN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE.get()), has(ModBlocks.NECRO_STONE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_NECRO_STONE.get(), 4)
                .pattern("NN")
                .pattern("NN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE.get()), has(ModBlocks.NECRO_STONE.get()))
                .save(pWriter);


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_NECRO_STONE.get(), 4)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModBlocks.POLISHED_NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.POLISHED_NECRO_STONE.get()), has(ModBlocks.POLISHED_NECRO_STONE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS.get())
                .requires(ModBlocks.NECRO_STONE_BRICKS.get())
                .requires(ModBlocks.CORRUPTED_SOIL.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE_PILLAR.get(), 2)
                .pattern("N")
                .pattern("N")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE_STAIRS.get(), 4)
                .pattern("N  ")
                .pattern("NN ")
                .pattern("NNN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_NECRO_STONE_STAIRS.get(), 4)
                .pattern("C  ")
                .pattern("CC ")
                .pattern("CCC")
                .define('C', ModBlocks.CHISELED_NECRO_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE_SLAB.get(), 6)
                .pattern("NNN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_NECRO_STONE_SLAB.get(), 6)
                .pattern("CCC")
                .define('C', ModBlocks.CHISELED_NECRO_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NECRO_STONE_WALL.get(), 6)
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COMPACT_NECRO_STONE.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', ModBlocks.NECRO_STONE.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.INFUSED_NECRO_STONE.get())
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIVING_ROCK.get())
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(ModBlocks.CORRUPTED_SOIL.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VEINED_ROCK.get())
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BROKEN_ROCK.get())
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(Items.GRAVEL)
                .unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);


        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.NECRO_STONE_BRICKS.get()),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModBlocks.CRACKED_NECRO_STONE_BRICKS.get(),
                        0.1f,
                        200
                ).unlockedBy(getHasName(ModBlocks.NECRO_STONE_BRICKS.get()), has(ModBlocks.NECRO_STONE_BRICKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_NECRO_STONE_BRICKS.get())
                .requires(ModBlocks.CUT_NECRO_STONE.get())
                .requires(ModItems.STORM_FRAGMENT.get())
                .unlockedBy(getHasName(ModBlocks.CUT_NECRO_STONE.get()), has(ModBlocks.CUT_NECRO_STONE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.UNSTABLE_NECRO_STONE.get())
                .requires(ModBlocks.INFUSED_NECRO_STONE.get())
                .requires(ModItems.STORM_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.STORM_FRAGMENT.get()), has(ModItems.STORM_FRAGMENT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENGRAVED_ROCK.get())
                .requires(ModBlocks.NECRO_STONE.get())
                .requires(ModItems.STORM_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.STORM_FRAGMENT.get()), has(ModItems.STORM_FRAGMENT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CORRUPTED_PICKAXE.get())
                .pattern("NFN")
                .pattern(" S ")
                .pattern(" S ")
                .define('N', ModBlocks.NECRO_STONE.get())
                .define('F', ModItems.ESSENCE_WITHER.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.ESSENCE_WITHER.get()), has(ModItems.STORM_FRAGMENT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WITHERED_PLANKS.get(), 4)
                .requires(ModBlocks.WITHERED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_LOG.get()), has(ModBlocks.WITHERED_LOG.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WITHERED_PLANKS.get(), 4)
                .requires(ModBlocks.STRIPPED_WITHERED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_WITHERED_LOG.get()), has(ModBlocks.STRIPPED_WITHERED_LOG.get()))
                .save(pWriter, "withered_planks_from_stripped_withered_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.VEINED_WITHERED_PLANKS.get())
                .requires(ModBlocks.WITHERED_PLANKS.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.REINFORCED_WITHERED_PLANKS.get())
                .requires(ModBlocks.WITHERED_PLANKS.get())
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WITHERED_STAIRS.get(), 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WITHERED_SLAB.get(), 6)
                .pattern("PPP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.WITHERED_FENCE.get(), 3)
                .pattern("PSP")
                .pattern("PSP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WITHERED_FENCE_GATE.get())
                .pattern("SPS")
                .pattern("SPS")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WITHERED_DOOR.get(), 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WITHERED_TRAPDOOR.get(), 2)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.WITHERED_BUTTON.get())
                .requires(ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WITHERED_PRESSURE_PLATE.get())
                .pattern("PP")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WITHERED_BEAM.get(), 2)
                .pattern("P")
                .pattern("P")
                .define('P', ModBlocks.WITHERED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_PLANKS.get()), has(ModBlocks.WITHERED_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_WITHERED_BEAM.get())
                .requires(ModBlocks.WITHERED_BEAM.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy(getHasName(ModBlocks.WITHERED_BEAM.get()), has(ModBlocks.WITHERED_BEAM.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.WITHER_SOUP.get())
                .requires(ModItems.WITHER_MUSHROOM.get())
                .requires(Items.BOWL)
                .unlockedBy("has_wither_mushroom", has(ModItems.WITHER_MUSHROOM.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CORRUPTED_STEW.get())
                .requires(ModItems.WITHER_MUSHROOM.get())
                .requires(ModItems.WITHER_MUSHROOM.get())
                .requires(Items.BOWL)
                .unlockedBy("has_wither_mushroom", has(ModItems.WITHER_MUSHROOM.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ESSENCE_WITHER.get())
                .pattern(" M ")
                .pattern("MBM")
                .pattern(" M ")
                .define('M', ModItems.WITHER_MUSHROOM.get())
                .define('B', Items.GLASS_BOTTLE)
                .unlockedBy("has_mushroom", has(ModItems.WITHER_MUSHROOM.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CORRUPTED_SOIL.get())
                .requires(Blocks.DIRT)
                .requires(ModItems.DEAD_LEAVES.get())
                .requires(ModItems.ESSENCE_WITHER.get())
                .unlockedBy("has_dead_leaves", has(ModItems.DEAD_LEAVES.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NECROSED_BLADE.get())
                .pattern(" E ")
                .pattern(" E ")
                .pattern(" S ")
                .define('E', ModItems.ESSENCE_WITHER.get())
                .define('S', ModItems.WITHER_STICK.get())
                .unlockedBy("has_wither_stick", has(ModItems.WITHER_STICK.get()))
                .save(pWriter);


        // ------------

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
                .pattern(" S ")
                .pattern("P  ")
                .define('R', ModItems.RUBIS.get())
                .define('S', Items.STICK)
                .define('P', ModItems.RUBY_WAND_PLAN.get())
                .unlockedBy(getHasName(ModItems.RUBY_WAND_PLAN.get()), has(ModItems.RUBY_WAND_PLAN.get()))
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
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.RUBY_BOAT.get())
                .pattern("# #")
                .pattern("###")
                .define('#', ModBlocks.RUBY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.RUBY_PLANKS.get()), has(ModBlocks.RUBY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBIS.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.RUBY_SHARD.get())
                .unlockedBy(getHasName(ModItems.RUBY_SHARD.get()), has(ModItems.RUBY_SHARD.get()))
                .save(pWriter, modLoc("rubis_from_shards"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBY_FIRE_CORE.get())
                .pattern(" R ")
                .pattern("PEP")
                .pattern(" O ")
                .define('R', ModBlocks.RUBIS_BLOCK.get())
                .define('P', ModItems.RUBY_FIRE_CORE_PLAN.get())
                .define('E', ModItems.RUBY_ESSENCE.get())
                .define('O', Items.BLAZE_POWDER)
                .unlockedBy(getHasName(ModItems.RUBY_FIRE_CORE_PLAN.get()), has(ModItems.RUBY_FIRE_CORE_PLAN.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RUBY_BRAZIER.get())
                .pattern("RRR")
                .pattern("RBR")
                .pattern("RRR")
                .define('B', ModBlocks.RUBIS_BLOCK.get())
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModBlocks.RUBY_BRAZIER.get()), has(ModBlocks.RUBY_BRAZIER.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RUBY_RELIC_ARMOR.get())
                .pattern(" H ")
                .pattern("ECE")
                .pattern(" B ")
                .define('H', ModItems.RUBY_HEART.get())
                .define('E', ModItems.RUBY_EYE.get())
                .define('C', ModItems.RUBY_CORE_RELIC.get())
                .define('B', ModItems.RUBY_BLOOD.get())
                .unlockedBy(getHasName(ModItems.RUBY_EYE.get()), has(ModItems.RUBY_EYE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RUBY_SHARD.get(), 9)
                .requires(ModItems.RUBIS.get())
                .unlockedBy(getHasName(ModItems.RUBIS.get()), has(ModItems.RUBIS.get()))
                .save(pWriter, modLoc("shards_from_rubis"));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, ModItems.RUBY_CHEST_BOAT.get())
                .requires(ModItems.RUBY_BOAT.get())
                .requires(Items.CHEST)
                .unlockedBy(getHasName(ModItems.RUBY_BOAT.get()), has(ModItems.RUBY_BOAT.get()))
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
    private ResourceLocation modLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, name);
    }

    }

