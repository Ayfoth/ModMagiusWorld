package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModFrenchLangProvider extends LanguageProvider {
    public ModFrenchLangProvider(PackOutput output) {
        super(output, MagiusWorldMod.MOD_ID, "fr_fr");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CORRUPTION_TESTER.get(), "Testeur de corruption");
        add(ModItems.ESSENCE_WITHER.get(), "Essence de Wither");

        add(ModBlocks.NECRO_STONE.get(), "Pierre nécrosée");
    }
}
