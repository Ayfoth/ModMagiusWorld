package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.WitheredHusk;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WitheredHuskRenderer extends MobRenderer<WitheredHusk, HumanoidModel<WitheredHusk>> {

    public WitheredHuskRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.HUSK)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(WitheredHusk entity) {
        return ResourceLocation.fromNamespaceAndPath(
                MagiusWorldMod.MOD_ID,
                "textures/entity/withered_husk.png"
        );
    }
}
