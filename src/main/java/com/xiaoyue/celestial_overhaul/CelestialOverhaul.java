package com.xiaoyue.celestial_overhaul;

import com.mojang.logging.LogUtils;
import com.xiaoyue.celestial_overhaul.content.EffectOverrideHandler;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

@Mod(CelestialOverhaul.MODID)
public class CelestialOverhaul {
	public static final String MODID = "celestial_overhaul";
	public static final Logger LOGGER = LogUtils.getLogger();

	public CelestialOverhaul() {
		COModConfig.init();
	}

	@SubscribeEvent
	public static void commonSetup(ModConfigEvent.Loading event) {
		if (event.getConfig().getType() == ModConfig.Type.COMMON)
			EffectOverrideHandler.reloadEffectAttributes();
	}

	@SubscribeEvent
	public static void commonSetup(ModConfigEvent.Reloading event) {
		if (event.getConfig().getType() == ModConfig.Type.COMMON)
			EffectOverrideHandler.reloadEffectAttributes();
	}

	public static ResourceLocation loc(String s) {
		return new ResourceLocation(MODID, s);
	}
}
