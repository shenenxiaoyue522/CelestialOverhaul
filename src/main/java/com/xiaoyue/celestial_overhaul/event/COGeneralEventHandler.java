package com.xiaoyue.celestial_overhaul.event;

import com.xiaoyue.celestial_overhaul.content.EffectOverrideHandler;
import com.xiaoyue.celestial_overhaul.content.OverhaulUtils;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.data.COTagGen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import static com.xiaoyue.celestial_overhaul.CelestialOverhaul.MODID;

@EventBusSubscriber(modid = MODID)
public class COGeneralEventHandler {

	@SubscribeEvent
	public static void serverTick(ServerTickEvent.Post event) {
		EffectOverrideHandler.check();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingHeal(LivingHealEvent event) {
		Double config = COModConfig.SERVER.maxHealAmountTweak.get();
		if (config > 0) {
			float min = Math.min(event.getEntity().getMaxHealth() * config.floatValue(), event.getAmount());
			event.setAmount(min);
		}
	}

	@SubscribeEvent
	public static void onBlockedHurt(LivingIncomingDamageEvent event) {
		LivingEntity entity = event.getEntity();
		if (WeaponBlockHandler.isBlocking(entity)) {
			ItemStack stack = entity.getMainHandItem();
			float modifier = WeaponBlockHandler.getHurtModifier(stack, entity, event.getSource());
			event.setAmount(event.getAmount() * modifier);
			stack.hurtAndBreak(WeaponBlockHandler.getItemDamaged(stack), entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
		}
	}

	@SubscribeEvent
	public static void onCritHit(CriticalHitEvent event) {
        if (event.isVanillaCritical()) {
            if (!COModConfig.SERVER.canVanillaCritical.get()) {
                event.setCriticalHit(false);
				event.setDisableSweep(true);
            }
        }
    }

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent.Pre event) {
		DamageSource source = event.getSource();
		LivingEntity entity = event.getEntity();
		if (source.getEntity() != null || source.getDirectEntity() != null || !COTagGen.allow(entity))
			return;
		if (source.is(DamageTypes.STARVE)) {
			Double config = COModConfig.SERVER.playerHungryDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.FREEZE)) {
			Double config = COModConfig.SERVER.frozenTypeDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.FALL)) {
			Double config = COModConfig.SERVER.entityFallDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL)) {
			Double config = COModConfig.SERVER.entityCrammingDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypes.DROWN)) {
			Double config = COModConfig.SERVER.entityDrownDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1f, entity.getMaxHealth() * config.floatValue()));
			}
		}
		if (source.is(DamageTypeTags.IS_FIRE)) {
			Double config = COModConfig.SERVER.fireTypeDamageTweak.get();
			if (config >= 0) {
				event.setNewDamage(event.getOriginalDamage() * Math.max(1, entity.getMaxHealth() * config.floatValue()));
			}
		}
	}

	@SubscribeEvent
	public static void onClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		Player player = event.getEntity();
		if (!OverhaulUtils.canGrassPierce(player, event.getLevel(), event.getPos())) return;
		double blockReach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
		double entityReach = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
		double distance = Math.max(blockReach, entityReach);
		var filter = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(e -> e != null && e.isPickable() && e instanceof LivingEntity && !(e instanceof FakePlayer) && !OverhaulUtils.getVehicle(player).contains(e));
		EntityHitResult result = OverhaulUtils.rayTraceEntity(player, 1f, distance, filter);
		if (result != null && !event.getLevel().isClientSide()) {
			player.attack(result.getEntity());
			player.resetAttackStrengthTicker();
		}
	}

	@SubscribeEvent
	public static void onArrowImpact(ProjectileImpactEvent event) {
		if (!COModConfig.SERVER.arrowHitMobSound.get()) return;
		if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;
		HitResult result = event.getRayTraceResult();
		if (arrow.getOwner() instanceof Player player && result.getType().equals(HitResult.Type.ENTITY)) {
			EntityHitResult hit = (EntityHitResult) result;
			if (hit.getEntity() instanceof Player) return;
			arrow.playSound(SoundEvents.ARROW_HIT_PLAYER, 1.0F, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
		}
	}
}
