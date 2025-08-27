package com.xiaoyue.celestial_overhaul.content;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectOverrideHandler {

	public static double strength = -1;
	private static boolean dirty = true;

	public static void check() {
		if (!dirty) return;
		dirty = false;
		MobEffects.DAMAGE_BOOST.getAttributeModifiers().clear();
		strength = COModConfig.COMMON.strengthEffectDamageBonus.get();
		var attr = Attributes.ATTACK_DAMAGE;
		var uuid = "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9";
		var op = strength < 0 ? AttributeModifier.Operation.ADDITION : AttributeModifier.Operation.MULTIPLY_BASE;
		MobEffects.DAMAGE_BOOST.addAttributeModifier(attr, uuid, 0, op);
	}

	public synchronized static void reloadEffectAttributes() {
		dirty = true;
	}

}
