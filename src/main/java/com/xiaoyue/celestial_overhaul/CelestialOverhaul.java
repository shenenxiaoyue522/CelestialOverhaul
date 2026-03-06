package com.xiaoyue.celestial_overhaul;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.xiaoyue.celestial_invoker.invoker.config.ConfigLoader;
import com.xiaoyue.celestial_overhaul.content.EffectOverrideHandler;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

@Mod(CelestialOverhaul.MODID)
public class CelestialOverhaul {

	public static final String MODID = "celestial_overhaul";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final Registrate REGISTRATE = Registrate.create(MODID);

	public CelestialOverhaul() {
		COModConfig.init();
	}

	@SubscribeEvent
	public static void loadConfig(ModConfigEvent.Loading event) {
		if (event.getConfig().getType() == ModConfig.Type.SERVER)
			EffectOverrideHandler.reloadEffectAttributes();
	}

	@SubscribeEvent
	public static void reloadConfig(ModConfigEvent.Reloading event) {
		if (event.getConfig().getType() == ModConfig.Type.SERVER)
			EffectOverrideHandler.reloadEffectAttributes();
	}

	public static ResourceLocation loc(String s) {
		return ResourceLocation.fromNamespaceAndPath(MODID, s);
	}
}
