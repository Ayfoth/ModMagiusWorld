package com.magius.world.mod.client.renderer;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.custom.UnchainedSealBlock;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class AruhaWolfRenderer extends WolfRenderer {

    private static final String ARUHA_HOUND_NAME =
            "Molosse d'Aruha";

    private static final ResourceLocation ARUHA_HOUND_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/entity/unchained/ender_wolf.png"
            );

    public AruhaWolfRenderer(
            EntityRendererProvider.Context context
    ) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf wolf) {
        boolean hasServerTag =
                wolf.getPersistentData().getBoolean(
                        UnchainedSealBlock.ARUHA_HOUND_TAG
                );

        boolean hasSyncedName =
                wolf.hasCustomName()
                        && wolf.getCustomName() != null
                        && ARUHA_HOUND_NAME.equals(
                                wolf.getCustomName().getString()
                        );

        if (hasServerTag || hasSyncedName) {
            return ARUHA_HOUND_TEXTURE;
        }

        return super.getTextureLocation(wolf);
    }
}
