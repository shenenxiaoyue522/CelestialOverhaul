package com.xiaoyue.celestial_overhaul.content;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectOverrideHandler {

	public static double strength = -1;
	public static double weakness = -1;
	private static boolean dirty = true;

	public static void check() {
		if (!dirty) return;
		dirty = false;
		var attr = Attributes.ATTACK_DAMAGE;

		MobEffects.DAMAGE_BOOST.getAttributeModifiers().clear();
		strength = COModConfig.COMMON.strengthEffectDamageBonus.get();
		var strengthUuid = "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9";
		var strengthOp = strength < 0 ? AttributeModifier.Operation.ADDITION : AttributeModifier.Operation.MULTIPLY_BASE;
		MobEffects.DAMAGE_BOOST.addAttributeModifier(attr, strengthUuid, 0, strengthOp);

		MobEffects.WEAKNESS.getAttributeModifiers().clear();
		weakness = COModConfig.COMMON.weaknessEffectDamageReduce.get();
		var weaknessUuid = "22653B89-116E-49DC-9B6B-9971489B5BE5";
		var weaknessOp = weakness < 0 ? AttributeModifier.Operation.ADDITION : AttributeModifier.Operation.MULTIPLY_BASE;
		MobEffects.WEAKNESS.addAttributeModifier(attr, weaknessUuid, 0, weaknessOp);
	}

	public synchronized static void reloadEffectAttributes() {
		dirty = true;
	}

}
