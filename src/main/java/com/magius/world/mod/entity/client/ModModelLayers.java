package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation RHINO_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "rhino_layer"), "main");
    public static final ModelLayerLocation PINE_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "boat/pine"), "main");
    public static final ModelLayerLocation PINE_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "chest_boat/pine"), "main");
    public static final ModelLayerLocation RUBY_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "boat/ruby"), "main");

    public static final ModelLayerLocation RUBY_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "chest_boat/ruby"), "main");

}
