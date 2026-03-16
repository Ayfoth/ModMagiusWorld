package com.magius.world.mod.datagen;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ForgeAdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancement()));
    }
    public static class ModAdvancement implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            // progrès racine
            Advancement root = Advancement.Builder.advancement()
                    .display(
                            ModItems.RUBIS.get(),
                            Component.translatable("advancement.magiusworldmod.rubis_title"),
                            Component.translatable("advancement.magiusworldmod.rubis_description"),
                            ResourceLocation.fromNamespaceAndPath("magiusworldmod","textures/gui/advancements/backgrounds/ruby.png"),
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("has_ruby", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS.get()))
                    .save(saver, "magiusworldmod:root");

            // 1 - Obtenir un Rubis
            Advancement ruby = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModItems.RUBIS.get(),
                            Component.translatable("advancement.magiusworldmod.ruby_title"),
                            Component.translatable("advancement.magiusworldmod.rubis_goal"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "has_ruby",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS.get())
                    )

                    .save(saver, "magiusworldmod:get_ruby");
            // 2 - Armure complète de Rubis
           Advancement rubyEquipment =  Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            ModItems.RUBIS_CHESTPLATE.get(),
                            Component.translatable("advancement.magiusworldmod.fire_protection_title"),
                            Component.translatable("advancement.magiusworldmod.fire_protection_goal"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion("helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_HELMET.get()))
                    .addCriterion("chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_CHESTPLATE.get()))
                    .addCriterion("leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_LEGGINGS.get()))
                    .addCriterion("boots", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_BOOTS.get()))
                    .save(saver, "magiusworldmod:ruby_armor");
            // 3 - La Fonderie
            Advancement founderieRoot = Advancement.Builder.advancement()
                    .parent(ruby) // remplace "ruby" si ton parent a un autre nom
                    .display(
                            ModBlocks.FIRE_FOUNDERIE.get(),
                            Component.translatable("advancement.magiusworldmod.founderie.root.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.root.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "has_fire_founderie",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.FIRE_FOUNDERIE.get())
                    )
                    .save(saver, "magiusworldmod:founderie/root");

            Advancement.Builder.advancement()
                    .parent(founderieRoot)
                    .display(
                            Items.TORCH,
                            Component.translatable("advancement.magiusworldmod.founderie.torch.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.torch.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "crafted_in_founderie",
                            new ImpossibleTrigger.TriggerInstance()
                    )
                    .save(saver, "magiusworldmod:founderie/torch");

            Advancement.Builder.advancement()
                    .parent(founderieRoot)
                    .display(
                            Items.REDSTONE_TORCH,
                            Component.translatable("advancement.magiusworldmod.founderie.redstone_torch.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.redstone_torch.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "crafted_in_founderie",
                           new ImpossibleTrigger.TriggerInstance()
                    )
                    .save(saver, "magiusworldmod:founderie/redstone_torch");

            Advancement.Builder.advancement()
                    .parent(founderieRoot)
                    .display(
                            Items.MAGMA_BLOCK,
                            Component.translatable("advancement.magiusworldmod.founderie.magma_block.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.magma_block.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "crafted_in_founderie",
                            new ImpossibleTrigger.TriggerInstance()
                    )
                    .save(saver, "magiusworldmod:founderie/magma_block");

            Advancement.Builder.advancement()
                    .parent(founderieRoot)
                    .display(
                            Items.LAVA_BUCKET,
                            Component.translatable("advancement.magiusworldmod.founderie.lava_bucket.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.lava_bucket.description"),
                            null,
                            FrameType.GOAL,
                            true, true, false
                    )
                    .addCriterion(
                            "crafted_in_founderie",
                            new ImpossibleTrigger.TriggerInstance()
                    )
                    .save(saver, "magiusworldmod:founderie/lava_bucket");
            Advancement.Builder.advancement()
                    .parent(founderieRoot)
                    .display(
                            Items.BLAZE_POWDER,
                            Component.translatable("advancement.magiusworldmod.founderie.master.title"),
                            Component.translatable("advancement.magiusworldmod.founderie.master.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("crafted_torch", new ImpossibleTrigger.TriggerInstance())
                    .addCriterion("crafted_redstone_torch", new ImpossibleTrigger.TriggerInstance())
                    .addCriterion("crafted_magma_block", new ImpossibleTrigger.TriggerInstance())
                    .addCriterion("crafted_lava_bucket", new ImpossibleTrigger.TriggerInstance())
                    .requirements(RequirementsStrategy.AND)
                    .save(saver, "magiusworldmod:founderie/master");
            // 4 - Outils Rubis
            Map<String, RegistryObject<Item>> rubyTools = Map.of(
                    "pickaxe", ModItems.RUBIS_PICKAXE,
                    "axe", ModItems.RUBIS_AXE,
                    "shovel", ModItems.RUBIS_SHOVEL,
                    "sword", ModItems.RUBIS_SWORD
            );

            Advancement rubisToolsRoot = Advancement.Builder.advancement()
                    .parent(ruby) // remplace si ton parent a un autre nom
                    .display(
                            ModItems.RUBIS_PICKAXE.get(),
                            Component.translatable("advancement.magiusworldmod.rubis_tools.root.title"),
                            Component.translatable("advancement.magiusworldmod.rubis_tools.root.description"),
                            null,
                            FrameType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            "has_rubis",
                            InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS.get())
                    )
                    .save(saver, "magiusworldmod:rubis_tools/root");

            for (Map.Entry<String, RegistryObject<Item>> entry : rubyTools.entrySet()) {
                String toolName = entry.getKey();
                RegistryObject<Item> toolItem = entry.getValue();

                Advancement.Builder.advancement()
                        .parent(rubisToolsRoot)
                        .display(
                                toolItem.get(),
                                Component.translatable("advancement.magiusworldmod.rubis_tools." + toolName + ".title"),
                                Component.translatable("advancement.magiusworldmod.rubis_tools." + toolName + ".description"),
                                null,
                                FrameType.TASK,
                                true, true, false
                        )
                        .addCriterion(
                                "has_tool",
                                InventoryChangeTrigger.TriggerInstance.hasItems(toolItem.get())
                        )
                        .save(saver, "magiusworldmod:rubis_tools/" + toolName);
            }
            Advancement.Builder.advancement()
                    .parent(rubisToolsRoot)
                    .display(
                            ModItems.RUBIS_SWORD.get(),
                            Component.translatable("advancement.magiusworldmod.rubis_tools.master.title"),
                            Component.translatable("advancement.magiusworldmod.rubis_tools.master.description"),
                            null,
                            FrameType.CHALLENGE,
                            true, true, false
                    )
                    .addCriterion("has_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_PICKAXE.get()))
                    .addCriterion("has_axe", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_AXE.get()))
                    .addCriterion("has_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_SHOVEL.get()))
                    .addCriterion("has_sword", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.RUBIS_SWORD.get()))
                    .requirements(RequirementsStrategy.AND)
                    .save(saver, "magiusworldmod:rubis_tools/master");
        }


    }
}
