package com.magius.world.mod.block.entity;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.recipe.FireFounderieRecipe;

import com.magius.world.mod.screen.FireFounderieMenu;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;



public class FireFounderieBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private UUID lastPlayerUUID;
    public void setLastPlayer(Player player) {
        if (player != null) {
            this.lastPlayerUUID = player.getUUID();
            setChanged();
        }
    }

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 500;

    public FireFounderieBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FIRE_FOUNDERIE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FireFounderieBlockEntity.this.progress;
                    case 1 -> FireFounderieBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FireFounderieBlockEntity.this.progress = pValue;
                    case 1 -> FireFounderieBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    public ItemStack getRenderStack(){
        if (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()){
            return itemHandler.getStackInSlot(INPUT_SLOT);
        } else {
            return itemHandler.getStackInSlot(OUTPUT_SLOT);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.magiusworldmod.fire_founderie");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FireFounderieMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("gem_polishing_station.progress", progress);
        if (lastPlayerUUID != null) {
            pTag.putUUID("last_player_uuid", lastPlayerUUID);
        }

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_polishing_station.progress");if (pTag.hasUUID("last_player_uuid")) {
            lastPlayerUUID = pTag.getUUID("last_player_uuid");
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<FireFounderieRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty() || level == null || level.isClientSide) {
            return;
        }

        ItemStack result = recipe.get().getResultItem(level.registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(
                OUTPUT_SLOT,
                new ItemStack(
                        result.getItem(),
                        this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()
                )
        );

        grantAdvancementForResult(result);
    }
    private void grantAdvancementForResult(ItemStack result) {
        if (level == null || level.isClientSide || lastPlayerUUID == null || level.getServer() == null) {
            return;
        }

        ServerPlayer player = level.getServer().getPlayerList().getPlayer(lastPlayerUUID);
        if (player == null) {
            return;
        }

        ResourceLocation advancementId = null;

        if (result.is(Items.TORCH)) {
            awardAdvancement(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/torch"));
            awardCriterion(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/master"), "crafted_torch");
        } else if (result.is(Items.REDSTONE_TORCH)) {
            awardAdvancement(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/redstone_torch"));
            awardCriterion(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/master"), "crafted_redstone_torch");
        } else if (result.is(Items.MAGMA_BLOCK)) {
            awardAdvancement(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/magma_block"));
            awardCriterion(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/master"), "crafted_magma_block");
        } else if (result.is(Items.LAVA_BUCKET)) {
            awardAdvancement(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/lava_bucket"));
            awardCriterion(player, ResourceLocation.fromNamespaceAndPath("magiusworldmod", "founderie/master"), "crafted_lava_bucket");
        }

        if (advancementId == null) return;

        Advancement advancement = player.server.getAdvancements().getAdvancement(advancementId);
        if (advancement == null) return;

        var progress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criterion);
        }
    }
    private void awardAdvancement(ServerPlayer player, ResourceLocation advancementId) {
        Advancement advancement = player.server.getAdvancements().getAdvancement(advancementId);
        if (advancement == null) return;

        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criterion);
        }
    }

    private void awardCriterion(ServerPlayer player, ResourceLocation advancementId, String criterion) {
        Advancement advancement = player.server.getAdvancements().getAdvancement(advancementId);
        if (advancement == null) return;

        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String remainingCriterion : progress.getRemainingCriteria()) {
                if (remainingCriterion.equals(criterion)) {
                    player.getAdvancements().award(advancement, criterion);
                    break;
                }
            }
        }
    }

    private boolean hasRecipe() {
        Optional<FireFounderieRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<FireFounderieRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FireFounderieRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}