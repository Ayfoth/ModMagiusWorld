package com.magius.world.mod.block.custom;

import com.magius.world.mod.clan.quest.ancientgear.AncientGearThirdQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class AncientGearPowerReactorBlock extends Block {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public AncientGearPowerReactorBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        if (state.getValue(ACTIVE)) {
            serverPlayer.displayClientMessage(
                    Component.literal("§bLe cœur énergétique de Geartown fonctionne."), true
            );
            return InteractionResult.CONSUME;
        }

        var questData = QuestManager.get(serverPlayer).resolve();
        if (questData.isEmpty()
                || QuestManager.getStatus(questData.get(), AncientGearThirdQuest.ID)
                != QuestStatus.IN_PROGRESS) {
            serverPlayer.displayClientMessage(
                    Component.literal("§7Le réacteur ne contient plus aucune source d’énergie."), true
            );
            return InteractionResult.CONSUME;
        }

        if (!player.getItemInHand(hand).is(ModItems.ANCIENT_GEAR_REACTOR_CORE.get())) {
            serverPlayer.displayClientMessage(
                    Component.literal("§eIl manque un cœur de réacteur antique."), true
            );
            return InteractionResult.CONSUME;
        }

        level.setBlock(pos, state.setValue(ACTIVE, true), Block.UPDATE_ALL);
        if (!player.getAbilities().instabuild) {
            player.getItemInHand(hand).shrink(1);
        }

        if (QuestManager.completeQuest(questData.get(), AncientGearThirdQuest.ID)) {
            QuestSyncManager.sync(serverPlayer);
            serverPlayer.sendSystemMessage(Component.literal(
                    "§bLe réacteur de Geartown reprend vie ! Retournez voir l'émissaire mécaniste."
            ));
        }

        return InteractionResult.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
