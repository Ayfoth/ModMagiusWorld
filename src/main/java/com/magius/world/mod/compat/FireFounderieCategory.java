package com.magius.world.mod.compat;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.recipe.FireFounderieRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FireFounderieCategory implements IRecipeCategory<FireFounderieRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "fire_founderie");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID,
            "textures/gui/gem_polishing_station_gui.png");
    public static final RecipeType<FireFounderieRecipe> FIRE_FOUNDERIE_TYPE =
            new RecipeType<>(UID, FireFounderieRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FireFounderieCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FIRE_FOUNDERIE.get()));
    }


    @Override
    public RecipeType<FireFounderieRecipe> getRecipeType() {
        return FIRE_FOUNDERIE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.magiusworldmod.fire_founderie");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FireFounderieRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,80,11).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT,80,59).addItemStack(recipe.getResultItem(null));
    }
}
