package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.custom.CornCropBlock;
import com.magius.world.mod.block.custom.RedWheatCropBlock;
import com.magius.world.mod.block.custom.StrawberryCropBlock;
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

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MagiusWorldMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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
        simpleBlockWithItem(ModBlocks.RUBY_BRAZIER.get(), cubeAll(ModBlocks.RUBY_BRAZIER.get()));

        axisBlock((RotatedPillarBlock) ModBlocks.CHARRED_RUBY_BEAM.get(),
                modLoc("block/charred_ruby_beam_side"),
                modLoc("block/charred_ruby_beam_top"));
        simpleBlockItem(ModBlocks.CHARRED_RUBY_BEAM.get(),
                models().cubeColumn("charred_ruby_beam",
                        modLoc("block/charred_ruby_beam_side"),
                        modLoc("block/charred_ruby_beam_top")));
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
}
