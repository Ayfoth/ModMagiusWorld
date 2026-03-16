package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MagiusWorldMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
                .add(ModBlocks.WITHER_ORE.get()).add(ModBlocks.DEEPSLATE_WITHER_ORE.get()).addTag(Tags.Blocks.ORES);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.WITHER_ORE.get(),
                        ModBlocks.DEEPSLATE_WITHER_ORE.get(),
                       ModBlocks.RUBIS_ORE.get(),
                       ModBlocks.DEEPSLATE_RUBIS_ORE.get(),
                        ModBlocks.NETHER_RUBIS_ORE.get(),
                        ModBlocks.END_STONE_RUBIS_ORE.get(),
                        ModBlocks.RUBIS_BLOCK.get(),
                        ModBlocks.WHITE_LEGENDARY_BLOCK.get())
                ;
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.BLACKWOOD_LOG.get(),
                        ModBlocks.RUBY_DOOR.get(),
                        ModBlocks.RUBY_TRAPDOOR.get());


        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DEEPSLATE_WITHER_ORE.get(),
                        ModBlocks.WITHER_ORE.get(),
                      ModBlocks.RUBIS_ORE.get(),
                        ModBlocks.DEEPSLATE_RUBIS_ORE.get(),
                        ModBlocks.RUBIS_BLOCK.get())
                ;

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.NETHER_RUBIS_ORE.get())
                ;

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.END_STONE_RUBIS_ORE.get())
        ;

        this.tag(ModTags.Blocks.NEEDS_SAPPHIRE_TOOL)
                .add(ModBlocks.SOUND_BLOCK.get());
        this.tag(ModTags.Blocks.NEEDS_WITHER_TOOL)
                .add(ModBlocks.SOUND_BLOCK.get());
        this.tag(ModTags.Blocks.NEEDS_RUBIS_TOOL)
                .add(ModBlocks.BLACKWOOD_LOG.get());

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.WITHER_FENCE.get(),
                ModBlocks.RUBY_FENCE.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.RUBY_FENCE.get());


        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.WITHER_FENCE_GATE.get(),
                        ModBlocks.RUBY_FENCE_GATE.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.RUBY_FENCE_GATE.get());

        this.tag(BlockTags.WALLS)
                .add(ModBlocks.WITHER_WALL.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.PINE_LOG.get())
                .add(ModBlocks.PINE_WOOD.get())
                .add(ModBlocks.STRIPPED_PINE_LOG.get())
                .add(ModBlocks.STRIPPED_PINE_WOOD.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.PINE_PLANKS.get());

        this.tag(BlockTags.DOORS).add(ModBlocks.RUBY_DOOR.get());
        this.tag(BlockTags.TRAPDOORS).add(ModBlocks.RUBY_TRAPDOOR.get());

        // utile si tu veux qu’elle puisse être ouverte par les villageois, pathfinding, etc.
        this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.RUBY_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.RUBY_TRAPDOOR.get());


    }
}
