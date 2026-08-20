package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModChestLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
        writer.accept(
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "chests/necro_cave"),
                LootTable.lootTable()
                        .withPool(net.minecraft.world.level.storage.loot.LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2, 4))
                                .add(LootItem.lootTableItem(ModItems.ESSENCE_WITHER.get()).setWeight(4))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(3))
                                .add(LootItem.lootTableItem(Items.BLAZE_POWDER).setWeight(2))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1))
                        )
        );
        writer.accept(
                ResourceLocation.fromNamespaceAndPath(
                        MagiusWorldMod.MOD_ID,
                        "chests/dragonmaid_ruins"
                ),
                LootTable.lootTable()

                        /*
                         * =========================================
                         * GRIMOIRE GARANTI
                         * =========================================
                         */
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(
                                                ConstantValue.exactly(1)
                                        )
                                        .add(
                                                LootItem.lootTableItem(
                                                        ModItems.DRAGONMAID_GRIMOIRE.get()
                                                )
                                        )
                        )

                        /*
                         * =========================================
                         * BUTIN SECONDAIRE
                         * =========================================
                         */
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(
                                                UniformGenerator.between(
                                                        2,
                                                        4
                                                )
                                        )

                                        .add(
                                                LootItem.lootTableItem(
                                                                Items.BREAD
                                                        )
                                                        .setWeight(5)
                                                        .apply(
                                                                SetItemCountFunction.setCount(
                                                                        UniformGenerator.between(
                                                                                1,
                                                                                4
                                                                        )
                                                                )
                                                        )
                                        )

                                        .add(
                                                LootItem.lootTableItem(
                                                                Items.BOOK
                                                        )
                                                        .setWeight(4)
                                                        .apply(
                                                                SetItemCountFunction.setCount(
                                                                        UniformGenerator.between(
                                                                                1,
                                                                                3
                                                                        )
                                                                )
                                                        )
                                        )

                                        .add(
                                                LootItem.lootTableItem(
                                                                Items.PAPER
                                                        )
                                                        .setWeight(4)
                                                        .apply(
                                                                SetItemCountFunction.setCount(
                                                                        UniformGenerator.between(
                                                                                2,
                                                                                6
                                                                        )
                                                                )
                                                        )
                                        )

                                        .add(
                                                LootItem.lootTableItem(
                                                                Items.GOLD_NUGGET
                                                        )
                                                        .setWeight(2)
                                                        .apply(
                                                                SetItemCountFunction.setCount(
                                                                        UniformGenerator.between(
                                                                                1,
                                                                                4
                                                                        )
                                                                )
                                                        )
                                        )
                        )
        );
    }
}
