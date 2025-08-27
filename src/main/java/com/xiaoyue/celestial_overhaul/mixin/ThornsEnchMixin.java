package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.data.COTagGen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "doPostHurt")
	public boolean celestial_overhaul$doPostHurt(Entity instance, DamageSource pSource, float pAmount, Operation<Boolean> original) {
		Double config = COModConfig.COMMON.thornsEnchantmentTweak.get();
		if (config >= 0 && instance instanceof LivingEntity entity && COTagGen.allow(entity)) {
			return original.call(instance, pSource, pAmount * Math.max(1, entity.getMaxHealth() * config.floatValue()));
		}
		return original.call(instance, pSource, pAmount);
	}
}
