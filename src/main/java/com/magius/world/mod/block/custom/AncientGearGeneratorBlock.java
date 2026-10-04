package com.magius.world.mod.block.custom;

import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.item.custom.AncientGearAutomatonKitItem;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearFirstQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class AncientGearGeneratorBlock extends Block {
    public static final BooleanProperty HAS_COG = BooleanProperty.create("has_cog");
    public static final BooleanProperty HAS_AXLE = BooleanProperty.create("has_axle");
    public static final BooleanProperty HAS_CORE = BooleanProperty.create("has_core");

    public AncientGearGeneratorBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(HAS_COG, false)
                .setValue(HAS_AXLE, false)
                .setValue(HAS_CORE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_COG, HAS_AXLE, HAS_CORE);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof AncientGearAutomatonKitItem kit) {
            return kit.useOn(new UseOnContext(player, hand, hitResult));
        }
        BooleanProperty part = null;
        String name = "";
        if (held.is(ModItems.ANCIENT_GEAR_COG.get())) {
            part = HAS_COG;
            name = "Rouage antique";
        } else if (held.is(ModItems.ANCIENT_GEAR_AXLE.get())) {
            part = HAS_AXLE;
            name = "Axe antique";
        } else if (held.is(ModItems.ANCIENT_GEAR_CORE.get())) {
            part = HAS_CORE;
            name = "Cœur mécanique";
        }

        if (part == null) {
            if (!level.isClientSide) {
                int remaining = (state.getValue(HAS_COG) ? 0 : 1)
                        + (state.getValue(HAS_AXLE) ? 0 : 1)
                        + (state.getValue(HAS_CORE) ? 0 : 1);
                player.displayClientMessage(Component.literal(remaining == 0
                        ? "§aLe générateur antique est activé. Retournez voir l'émissaire."
                        : "§6Le générateur est inerte. Il manque " + remaining
                                + (remaining == 1 ? " composant antique." : " composants antiques.")), false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (state.getValue(part)) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.literal("§eCe composant est déjà installé."), false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (!level.isClientSide) {
            var questData = QuestManager.get(player).resolve();
            if (questData.isEmpty() || QuestManager.getStatus(questData.get(), AncientGearFirstQuest.ID)
                    != QuestStatus.IN_PROGRESS) {
                player.displayClientMessage(Component.literal("§cCommencez d'abord la quête auprès de l'émissaire."), false);
                return InteractionResult.CONSUME;
            }
            BlockState updated = state.setValue(part, true);
            level.setBlock(pos, updated, Block.UPDATE_ALL);
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            boolean complete = updated.getValue(HAS_COG) && updated.getValue(HAS_AXLE)
                    && updated.getValue(HAS_CORE);
            if (complete && QuestManager.completeQuest(questData.get(), AncientGearFirstQuest.ID)
                    && player instanceof ServerPlayer serverPlayer) {
                QuestSyncManager.sync(serverPlayer);
            }
            player.displayClientMessage(Component.literal(complete
                    ? "§aLe générateur antique reprend vie ! Retournez voir l'émissaire."
                    : "§6" + name + " installé dans le générateur."), false);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
