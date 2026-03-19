package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.RubyBoarEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RubyBoarRenderer extends MobRenderer<RubyBoarEntity, RubyBoarModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "textures/entity/ruby_boar.png");

    public RubyBoarRenderer(EntityRendererProvider.Context context) {
        super(context, new RubyBoarModel(context.bakeLayer(RubyBoarModel.LAYER_LOCATION)), 0.9f);
    }

    @Override
    public ResourceLocation getTextureLocation(RubyBoarEntity entity) {
        return TEXTURE;
    }
}
