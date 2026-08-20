package com.magius.world.mod.entity.client;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.entity.dragonmaid.DragonmaidTinkhecEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DragonmaidTinkhecRenderer
        extends MobRenderer<
        DragonmaidTinkhecEntity,
        HumanoidModel<DragonmaidTinkhecEntity>
        > {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/entity/dragonmaid/tinkhec.png"
            );

    public DragonmaidTinkhecRenderer(
            EntityRendererProvider.Context context
    ) {

        super(
                context,
                new HumanoidModel<>(
                        context.bakeLayer(
                                ModelLayers.PLAYER
                        )
                ),
                0.5F
        );
    }

    @Override
    public ResourceLocation getTextureLocation(
            DragonmaidTinkhecEntity entity
    ) {

        return TEXTURE;
    }
}
