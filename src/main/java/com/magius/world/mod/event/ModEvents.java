package com.magius.world.mod.event;

import com.magius.world.mod.MagiusWorldMod;

import com.magius.world.mod.block.ModBlocks;
import com.magius.world.mod.item.ModItems;
import com.magius.world.mod.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Horse;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
    public static void onHorseDamage(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Horse horse)) return;

        ItemStack armor = horse.getArmor();
        if (armor.getItem() != ModItems.RUBY_HORSE_ARMOR.get()) return;

        // Immunité feu / lave / chaleur
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setCanceled(true);
            return;
        }

        // Résistance explosion
        if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
            event.setAmount(event.getAmount() * 0.25F);
        }
    }

    @SubscribeEvent
    public static void onHorseTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Horse horse)) return;

        ItemStack armor = horse.getArmor();
        if (armor.getItem() != ModItems.RUBY_HORSE_ARMOR.get()) return;

        // Retire le feu visuel
        if (horse.isOnFire()) {
            horse.clearFire();
        }


        // Immunité magma block / dégâts chauds au sol
        if (horse.hurtMarked) {
            horse.fallDistance = 0.0F;
        }

        // Petite amélioration hors lave
        if (horse.isVehicle() && horse.onGround() && !horse.isInLava()) {
            Vec3 motion = horse.getDeltaMovement();
            horse.setDeltaMovement(motion.x * 1.02D, motion.y, motion.z * 1.02D);
        }

        // Mode lave : monture légendaire
        if (horse.isInLava()) {
            Vec3 motion = horse.getDeltaMovement();

            double upward = 0.16D;
            double horizontalBoost = horse.isVehicle() ? 1.70D : 1.35D;

            double x = motion.x * horizontalBoost;
            double z = motion.z * horizontalBoost;

            // petit minimum pour éviter l'effet "trop lourd"
            if (Math.abs(x) < 0.08D) x *= 1.25D;
            if (Math.abs(z) < 0.08D) z *= 1.25D;

            horse.setDeltaMovement(x, upward, z);
            horse.fallDistance = 0.0F;

            // Particules rubis / feu
            if (horse.level().isClientSide) {
                horse.level().addParticle(
                        ParticleTypes.FLAME,
                        horse.getX(),
                        horse.getY() + 0.5,
                        horse.getZ(),
                        0.0D, 0.02D, 0.0D
                );

                horse.level().addParticle(
                        ParticleTypes.SMOKE,
                        horse.getX(),
                        horse.getY() + 0.8,
                        horse.getZ(),
                        0.0D, 0.01D, 0.0D
                );
            }
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
