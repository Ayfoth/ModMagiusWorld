package com.magius.world.mod.compat;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.recipe.FireFounderieRecipe;
import com.magius.world.mod.recipe.GemPolishingRecipe;
import com.magius.world.mod.screen.FireFounderieScreen;
import com.magius.world.mod.screen.GemPolishingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIMagiusWorldModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
       registration.addRecipeCategories(new GemPolishingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FireFounderieCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<GemPolishingRecipe> polishingRecipes = recipeManager.getAllRecipesFor(GemPolishingRecipe.Type.INSTANCE);
        registration.addRecipes(GemPolishingCategory.GEM_POLISHING_TYPE, polishingRecipes);
        List<FireFounderieRecipe> founderieRecipes = recipeManager.getAllRecipesFor(FireFounderieRecipe.Type.INSTANCE);
        registration.addRecipes(FireFounderieCategory.FIRE_FOUNDERIE_TYPE, founderieRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
       registration.addRecipeClickArea(GemPolishingStationScreen.class,60,30,20,30,
               GemPolishingCategory.GEM_POLISHING_TYPE);
       registration.addRecipeClickArea(FireFounderieScreen.class,60,30,20,30,
               FireFounderieCategory.FIRE_FOUNDERIE_TYPE);
    }
}
