package com.xiaoyue.celestial_overhaul.event;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.xiaoyue.celestial_overhaul.CelestialOverhaul.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class COClientEventHandler {

	@SubscribeEvent
	public static void onRenderScreen(RenderBlockScreenEffectEvent event) {
		Player player = event.getPlayer();
		if (!event.getOverlayType().equals(RenderBlockScreenEffectEvent.OverlayType.FIRE)) return;
		if (!player.hasEffect(MobEffects.FIRE_RESISTANCE)) return;
		if (COModConfig.CLIENT.hideEffectiveFireScreen.get()) {
			event.setCanceled(true);
		}
	}
}
