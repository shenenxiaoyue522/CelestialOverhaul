package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.data.COTagGen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MobEffect.class)
public class MobEffectMixin {

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V", ordinal = 0), method = "applyEffectTick")
	public float celestial_overhaul$modify(float pHealAmount, @Local(argsOnly = true) LivingEntity entity) {
		Double config = COModConfig.COMMON.regenerationEffectTweak.get();
		if (config >= 0) {
			return pHealAmount * Math.max(1f, entity.getMaxHealth() * config.floatValue());
		}
		return pHealAmount;
	}

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 0), index = 1, method = "applyEffectTick")
	public float celestial_overhaul$modifyPoison(float pAmount, @Local(argsOnly = true) LivingEntity entity) {
		Double config = COModConfig.COMMON.poisonEffectDamageTweak.get();
		if (config >= 0 && COTagGen.allow(entity)) {
			return pAmount * Math.max(1f, entity.getHealth() * config.floatValue());
		}
		return pAmount;
	}

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 1), index = 1, method = "applyEffectTick")
	public float celestial_overhaul$modifyWither(float pAmount, @Local(argsOnly = true) LivingEntity entity) {
		Double config = COModConfig.COMMON.witherEffectDamageTweak.get();
		if (config >= 0 && COTagGen.allow(entity)) {
			return pAmount * Math.max(1f, entity.getMaxHealth() * config.floatValue());
		}
		return pAmount;
	}
}
