package com.magius.world.mod.item;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier SAPPHIRE = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25,
                    ModTags.Blocks.NEEDS_SAPPHIRE_TOOL, () -> Ingredient.of(ModItems.SAPPHIRE.get())),
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "sapphire"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier WITHER = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25,
                    ModTags.Blocks.NEEDS_WITHER_TOOL, () -> Ingredient.of(ModItems.WITHER.get())),
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "wither"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier RUBIS = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25,
                    ModTags.Blocks.NEEDS_RUBIS_TOOL, () -> Ingredient.of(ModItems.RUBIS.get())),
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "rubis"), List.of(Tiers.NETHERITE), List.of());
}
