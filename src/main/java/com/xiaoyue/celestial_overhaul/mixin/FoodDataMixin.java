package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FoodData.class)
public class FoodDataMixin {

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V", ordinal = 0), method = "tick")
	public float celestial_overhaul$modifyHeal0(float par1, @Local(argsOnly = true) Player pPlayer) {
		Double config = COModConfig.COMMON.fullFoodLevelHealTweak.get();
		if (config >= 0) {
			return par1 * Math.max(1, pPlayer.getMaxHealth() * config.floatValue());
		}
		return par1;
	}

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;heal(F)V", ordinal = 1), method = "tick")
	public float celestial_overhaul$modifyHeal1(float par1, @Local(argsOnly = true) Player pPlayer) {
		Double config = COModConfig.COMMON.fullFoodLevelHealTweak.get();
		if (config >= 0) {
			return par1 * Math.max(1, pPlayer.getMaxHealth() * config.floatValue());
		}
		return par1;
	}
}
