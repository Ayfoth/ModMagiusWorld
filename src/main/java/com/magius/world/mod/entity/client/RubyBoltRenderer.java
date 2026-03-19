package com.magius.world.mod.entity.client;

import com.magius.world.mod.entity.custom.RubyBoltEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class RubyBoltRenderer extends ThrownItemRenderer<RubyBoltEntity> {
    public RubyBoltRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0F, true);
    }
}