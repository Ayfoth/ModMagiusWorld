package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.echo.TempestBlaze;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TempestBlazeRenderer extends MobRenderer<TempestBlaze, BlazeModel<TempestBlaze>> {

    public TempestBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new BlazeModel<>(context.bakeLayer(ModelLayers.BLAZE)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TempestBlaze entity) {
        return ResourceLocation.fromNamespaceAndPath(
                MagiusWorldMod.MOD_ID,
                "textures/entity/tempest_blaze.png"
        );
    }
}