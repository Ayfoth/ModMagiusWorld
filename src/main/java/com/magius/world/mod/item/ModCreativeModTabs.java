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

//    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MOD_TABS.register("tutorial_tab",
//            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SAPPHIRE.get()))
//                    .title(Component.translatable("creativetab.tutorial_tab"))
//                    .displayItems((itemDisplayParameters, pOutput) -> {
//                        pOutput.accept(ModItems.SAPPHIRE.get());
//                        pOutput.accept(ModItems.RAW_SAPPHIRE.get());
//                        pOutput.accept(ModItems.METAL_DETECTOR.get());
//                        pOutput.accept(ModItems.GOLD_DETECTOR.get());
//                        pOutput.accept(ModItems.LAPIS_DETECTOR.get());
//                        pOutput.accept(ModItems.PRECIOUS_DETECTOR.get());
//                        pOutput.accept(ModItems.STRAWBERRY.get());
//                        pOutput.accept(ModItems.PINE_CONE.get());
//                        pOutput.accept(ModItems.SAPPHIRE_STAFF.get());
//
//                        pOutput.accept(ModItems.SAPPHIRE_SWORD.get());
//                        pOutput.accept(ModItems.SAPPHIRE_PICKAXE.get());
//                        pOutput.accept(ModItems.SAPPHIRE_AXE.get());
//                        pOutput.accept(ModItems.SAPPHIRE_SHOVEL.get());
//                        pOutput.accept(ModItems.SAPPHIRE_HOE.get());
//
//                        pOutput.accept(ModItems.SAPPHIRE_HELMET.get());
//                        pOutput.accept(ModItems.SAPPHIRE_CHESTPLATE.get());
//                        pOutput.accept(ModItems.SAPPHIRE_LEGGINGS.get());
//                        pOutput.accept(ModItems.SAPPHIRE_BOOTS.get());
//
//                        pOutput.accept(ModItems.STRAWBERRY_SEEDS.get());
//                        pOutput.accept(ModItems.CORN_SEEDS.get());
//                        pOutput.accept(ModItems.CORN.get());
//                        pOutput.accept(ModItems.BAR_BRAWL_MUSIC_DISC.get());
//                        pOutput.accept(ModItems.RHINO_SPAWN_EGG.get());
//
//                        pOutput.accept(ModItems.PINE_SIGN.get());
//                        pOutput.accept(ModItems.PINE_HANGING_SIGN.get());
//
//                        pOutput.accept(ModItems.PINE_BOAT.get());
//                        pOutput.accept(ModItems.PINE_CHEST_BOAT.get());
//
//                        pOutput.accept(ModItems.DICE.get());
//
//
//                        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
//                        pOutput.accept(ModBlocks.SAPPHIRE_DOOR.get());
//                        pOutput.accept(ModBlocks.SAPPHIRE_TRAPDOOR.get());
//
//                        pOutput.accept(ModBlocks.CATMINT.get());
//                        pOutput.accept(ModBlocks.GEM_POLISHING_STATION.get());
//
//                        pOutput.accept(ModBlocks.PINE_LOG.get());
//                        pOutput.accept(ModBlocks.PINE_WOOD.get());
//                        pOutput.accept(ModBlocks.STRIPPED_PINE_LOG.get());
//                        pOutput.accept(ModBlocks.STRIPPED_PINE_WOOD.get());
//
//                        pOutput.accept(ModBlocks.PINE_PLANKS.get());
//                        pOutput.accept(ModBlocks.PINE_LEAVES.get());
//                        pOutput.accept(ModBlocks.PINE_SAPLING.get());
//
//                        pOutput.accept(ModBlocks.MOD_PORTAL.get());
//
//                    })
//                    .build());
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
                      //  pOutput.accept(ModItems.WITHER.get());
                        pOutput.accept(ModItems.RUBIS.get());
                        pOutput.accept(ModItems.RUBIS_SWORD.get());
                        pOutput.accept(ModItems.RUBIS_WAND.get());
                      //  pOutput.accept(ModItems.WITHER_PICKAXE.get());
                        pOutput.accept(ModItems.RUBIS_PICKAXE.get());
                     //   pOutput.accept(ModItems.WITHER_AXE.get());
                        pOutput.accept(ModItems.RUBIS_AXE.get());
                        pOutput.accept(ModItems.RUBIS_HOE.get());
                        pOutput.accept(ModItems.RUBIS_SHOVEL.get());
