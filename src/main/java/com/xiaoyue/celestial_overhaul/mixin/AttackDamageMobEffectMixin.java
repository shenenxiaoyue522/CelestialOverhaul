package com.xiaoyue.celestial_overhaul.mixin;

import com.xiaoyue.celestial_overhaul.content.EffectOverrideHandler;
import net.minecraft.world.effect.AttackDamageMobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AttackDamageMobEffect.class)
public class AttackDamageMobEffectMixin {

	@Inject(at = @At("HEAD"), method = "getAttributeModifierValue", cancellable = true)
	public void celestial_overhaul$modify(int amp, AttributeModifier mod, CallbackInfoReturnable<Double> cir) {
		AttackDamageMobEffect self = (AttackDamageMobEffect) (Object) this;
		if (self == MobEffects.DAMAGE_BOOST && EffectOverrideHandler.strength >= 0) {
			cir.setReturnValue((1 + amp) * EffectOverrideHandler.strength);
		}
	}

}
