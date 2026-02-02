package com.xiaoyue.celestial_overhaul.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface CanBlockItem {

	default float getHurtModifier(ItemStack stack, LivingEntity entity, DamageSource source) {
		return 0.5f;
	}

	default int getItemDamaged(ItemStack stack) {
		return 3;
	}

	default int getBlockTime(ItemStack stack) {
		return 72000;
	}

}