//                        pOutput.accept(ModItems.WITHER_HELMET.get());
//                        pOutput.accept(ModItems.WITHER_CHESTPLATE.get());
//                        pOutput.accept(ModItems.WITHER_LEGGINGS.get());
//                        pOutput.accept(ModItems.WITHER_BOOTS.get());
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
//                        pOutput.accept(ModBlocks.WITHER_BLOCK.get());
//                        pOutput.accept(ModBlocks.WITHER_ORE.get());
//                        pOutput.accept(ModBlocks.DEEPSLATE_WITHER_ORE.get());
                        pOutput.accept(ModBlocks.RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_RUBIS_ORE.get());
                        pOutput.accept(ModBlocks.RUBIS_BLOCK.get());
//                        pOutput.accept(ModBlocks.WHITE_LEGENDARY_BLOCK.get());
//                        pOutput.accept(ModBlocks.WITHER_SLAB.get());
//                        pOutput.accept(ModBlocks.WITHER_WALL.get());
//                        pOutput.accept(ModBlocks.WITHER_FENCE_GATE.get());
//                        pOutput.accept(ModBlocks.WITHER_STAIRS.get());
//                        pOutput.accept(ModBlocks.WITHER_FENCE.get());
//                        pOutput.accept(ModBlocks.WITHER_BUTTON.get());
//                        pOutput.accept(ModBlocks.WITHER_PRESSURE_PLATE.get());
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
                        pOutput.accept(ModBlocks.RUBY_SIGN.get());
                        pOutput.accept(ModBlocks.RUBY_HANGING_SIGN.get());
                        pOutput.accept(ModItems.RUBY_BOAT.get());
                        pOutput.accept(ModItems.RUBY_CHEST_BOAT.get());
                        pOutput.accept(ModBlocks.RUBY_TILE.get());
                        pOutput.accept(ModBlocks.RUBY_PILLAR.get());
                        pOutput.accept(ModBlocks.RUBY_LAMP.get());
                        pOutput.accept(ModBlocks.RUBY_BRAZIER.get());
                        pOutput.accept(ModBlocks.CHARRED_RUBY_BEAM.get());
                        pOutput.accept(ModBlocks.RUBY_FIRE_CORE.get());
                        pOutput.accept(ModItems.RUBY_BOAR_SPAWN_EGG.get());
                        pOutput.accept(ModItems.RUBY_WISP_SPAWN_EGG.get());
                        pOutput.accept(ModItems.RUBY_SHARD.get());
                        pOutput.accept(ModItems.RUBY_ESSENCE.get());
                       pOutput.accept(ModItems.RUBY_KEY.get());
                      pOutput.accept(ModBlocks.RUBY_CACHE.get());
                      pOutput.accept(ModItems.BOSS_RUBY_KEY.get());
                      pOutput.accept(ModBlocks.BOSS_RUBY_DOOR.get());

