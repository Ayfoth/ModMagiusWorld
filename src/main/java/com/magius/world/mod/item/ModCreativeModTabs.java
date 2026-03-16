package com.magius.world.mod.item;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagiusWorldMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MOD_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
                    .title(Component.translatable("creativetab.tutorial_tab"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.SAPPHIRE.get());
                        pOutput.accept(ModItems.RAW_SAPPHIRE.get());
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModItems.GOLD_DETECTOR.get());
                        pOutput.accept(ModItems.LAPIS_DETECTOR.get());
                        pOutput.accept(ModItems.PRECIOUS_DETECTOR.get());
                        pOutput.accept(ModItems.STRAWBERRY.get());
                        pOutput.accept(ModItems.PINE_CONE.get());
                        pOutput.accept(ModItems.SAPPHIRE_STAFF.get());

                        pOutput.accept(ModItems.SAPPHIRE_SWORD.get());
                        pOutput.accept(ModItems.SAPPHIRE_PICKAXE.get());
                        pOutput.accept(ModItems.SAPPHIRE_AXE.get());
                        pOutput.accept(ModItems.SAPPHIRE_SHOVEL.get());
                        pOutput.accept(ModItems.SAPPHIRE_HOE.get());

                        pOutput.accept(ModItems.SAPPHIRE_HELMET.get());
                        pOutput.accept(ModItems.SAPPHIRE_CHESTPLATE.get());
                        pOutput.accept(ModItems.SAPPHIRE_LEGGINGS.get());
                        pOutput.accept(ModItems.SAPPHIRE_BOOTS.get());

                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());
                        pOutput.accept(ModItems.CORN_SEEDS.get());
                        pOutput.accept(ModItems.CORN.get());
                        pOutput.accept(ModItems.BAR_BRAWL_MUSIC_DISC.get());
                        pOutput.accept(ModItems.RHINO_SPAWN_EGG.get());

                        pOutput.accept(ModItems.PINE_SIGN.get());
                        pOutput.accept(ModItems.PINE_HANGING_SIGN.get());

                        pOutput.accept(ModItems.PINE_BOAT.get());
                        pOutput.accept(ModItems.PINE_CHEST_BOAT.get());

                        pOutput.accept(ModItems.DICE.get());


                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_DOOR.get());
                        pOutput.accept(ModBlocks.SAPPHIRE_TRAPDOOR.get());

                        pOutput.accept(ModBlocks.CATMINT.get());
                        pOutput.accept(ModBlocks.GEM_POLISHING_STATION.get());

                        pOutput.accept(ModBlocks.PINE_LOG.get());
                        pOutput.accept(ModBlocks.PINE_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_PINE_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_PINE_WOOD.get());

                        pOutput.accept(ModBlocks.PINE_PLANKS.get());
                        pOutput.accept(ModBlocks.PINE_LEAVES.get());
                        pOutput.accept(ModBlocks.PINE_SAPLING.get());

                        pOutput.accept(ModBlocks.MOD_PORTAL.get());

                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> MONNAIE = CREATIVE_MOD_TABS.register("monnaie",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PIECE_MG.get()))
                    .title(Component.translatable("creativetab.monnaie"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.PIECE_MG.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> ITEM_MAGIUS = CREATIVE_MOD_TABS.register("item_magius",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WITHER.get()))
                    .title(Component.translatable("creativetab.item_magius"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.WITHER.get());
                        pOutput.accept(ModItems.RUBIS.get());
                        pOutput.accept(ModItems.RUBIS_SWORD.get());
                        pOutput.accept(ModItems.RUBIS_WAND.get());
                        pOutput.accept(ModItems.WITHER_PICKAXE.get());
                        pOutput.accept(ModItems.RUBIS_PICKAXE.get());
                        pOutput.accept(ModItems.WITHER_AXE.get());
                        pOutput.accept(ModItems.RUBIS_AXE.get());
                        pOutput.accept(ModItems.RUBIS_HOE.get());
                        pOutput.accept(ModItems.RUBIS_SHOVEL.get());
                        pOutput.accept(ModItems.WITHER_HELMET.get());
                        pOutput.accept(ModItems.WITHER_CHESTPLATE.get());
                        pOutput.accept(ModItems.WITHER_LEGGINGS.get());
                        pOutput.accept(ModItems.WITHER_BOOTS.get());
                        pOutput.accept(ModItems.RUBIS_HELMET.get());
                        pOutput.accept(ModItems.RUBIS_CHESTPLATE.get());
                        pOutput.accept(ModItems.RUBIS_LEGGINGS.get());
                        pOutput.accept(ModItems.RUBIS_BOOTS.get());
                        pOutput.accept(ModItems.BLACKWOOD_BLOCK.get());


                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> BLOCK_MAGIUS = CREATIVE_MOD_TABS.register("block_magius",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WITHER_BLOCK.get()))
                    .title(Component.translatable("creativetab.block_magius"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.WITHER_BLOCK.get());
                        pOutput.accept(ModBlocks.WITHER_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_WITHER_ORE.get());
                        pOutput.accept(ModBlocks.RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.RUBIS_BLOCK.get());
                        pOutput.accept(ModBlocks.WHITE_LEGENDARY_BLOCK.get());
                        pOutput.accept(ModBlocks.WITHER_SLAB.get());
                        pOutput.accept(ModBlocks.WITHER_WALL.get());
                        pOutput.accept(ModBlocks.WITHER_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.WITHER_STAIRS.get());
                        pOutput.accept(ModBlocks.WITHER_FENCE.get());
                        pOutput.accept(ModBlocks.WITHER_BUTTON.get());
                        pOutput.accept(ModBlocks.WITHER_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.FIRE_FOUNDERIE.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> RUBY_BIOME_TAB = CREATIVE_MOD_TABS.register("ruby_biome_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.magiusworldmod.ruby_biome_tab"))
                    .icon(() -> new ItemStack(ModItems.RUBIS.get()))
                    .displayItems((itemDisplayParameters, pOutput) -> {

                        // blocs du biome
                        pOutput.accept(ModBlocks.RUBIS_BLOCK.get());
                        pOutput.accept(ModBlocks.RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.RED_GRASS.get());
                        pOutput.accept(ModBlocks.DARK_RED_GRASS.get());
                        pOutput.accept(ModBlocks.RUBY_FLOWER.get());
                        pOutput.accept(ModBlocks.RUBY_BUSH.get());
                       pOutput.accept(ModBlocks.CRYSTAL_SHARD.get());
                        pOutput.accept(ModItems.RUBY_MUSHROOM.get());
//
//                        // arbre rubis
                        pOutput.accept(ModBlocks.RUBY_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_RUBY_LOG.get());
                        pOutput.accept(ModBlocks.RUBY_LEAVES.get());
                        pOutput.accept(ModBlocks.RUBY_SAPLING.get());
                        pOutput.accept(ModBlocks.RUBY_PLANKS.get());
                        pOutput.accept(ModBlocks.RUBY_STAIRS.get());
                        pOutput.accept(ModBlocks.RUBY_SLAB.get());
                        pOutput.accept(ModBlocks.RUBY_BUTTON.get());
                        pOutput.accept(ModBlocks.RUBY_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.RUBY_FENCE.get());
                        pOutput.accept(ModBlocks.RUBY_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.RUBY_DOOR.get());
                        pOutput.accept(ModBlocks.RUBY_TRAPDOOR.get());
//
//                        // items rubis
                        pOutput.accept(ModItems.RED_WHEAT_SEEDS.get());
                        pOutput.accept(ModItems.RED_WHEAT.get());
                        pOutput.accept(ModItems.RUBIS.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
