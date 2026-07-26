package com.magius.world.mod.block;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.custom.*;
import com.magius.world.mod.block.echo.*;
import com.magius.world.mod.item.CorruptionTooltipBlockItem;
import com.magius.world.mod.item.ModFoods;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.item.custom.FuelItem;
import com.magius.world.mod.item.echo.WitherMushroomItem;
import com.magius.world.mod.sound.ModSounds;
import com.magius.world.mod.util.ModWoodTypes;
import com.magius.world.mod.worldgen.tree.PineTreeGrower;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MagiusWorldMod.MOD_ID);

    // Mod Echo du Premier

    public static final RegistryObject<Block> NECRO_STONE = registerBlock("necro_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.8f,6.0f)));
    public static final RegistryObject<Block> CORRUPTED_SOIL = registerBlock("corrupted_soil",
            () -> new CorruptedSoilBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).randomTicks()));
    public static final RegistryObject<Block> PURIFYING_CORE = registerBlock("purifying_core",
            () -> new PurifyingCoreBlock(
                    BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                            .strength(2.0f, 6.0f)
                            .lightLevel(state -> 8)
                            .randomTicks()
            ));
    public static final RegistryObject<Block> POLISHED_NECRO_STONE = registerBlock("polished_necro_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> CUT_NECRO_STONE = registerBlock("cut_necro_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> NECRO_STONE_BRICKS = registerBlock("necro_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> CRACKED_NECRO_STONE_BRICKS = registerBlock("cracked_necro_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> BLACK_MOSSY_NECRO_STONE_BRICKS = registerBlock("black_mossy_necro_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> CHISELED_NECRO_STONE_BRICKS = registerBlock("chiseled_necro_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> NECRO_STONE_PILLAR = registerBlock("necro_stone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> NECRO_STONE_STAIRS = registerBlock("necro_stone_stairs",
            () -> new StairBlock(() -> ModBlocks.NECRO_STONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> NECRO_STONE_SLAB = registerBlock("necro_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> NECRO_STONE_WALL = registerBlock("necro_stone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final RegistryObject<Block> CHISELED_NECRO_STONE_STAIRS = registerBlock("chiseled_necro_stone_stairs",
            () -> new StairBlock(() -> ModBlocks.CHISELED_NECRO_STONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> CHISELED_NECRO_STONE_SLAB = registerBlock("chiseled_necro_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> COMPACT_NECRO_STONE = registerBlock("compact_necro_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.5f, 7.0f)));
    public static final RegistryObject<Block> INFUSED_NECRO_STONE = registerBlock("infused_necro_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 4)));
    public static final RegistryObject<Block> UNSTABLE_NECRO_STONE = registerBlock("unstable_necro_stone",
            () -> new UnstableNecroStoneBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.5f, 4.0f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 6)
                    .randomTicks()));
    public static final RegistryObject<Block> LIVING_ROCK = registerBlock("living_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> VEINED_ROCK = registerBlock("veined_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.0f, 6.0f)));
    public static final RegistryObject<Block> BROKEN_ROCK = registerBlock("broken_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.2f, 3.0f)));
    public static final RegistryObject<Block> ENGRAVED_ROCK = registerBlock("engraved_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.0f, 6.0f)));

    public static final RegistryObject<Block> WITHERED_LOG = registerBlock("withered_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .strength(2.0f)));
    public static final RegistryObject<Block> STRIPPED_WITHERED_LOG = registerBlock("stripped_withered_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)
                    .strength(2.0f)));
    public static final RegistryObject<Block> WITHERED_PLANKS = registerBlock("withered_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> VEINED_WITHERED_PLANKS = registerBlock("veined_withered_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> REINFORCED_WITHERED_PLANKS = registerBlock("reinforced_withered_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(3.0f, 6.0f)));
    public static final RegistryObject<Block> WITHERED_STAIRS = registerBlock("withered_stairs",
            () -> new StairBlock(() -> WITHERED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<Block> WITHERED_SLAB = registerBlock("withered_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<Block> WITHERED_FENCE = registerBlock("withered_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<Block> WITHERED_FENCE_GATE = registerBlock("withered_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
    public static final RegistryObject<Block> WITHERED_DOOR = registerBlock("withered_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> WITHERED_TRAPDOOR = registerBlock("withered_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> WITHERED_BUTTON = registerBlock("withered_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> WITHERED_PRESSURE_PLATE = registerBlock("withered_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
    public static final RegistryObject<Block> WITHERED_BEAM = registerBlock("withered_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .strength(2.5f)));
    public static final RegistryObject<Block> CRACKED_WITHERED_BEAM = registerBlock("cracked_withered_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .strength(2.0f)));
    public static final RegistryObject<Block> BLACKENED_LEAVES = registerBlock("blackened_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .strength(0.2F)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()));
    public static final RegistryObject<Block> WITHERED_ROOTS = registerBlock("withered_roots",
            () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER)
                    .strength(0.2F)
                    .sound(SoundType.VINE)
                    .noCollission()
                    .noOcclusion()));
    public static final RegistryObject<Block> WITHER_MUSHROOM_PLANT = BLOCKS.register("wither_mushroom_plant",
            () -> new WitherMushroomPlantBlock(() -> MobEffects.WITHER, 100,
                    BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .randomTicks()
                            .offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> NECROTIC_FLESH_WALL = registerBlock("necrotic_flesh_wall",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(1.5f)
                    .sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COMPACT_FLESH = registerBlock("compact_flesh",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(1.8f)
                    .sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PULSATING_FLESH = registerBlock("pulsating_flesh",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(1.4f)
                    .sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 3)));
    public static final RegistryObject<Block> VEINED_FLESH = registerBlock("veined_flesh",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(1.6f)
                    .sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HARDENED_FLESH = registerBlock("hardened_flesh",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(2.5f, 4.0f)
                    .sound(SoundType.NETHERRACK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EXPOSED_HEART = registerBlock("exposed_heart",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(2.0f)
                    .lightLevel(state -> 8)
                    .sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PROTECTED_HEART = registerBlock("protected_heart",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .strength(4.0f, 6.0f)
                    .lightLevel(state -> 4)
                    .sound(SoundType.SCULK)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ORGANIC_NODE = registerBlock("organic_node",
            () -> new OrganicFleshBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK)
                    .strength(2.2f, 3.0f)
                    .lightLevel(state -> 2)
                    .sound(SoundType.SCULK)
                    .requiresCorrectToolForDrops()));




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
    public static final RegistryObject<Block> RUBY_TILE = registerBlock("ruby_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f)
                    .sound(SoundType.STONE)
            ));
    public static final RegistryObject<Block> RUBY_PILLAR = registerBlock("ruby_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR)
                    .strength(3f)
                    .sound(SoundType.STONE)
            ));
    public static final RegistryObject<Block> RUBY_LAMP = registerBlock("ruby_lamp",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.REDSTONE_LAMP)
                    .lightLevel(state -> 12)
                    .strength(2f)
                    .sound(SoundType.GLASS)
            ));
    public static final RegistryObject<Block> RUBY_BRAZIER = registerBlock("ruby_brazier",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.LANTERN)
                    .strength(2.0f)
                    .lightLevel(state -> 13)
                    .sound(SoundType.LANTERN)
                    .noOcclusion()));

    public static final RegistryObject<Block> CHARRED_RUBY_BEAM = registerBlock("charred_ruby_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                    .strength(2.5f)
                    .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> RUBY_FIRE_CORE = registerBlock("ruby_fire_core",
            () -> new RubyFireCoreBlock(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE)
                    .strength(3f)
                    .lightLevel(state -> 15)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));
    public static final RegistryObject<Block> RUBY_CACHE = registerBlock("ruby_cache",
            () -> new RubyCacheBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.STONE)
                    .noLootTable()));
    public static final RegistryObject<Block> BOSS_RUBY_DOOR = BLOCKS.register("boss_ruby_door",
            () -> new BossRubyDoorBlock(Block.Properties.of()
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()));
    public static final RegistryObject<Block> BOSS_ARENA_TRIGGER = BLOCKS.register("boss_arena_trigger",
            () -> new BossArenaTriggerBlock(Block.Properties.of()
                    .noCollission()
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()));
    public static final RegistryObject<Block> RUBY_ALTAR = BLOCKS.register("ruby_altar",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.LECTERN)));
    public static final RegistryObject<Block> CORRUPTED_LECTERN = BLOCKS.register("corrupted_lectern",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.LECTERN)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
            ));
    public static final RegistryObject<Block> RUBY_PEDESTAL = registerBlock("ruby_pedestal",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)
                    .strength(3.0F)
                    .sound(SoundType.STONE)));











    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {

        int corruptionLevel = getCorruptionLevelForBlock(name);

        ModItems.ITEMS.register(name, () ->
                new CorruptionTooltipBlockItem(
                        block.get(),
                        new Item.Properties(),
                        corruptionLevel
                )
        );
    }
    private static int getCorruptionLevelForBlock(String name) {

        return switch (name) {

            // Niveau 2
            case "necro_stone",
                 "necro_stone_bricks",
                 "infused_necro_stone" -> 2;

            // Niveau 3
            case "unstable_necro_stone",
                 "living_rock",
                 "veined_rock" -> 3;

            // Niveau 4
            case "chiseled_necro_stone_bricks",
                 "engraved_rock" -> 4;

            default -> 0;
        };
    }



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
