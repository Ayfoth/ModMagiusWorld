package com.magius.world.mod.datagen.loot;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModGameplayLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
        writer.accept(ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "gameplay/ruby_cache"),
                LootTable.lootTable()

                        // Pool principal : loot commun
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2, 4))
                                .add(LootItem.lootTableItem(Items.BLAZE_POWDER)
                                        .setWeight(12)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6))))
                                .add(LootItem.lootTableItem(Items.MAGMA_CREAM)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
                                .add(LootItem.lootTableItem(Items.REDSTONE)
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8))))
                                .add(LootItem.lootTableItem(ModItems.RUBIS.get())
                                        .setWeight(14)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 7))))
                        )

                        // Pool rare : 15% de chance
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.15f))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                                .add(LootItem.lootTableItem(ModBlocks.RUBY_BRAZIER.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                                .add(LootItem.lootTableItem(Items.BLAZE_ROD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        )

                        // Pool très rare : 5% de chance
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance(0.05f))
                                .add(LootItem.lootTableItem(ModItems.RUBIS_WAND.get()).setWeight(3))
                                .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK)
                                        .setWeight(1)
                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        )
        );
    }
}
