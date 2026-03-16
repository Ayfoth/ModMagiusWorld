package com.magius.world.mod.block;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.custom.*;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.item.custom.FuelItem;
import com.magius.world.mod.sound.ModSounds;
import com.magius.world.mod.util.ModWoodTypes;
import com.magius.world.mod.worldgen.tree.PineTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MagiusWorldMod.MOD_ID);


    public static final RegistryObject<Block> WITHER_BLOCK = registerBlock("wither_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RUBIS_BLOCK = registerBlock("rubis_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(SoundType.STONE).lightLevel(state -> 7).strength(6)));
    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(ModSounds.SOUND_BLOCK_SOUNDS)));
    public static final RegistryObject<Block> WHITE_LEGENDARY_BLOCK = registerBlock("white_legendary_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.END_STONE).sound(SoundType.DEEPSLATE_BRICKS)));


     public static final RegistryObject<Block> WITHER_ORE = registerBlock("wither_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DEEPSLATE_WITHER_ORE = registerBlock("deepslate_wither_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.5f).requiresCorrectToolForDrops(), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> RUBIS_ORE = registerBlock("rubis_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 7), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> DEEPSLATE_RUBIS_ORE = registerBlock("deepslate_rubis_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 7), UniformInt.of(3, 7)));
    public static final RegistryObject<Block> NETHER_RUBIS_ORE = registerBlock("nether_rubis_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 7), UniformInt.of(2, 5)));
    public static final RegistryObject<Block> END_STONE_RUBIS_ORE = registerBlock("end_stone_rubis_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 7), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> WITHER_STAIRS = registerBlock("wither_stairs",
            () -> new StairBlock(() -> ModBlocks.WITHER_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> WITHER_SLAB = registerBlock("wither_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> WITHER_BUTTON = registerBlock("wither_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.AMETHYST),
                    BlockSetType.IRON, 10, true));
    public static final RegistryObject<Block> WITHER_PRESSURE_PLATE = registerBlock("wither_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.AMETHYST),
                    BlockSetType.IRON));

    public static final RegistryObject<Block> WITHER_FENCE = registerBlock("wither_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> WITHER_FENCE_GATE = registerBlock("wither_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST), SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));
    public static final RegistryObject<Block> WITHER_WALL = registerBlock("wither_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_DOOR = registerBlock("sapphire_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST).noOcclusion(), BlockSetType.IRON));
    public static final RegistryObject<Block> SAPPHIRE_TRAPDOOR = registerBlock("sapphire_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST).noOcclusion(), BlockSetType.IRON));

    public static final RegistryObject<Block> STRAWBERRY_CROP = BLOCKS.register("strawberry_crop",
            () -> new StrawberryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
    public static final RegistryObject<Block> CORN_CROP = BLOCKS.register("corn_crop",
            () -> new CornCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    public static final RegistryObject<Block> CATMINT = registerBlock("catmint",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 5,
                    BlockBehaviour.Properties.copy(Blocks.ALLIUM).noOcclusion().noCollission()));
    public static final RegistryObject<Block> POTTED_CATMINT = BLOCKS.register("potted_catmint",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.CATMINT,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()));

    public static final RegistryObject<Block> GEM_POLISHING_STATION = registerBlock("gem_polishing_station",
            () -> new GemPolishingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> FIRE_FOUNDERIE = registerBlock("fire_founderie",
            () -> new FireFounderieBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> PINE_LOG = registerBlock("pine_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> PINE_WOOD = registerBlock("pine_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_PINE_LOG = registerBlock("stripped_pine_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_PINE_WOOD = registerBlock("stripped_pine_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> BLACKWOOD_LOG = registerBlock("blackwood_log",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> PINE_PLANKS = registerBlock("pine_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> PINE_LEAVES = registerBlock("pine_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final RegistryObject<Block> PINE_SIGN = BLOCKS.register("pine_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.PINE));
    public static final RegistryObject<Block> PINE_WALL_SIGN = BLOCKS.register("pine_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.PINE));

    public static final RegistryObject<Block> PINE_HANGING_SIGN = BLOCKS.register("pine_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), ModWoodTypes.PINE));
    public static final RegistryObject<Block> PINE_WALL_HANGING_SIGN = BLOCKS.register("pine_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), ModWoodTypes.PINE));

    public static final RegistryObject<Block> DICE_BLOCK = BLOCKS.register("dice_block",
            () -> new DiceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable()));

    public static final RegistryObject<Block> PINE_SAPLING = registerBlock("pine_sapling",
            () -> new SaplingBlock(new PineTreeGrower(), BlockBehaviour.Properties.copy(Blocks.BIRCH_SAPLING)));

    public static final RegistryObject<Block> MOD_PORTAL = registerBlock("mod_portal",
            () -> new ModPortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable().noOcclusion().noCollission()));

    public static final RegistryObject<Block> RED_GRASS = registerBlock("red_grass",
            () -> new RedGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> RED_WHEAT_CROP = registerBlock("red_wheat_crop",
            () -> new RedWheatCropBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)));
    public static final RegistryObject<Block> RUBY_FLOWER = registerBlock("ruby_flower",
            () -> new RubyFlowerBlock(BlockBehaviour.Properties.copy(Blocks.DANDELION)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> CRYSTAL_SHARD = registerBlock("crystal_shard",
            () -> new CrystalShardBlock(BlockBehaviour.Properties.copy(Blocks.DANDELION)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GLASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> RUBY_BUSH = registerBlock("ruby_bush",
            () -> new RubyBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> DARK_RED_GRASS = registerBlock("dark_red_grass",
            () -> new DarkRedGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> RUBY_MUSHROOM = BLOCKS.register("ruby_mushroom",
            () -> new RubyMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> RUBY_LOG = registerBlock("ruby_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_LEAVES = registerBlock("ruby_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .strength(0.2f)
                    .sound(SoundType.GRASS)
                    .noOcclusion()));
    public static final RegistryObject<Block> RUBY_SAPLING = registerBlock("ruby_sapling",
            () -> new RubySaplingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XYZ)));
    public static final RegistryObject<Block> STRIPPED_RUBY_LOG = registerBlock("stripped_ruby_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_PLANKS = registerBlock("ruby_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0f, 3.0f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_STAIRS = registerBlock("ruby_stairs",
            () -> new StairBlock(() -> ModBlocks.RUBY_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> RUBY_SLAB = registerBlock("ruby_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> RUBY_WOOD = registerBlock("ruby_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> STRIPPED_RUBY_WOOD = registerBlock("stripped_ruby_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_BUTTON = registerBlock("ruby_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)
                    .strength(0.5f)
                    .sound(SoundType.WOOD)
                    .noCollission(), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> RUBY_PRESSURE_PLATE = registerBlock("ruby_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)
                            .strength(0.5f)
                            .sound(SoundType.WOOD),
                    BlockSetType.OAK));
    public static final RegistryObject<Block> RUBY_FENCE = registerBlock("ruby_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)
                    .strength(2.0f, 3.0f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_FENCE_GATE = registerBlock("ruby_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)
                    .strength(2.0f, 3.0f)
                    .sound(SoundType.WOOD), WoodType.OAK));
    public static final RegistryObject<Block> RUBY_DOOR = registerBlock("ruby_door",
            () -> new DoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)
                            .sound(SoundType.WOOD),
                    BlockSetType.OAK
            ));

    public static final RegistryObject<Block> RUBY_TRAPDOOR = registerBlock("ruby_trapdoor",
            () -> new TrapDoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)
                            .sound(SoundType.WOOD),
                    BlockSetType.OAK
            ));
    public static final RegistryObject<Block> RUBY_SIGN = BLOCKS.register("ruby_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN),
                    ModWoodTypes.RUBY));

    public static final RegistryObject<Block> RUBY_WALL_SIGN = BLOCKS.register("ruby_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN),
                    ModWoodTypes.RUBY));

    public static final RegistryObject<Block> RUBY_HANGING_SIGN = BLOCKS.register("ruby_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN),
                    ModWoodTypes.RUBY));

    public static final RegistryObject<Block> RUBY_WALL_HANGING_SIGN = BLOCKS.register("ruby_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN),
                    ModWoodTypes.RUBY));






    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
