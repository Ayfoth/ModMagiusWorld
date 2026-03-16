package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MagiusWorldMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() != ToolActions.AXE_STRIP) {
            return;
        }

        BlockState state = event.getState();

        if (state.is(ModBlocks.RUBY_LOG.get())) {
            event.setFinalState(ModBlocks.STRIPPED_RUBY_LOG.get()
                    .defaultBlockState()
                    .setValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS,
                            state.getValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS)));
        }

        if (state.is(ModBlocks.RUBY_WOOD.get())) {
            event.setFinalState(ModBlocks.STRIPPED_RUBY_WOOD.get()
                    .defaultBlockState()
                    .setValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS,
                            state.getValue(net.minecraft.world.level.block.RotatedPillarBlock.AXIS)));
        }
    }
    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent.AdvancementEarnEvent event) {

        if (event.getEntity() instanceof ServerPlayer player) {

            ResourceLocation id = event.getAdvancement().getId();

            if (!id.getNamespace().equals("magiusworldmod")) return;

            switch (id.getPath()) {

                case "get_ruby" -> {
                    player.addItem(new ItemStack(ModBlocks.RUBIS_BLOCK.get()));
                }
                case "ruby_armor" -> {
                    player.addItem(new ItemStack(Blocks.NETHERITE_BLOCK));
                    player.giveExperiencePoints(500);
                }
                case "founderie/master" -> {
                    player.giveExperiencePoints(500);
                    player.addEffect(new MobEffectInstance(
                            MobEffects.CONDUIT_POWER, 20 * 120, 0
                    ));
                }
                case "rubis_tools/master" -> {
                    player.addItem(new ItemStack(ModBlocks.FIRE_FOUNDERIE.get()));
                }

               // case "ruby_shovel" -> {
                  //  player.giveExperiencePoints(200);
               // }



                //case "use_foundry" -> {
                 //   player.addEffect(new MobEffectInstance(
                  //          MobEffects.LUCK,
                   //         20 * 120,
                   //         0
                  //  ));
                }

            }
        }
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Sheep sheep)) {
            return;
        }

        ItemStack stack = event.getItemStack();
        if (!stack.is(ModItems.RED_WHEAT.get())) {
            return;
        }

        if (sheep.getColor() != DyeColor.WHITE) {
            return;
        }

        if (!event.getLevel().isClientSide) {
            sheep.setColor(DyeColor.RED);

            if (!event.getEntity().getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
        // Niveaux
        // 1 -> Novice || 2 XP || 0.05 Multiplicateur
        // 2 -> Apprenti || 10 || 0.05
        // 3 -> Compagnon || 20 || 0.05
        // 4 -> Expert || 15 || 0.2
        // 5 -> Maitre || 30 || 0.2
        if (event.getType() == VillagerProfession.ARMORER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,20,0.05f
            ));
        }
        if (event.getType() == VillagerProfession.WEAPONSMITH){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,15,0.2f
            ));
        }
        if (event.getType() == VillagerProfession.TOOLSMITH){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.RUBIS.get(),1),
                    12,15,0.2f
            ));
        }

        if (event.getType() == VillagerProfession.FARMER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.STRAWBERRY.get(), 12),
                    10, 8, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.CORN.get(), 6),
                    5, 9, 0.035f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 8),
                    new ItemStack(ModItems.CORN_SEEDS.get(), 2),
                    2, 12, 0.075f));
        }
        if (event.getType() == VillagerProfession.LIBRARIAN){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
           ItemStack enchantedBook =  EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.THORNS, 2));

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    enchantedBook,
                    2, 8, 0.02f));
        }
        if (event.getType() == ModVillagers.SOUND_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModBlocks.SOUND_BLOCK.get(), 1),
                    16, 8, 0.02f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModBlocks.WITHER_ORE.get(), 2),
                    5, 12, 0.02f));
        }

    }
    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event){
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 12),
                new ItemStack(ModItems.WITHER_BOOTS.get(), 1),
                3, 2, 0.2f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(ModItems.METAL_DETECTOR.get(), 1),
                2, 12, 0.15f));
    }

}
