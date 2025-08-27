package com.xiaoyue.celestial_overhaul.mixin;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchMixin {

	@Inject(at = @At("HEAD"), method = "getDamageBonus", cancellable = true)
	public void celestial_overhaul$getDamageBonus(int pLevel, MobType pCreatureType, CallbackInfoReturnable<Float> cir) {
		Enchantment enchantment = (Enchantment) (Object) this;
		Double sharpnessConfig = COModConfig.COMMON.sharpnessEnchantmentBonus.get();
		if (enchantment.equals(Enchantments.SHARPNESS) && sharpnessConfig >= 0) {
			cir.setReturnValue(0f);
		}
		Double killerConfig = COModConfig.COMMON.killerEnchantmentBonus.get();
		if (enchantment.equals(Enchantments.SMITE) || enchantment.equals(Enchantments.BANE_OF_ARTHROPODS)) {
			if (killerConfig >= 0) {
				cir.setReturnValue(0f);
			}
		}
	}
}
