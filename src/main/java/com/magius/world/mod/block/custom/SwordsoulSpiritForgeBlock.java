package com.magius.world.mod.block.custom;

import com.magius.world.mod.block.entity.SwordsoulSanctuaryCoreBlockEntity;
import com.magius.world.mod.block.entity.SwordsoulSpiritForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class SwordsoulSpiritForgeBlock
        extends BaseEntityBlock {

    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    public SwordsoulSpiritForgeBlock(
            BlockBehaviour.Properties properties
    ) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(
                                FACING,
                                Direction.NORTH
                        )
        );
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {
        return defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection()
                                .getOpposite()
                );
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (!level.isClientSide()) {

            SwordsoulSanctuaryCoreBlockEntity core =
                    findSanctuaryCore(
                            level,
                            pos,
                            32
                    );

            if (core == null) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cAucun Cœur du Sanctuaire Swordsoul n'a été trouvé."
                        )
                );

                return InteractionResult.CONSUME;
            }

            if (!core.isSpiritForgeUnlocked()) {
                player.sendSystemMessage(
                        Component.literal(
                                "§cLa Forge spirituelle doit d'abord être activée depuis le terminal."
                        )
                );

                return InteractionResult.CONSUME;
            }

            BlockEntity forgeBlockEntity =
                    level.getBlockEntity(pos);

            if (player instanceof ServerPlayer serverPlayer
                    && forgeBlockEntity instanceof
                    SwordsoulSpiritForgeBlockEntity forge) {

                NetworkHooks.openScreen(
                        serverPlayer,
                        forge,
                        pos
                );

            } else {
                player.sendSystemMessage(
                        Component.literal(
                                "§cImpossible d'ouvrir la Forge spirituelle."
                        )
                );
            }
        }

        return InteractionResult.sidedSuccess(
                level.isClientSide()
        );
    }

    private static SwordsoulSanctuaryCoreBlockEntity
    findSanctuaryCore(
            Level level,
            BlockPos origin,
            int radius
    ) {
        BlockPos.MutableBlockPos mutable =
                new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    mutable.set(
                            origin.getX() + x,
                            origin.getY() + y,
                            origin.getZ() + z
                    );

                    BlockEntity blockEntity =
                            level.getBlockEntity(mutable);

                    if (blockEntity instanceof
                            SwordsoulSanctuaryCoreBlockEntity core) {
                        return core;
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new SwordsoulSpiritForgeBlockEntity(
                pos,
                state
        );
    }

    @Override
    public RenderShape getRenderShape(
            BlockState state
    ) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }
}