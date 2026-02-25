package com.xiaoyue.celestial_overhaul.content;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.mixin.MobEffectAccessor;
import net.minecraft.resources.ResourceLocation;
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

		((MobEffectAccessor) MobEffects.DAMAGE_BOOST.value()).getAttributeModifiers().clear();
		strength = COModConfig.SERVER.strengthEffectDamageBonus.get();
		var strengthUuid = ResourceLocation.withDefaultNamespace("effect.strength");
		var strengthOp = strength < 0 ? AttributeModifier.Operation.ADD_VALUE : AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
		MobEffects.DAMAGE_BOOST.value().addAttributeModifier(attr, strengthUuid, 0, strengthOp);

		((MobEffectAccessor) MobEffects.WEAKNESS.value()).getAttributeModifiers().clear();
		weakness = COModConfig.SERVER.weaknessEffectDamageReduce.get();
		var weaknessUuid = ResourceLocation.withDefaultNamespace("effect.weakness");
		var weaknessOp = weakness < 0 ? AttributeModifier.Operation.ADD_VALUE : AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
		MobEffects.WEAKNESS.value().addAttributeModifier(attr, weaknessUuid, 0, weaknessOp);
	}

	public synchronized static void reloadEffectAttributes() {
		dirty = true;
	}

}
