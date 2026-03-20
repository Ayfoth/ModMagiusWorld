package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.entity.ModBlockEntities;
import com.magius.world.mod.block.entity.renderer.FireFounderieBlockEntityRenderer;
import com.magius.world.mod.block.entity.renderer.GemPolishingBlockEntityRenderer;
import com.magius.world.mod.entity.ModEntities;
import com.magius.world.mod.entity.client.ModModelLayers;
import com.magius.world.mod.entity.client.RhinoModel;
import com.magius.world.mod.entity.client.RubyBoarModel;
import com.magius.world.mod.entity.client.RubyWispModel;
import com.magius.world.mod.entity.custom.RubyBoarEntity;
import com.magius.world.mod.entity.custom.RubyWispEntity;
import com.magius.world.mod.particle.ModParticles;
import com.magius.world.mod.particle.custom.CrystalFragmentParticle;
import com.magius.world.mod.util.ModWoodTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(ModWoodTypes.PINE);
            Sheets.addWoodType(ModWoodTypes.RUBY);
        });
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RHINO_LAYER, RhinoModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PINE_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.PINE_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.RUBY_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayers.RUBY_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(RubyBoarModel.LAYER_LOCATION, RubyBoarModel::createBodyLayer);
        event.registerLayerDefinition(RubyWispModel.LAYER_LOCATION, RubyWispModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.GEM_POLISHING_BE.get(), GemPolishingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIRE_FOUNDERIE_BE.get(), FireFounderieBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

}
