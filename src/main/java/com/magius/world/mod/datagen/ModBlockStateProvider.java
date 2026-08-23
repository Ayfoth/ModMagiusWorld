package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.custom.CornCropBlock;
import com.magius.world.mod.block.custom.RedWheatCropBlock;
import com.magius.world.mod.block.custom.StrawberryCropBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.magius.world.mod.block.custom.HearthCoreBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MagiusWorldMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // =====================================================
// SWORDSOUL
// =====================================================

        swordsoulSpiritForgeBlock();
        blockWithItem(
                ModBlocks.SWORDSOUL_SPIRIT_FORGE_TERMINAL
        );

        simpleBlock(
                ModBlocks.SWORDSOUL_SANCTUARY_CORE.get()
        );
        simpleBlock(
                ModBlocks.SWORDSOUL_TAIA_MARKER.get(),
                models().getExistingFile(
                        mcLoc("block/packed_ice")
                )
        );
        // Mod Echo
        blockWithItem(ModBlocks.NECRO_STONE);
        blockWithItem(ModBlocks.CORRUPTED_SOIL);
        blockWithItem(ModBlocks.PURIFYING_CORE);
        blockWithItem(ModBlocks.POLISHED_NECRO_STONE);
        blockWithItem(ModBlocks.CUT_NECRO_STONE);
        blockWithItem(ModBlocks.NECRO_STONE_BRICKS);
        blockWithItem(ModBlocks.CRACKED_NECRO_STONE_BRICKS);
        blockWithItem(ModBlocks.BLACK_MOSSY_NECRO_STONE_BRICKS);
        blockWithItem(ModBlocks.CHISELED_NECRO_STONE_BRICKS);
        axisBlock((RotatedPillarBlock) ModBlocks.NECRO_STONE_PILLAR.get(),
                modLoc("block/necro_stone_pillar"),
                modLoc("block/necro_stone_pillar_top"));
        simpleBlockItem(ModBlocks.NECRO_STONE_PILLAR.get(),
                models().cubeColumn("necro_stone_pillar",
                        modLoc("block/necro_stone_pillar"),
                        modLoc("block/necro_stone_pillar_top")));
        stairsBlock((StairBlock) ModBlocks.NECRO_STONE_STAIRS.get(),
                modLoc("block/necro_stone"));
        simpleBlockItem(ModBlocks.NECRO_STONE_STAIRS.get(),
                models().stairs("necro_stone_stairs",
                        modLoc("block/necro_stone"),
                        modLoc("block/necro_stone"),
                        modLoc("block/necro_stone")));
        slabBlock((SlabBlock) ModBlocks.NECRO_STONE_SLAB.get(),
                modLoc("block/necro_stone"),
                modLoc("block/necro_stone"));
        simpleBlockItem(ModBlocks.NECRO_STONE_SLAB.get(),
                models().slab("necro_stone_slab",
                        modLoc("block/necro_stone"),
                        modLoc("block/necro_stone"),
                        modLoc("block/necro_stone")));
        wallBlock((WallBlock) ModBlocks.NECRO_STONE_WALL.get(), blockTexture(ModBlocks.NECRO_STONE.get()));
        simpleBlockItem(ModBlocks.NECRO_STONE_WALL.get(), models().wallInventory("necro_stone_wall", blockTexture(ModBlocks.NECRO_STONE.get())));
        stairsBlock((StairBlock) ModBlocks.CHISELED_NECRO_STONE_STAIRS.get(),
                modLoc("block/chiseled_necro_stone_bricks"));
        simpleBlockItem(ModBlocks.CHISELED_NECRO_STONE_STAIRS.get(),
                models().stairs("chiseled_necro_stone_stairs",
                        modLoc("block/chiseled_necro_stone_bricks"),
                        modLoc("block/chiseled_necro_stone_bricks"),
                        modLoc("block/chiseled_necro_stone_bricks")));
        slabBlock((SlabBlock) ModBlocks.CHISELED_NECRO_STONE_SLAB.get(),
                modLoc("block/chiseled_necro_stone_bricks"),
                modLoc("block/chiseled_necro_stone_bricks"));
        simpleBlockItem(ModBlocks.CHISELED_NECRO_STONE_SLAB.get(),
                models().slab("chiseled_necro_stone_slab",
                        modLoc("block/chiseled_necro_stone_bricks"),
                        modLoc("block/chiseled_necro_stone_bricks"),
                        modLoc("block/chiseled_necro_stone_bricks")));
        blockWithItem(ModBlocks.COMPACT_NECRO_STONE);
        blockWithItem(ModBlocks.INFUSED_NECRO_STONE);
        blockWithItem(ModBlocks.UNSTABLE_NECRO_STONE);
        blockWithItem(ModBlocks.LIVING_ROCK);
        blockWithItem(ModBlocks.VEINED_ROCK);
        blockWithItem(ModBlocks.BROKEN_ROCK);
        blockWithItem(ModBlocks.ENGRAVED_ROCK);
        axisBlock((RotatedPillarBlock) ModBlocks.WITHERED_LOG.get(),
                modLoc("block/withered_log"),
                modLoc("block/withered_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WITHERED_LOG.get(),
                modLoc("block/stripped_withered_log"),
                modLoc("block/stripped_withered_log_top"));
        simpleBlockItem(ModBlocks.WITHERED_LOG.get(),
                models().cubeColumn("withered_log",
                        modLoc("block/withered_log"),
                        modLoc("block/withered_log_top")));
        simpleBlockItem(ModBlocks.STRIPPED_WITHERED_LOG.get(),
                models().cubeColumn("stripped_withered_log",
                        modLoc("block/stripped_withered_log"),
                        modLoc("block/stripped_withered_log_top")));
        blockWithItem(ModBlocks.WITHERED_PLANKS);
        blockWithItem(ModBlocks.VEINED_WITHERED_PLANKS);
        blockWithItem(ModBlocks.REINFORCED_WITHERED_PLANKS);
        stairsBlock((StairBlock) ModBlocks.WITHERED_STAIRS.get(), blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_STAIRS.get(),
                models().stairs("withered_stairs",
                        blockTexture(ModBlocks.WITHERED_PLANKS.get()),
                        blockTexture(ModBlocks.WITHERED_PLANKS.get()),
                        blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        slabBlock((SlabBlock) ModBlocks.WITHERED_SLAB.get(),
                blockTexture(ModBlocks.WITHERED_PLANKS.get()),
                blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_SLAB.get(),
                models().slab("withered_slab",
                        blockTexture(ModBlocks.WITHERED_PLANKS.get()),
                        blockTexture(ModBlocks.WITHERED_PLANKS.get()),
                        blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        fenceBlock((FenceBlock) ModBlocks.WITHERED_FENCE.get(), blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_FENCE.get(),
                models().fenceInventory("withered_fence_inventory", blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        fenceGateBlock((FenceGateBlock) ModBlocks.WITHERED_FENCE_GATE.get(), blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_FENCE_GATE.get(),
                models().fenceGate("withered_fence_gate", blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        doorBlockWithRenderType((DoorBlock) ModBlocks.WITHERED_DOOR.get(),
                modLoc("block/withered_door_bottom"),
                modLoc("block/withered_door_top"),
                "cutout");
        itemModels().basicItem(ModBlocks.WITHERED_DOOR.get().asItem());
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.WITHERED_TRAPDOOR.get(),
                modLoc("block/withered_trapdoor"),
                true,
                "cutout");
        simpleBlockItem(ModBlocks.WITHERED_TRAPDOOR.get(),
                models().trapdoorBottom("withered_trapdoor_bottom", modLoc("block/withered_trapdoor")));
        buttonBlock((ButtonBlock) ModBlocks.WITHERED_BUTTON.get(), blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_BUTTON.get(),
                models().buttonInventory("withered_button_inventory", blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.WITHERED_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WITHERED_PLANKS.get()));
        simpleBlockItem(ModBlocks.WITHERED_PRESSURE_PLATE.get(),
                models().pressurePlate("withered_pressure_plate", blockTexture(ModBlocks.WITHERED_PLANKS.get())));
        axisBlock((RotatedPillarBlock) ModBlocks.WITHERED_BEAM.get(),
                modLoc("block/withered_beam"),
                modLoc("block/withered_beam_top"));
        simpleBlockItem(ModBlocks.WITHERED_BEAM.get(),
                models().cubeColumn("withered_beam",
                        modLoc("block/withered_beam"),
                        modLoc("block/withered_beam_top")));
        axisBlock((RotatedPillarBlock) ModBlocks.CRACKED_WITHERED_BEAM.get(),
                modLoc("block/cracked_withered_beam"),
                modLoc("block/cracked_withered_beam_top"));
        simpleBlockItem(ModBlocks.CRACKED_WITHERED_BEAM.get(),
                models().cubeColumn("cracked_withered_beam",
                        modLoc("block/cracked_withered_beam"),
                        modLoc("block/cracked_withered_beam_top")));
        leavesBlock(ModBlocks.BLACKENED_LEAVES);
        ladderLikeBlock(ModBlocks.WITHERED_ROOTS);
        crossBlock(ModBlocks.WITHER_MUSHROOM_PLANT);
        blockWithItem(ModBlocks.NECROTIC_FLESH_WALL);
        blockWithItem(ModBlocks.COMPACT_FLESH);
        blockWithItem(ModBlocks.PULSATING_FLESH);
        blockWithItem(ModBlocks.VEINED_FLESH);
        blockWithItem(ModBlocks.HARDENED_FLESH);
        blockWithItem(ModBlocks.EXPOSED_HEART);
        blockWithItem(ModBlocks.PROTECTED_HEART);
        blockWithItem(ModBlocks.ORGANIC_NODE);



        blockWithItem(ModBlocks.SOUND_BLOCK);
        blockWithItem(ModBlocks.WITHER_BLOCK);
        blockWithItem(ModBlocks.WITHER_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_WITHER_ORE);
        blockWithItem(ModBlocks.RUBIS_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_RUBIS_ORE);
        blockWithItem(ModBlocks.NETHER_RUBIS_ORE);
        blockWithItem(ModBlocks.END_STONE_RUBIS_ORE);
        blockWithItem(ModBlocks.RUBIS_BLOCK);
        blockWithItem(ModBlocks.BLACKWOOD_LOG);
        blockWithItem(ModBlocks.WHITE_LEGENDARY_BLOCK);


        stairsBlock(((StairBlock) ModBlocks.WITHER_STAIRS.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.WITHER_SLAB.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));

        buttonBlock(((ButtonBlock) ModBlocks.WITHER_BUTTON.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.WITHER_PRESSURE_PLATE.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));

        fenceBlock(((FenceBlock) ModBlocks.WITHER_FENCE.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.WITHER_FENCE_GATE.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));
        wallBlock(((WallBlock) ModBlocks.WITHER_WALL.get()), blockTexture(ModBlocks.WITHER_BLOCK.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.SAPPHIRE_DOOR.get()), modLoc("block/sapphire_door_bottom"), modLoc("block/sapphire_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.SAPPHIRE_TRAPDOOR.get()), modLoc("block/sapphire_trapdoor"), true, "cutout");

        makeStrawberryCrop((CropBlock) ModBlocks.STRAWBERRY_CROP.get(), "strawberry_stage", "strawberry_stage");
        makeCornCrop((CropBlock) ModBlocks.CORN_CROP.get(), "corn_stage_", "corn_stage_");

        simpleBlockWithItem(ModBlocks.CATMINT.get(), models().cross(blockTexture(ModBlocks.CATMINT.get()).getPath(),
                blockTexture(ModBlocks.CATMINT.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_CATMINT.get(), models().singleTexture("potted_catmint", ResourceLocation.fromNamespaceAndPath("minecraft", "flower_pot_cross"), "plant",
                blockTexture(ModBlocks.CATMINT.get())).renderType("cutout"));

        simpleBlockWithItem(ModBlocks.GEM_POLISHING_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/gem_polishing_station")));
        simpleBlockWithItem(ModBlocks.FIRE_FOUNDERIE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/fire_founderie")));

        logBlock(((RotatedPillarBlock) ModBlocks.PINE_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.PINE_WOOD.get()), blockTexture(ModBlocks.PINE_LOG.get()), blockTexture(ModBlocks.PINE_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_PINE_LOG.get()), blockTexture(ModBlocks.STRIPPED_PINE_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "block/stripped_pine_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_PINE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_PINE_LOG.get()),
                blockTexture(ModBlocks.PINE_LOG.get()));

        blockItem(ModBlocks.PINE_LOG);

        blockItem(ModBlocks.PINE_WOOD);
        blockItem(ModBlocks.STRIPPED_PINE_LOG);
        blockItem(ModBlocks.STRIPPED_PINE_WOOD);

        blockWithItem(ModBlocks.PINE_PLANKS);

        leavesBlock(ModBlocks.PINE_LEAVES);

        signBlock(((StandingSignBlock) ModBlocks.PINE_SIGN.get()), ((WallSignBlock) ModBlocks.PINE_WALL_SIGN.get()),
                blockTexture(ModBlocks.PINE_PLANKS.get()));
        hangingSignBlock(ModBlocks.PINE_HANGING_SIGN.get(), ModBlocks.PINE_WALL_HANGING_SIGN.get(),
                blockTexture(ModBlocks.PINE_PLANKS.get()));
        saplingBlock(ModBlocks.PINE_SAPLING);

        blockWithItem(ModBlocks.MOD_PORTAL);
        plantBlock(ModBlocks.RED_GRASS.get());
        plantBlock(ModBlocks.RUBY_FLOWER.get());
        plantBlock(ModBlocks.CRYSTAL_SHARD.get());
        simpleBlock(ModBlocks.RUBY_BUSH.get(),
                models().cross("ruby_bush", blockTexture(ModBlocks.RUBY_BUSH.get())).renderType("cutout"));
        simpleBlockItem(ModBlocks.RUBY_BUSH.get(),
                models().cross("ruby_bush", blockTexture(ModBlocks.RUBY_BUSH.get())).renderType("cutout"));
        makeRedWheatCrop((CropBlock) ModBlocks.RED_WHEAT_CROP.get(), "redwheat_stage", "redwheat_stage");
        plantBlock(ModBlocks.DARK_RED_GRASS.get());
        plantBlock(ModBlocks.RUBY_MUSHROOM.get());

        axisBlock((RotatedPillarBlock) ModBlocks.RUBY_LOG.get(),
                blockTexture(ModBlocks.RUBY_LOG.get()),
                modLoc("block/ruby_log_top"));

        simpleBlockItem(ModBlocks.RUBY_LOG.get(),
                models().cubeColumn("ruby_log",
                        blockTexture(ModBlocks.RUBY_LOG.get()),
                        modLoc("block/ruby_log_top")));
        simpleBlockWithItem(ModBlocks.RUBY_LEAVES.get(),
                models().cubeAll("ruby_leaves", blockTexture(ModBlocks.RUBY_LEAVES.get())).renderType("cutout"));
        plantBlock(ModBlocks.RUBY_SAPLING.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_RUBY_LOG.get(),
                blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get()),
                modLoc("block/stripped_ruby_log_top"));
        simpleBlockItem(ModBlocks.STRIPPED_RUBY_LOG.get(),
                models().cubeColumn("stripped_ruby_log",
                        blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get()),
                        modLoc("block/stripped_ruby_log_top")));
        simpleBlockWithItem(ModBlocks.RUBY_PLANKS.get(), cubeAll(ModBlocks.RUBY_PLANKS.get()));
        stairsBlock((StairBlock) ModBlocks.RUBY_STAIRS.get(), blockTexture(ModBlocks.RUBY_PLANKS.get()));
        simpleBlockItem(ModBlocks.RUBY_STAIRS.get(),
                models().stairs("ruby_stairs", blockTexture(ModBlocks.RUBY_PLANKS.get()),
                        blockTexture(ModBlocks.RUBY_PLANKS.get()),
                        blockTexture(ModBlocks.RUBY_PLANKS.get())));
        slabBlock((SlabBlock) ModBlocks.RUBY_SLAB.get(),
                blockTexture(ModBlocks.RUBY_PLANKS.get()),
                blockTexture(ModBlocks.RUBY_PLANKS.get()));
        simpleBlockItem(ModBlocks.RUBY_SLAB.get(),
                models().slab("ruby_slab",
                        blockTexture(ModBlocks.RUBY_PLANKS.get()),
                        blockTexture(ModBlocks.RUBY_PLANKS.get()),
                        blockTexture(ModBlocks.RUBY_PLANKS.get())));
        axisBlock((RotatedPillarBlock) ModBlocks.RUBY_WOOD.get(),
                blockTexture(ModBlocks.RUBY_LOG.get()),
                blockTexture(ModBlocks.RUBY_LOG.get()));
        simpleBlockItem(ModBlocks.RUBY_WOOD.get(),
                models().cubeColumn("ruby_wood",
                        blockTexture(ModBlocks.RUBY_LOG.get()),
                        blockTexture(ModBlocks.RUBY_LOG.get())));

        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_RUBY_WOOD.get(),
                blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get()));
        simpleBlockItem(ModBlocks.STRIPPED_RUBY_WOOD.get(),
                models().cubeColumn("stripped_ruby_wood",
                        blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get()),
                        blockTexture(ModBlocks.STRIPPED_RUBY_LOG.get())));
        buttonBlock(((ButtonBlock) ModBlocks.RUBY_BUTTON.get()), blockTexture(ModBlocks.RUBY_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.RUBY_PRESSURE_PLATE.get()),
                blockTexture(ModBlocks.RUBY_PLANKS.get()));

        fenceBlock((FenceBlock) ModBlocks.RUBY_FENCE.get(), blockTexture(ModBlocks.RUBY_PLANKS.get()));
        simpleBlockItem(ModBlocks.RUBY_FENCE.get(),
                models().fenceInventory("ruby_fence_inventory", blockTexture(ModBlocks.RUBY_PLANKS.get())));
        fenceGateBlock((FenceGateBlock) ModBlocks.RUBY_FENCE_GATE.get(), blockTexture(ModBlocks.RUBY_PLANKS.get()));
        simpleBlockItem(ModBlocks.RUBY_FENCE_GATE.get(),
                models().fenceGate("ruby_fence_gate", blockTexture(ModBlocks.RUBY_PLANKS.get())));
        rubyDoorBlock(ModBlocks.RUBY_DOOR, modLoc("block/ruby_door_bottom"), modLoc("block/ruby_door_top"));
        rubyTrapdoorBlock(ModBlocks.RUBY_TRAPDOOR, modLoc("block/ruby_trapdoor"));
        signBlock(((StandingSignBlock) ModBlocks.RUBY_SIGN.get()),
                ((WallSignBlock) ModBlocks.RUBY_WALL_SIGN.get()),
                blockTexture(ModBlocks.RUBY_PLANKS.get()));

        hangingSignBlock(ModBlocks.RUBY_HANGING_SIGN.get(),
                ModBlocks.RUBY_WALL_HANGING_SIGN.get(),
                blockTexture(ModBlocks.RUBY_PLANKS.get()));
        simpleBlockWithItem(ModBlocks.RUBY_TILE.get(), cubeAll(ModBlocks.RUBY_TILE.get()));

        axisBlock((RotatedPillarBlock) ModBlocks.RUBY_PILLAR.get(),
                modLoc("block/ruby_pillar_side"),
                modLoc("block/ruby_pillar_top"));
        simpleBlockItem(ModBlocks.RUBY_PILLAR.get(),
                models().cubeColumn("ruby_pillar",
                        modLoc("block/ruby_pillar_side"),
                        modLoc("block/ruby_pillar_top")));

        simpleBlockWithItem(ModBlocks.RUBY_LAMP.get(), cubeAll(ModBlocks.RUBY_LAMP.get()));
        simpleBlockWithItem(ModBlocks.RUBY_ALTAR.get(), cubeAll(ModBlocks.RUBY_ALTAR.get()));
        simpleBlockWithItem(ModBlocks.RUBY_BRAZIER.get(), cubeAll(ModBlocks.RUBY_BRAZIER.get()));
        simpleBlockWithItem(ModBlocks.CORRUPTED_LECTERN.get(), cubeAll(ModBlocks.CORRUPTED_LECTERN.get()));
        simpleBlockWithItem(ModBlocks.RUBY_PEDESTAL.get(), cubeAll(ModBlocks.RUBY_PEDESTAL.get()));

        axisBlock((RotatedPillarBlock) ModBlocks.CHARRED_RUBY_BEAM.get(),
                modLoc("block/charred_ruby_beam_side"),
                modLoc("block/charred_ruby_beam_top"));
        simpleBlockItem(ModBlocks.CHARRED_RUBY_BEAM.get(),
                models().cubeColumn("charred_ruby_beam",
                        modLoc("block/charred_ruby_beam_side"),
                        modLoc("block/charred_ruby_beam_top")));
        simpleBlockWithItem(ModBlocks.RUBY_FIRE_CORE.get(),
                cubeAll(ModBlocks.RUBY_FIRE_CORE.get()));
        horizontalBlock(ModBlocks.RUBY_CACHE.get(),
                models().orientable(
                        blockName(ModBlocks.RUBY_CACHE.get()),
                        modLoc("block/ruby_cache_side"),
                        modLoc("block/ruby_cache_front"),
                        modLoc("block/ruby_cache_top")
                )
        );

        simpleBlockItem(ModBlocks.RUBY_CACHE.get(),
                models().getExistingFile(modLoc("block/" + blockName(ModBlocks.RUBY_CACHE.get()))));
        simpleBlockWithItem(ModBlocks.BOSS_RUBY_DOOR.get(), cubeAll(ModBlocks.BOSS_RUBY_DOOR.get()));

        // =====================================================
// DRAGONMAID - CŒUR DU FOYER
// =====================================================

        ModelFile hearthCoreOff = models().cubeAll(
                "hearth_core_off",
                modLoc("block/hearth_core_off")
        );

        ModelFile hearthCoreOn = models().cubeAll(
                "hearth_core_on",
                modLoc("block/hearth_core_on")
        );

        getVariantBuilder(ModBlocks.HEARTH_CORE.get())
                .partialState()
                .with(HearthCoreBlock.ACTIVE, false)
                .modelForState()
                .modelFile(hearthCoreOff)
                .addModel()
                .partialState()
                .with(HearthCoreBlock.ACTIVE, true)
                .modelForState()
                .modelFile(hearthCoreOn)
                .addModel();

        simpleBlockItem(
                ModBlocks.HEARTH_CORE.get(),
                hearthCoreOff
        );
        // =====================================================
// DRAGONMAID - AUTEL D'ALLÉGEANCE
// =====================================================

        blockWithItem(
                ModBlocks.DRAGONMAID_ALLEGIANCE_ALTAR
        );



    }
    private void vineBlock(RegistryObject<Block> block) {
        simpleBlock(block.get(),
                models().withExistingParent(name(block.get()), "minecraft:block/vine")
                        .texture("vine", blockTexture(block.get()))
                        .renderType("cutout"));

        itemModels().withExistingParent(name(block.get()), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }




    private void crossBlock(RegistryObject<Block> block) {
        simpleBlock(block.get(),
                models().cross(name(block.get()), blockTexture(block.get()))
                        .renderType("cutout"));

        simpleBlockItem(block.get(),
                models().withExistingParent(name(block.get()), "item/generated")
                        .texture("layer0", blockTexture(block.get())));
    }
private String blockName(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block).getPath();
}
    private void rubyDoorBlock(RegistryObject<Block> block, ResourceLocation bottom, ResourceLocation top) {
        doorBlockWithRenderType(
                (net.minecraft.world.level.block.DoorBlock) block.get(),
                bottom,
                top,
                "cutout"
        );

        itemModels().basicItem(block.getId());
    }

    private void rubyTrapdoorBlock(RegistryObject<Block> block, ResourceLocation texture) {
        trapdoorBlockWithRenderType(
                (net.minecraft.world.level.block.TrapDoorBlock) block.get(),
                texture,
                true,
                "cutout"
        );
        itemModels().withExistingParent(block.getId().getPath(),
                modLoc("block/" + block.getId().getPath() + "_bottom"));
    }

    private void plantBlock(Block block) {
        simpleBlock(block,
                models().cross(name(block), blockTexture(block)).renderType("cutout"));
        simpleBlockItem(block,
                models().cross(name(block), blockTexture(block)).renderType("cutout"));
    }
    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }
    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }
    private String name(Block block){ return key(block).getPath();}
    private ResourceLocation key(Block block){ return ForgeRegistries.BLOCKS.getKey(block);}


    private void leavesBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
                        ResourceLocation.tryParse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(MagiusWorldMod.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));

    }
    public void makeRedWheatCrop(CropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> redwheatStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] redwheatStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RedWheatCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "block/" + textureName + state.getValue(((RedWheatCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }

    public void makeStrawberryCrop(CropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }
    public void makeCornCrop(CropBlock block, String modelName, String textureName){
        Function<BlockState, ConfiguredModel[]> function = state -> cornStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }
    private ConfiguredModel[] cornStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CornCropBlock) block).getAgeProperty()),
                ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "block/" + textureName + state.getValue(((CornCropBlock) block).getAgeProperty()))).renderType("cutout"));
        return models;
    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void ladderLikeBlock(RegistryObject<Block> block) {
        ModelFile model = models().withExistingParent(name(block.get()), "minecraft:block/ladder")
                .texture("texture", blockTexture(block.get()))
                .renderType("cutout");

        horizontalBlock(block.get(), model);

        itemModels().withExistingParent(name(block.get()), "item/generated")
                .texture("layer0", blockTexture(block.get()));
    }
    private void swordsoulSpiritForgeBlock() {

        ModelFile model =
                models()
                        .withExistingParent(
                                "swordsoul_spirit_forge",
                                mcLoc("block/block")
                        )

                        .texture(
                                "front",
                                modLoc("block/swordsoul_spirit_forge_front")
                        )

                        .texture(
                                "back",
                                modLoc("block/swordsoul_spirit_forge_back")
                        )

                        .texture(
                                "side",
                                modLoc("block/swordsoul_spirit_forge_side")
                        )

                        .texture(
                                "top",
                                modLoc("block/swordsoul_spirit_forge_top")
                        )

                        .texture(
                                "bottom",
                                modLoc("block/swordsoul_spirit_forge_bottom")
                        )

                        .texture(
                                "particle",
                                modLoc("block/swordsoul_spirit_forge_side")
                        )

                        .element()
                        .from(0, 0, 0)
                        .to(16, 16, 16)

                        .face(Direction.NORTH)
                        .texture("#front")
                        .end()

                        .face(Direction.SOUTH)
                        .texture("#back")
                        .end()

                        .face(Direction.WEST)
                        .texture("#side")
                        .end()

                        .face(Direction.EAST)
                        .texture("#side")
                        .end()

                        .face(Direction.UP)
                        .texture("#top")
                        .end()

                        .face(Direction.DOWN)
                        .texture("#bottom")
                        .end()

                        .end();

        horizontalBlock(
                ModBlocks.SWORDSOUL_SPIRIT_FORGE.get(),
                model
        );

        simpleBlockItem(
                ModBlocks.SWORDSOUL_SPIRIT_FORGE.get(),
                model
        );
    }
}
