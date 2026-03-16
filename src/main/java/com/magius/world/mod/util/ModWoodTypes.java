package com.magius.world.mod.util;

import com.magius.world.mod.MagiusWorldMod;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType PINE = WoodType.register(new WoodType(MagiusWorldMod.MOD_ID + ":pine",
            BlockSetType.OAK));
    public static final WoodType RUBY = WoodType.register(
            new WoodType(MagiusWorldMod.MOD_ID + ":ruby", BlockSetType.OAK)
    );

}
