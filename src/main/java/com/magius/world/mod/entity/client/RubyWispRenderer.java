package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.custom.RubyWispEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RubyWispRenderer extends MobRenderer<RubyWispEntity, RubyWispModel<RubyWispEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "textures/entity/ruby_wisp.png");

    public RubyWispRenderer(EntityRendererProvider.Context context) {
        super(context, new RubyWispModel<>(context.bakeLayer(RubyWispModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(RubyWispEntity entity) {
        return TEXTURE;
    }
    @Override
    public RenderType getRenderType(RubyWispEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }
    @Override
    protected int getBlockLightLevel(RubyWispEntity entity, net.minecraft.core.BlockPos pos) {
        return 15;
    }
}
