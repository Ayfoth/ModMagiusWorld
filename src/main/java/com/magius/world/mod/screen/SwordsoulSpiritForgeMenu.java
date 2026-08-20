package com.magius.world.mod.screen;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.block.entity.SwordsoulSpiritForgeBlockEntity;
import com.magius.world.mod.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class SwordsoulSpiritForgeMenu
        extends AbstractContainerMenu {

    private static final int PLAYER_SLOT_COUNT = 36;
    private static final int FORGE_FIRST_SLOT = 36;
    private static final int FORGE_SLOT_COUNT = 4;

    public final SwordsoulSpiritForgeBlockEntity blockEntity;
    private final Level level;

    public SwordsoulSpiritForgeMenu(
            int containerId,
            Inventory inventory,
            FriendlyByteBuf extraData
    ) {
        this(
                containerId,
                inventory,
                inventory.player.level().getBlockEntity(
                        extraData.readBlockPos()
                )
        );
    }

    public SwordsoulSpiritForgeMenu(
            int containerId,
            Inventory inventory,
            BlockEntity blockEntity
    ) {
        super(
                ModMenuTypes.SWORDSOUL_SPIRIT_FORGE_MENU.get(),
                containerId
        );

        this.blockEntity =
                (SwordsoulSpiritForgeBlockEntity) blockEntity;

        this.level = inventory.player.level();

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity
                .getCapability(ForgeCapabilities.ITEM_HANDLER)
                .ifPresent(handler -> {

                    /*
                     * Lame sans maître.
                     */
                    addSlot(
                            new SlotItemHandler(
                                    handler,
                                    SwordsoulSpiritForgeBlockEntity.BLADE_SLOT,
                                    26,
                                    35
                            )
                    );

                    /*
                     * Jeton spirituel.
                     */
                    addSlot(
                            new SlotItemHandler(
                                    handler,
                                    SwordsoulSpiritForgeBlockEntity.TOKEN_SLOT,
                                    62,
                                    35
                            )
                    );

                    /*
                     * Catalyseur d'Émergence.
                     */
                    addSlot(
                            new SlotItemHandler(
                                    handler,
                                    SwordsoulSpiritForgeBlockEntity.CATALYST_SLOT,
                                    98,
                                    35
                            )
                    );

                    /*
                     * Résultat.
                     */
                    addSlot(
                            new SlotItemHandler(
                                    handler,
                                    SwordsoulSpiritForgeBlockEntity.RESULT_SLOT,
                                    143,
                                    35
                            ) {
                                @Override
                                public boolean mayPlace(ItemStack stack) {
                                    return false;
                                }
                            }
                    );
                });
    }

    @Override
    public boolean clickMenuButton(
            Player player,
            int buttonId
    ) {
        if (buttonId != 0) {
            return false;
        }

        if (level.isClientSide()) {
            return true;
        }

        return blockEntity.synchronize();
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int index
    ) {
        Slot sourceSlot = slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack =
                sourceSlot.getItem();

        ItemStack originalStack =
                sourceStack.copy();

        /*
         * Déplacement depuis l'inventaire du joueur
         * vers la forge.
         */
        if (index < PLAYER_SLOT_COUNT) {

            if (sourceStack.is(
                    ModItems.BROKEN_SPIRIT_BLADE.get()
            )) {
                if (!moveItemStackTo(
                        sourceStack,
                        FORGE_FIRST_SLOT,
                        FORGE_FIRST_SLOT + 1,
                        false
                )) {
                    return ItemStack.EMPTY;
                }

            } else if (sourceStack.is(
                    ModItems.SWORDSOUL_SPIRIT_TOKEN.get()
            )) {
                if (!moveItemStackTo(
                        sourceStack,
                        FORGE_FIRST_SLOT + 1,
                        FORGE_FIRST_SLOT + 2,
                        false
                )) {
                    return ItemStack.EMPTY;
                }

            } else if (sourceStack.is(
                    ModItems.SWORDSOUL_EMERGENCE_SEAL.get()
            )) {
                if (!moveItemStackTo(
                        sourceStack,
                        FORGE_FIRST_SLOT + 2,
                        FORGE_FIRST_SLOT + 3,
                        false
                )) {
                    return ItemStack.EMPTY;
                }

            } else {
                return ItemStack.EMPTY;
            }

            /*
             * Déplacement depuis la forge
             * vers l'inventaire du joueur.
             */
        } else if (index <
                FORGE_FIRST_SLOT + FORGE_SLOT_COUNT) {

            if (!moveItemStackTo(
                    sourceStack,
                    0,
                    PLAYER_SLOT_COUNT,
                    false
            )) {
                return ItemStack.EMPTY;
            }

        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(
                player,
                sourceStack
        );

        return originalStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(
                ContainerLevelAccess.create(
                        level,
                        blockEntity.getBlockPos()
                ),
                player,
                ModBlocks.SWORDSOUL_SPIRIT_FORGE.get()
        );
    }

    private void addPlayerInventory(
            Inventory inventory
    ) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(
                        new Slot(
                                inventory,
                                column + row * 9 + 9,
                                8 + column * 18,
                                84 + row * 18
                        )
                );
            }
        }
    }

    private void addPlayerHotbar(
            Inventory inventory
    ) {
        for (int column = 0; column < 9; column++) {
            addSlot(
                    new Slot(
                            inventory,
                            column,
                            8 + column * 18,
                            142
                    )
            );
        }
    }
}