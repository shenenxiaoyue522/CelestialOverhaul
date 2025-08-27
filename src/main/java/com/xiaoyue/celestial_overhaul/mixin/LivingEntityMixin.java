package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), method = "getDamageAfterMagicAbsorb")
	public float celestial_overhaul$getDamageAfterMagicAbsorb(float a, float b, Operation<Float> original, @Local(argsOnly = true) float pDamageAmount) {
		LivingEntity entity = (LivingEntity) (Object) this;
		Double config = COModConfig.COMMON.resistanceEffectTweak.get();
		if (config >= 0 && entity.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
			int lv = entity.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1;
			double powed = Math.pow(config.floatValue(), lv);
			return (float) (pDamageAmount * powed);
		}
		return original.call(a, b);
	}
}
