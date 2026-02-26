package com.xiaoyue.celestial_overhaul.data;

import com.xiaoyue.celestial_invoker.invoker.config.wrapper.ConfigWrapper;
import com.xiaoyue.celestial_overhaul.CelestialOverhaul;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class COModConfig {

	public static class Client extends ConfigWrapper {

		public final ModConfigSpec.BooleanValue hideEffectiveFireScreen;

		public Client(Builder builder) {
			builder.push("function", "Function");
			hideEffectiveFireScreen = builder
					.name("Hide the flame screen when it has the flame resistance effect")
					.define("hideEffectiveFireScreen", true);
			builder.pop();
		}
	}

	public static class Server extends ConfigWrapper {

		public final ModConfigSpec.BooleanValue grassPiercingAttack;
		public final ModConfigSpec.BooleanValue canUseSwordBlock;
		public final ModConfigSpec.BooleanValue arrowHitMobSound;
		public final ModConfigSpec.BooleanValue canVanillaCritical;
		public final ModConfigSpec.DoubleValue playerHungryDamageTweak;
		public final ModConfigSpec.DoubleValue entityCrammingDamageTweak;
		public final ModConfigSpec.DoubleValue entityDrownDamageTweak;
		public final ModConfigSpec.DoubleValue sharpnessEnchantmentBonus;
		public final ModConfigSpec.DoubleValue killerEnchantmentBonus;
		public final ModConfigSpec.DoubleValue thornsEnchantmentTweak;
		public final ModConfigSpec.DoubleValue strengthEffectDamageBonus;
		public final ModConfigSpec.DoubleValue weaknessEffectDamageReduce;
		public final ModConfigSpec.DoubleValue resistanceEffectTweak;
		public final ModConfigSpec.DoubleValue regenerationEffectTweak;
		public final ModConfigSpec.DoubleValue poisonEffectDamageTweak;
		public final ModConfigSpec.DoubleValue entityFallDamageTweak;
		public final ModConfigSpec.DoubleValue witherEffectDamageTweak;
		public final ModConfigSpec.DoubleValue protectionEnchantmentTweak;
		public final ModConfigSpec.DoubleValue fullFoodLevelHealTweak;
		public final ModConfigSpec.DoubleValue fireTypeDamageTweak;
		public final ModConfigSpec.DoubleValue frozenTypeDamageTweak;
		public final ModConfigSpec.BooleanValue scalingToPlayerOnly;
		public final ModConfigSpec.DoubleValue maxHealAmountTweak;

		public Server(Builder builder) {
			builder.push("misc", "Misc");
			grassPiercingAttack = builder
					.name("Ignore the nearby grass when attacking")
					.define("grassPiercingAttack", true);
			canUseSwordBlock = builder
					.name("Enable sword holding block")
					.define("canUseSwordBlock", true);
			arrowHitMobSound = builder
					.name("When an arrow hits a non-player mob, it also makes a hit sound")
					.define("arrowHitMobSound", true);
			canVanillaCritical = builder
					.name("Whether to allow vanilla crit")
					.define("canVanillaCritical", true);
			builder.pop();

			builder.push("enchantments_and_effects", "Enchantments And Effects");
			sharpnessEnchantmentBonus = builder
					.name("Sharp enchantment damage bonus as percentage bonus")
					.comment("A value of -1 disables this function")
					.defineInRange("sharpnessEnchantmentBonus", 0.06, -1, 10);
			killerEnchantmentBonus = builder
					.name("Smite-like enchantment damage bonus as percentage bonus")
					.comment("A value of -1 disables this function")
					.defineInRange("killerEnchantmentTweak", 0.12, -1, 10);
			strengthEffectDamageBonus = builder
					.name("Strength effect damage bonus per level")
					.comment("A value of -1 disables this function")
					.defineInRange("strengthEffectDamageBonus", 0.3, -1, 10);
			weaknessEffectDamageReduce = builder
					.name("Weakness effect damage reduce per level")
					.comment("A value of -1 disables this function")
					.defineInRange("weaknessEffectDamageReduce", -1, -1, 10.0);
			resistanceEffectTweak = builder
					.name("Multiplicative stacking factor for Resistance Effect")
					.comment("newDamage = incomingDamage * factor ^ level")
					.comment("A value of -1 disables this function")
					.defineInRange("resistanceEffectTweak", 0.8, -1, 10);
			protectionEnchantmentTweak = builder
					.name("Multiplicative stacking factor for Protection-like Enchantments")
					.comment("newDamage = incomingDamage * factor ^ totalPoints")
					.comment("A value of -1 disables this function")
					.defineInRange("protectionEnchantmentTweak", 0.96, -1, 1);
			builder.pop();

			builder.push("max_health_based_scaling", "Max Health Based Scaling");
			scalingToPlayerOnly = builder
					.name("All damage scaling applies to player only")
					.define("scalingToPlayerOnly",false);
			fireTypeDamageTweak = builder
					.name("Fire type damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("fireTypeDamageTweak", 0.02, -1, 1);
			frozenTypeDamageTweak = builder
					.name("Frozen type damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("frozenTypeDamageTweak", 0.02, -1, 1);
			playerHungryDamageTweak = builder
					.name("Starvation damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("playerHungryDamageTweak", 0.02, -1, 1);
			entityCrammingDamageTweak = builder
					.name("Suffocation and Cramming damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityCrammingDamageTweak", 0.02, -1, 1);
			entityDrownDamageTweak = builder
					.name("Drowning damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityDrownDamageTweak", 0.02, -1, 1);
			entityFallDamageTweak = builder
					.name("Fall damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityFallDamageTweak", -1, -1, 1f);
			poisonEffectDamageTweak = builder
					.name("Poison damage scales with current health")
					.comment("newDamage = oldDamage * max(1, factor * health)")
					.comment("A value of -1 disables this function")
					.defineInRange("poisonEffectDamageTweak", 0.05, -1, 1);
			witherEffectDamageTweak = builder
					.name("Wither effect damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("witherEffectDamageTweak", 0.02, -1, 1);
			thornsEnchantmentTweak = builder
					.name("Thorn damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("thornsEnchantmentTweak", 0.02, -1, 1);

			regenerationEffectTweak = builder
					.name("Regeneration effect scales with max health")
					.comment("newHealing = oldHealing * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("regenerationEffectTweak", 0.05, -1, 1);
			fullFoodLevelHealTweak = builder
					.name("Natural healing scales with max health")
					.comment("newHealing = oldHealing * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("fullFoodLevelHealTweak", 0.05, -1, 1);
			maxHealAmountTweak = builder
					.name("Entities are bound by this value when they healed")
					.comment("newHealing = maxHealth * factor")
					.comment("A value of -1 disables this function")
					.defineInRange("maxHealAmountTweak", -1, -1, Double.MAX_VALUE);
			builder.pop();
		}
	}

	public static final Client CLIENT = ConfigWrapper.init(CelestialOverhaul.REGISTRATE, ModConfig.Type.CLIENT, Client::new);
	public static final Server SERVER = ConfigWrapper.init(CelestialOverhaul.REGISTRATE, ModConfig.Type.SERVER, Server::new);

	public static void init() {
		ConfigWrapper.addTitleTooltip(CelestialOverhaul.REGISTRATE);
	}
}
