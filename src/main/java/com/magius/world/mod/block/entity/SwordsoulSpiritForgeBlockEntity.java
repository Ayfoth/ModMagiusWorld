package com.magius.world.mod.block.entity;

import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.screen.SwordsoulSpiritForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwordsoulSpiritForgeBlockEntity
        extends BlockEntity
        implements MenuProvider {

    public static final int BLADE_SLOT = 0;
    public static final int TOKEN_SLOT = 1;
    public static final int CATALYST_SLOT = 2;
    public static final int RESULT_SLOT = 3;

    private final ItemStackHandler itemHandler =
            new ItemStackHandler(4) {

                @Override
                public boolean isItemValid(
                        int slot,
                        @NotNull ItemStack stack
                ) {
                    return switch (slot) {

                        case BLADE_SLOT ->
                                stack.is(
                                        ModItems.BROKEN_SPIRIT_BLADE.get()
                                );

                        case TOKEN_SLOT ->
                                stack.is(
                                        ModItems.SWORDSOUL_SPIRIT_TOKEN.get()
                                );

                        case CATALYST_SLOT ->
                                stack.is(
                                        ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
                                );

                        case RESULT_SLOT -> false;

                        default -> false;
                    };
                }

                @Override
                protected void onContentsChanged(int slot) {
                    setChanged();
                }
            };

    private LazyOptional<IItemHandler> lazyItemHandler =
            LazyOptional.empty();

    public SwordsoulSpiritForgeBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.SWORDSOUL_SPIRIT_FORGE_BE.get(),
                pos,
                state
        );
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(
                "container.magiusworldmod.swordsoul_spirit_forge"
        );
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(
            int containerId,
            Inventory inventory,
            Player player
    ) {
        return new SwordsoulSpiritForgeMenu(
                containerId,
                inventory,
                this
        );
    }

    @Override
    public void onLoad() {
        super.onLoad();

        lazyItemHandler =
                LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public <T> LazyOptional<T> getCapability(
            Capability<T> capability,
            @Nullable Direction side
    ) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(
                capability,
                side
        );
    }

    public boolean canSynchronize() {

        ItemStack blade =
                itemHandler.getStackInSlot(
                        BLADE_SLOT
                );

        ItemStack token =
                itemHandler.getStackInSlot(
                        TOKEN_SLOT
                );

        ItemStack catalyst =
                itemHandler.getStackInSlot(
                        CATALYST_SLOT
                );

        ItemStack result =
                itemHandler.getStackInSlot(
                        RESULT_SLOT
                );

        return blade.is(
                ModItems.BROKEN_SPIRIT_BLADE.get()
        )
                && token.is(
                ModItems.SWORDSOUL_SPIRIT_TOKEN.get()
        )
                && catalyst.is(
                ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
        )
                && result.isEmpty();
    }

    public boolean synchronize() {

        if (!canSynchronize()) {
            return false;
        }

        /*
         * Consommation d'un exemplaire
         * de chaque ingrédient.
         */
        itemHandler.extractItem(
                BLADE_SLOT,
                1,
                false
        );

        itemHandler.extractItem(
                TOKEN_SLOT,
                1,
                false
        );

        itemHandler.extractItem(
                CATALYST_SLOT,
                1,
                false
        );

        /*
         * Création de la lame synchronisée.
         */
        itemHandler.setStackInSlot(
                RESULT_SLOT,
                new ItemStack(
                        ModItems.SYNCHRONIZED_SPIRIT_BLADE.get()
                )
        );

        setChanged();

        if (level != null) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    3
            );
        }

        return true;
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag
    ) {
        tag.put(
                "Inventory",
                itemHandler.serializeNBT()
        );

        super.saveAdditional(tag);
    }

    @Override
    public void load(
            CompoundTag tag
    ) {
        super.load(tag);

        itemHandler.deserializeNBT(
                tag.getCompound("Inventory")
        );

        /*
         * Migration des anciennes forges qui possédaient
         * seulement trois emplacements.
         */
        if (itemHandler.getSlots() == 3) {

            ItemStack savedBlade =
                    itemHandler.getStackInSlot(
                            BLADE_SLOT
                    ).copy();

            ItemStack savedToken =
                    itemHandler.getStackInSlot(
                            TOKEN_SLOT
                    ).copy();

            /*
             * L'ancien emplacement 2 était le résultat.
             */
            ItemStack savedResult =
                    itemHandler.getStackInSlot(2).copy();

            itemHandler.setSize(4);

            itemHandler.setStackInSlot(
                    BLADE_SLOT,
                    savedBlade
            );

            itemHandler.setStackInSlot(
                    TOKEN_SLOT,
                    savedToken
            );

            itemHandler.setStackInSlot(
                    RESULT_SLOT,
                    savedResult
            );
        }
    }
}