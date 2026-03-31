package com.magius.world.mod.entity.client;


import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.client.RubyBossModel;
import com.magius.world.mod.entity.custom.RubyBossEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RubyBossRenderer extends MobRenderer<RubyBossEntity, RubyBossModel<RubyBossEntity>> {

    public RubyBossRenderer(EntityRendererProvider.Context context) {
        super(context, new RubyBossModel<>(context.bakeLayer(RubyBossModel.LAYER_LOCATION)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(RubyBossEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "textures/entity/ruby_boss.png");
    }
}
