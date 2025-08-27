package com.xiaoyue.celestial_overhaul.api;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class WeaponBlockedEvent extends Event {

	private final ItemStack stack;
	private final LivingEntity entity;
	private final DamageSource source;
	private float damageModifier;

	public WeaponBlockedEvent(ItemStack stack, LivingEntity entity, DamageSource source, float damageModifier) {
		this.stack = stack;
		this.entity = entity;
		this.source = source;
		this.damageModifier = damageModifier;
	}

	public ItemStack getStack() {
		return stack;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public DamageSource getSource() {
		return source;
	}

	public float getDamageModifier() {
		return damageModifier;
	}

	public void setDamageModifier(float damageModifier) {
		this.damageModifier = damageModifier;
	}
}
