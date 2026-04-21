package com.magius.world.mod.datagen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnglishLangProvider extends LanguageProvider {
    public ModEnglishLangProvider(PackOutput output) {
        super(output, MagiusWorldMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CORRUPTION_TESTER.get(), "Corruption Tester");
        add(ModItems.ESSENCE_WITHER.get(), "Wither Essence");

        add(ModBlocks.NECRO_STONE.get(), "Necro Stone");
    }
}
