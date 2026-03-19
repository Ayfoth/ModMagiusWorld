package com.magius.world.mod;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.entity.ModBlockEntities;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.client.ModBoatRenderer;
import com.magius.world.mod.entity.client.RhinoRenderer;
import com.magius.world.mod.entity.custom.ModChestBoatEntity;
import com.magius.world.mod.item.ModCreativeModTabs;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.loot.ModLootModifiers;
import com.magius.world.mod.recipe.ModRecipes;
import com.magius.world.mod.screen.FireFounderieScreen;
import com.magius.world.mod.screen.GemPolishingStationScreen;
import com.magius.world.mod.screen.ModMenuTypes;
import com.magius.world.mod.sound.ModSounds;
import com.magius.world.mod.util.ModWoodTypes;
import com.magius.world.mod.villager.ModVillagers;
import com.magius.world.mod.worldgen.biome.surface.ModSurfaceRules;
import com.magius.world.mod.worldgen.biome.surface.ModTerrablender;
import com.magius.world.mod.worldgen.feature.ModFeatures;
import com.magius.world.mod.worldgen.tree.ModFoliagePlacer;
import com.magius.world.mod.worldgen.tree.ModTrunkPlacerTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraft.client.renderer.entity.SheepRenderer;
import com.magius.world.mod.entity.client.RubyBoarRenderer;
import com.magius.world.mod.entity.client.RubyWispRenderer;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagiusWorldMod.MOD_ID)
public class MagiusWorldMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "magiusworldmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public MagiusWorldMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModVillagers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModTrunkPlacerTypes.register(modEventBus);
        ModFoliagePlacer.register(modEventBus);
       ModTerrablender.registerBiomes();
        ModFeatures.FEATURES.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)    {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);

          //  SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
        });

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RAW_SAPPHIRE);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            Sheets.addWoodType(ModWoodTypes.PINE);
            Sheets.addWoodType(ModWoodTypes.RUBY);
            EntityRenderers.register(ModEntities.RHINO.get(), RhinoRenderer::new);
            EntityRenderers.register(ModEntities.RUBY_SHEEP.get(), SheepRenderer::new);
            EntityRenderers.register(ModEntities.MOD_BOAT.get(), pContext -> new ModBoatRenderer(pContext,
                    false));
            EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext,
                    true));
            EntityRenderers.register(ModEntities.RUBY_BOAR.get(), RubyBoarRenderer::new);
            EntityRenderers.register(ModEntities.RUBY_WISP.get(), RubyWispRenderer::new);

            EntityRenderers.register(ModEntities.DICE_PROJECTILE.get(), ThrownItemRenderer::new);

            MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), GemPolishingStationScreen::new);
            MenuScreens.register(ModMenuTypes.FIRE_FOUNDERIE_MENU.get(), FireFounderieScreen::new);
        }
    }
}
