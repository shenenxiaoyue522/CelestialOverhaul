package com.xiaoyue.celestial_overhaul.mixin;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

	@Inject(at = @At("HEAD"), method = "getDamageAfterMagicAbsorb", cancellable = true)
	private static void celestial_overhaul$getDamageAfterMagicAbsorb(float pDamage, float pEnchantModifiers, CallbackInfoReturnable<Float> cir) {
		Double config = COModConfig.COMMON.protectionEnchantmentTweak.get();
		if (config >= 0) {
			double powed = Math.pow(config.floatValue(), pEnchantModifiers);
			cir.setReturnValue((float) (pDamage * powed));
		}
	}
}
