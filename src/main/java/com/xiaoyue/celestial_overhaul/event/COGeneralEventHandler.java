package com.xiaoyue.celestial_overhaul.event;

import com.xiaoyue.celestial_overhaul.content.EffectOverrideHandler;
import com.xiaoyue.celestial_overhaul.content.OverhaulUtils;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.data.COTagGen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.xiaoyue.celestial_overhaul.CelestialOverhaul.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class COGeneralEventHandler {

	@SubscribeEvent
	public static void serverTick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			EffectOverrideHandler.check();
		}
	}

	@SubscribeEvent
	public static void onBlockedHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		if (WeaponBlockHandler.isBlocking(entity)) {
			ItemStack stack = entity.getMainHandItem();
			float modifier = WeaponBlockHandler.getHurtModifier(stack, entity, event.getSource());
			event.setAmount(event.getAmount() * modifier);
			stack.hurtAndBreak(WeaponBlockHandler.getItemDamaged(stack), entity, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		}
	}

	@SubscribeEvent
	public static void onCritHit(CriticalHitEvent event) {
		if (!event.isVanillaCritical() || event.getResult().equals(Event.Result.ALLOW)) return;
		if (!COModConfig.COMMON.canVanillaCritical.get()) {
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		LivingEntity entity = event.getEntity();
		if (source.getEntity() != null || source.getDirectEntity() != null || !COTagGen.allow(entity))
			return;
		if (source.is(DamageTypes.STARVE)) {
			Double config = COModConfig.COMMON.playerHungryDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.FREEZE)) {
			Double config = COModConfig.COMMON.frozenTypeDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.FALL)) {
			Double config = COModConfig.COMMON.entityFallDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL)) {
			Double config = COModConfig.COMMON.entityCrammingDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.DROWN)) {
			Double config = COModConfig.COMMON.entityDrownDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypeTags.IS_FIRE)) {
			Double config = COModConfig.COMMON.fireTypeDamageTweak.get();
			if (config >= 0) {
				event.setAmount(event.getAmount() * Math.max(1, entity.getMaxHealth() * config.floatValue()));
			}
		}
	}

	@SubscribeEvent
	public static void onHurtLiving(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		LivingEntity entity = event.getEntity();
		if (!(source.getEntity() instanceof LivingEntity attacker)) return;
		if (source.getDirectEntity() != null && source.getDirectEntity().equals(attacker)) {
			ItemStack stack = attacker.getMainHandItem();
			Double sharpnessConfig = COModConfig.COMMON.sharpnessEnchantmentBonus.get();
			int sharpnessLv = stack.getEnchantmentLevel(Enchantments.SHARPNESS);
			if (sharpnessLv > 0 && sharpnessConfig >= 0) {
				event.setAmount(event.getAmount() * (1 + sharpnessConfig.floatValue() * sharpnessLv));
			}
			Double killerConfig = COModConfig.COMMON.killerEnchantmentBonus.get();
			if (killerConfig >= 0) {
				int smiteLv = stack.getEnchantmentLevel(Enchantments.SMITE);
				if (smiteLv > 0 && entity.getMobType().equals(MobType.UNDEAD)) {
					event.setAmount(event.getAmount() * (1 + killerConfig.floatValue() * smiteLv));
				}
				int boaLv = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
				if (boaLv > 0 && entity.getMobType().equals(MobType.ARTHROPOD)) {
					event.setAmount(event.getAmount() * (1 + killerConfig.floatValue() * boaLv));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		Player player = event.getEntity();
		if (!OverhaulUtils.canGrassPierce(player, event.getLevel(), event.getPos())) return;
		double distance = Math.max(player.getBlockReach(), player.getEntityReach());
		var filter = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(e -> e != null && e.isPickable() && e instanceof LivingEntity && !(e instanceof FakePlayer) && !OverhaulUtils.getVehicle(player).contains(e));
		EntityHitResult result = OverhaulUtils.rayTraceEntity(player, 1f, distance, filter);
		if (result != null && !event.getLevel().isClientSide()) {
			player.attack(result.getEntity());
			player.resetAttackStrengthTicker();
		}
	}

	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
		if (!COModConfig.COMMON.arrowHitMobSound.get()) return;
		if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
		HitResult result = event.getRayTraceResult();
		if (arrow.getOwner() instanceof Player player && result.getType().equals(HitResult.Type.ENTITY)) {
			EntityHitResult hit = (EntityHitResult) result;
			if (hit.getEntity() instanceof Player) return;
			arrow.playSound(SoundEvents.ARROW_HIT_PLAYER, 1.0F, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
		}
	}
}
