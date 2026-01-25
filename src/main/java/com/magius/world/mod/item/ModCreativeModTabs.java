package com.magius.world.mod.item;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
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
                        pOutput.accept(ModBlocks.SAPPHIRE_BLOCK.get());
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
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> BLOCK_MAGIUS = CREATIVE_MOD_TABS.register("block_magius",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WITHER_BLOCK.get()))
                    .title(Component.translatable("creativetab.block_magius"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.WITHER_BLOCK.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
