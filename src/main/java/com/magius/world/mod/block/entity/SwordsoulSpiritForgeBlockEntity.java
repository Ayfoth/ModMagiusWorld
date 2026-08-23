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
                                isValidBlade(stack);

                        case TOKEN_SLOT ->
                                isValidSwordsoulToken(stack);

                        case CATALYST_SLOT ->
                                isValidCatalyst(stack);

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

    private boolean isValidSwordsoulToken(ItemStack stack) {
        return stack.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_II.get())
                || stack.is(ModItems.SWORDSOUL_SPIRIT_TOKEN.get())
                || stack.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VI.get())
                || stack.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VIII.get());
    }

    private ItemStack getSynchronizationResult(ItemStack token) {

        if (token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_II.get())) {
            return new ItemStack(
                    ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get()
            );
        }

        if (token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN.get())) {
            return new ItemStack(
                    ModItems.SYNCHRONIZED_SPIRIT_BLADE.get()
            );
        }

        if (token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VI.get())) {
            return new ItemStack(
                    ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get()
            );
        }

        if (token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VIII.get())) {
            return new ItemStack(
                    ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get()
            );
        }

        return ItemStack.EMPTY;
    }

    private boolean isSynchronizedBlade(ItemStack stack) {
        return stack.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get())
                || stack.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE.get())
                || stack.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get())
                || stack.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get());
    }

    private boolean isValidBlade(ItemStack stack) {
        return stack.is(ModItems.BROKEN_SPIRIT_BLADE.get())
                || isSynchronizedBlade(stack);
    }

    private boolean isAttributeSeal(ItemStack stack) {
        return stack.is(ModItems.SWORDSOUL_WATER_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_FIRE_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_WIND_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_EARTH_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_LIGHT_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_DARK_SEAL.get())
                || stack.is(ModItems.SWORDSOUL_DIVINE_SEAL.get());
    }

    private boolean isValidCatalyst(ItemStack stack) {
        return stack.is(ModItems.SWORDSOUL_EMERGENCE_SEAL.get())
                || isAttributeSeal(stack);
    }

    private boolean tokenMatchesBlade(
            ItemStack blade,
            ItemStack token
    ) {
        if (blade.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_VI.get())) {
            return token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_II.get());
        }

        if (blade.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE.get())) {
            return token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN.get());
        }

        if (blade.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_X.get())) {
            return token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VI.get());
        }

        if (blade.is(ModItems.SYNCHRONIZED_SPIRIT_BLADE_XII.get())) {
            return token.is(ModItems.SWORDSOUL_SPIRIT_TOKEN_VIII.get());
        }

        return false;
    }

    private String getAttributeId(ItemStack catalyst) {

        if (catalyst.is(ModItems.SWORDSOUL_WATER_SEAL.get())) {
            return "water";
        }

        if (catalyst.is(ModItems.SWORDSOUL_FIRE_SEAL.get())) {
            return "fire";
        }

        if (catalyst.is(ModItems.SWORDSOUL_WIND_SEAL.get())) {
            return "wind";
        }

        if (catalyst.is(ModItems.SWORDSOUL_EARTH_SEAL.get())) {
            return "earth";
        }

        if (catalyst.is(ModItems.SWORDSOUL_LIGHT_SEAL.get())) {
            return "light";
        }

        if (catalyst.is(ModItems.SWORDSOUL_DARK_SEAL.get())) {
            return "dark";
        }

        if (catalyst.is(ModItems.SWORDSOUL_DIVINE_SEAL.get())) {
            return "divine";
        }

        return "";
    }

    private ItemStack getInfusionResult(
            ItemStack blade,
            ItemStack token,
            ItemStack catalyst
    ) {
        if (!isSynchronizedBlade(blade)
                || !tokenMatchesBlade(blade, token)
                || !isAttributeSeal(catalyst)) {
            return ItemStack.EMPTY;
        }

        String attribute = getAttributeId(catalyst);

        if (attribute.isEmpty()) {
            return ItemStack.EMPTY;
        }

        /*
         * La copie conserve le niveau, les dégâts,
         * les enchantements et le nom personnalisé.
         */
        ItemStack infusedBlade = blade.copy();
        infusedBlade.setCount(1);

        infusedBlade.getOrCreateTag().putString(
                "SwordsoulAttribute",
                attribute
        );

        return infusedBlade;
    }

    public boolean canSynchronize() {

        ItemStack blade =
                itemHandler.getStackInSlot(BLADE_SLOT);

        ItemStack token =
                itemHandler.getStackInSlot(TOKEN_SLOT);

        ItemStack catalyst =
                itemHandler.getStackInSlot(CATALYST_SLOT);

        ItemStack result =
                itemHandler.getStackInSlot(RESULT_SLOT);

        if (!result.isEmpty()) {
            return false;
        }

        /*
         * Recette initiale :
         * lame sans maître + jeton + Émergence.
         */
        boolean baseSynchronization =
                blade.is(ModItems.BROKEN_SPIRIT_BLADE.get())
                        && catalyst.is(
                        ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
                )
                        && !getSynchronizationResult(token).isEmpty();

        /*
         * Recette d'infusion :
         * lame synchronisée + jeton correspondant
         * + sceau d'attribut.
         */
        boolean attributeInfusion =
                !getInfusionResult(
                        blade,
                        token,
                        catalyst
                ).isEmpty();

        return baseSynchronization || attributeInfusion;
    }

    public boolean synchronize() {

        if (!canSynchronize()) {
            return false;
        }

        ItemStack blade =
                itemHandler.getStackInSlot(BLADE_SLOT);

        ItemStack token =
                itemHandler.getStackInSlot(TOKEN_SLOT);

        ItemStack catalyst =
                itemHandler.getStackInSlot(CATALYST_SLOT);

        ItemStack craftingResult;

        if (blade.is(ModItems.BROKEN_SPIRIT_BLADE.get())) {
            craftingResult =
                    getSynchronizationResult(token);
        } else {
            craftingResult =
                    getInfusionResult(
                            blade,
                            token,
                            catalyst
                    );
        }

        if (craftingResult.isEmpty()) {
            return false;
        }

        itemHandler.extractItem(BLADE_SLOT, 1, false);
        itemHandler.extractItem(TOKEN_SLOT, 1, false);
        itemHandler.extractItem(CATALYST_SLOT, 1, false);

        itemHandler.setStackInSlot(
                RESULT_SLOT,
                craftingResult
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