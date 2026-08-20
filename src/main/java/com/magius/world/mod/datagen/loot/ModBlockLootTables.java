package com.magius.world.mod.datagen.loot;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.custom.CornCropBlock;
import com.magius.world.mod.block.custom.RedWheatCropBlock;
import com.magius.world.mod.block.custom.StrawberryCropBlock;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.util.ModTags;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.SWORDSOUL_SPIRIT_FORGE.get());
        this.dropSelf(ModBlocks.SWORDSOUL_SPIRIT_FORGE_TERMINAL.get());
        this.dropSelf(ModBlocks.HEARTH_CORE.get());
        this.dropSelf(ModBlocks.DRAGONMAID_ALLEGIANCE_ALTAR.get());
        // Mod Echo
        this.dropSelf(ModBlocks.NECRO_STONE.get());
        this.dropSelf(ModBlocks.POLISHED_NECRO_STONE.get());
        this.dropSelf(ModBlocks.CUT_NECRO_STONE.get());
        this.dropSelf(ModBlocks.NECRO_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.CRACKED_NECRO_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.CHISELED_NECRO_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.NECRO_STONE_PILLAR.get());
        this.dropSelf(ModBlocks.NECRO_STONE_STAIRS.get());
        this.dropSelf(ModBlocks.NECRO_STONE_SLAB.get());
        this.dropSelf(ModBlocks.NECRO_STONE_WALL.get());
        this.dropSelf(ModBlocks.CHISELED_NECRO_STONE_STAIRS.get());
        this.dropSelf(ModBlocks.CHISELED_NECRO_STONE_SLAB.get());
        this.dropSelf(ModBlocks.COMPACT_NECRO_STONE.get());
        this.dropSelf(ModBlocks.INFUSED_NECRO_STONE.get());
        this.dropSelf(ModBlocks.UNSTABLE_NECRO_STONE.get());
        this.dropSelf(ModBlocks.LIVING_ROCK.get());
        this.dropSelf(ModBlocks.VEINED_ROCK.get());
        this.dropSelf(ModBlocks.BROKEN_ROCK.get());
        this.dropSelf(ModBlocks.ENGRAVED_ROCK.get());
        this.add(ModBlocks.CORRUPTED_SOIL.get(),
                block -> createSilkTouchDispatchTable(
                        block,
                        LootItem.lootTableItem(Blocks.DIRT)
                ));
        this.dropSelf(ModBlocks.PURIFYING_CORE.get());
        this.dropSelf(ModBlocks.WITHERED_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_WITHERED_LOG.get());
        this.dropSelf(ModBlocks.WITHERED_PLANKS.get());
        this.dropSelf(ModBlocks.VEINED_WITHERED_PLANKS.get());
        this.dropSelf(ModBlocks.REINFORCED_WITHERED_PLANKS.get());
        this.dropSelf(ModBlocks.WITHERED_STAIRS.get());
        this.dropSelf(ModBlocks.WITHERED_SLAB.get());
        this.dropSelf(ModBlocks.WITHERED_FENCE.get());
        this.dropSelf(ModBlocks.WITHERED_FENCE_GATE.get());
        this.add(ModBlocks.WITHERED_DOOR.get(), block -> createDoorTable(ModBlocks.WITHERED_DOOR.get()));
        this.dropSelf(ModBlocks.WITHERED_TRAPDOOR.get());
        this.dropSelf(ModBlocks.WITHERED_BUTTON.get());
        this.dropSelf(ModBlocks.WITHERED_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.WITHERED_BEAM.get());
        this.dropSelf(ModBlocks.CRACKED_WITHERED_BEAM.get());
        this.add(ModBlocks.BLACKENED_LEAVES.get(), this::createBlackenedLeavesDrops);
        this.dropSelf(ModBlocks.WITHERED_ROOTS.get());
        this.add(ModBlocks.WITHER_MUSHROOM_PLANT.get(),
                block -> createSingleItemTable(ModItems.WITHER_MUSHROOM.get()));
        this.dropSelfWithOrganicHarvester(ModBlocks.NECROTIC_FLESH_WALL.get());
        this.dropSelfWithOrganicHarvester(ModBlocks.COMPACT_FLESH.get());
        this.dropSelfWithOrganicHarvester(ModBlocks.PULSATING_FLESH.get());
        this.dropSelfWithOrganicHarvester(ModBlocks.VEINED_FLESH.get());
        this.dropSelfWithOrganicHarvester(ModBlocks.HARDENED_FLESH.get());
        this.dropSelfWithOrganicHarvester(ModBlocks.EXPOSED_HEART.get());
        dropSelfWithOrganicHarvester(ModBlocks.PROTECTED_HEART.get());
        dropSelfWithOrganicHarvester(ModBlocks.ORGANIC_NODE.get());



       this.dropSelf(ModBlocks.SOUND_BLOCK.get());
        this.dropSelf(ModBlocks.WITHER_BLOCK.get());
        this.dropSelf(ModBlocks.RUBIS_BLOCK.get());
        this.dropSelf(ModBlocks.WHITE_LEGENDARY_BLOCK.get());

        this.add(ModBlocks.WITHER_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.WITHER_ORE.get(), ModItems.WITHER.get()));
        this.add(ModBlocks.DEEPSLATE_WITHER_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_WITHER_ORE.get(), ModItems.WITHER.get()));
       this.add(ModBlocks.RUBIS_ORE.get(),
               block -> createRareOreDrops(ModBlocks.RUBIS_ORE.get(), ModItems.RUBIS.get()));
       this.add(ModBlocks.DEEPSLATE_RUBIS_ORE.get(),
                block -> createRareOreDrops(ModBlocks.DEEPSLATE_RUBIS_ORE.get(), ModItems.RUBIS.get()));
      this.add(ModBlocks.NETHER_RUBIS_ORE.get(),
              block -> createRareOreDrops(ModBlocks.NETHER_RUBIS_ORE.get(), ModItems.RUBIS.get()));
       this.add(ModBlocks.END_STONE_RUBIS_ORE.get(),
             block -> createRareOreDrops(ModBlocks.END_STONE_RUBIS_ORE.get(), ModItems.RUBIS.get()));


        this.dropSelf(ModBlocks.WITHER_STAIRS.get());
        this.dropSelf(ModBlocks.WITHER_BUTTON.get());
        this.dropSelf(ModBlocks.WITHER_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.SAPPHIRE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.WITHER_FENCE.get());
        this.dropSelf(ModBlocks.WITHER_FENCE_GATE.get());
        this.dropSelf(ModBlocks.WITHER_WALL.get());

        this.add(ModBlocks.WITHER_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WITHER_SLAB.get()));
        this.add(ModBlocks.SAPPHIRE_DOOR.get(),
                block -> createDoorTable(ModBlocks.SAPPHIRE_DOOR.get()));

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));
        this.add(ModBlocks.STRAWBERRY_CROP.get(), createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.STRAWBERRY.get(),
                ModItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));

        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 7))
                .or(LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 8)));

        // LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
        //         .hasBlockStateProperties(ModBlocks.CORN_CROP.get())
        //         .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 8));

        this.add(ModBlocks.CORN_CROP.get(), createCropDrops(ModBlocks.CORN_CROP.get(), ModItems.CORN.get(),
                ModItems.CORN_SEEDS.get(), lootitemcondition$builder2));
        LootItemCondition.Builder lootitemcondition$builder3 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.RED_WHEAT_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RedWheatCropBlock.AGE, 7));

        this.add(ModBlocks.RED_WHEAT_CROP.get(), createCropDrops(
                ModBlocks.RED_WHEAT_CROP.get(),
                ModItems.RED_WHEAT.get(),
                ModItems.RED_WHEAT_SEEDS.get(),
                lootitemcondition$builder3
        ));

        this.dropSelf(ModBlocks.CATMINT.get());
        this.add(ModBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(ModBlocks.CATMINT.get()));
        this.add(ModBlocks.RED_GRASS.get(), block ->
                createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block,
                                LootItem.lootTableItem(ModItems.RED_WHEAT_SEEDS.get())
                                        .when(LootItemRandomChanceCondition.randomChance(0.35f)))));
        this.add(ModBlocks.DARK_RED_GRASS.get(), block ->
                createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block,
                                LootItem.lootTableItem(ModItems.RED_WHEAT_SEEDS.get())
                                        .when(LootItemRandomChanceCondition.randomChance(0.35f)))));

        this.dropSelf(ModBlocks.GEM_POLISHING_STATION.get());
        this.dropSelf(ModBlocks.FIRE_FOUNDERIE.get());

        this.dropSelf(ModBlocks.PINE_LOG.get());
        this.dropSelf(ModBlocks.PINE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_PINE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_PINE_WOOD.get());
        this.dropSelf(ModBlocks.PINE_PLANKS.get());
        dropSelf(ModBlocks.RUBY_FLOWER.get());
        dropSelf(ModBlocks.CRYSTAL_SHARD.get());
        this.add(ModBlocks.RUBY_BUSH.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.RED_WHEAT_SEEDS.get())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                Enchantments.BLOCK_FORTUNE,
                                                new float[]{0.10F, 0.15F, 0.20F, 0.30F}
                                        )))
                        )
        );

        this.add(ModBlocks.BLACKWOOD_LOG.get(), block ->
                createSingleItemTable(ModItems.BLACKWOOD_BLOCK.get()));

        this.add(ModBlocks.PINE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.PINE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(ModBlocks.PINE_SIGN.get(), block ->
                createSingleItemTable(ModItems.PINE_SIGN.get()));
        this.add(ModBlocks.PINE_WALL_SIGN.get(), block ->
                createSingleItemTable(ModItems.PINE_SIGN.get()));
        this.add(ModBlocks.PINE_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.PINE_HANGING_SIGN.get()));
        this.add(ModBlocks.PINE_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.PINE_HANGING_SIGN.get()));
        this.dropSelf(ModBlocks.PINE_SAPLING.get());
        this.add(ModBlocks.RUBY_MUSHROOM.get(), block ->
                createSingleItemTable(ModItems.RUBY_MUSHROOM.get()));
        dropSelf(ModBlocks.RUBY_LOG.get());
        this.add(ModBlocks.RUBY_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.RUBY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(ModBlocks.RUBY_SAPLING.get());
        dropSelf(ModBlocks.STRIPPED_RUBY_LOG.get());
        dropSelf(ModBlocks.RUBY_PLANKS.get());
        dropSelf(ModBlocks.RUBY_STAIRS.get());
        add(ModBlocks.RUBY_SLAB.get(), createSlabItemTable(ModBlocks.RUBY_SLAB.get()));
        dropSelf(ModBlocks.RUBY_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_RUBY_WOOD.get());
        dropSelf(ModBlocks.RUBY_BUTTON.get());
        dropSelf(ModBlocks.RUBY_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.RUBY_FENCE.get());
        dropSelf(ModBlocks.RUBY_FENCE_GATE.get());
        add(ModBlocks.RUBY_DOOR.get(), createDoorTable(ModBlocks.RUBY_DOOR.get()));
        dropSelf(ModBlocks.RUBY_TRAPDOOR.get());
        this.add(ModBlocks.RUBY_SIGN.get(), block ->
                createSingleItemTable(ModItems.RUBY_SIGN.get()));
        this.add(ModBlocks.RUBY_WALL_SIGN.get(), block ->
                createSingleItemTable(ModItems.RUBY_SIGN.get()));

        this.add(ModBlocks.RUBY_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.RUBY_HANGING_SIGN.get()));
        this.add(ModBlocks.RUBY_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.RUBY_HANGING_SIGN.get()));
        dropSelf(ModBlocks.RUBY_TILE.get());
        dropSelf(ModBlocks.RUBY_PILLAR.get());
        dropSelf(ModBlocks.RUBY_LAMP.get());
        dropSelf(ModBlocks.RUBY_BRAZIER.get());
        dropSelf(ModBlocks.CHARRED_RUBY_BEAM.get());
        dropSelf(ModBlocks.RUBY_FIRE_CORE.get());
        //this.add(ModBlocks.RUBY_CACHE.get(), noDrop());
       // this.add(ModBlocks.BOSS_RUBY_DOOR.get(), noDrop());





    }

    protected LootTable.Builder createBlackenedLeavesDrops(Block block) {
        LootItemCondition.Builder hasShearsOrSilkTouch =
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS))
                        .or(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                .hasEnchantment(new EnchantmentPredicate(
                                        Enchantments.SILK_TOUCH,
                                        MinMaxBounds.Ints.atLeast(1)
                                ))));

        LootItemCondition.Builder hasNoShearsOrSilkTouch =
                InvertedLootItemCondition.invert(hasShearsOrSilkTouch);

        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(hasShearsOrSilkTouch)
                        .add(LootItem.lootTableItem(block))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(hasNoShearsOrSilkTouch)
                        .add(applyExplosionCondition(block,
                                LootItem.lootTableItem(ModItems.DEAD_LEAVES.get())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                Enchantments.BLOCK_FORTUNE,
                                                0.20F, 0.25F, 0.30F, 0.40F
                                        ))
                        ))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(hasNoShearsOrSilkTouch)
                        .add(applyExplosionCondition(block,
                                LootItem.lootTableItem(ModItems.WITHER_STICK.get())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                Enchantments.BLOCK_FORTUNE,
                                                0.08F, 0.11F, 0.14F, 0.18F
                                        ))
                        ))
                );
    }


    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    protected LootTable.Builder createRareOreDrops(Block pBlock, Item item) {
       return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                      LootItem.lootTableItem(item)
                              .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                              .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
   }
    private void dropSelfWithOrganicHarvester(Block block) {
        this.add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(MatchTool.toolMatches(
                                ItemPredicate.Builder.item()
                                        .of(ModTags.Items.ORGANIC_BLOCK_HARVESTERS)
                        ))
                        .add(LootItem.lootTableItem(block))));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(block -> block != ModBlocks.BOSS_RUBY_DOOR.get())
                .filter(block -> block != ModBlocks.RUBY_CACHE.get())
                .filter(block -> block != ModBlocks.BOSS_ARENA_TRIGGER.get())
                .filter(block -> block != ModBlocks.RUBY_ALTAR.get())
                .filter(block -> block != ModBlocks.CORRUPTED_LECTERN.get())
                .filter(block -> block != ModBlocks.RUBY_PEDESTAL.get())
                .toList();
    }
}