//
//                        // items rubis
                        pOutput.accept(ModItems.RED_WHEAT_SEEDS.get());
                        pOutput.accept(ModItems.RED_WHEAT.get());
                        pOutput.accept(ModItems.RUBIS.get());

                    }).build());

    public static final RegistryObject<CreativeModeTab> ECHO_TAB = CREATIVE_MOD_TABS.register("echo_du_premier",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ESSENCE_WITHER.get()))
                    .title(Component.translatable("creativetab.echo_du_premier"))
                    .displayItems((itemDisplayParameters, pOutput) -> {

                        //Outils
                        pOutput.accept(ModItems.NECROSED_BLADE.get());
                        pOutput.accept(ModItems.LIVING_AXE.get());
                        pOutput.accept(ModItems.WITHER_STICK.get());
                        pOutput.accept(ModItems.CORRUPTED_PICKAXE.get());
                        pOutput.accept(ModItems.DEAD_LEAVES.get());

                        // Corruption
                        pOutput.accept(ModItems.ESSENCE_WITHER.get());
                        pOutput.accept(ModBlocks.CORRUPTED_SOIL.get());
                        pOutput.accept(ModItems.WITHER_MUSHROOM.get());
                        pOutput.accept(ModItems.WITHER_SOUP.get());
                        pOutput.accept(ModItems.CORRUPTED_STEW.get());



                        // Purification
                        pOutput.accept(ModItems.PURIFYING_HEART.get());
                        pOutput.accept(ModItems.STORM_FRAGMENT.get());
                        pOutput.accept(ModBlocks.PURIFYING_CORE.get());

                        // Blocs Echo
                        pOutput.accept(ModBlocks.NECRO_STONE.get());
                        pOutput.accept(ModBlocks.POLISHED_NECRO_STONE.get());
                        pOutput.accept(ModBlocks.CUT_NECRO_STONE.get());
                        pOutput.accept(ModBlocks.NECRO_STONE_BRICKS.get());
                        pOutput.accept(ModBlocks.CRACKED_NECRO_STONE_BRICKS.get());
                        pOutput.accept(ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS.get());
                        pOutput.accept(ModBlocks.CHISELED_NECRO_STONE_BRICKS.get());
                        pOutput.accept(ModBlocks.NECRO_STONE_PILLAR.get());
                        pOutput.accept(ModBlocks.NECRO_STONE_STAIRS.get());
                        pOutput.accept(ModBlocks.NECRO_STONE_SLAB.get());
                        pOutput.accept(ModBlocks.NECRO_STONE_WALL.get());
                        pOutput.accept(ModBlocks.CHISELED_NECRO_STONE_STAIRS.get());
                        pOutput.accept(ModBlocks.CHISELED_NECRO_STONE_SLAB.get());
                        pOutput.accept(ModBlocks.COMPACT_NECRO_STONE.get());
                        pOutput.accept(ModBlocks.INFUSED_NECRO_STONE.get());
                        pOutput.accept(ModBlocks.UNSTABLE_NECRO_STONE.get());
                        pOutput.accept(ModBlocks.LIVING_ROCK.get());
                        pOutput.accept(ModBlocks.VEINED_ROCK.get());
                        pOutput.accept(ModBlocks.BROKEN_ROCK.get());
                        pOutput.accept(ModBlocks.ENGRAVED_ROCK.get());
                        pOutput.accept(ModBlocks.WITHERED_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_WITHERED_LOG.get());
                        pOutput.accept(ModBlocks.WITHERED_PLANKS.get());
                        pOutput.accept(ModBlocks.VEINED_WITHERED_PLANKS.get());
                        pOutput.accept(ModBlocks.REINFORCED_WITHERED_PLANKS.get());
                        pOutput.accept(ModBlocks.WITHERED_STAIRS.get());
                        pOutput.accept(ModBlocks.WITHERED_SLAB.get());
                        pOutput.accept(ModBlocks.WITHERED_FENCE.get());
                        pOutput.accept(ModBlocks.WITHERED_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.WITHERED_DOOR.get());
                        pOutput.accept(ModBlocks.WITHERED_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.WITHERED_BUTTON.get());
                        pOutput.accept(ModBlocks.WITHERED_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.WITHERED_BEAM.get());
                        pOutput.accept(ModBlocks.CRACKED_WITHERED_BEAM.get());
                        pOutput.accept(ModBlocks.BLACKENED_LEAVES.get());
                        pOutput.accept(ModBlocks.WITHERED_ROOTS.get());



                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
