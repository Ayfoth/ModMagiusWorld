package com.magius.world.mod.recipe;

import com.magius.world.mod.corruption.CorruptionHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class CorruptionRecipe extends ShapedRecipe {

    private final int requiredCorruption;

    public CorruptionRecipe(ShapedRecipe baseRecipe, int requiredCorruption) {
        super(
                baseRecipe.getId(),
                baseRecipe.getGroup(),
                baseRecipe.category(),
                baseRecipe.getWidth(),
                baseRecipe.getHeight(),
                baseRecipe.getIngredients(),
                baseRecipe.getResultItem(null)
        );
        this.requiredCorruption = requiredCorruption;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {

        if (!super.matches(inv, level))
            return false;

        if (level.isClientSide)
            return true;

        Player player = null;

        if (inv instanceof net.minecraft.world.inventory.AbstractContainerMenu menu) {
            // fallback (rarement utilisé)
            return true;
        }

        // ⚠️ Forge ne donne pas directement le player ici
        // On va utiliser une solution simple via last player interaction
        // (suffisant pour V1)

        return true; // TEMPORAIRE
    }

    public boolean canCraft(Player player) {
        return CorruptionHelper.getCorruption(player) >= requiredCorruption;
    }
}
