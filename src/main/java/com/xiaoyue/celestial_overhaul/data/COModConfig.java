package com.xiaoyue.celestial_overhaul.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class COModConfig {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue hideEffectiveFireScreen;

		Client(ForgeConfigSpec.Builder builder) {
			builder.push("function");
			hideEffectiveFireScreen = builder
					.comment("Hide the flame screen when it has the flame resistance effect")
					.define("hideEffectiveFireScreen", true);
			builder.pop();
		}

	}

	public static class Common {

		public final ForgeConfigSpec.BooleanValue grassPiercingAttack;
		public final ForgeConfigSpec.BooleanValue canUseSwordBlock;
		public final ForgeConfigSpec.BooleanValue arrowHitMobSound;
		public final ForgeConfigSpec.BooleanValue canVanillaCritical;
		public final ForgeConfigSpec.DoubleValue playerHungryDamageTweak;
		public final ForgeConfigSpec.DoubleValue entityCrammingDamageTweak;
		public final ForgeConfigSpec.DoubleValue entityDrownDamageTweak;
		public final ForgeConfigSpec.DoubleValue sharpnessEnchantmentBonus;
		public final ForgeConfigSpec.DoubleValue killerEnchantmentBonus;
		public final ForgeConfigSpec.DoubleValue thornsEnchantmentTweak;
		public final ForgeConfigSpec.DoubleValue strengthEffectDamageBonus;
		public final ForgeConfigSpec.DoubleValue resistanceEffectTweak;
		public final ForgeConfigSpec.DoubleValue regenerationEffectTweak;
		public final ForgeConfigSpec.DoubleValue poisonEffectDamageTweak;
		public final ForgeConfigSpec.DoubleValue entityFallDamageTweak;
		public final ForgeConfigSpec.DoubleValue witherEffectDamageTweak;
		public final ForgeConfigSpec.DoubleValue protectionEnchantmentTweak;
		public final ForgeConfigSpec.DoubleValue fullFoodLevelHealTweak;
		public final ForgeConfigSpec.DoubleValue fireTypeDamageTweak;
		public final ForgeConfigSpec.DoubleValue frozenTypeDamageTweak;
		public final ForgeConfigSpec.BooleanValue scalingToPlayerOnly;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("misc");
			grassPiercingAttack = builder
					.comment("Ignore the nearby grass when attacking")
					.define("grassPiercingAttack", true);
			canUseSwordBlock = builder
					.comment("Enable sword holding block")
					.define("canUseSwordBlock", true);
			arrowHitMobSound = builder
					.comment("When an arrow hits a non-player mob, it also makes a hit sound")
					.define("arrowHitMobSound", true);
			canVanillaCritical = builder
					.comment("Whether to allow vanilla crit")
					.define("canVanillaCritical", true);
			builder.pop();

			builder.push("enchantments_and_effects");
			sharpnessEnchantmentBonus = builder
					.comment("Sharp enchantment damage bonus as percentage bonus")
					.comment("A value of -1 disables this function")
					.defineInRange("sharpnessEnchantmentBonus", 0.06, -1, 10);
			killerEnchantmentBonus = builder
					.comment("Smite-like enchantment damage bonus as percentage bonus")
					.comment("A value of -1 disables this function")
					.defineInRange("killerEnchantmentTweak", 0.12, -1, 10);
			strengthEffectDamageBonus = builder
					.comment("Strength effect damage bonus per level")
					.comment("A value of -1 disables this function")
					.defineInRange("strengthEffectDamageBonus", 0.3, -1, 10);
			resistanceEffectTweak = builder
					.comment("Multiplicative stacking factor for Resistance Effect")
					.comment("newDamage = incomingDamage * factor ^ level")
					.comment("A value of -1 disables this function")
					.defineInRange("resistanceEffectTweak", 0.8, -1, 10);
			protectionEnchantmentTweak = builder
					.comment("Multiplicative stacking factor for Protection-like Enchantments")
					.comment("newDamage = incomingDamage * factor ^ totalPoints")
					.comment("A value of -1 disables this function")
					.defineInRange("protectionEnchantmentTweak", 0.96, -1, 1);
			builder.pop();

			builder.push("max_health_based_scaling");
			scalingToPlayerOnly = builder
					.comment("All damage scaling applies to player only")
					.define("scalingToPlayerOnly",false);
			fireTypeDamageTweak = builder
					.comment("Fire type damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("fireTypeDamageTweak", 0.02, -1, 1);
			frozenTypeDamageTweak = builder
					.comment("Frozen type damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("frozenTypeDamageTweak", 0.02, -1, 1);
			playerHungryDamageTweak = builder
					.comment("Starvation damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("playerHungryDamageTweak", 0.02, -1, 1);
			entityCrammingDamageTweak = builder
					.comment("Suffocation and Cramming damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityCrammingDamageTweak", 0.02, -1, 1);
			entityDrownDamageTweak = builder
					.comment("Drowning damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityDrownDamageTweak", 0.02, -1, 1);
			entityFallDamageTweak = builder
					.comment("Fall damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("entityFallDamageTweak", -1, -1, 1f);
			poisonEffectDamageTweak = builder
					.comment("Poison damage scales with current health")
					.comment("newDamage = oldDamage * max(1, factor * health)")
					.comment("A value of -1 disables this function")
					.defineInRange("poisonEffectDamageTweak", 0.05, -1, 1);
			witherEffectDamageTweak = builder
					.comment("Wither effect damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("witherEffectDamageTweak", 0.02, -1, 1);
			thornsEnchantmentTweak = builder
					.comment("Thorn damage scales with max health")
					.comment("newDamage = oldDamage * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("thornsEnchantmentTweak", 0.02, -1, 1);

			regenerationEffectTweak = builder
					.comment("Regeneration effect scales with max health")
					.comment("newHealing = oldHealing * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("regenerationEffectTweak", 0.05, -1, 1);
			fullFoodLevelHealTweak = builder
					.comment("Natural healing scales with max health")
					.comment("newHealing = oldHealing * max(1, factor * maxHealth)")
					.comment("A value of -1 disables this function")
					.defineInRange("fullFoodLevelHealTweak", 0.05, -1, 1);
			builder.pop();
		}
	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	public static String COMMON_PATH;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	public static void init() {
		register(ModConfig.Type.CLIENT, CLIENT_SPEC);
		COMMON_PATH = register(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	private static String register(ModConfig.Type type, IConfigSpec<?> spec) {
		var mod = ModLoadingContext.get().getActiveContainer();
		String path = "celestial_configs/" + mod.getModId() + "-" + type.extension() + ".toml";
		ModLoadingContext.get().registerConfig(type, spec, path);
		return path;
	}
}
