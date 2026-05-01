package com.magius.world.mod.item.echo;



import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.util.ModTags;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptedPickaxeItem extends PickaxeItem {

    public CorruptedPickaxeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float baseSpeed = super.getDestroySpeed(stack, state);

        if (state.is(ModTags.Blocks.FAST_CORRUPTED_PICKAXE_BLOCKS)) {
            return baseSpeed * 2.5f;
        }

        return baseSpeed;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (state.is(ModTags.Blocks.MINEABLE_WITH_CORRUPTED_PICKAXE)) {
            return true;
        }

        return super.isCorrectToolForDrops(stack, state);
    }
}
