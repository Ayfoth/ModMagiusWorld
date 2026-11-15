package com.magius.world.mod.entity.ancientgear;

import com.magius.world.mod.clan.quest.ancientgear.AncientGearSecondQuest;
import com.magius.world.mod.clan.quest.ancientgear.AncientGearThirdQuest;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.manager.QuestSyncManager;
import com.magius.world.mod.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class AncientGearAutomatonEntity extends AncientGearEntity implements Merchant {
    private UUID builder;
    private boolean repaired;
    private MerchantOffers offers;

    @Nullable
    private Player tradingPlayer;

    private int merchantXp;
    public AncientGearAutomatonEntity(EntityType<? extends AncientGearAutomatonEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected String getAncientGearName() {
        return "Premier automate de Geartown";
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public void setBuilder(UUID builder) {
        this.builder = builder;
    }

    public UUID getBuilderId() {
        return builder;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        var questDataOptional = QuestManager.get(player).resolve();

        QuestStatus thirdQuestStatus =
                questDataOptional.isPresent()
                        ? QuestManager.getStatus(
                                questDataOptional.get(),
                                AncientGearThirdQuest.ID
                        )
                        : QuestStatus.NOT_STARTED;

        boolean sagaCompleted =
                thirdQuestStatus == QuestStatus.COMPLETED
                        || thirdQuestStatus == QuestStatus.REWARDED;

        if (repaired && sagaCompleted) {
            if (!level().isClientSide) {
                setTradingPlayer(player);
                openTradingScreen(
                        player,
                        Component.literal("Marchand de Geartown"),
                        1
                );
            }

            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (hand != InteractionHand.MAIN_HAND || !player.getItemInHand(hand).is(Items.IRON_INGOT)) {
            return InteractionResult.PASS;
        }
        if (level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (repaired || builder == null || !builder.equals(player.getUUID())
                || !(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.CONSUME;
        }
        if (questDataOptional.isEmpty()
                || !QuestManager.completeQuest(questDataOptional.get(), AncientGearSecondQuest.ID)) {
            return InteractionResult.CONSUME;
        }
        repaired = true;
        if (!player.getAbilities().instabuild) {
            player.getItemInHand(hand).shrink(1);
        }
        QuestSyncManager.sync(serverPlayer);
        player.sendSystemMessage(Component.literal(
                "§6L'automate est réparé ! Retournez voir l'émissaire mécaniste."
        ));
        return InteractionResult.CONSUME;
    }

    private MerchantOffers createOffers() {
        MerchantOffers merchantOffers = new MerchantOffers();

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 2),
                new ItemStack(ModItems.ANCIENT_GEAR_COG.get(), 4),
                12,
                2,
                0.05F
        ));

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 3),
                new ItemStack(ModItems.ANCIENT_GEAR_AXLE.get(), 2),
                10,
                3,
                0.05F
        ));

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 6),
                new ItemStack(ModItems.ANCIENT_GEAR_POWER_CELL.get(), 2),
                8,
                5,
                0.05F
        ));

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 8),
                new ItemStack(ModItems.ANCIENT_GEAR_CORE.get()),
                6,
                6,
                0.05F
        ));

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 12),
                new ItemStack(ModItems.ANCIENT_GEAR_COMMAND_CORE.get()),
                4,
                8,
                0.05F
        ));

        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(ModItems.ANCIENT_GEAR_AUTOMATON_KIT.get()),
                2,
                12,
                0.05F
        ));

        return merchantOffers;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (offers == null) {
            offers = createOffers();
        }
        return offers;
    }

    @Override
    public void overrideOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        merchantXp += offer.getXp();
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        if (!level().isClientSide && ambientSoundTime > -getAmbientSoundInterval() + 20) {
            ambientSoundTime = -getAmbientSoundInterval();
            playSound(getNotifyTradeSound(), getSoundVolume(), getVoicePitch());
        }
    }

    @Override
    public int getVillagerXp() {
        return merchantXp;
    }

    @Override
    public void overrideXp(int xp) {
        merchantXp = xp;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.IRON_GOLEM_REPAIR;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Repaired", repaired);
        tag.putInt("MerchantXp", merchantXp);
        if (offers != null) {
            tag.put("Offers", offers.createTag());
        }
        if (builder != null) {
            tag.putUUID("Builder", builder);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        repaired = tag.getBoolean("Repaired");
        merchantXp = tag.getInt("MerchantXp");
        if (tag.contains("Offers", 10)) {
            offers = new MerchantOffers(tag.getCompound("Offers"));
        }
        builder = tag.hasUUID("Builder") ? tag.getUUID("Builder") : null;
    }
}
