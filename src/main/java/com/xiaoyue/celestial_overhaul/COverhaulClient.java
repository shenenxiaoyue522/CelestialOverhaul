package com.xiaoyue.celestial_overhaul;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static com.xiaoyue.celestial_overhaul.CelestialOverhaul.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class COverhaulClient {

	public static final HumanoidModel.ArmPose SWORD_BLOCK = HumanoidModel.ArmPose.BLOCK;

	@SubscribeEvent
	public static void onClientInit(FMLClientSetupEvent event) {
	}

	public static void renderArm(HumanoidArm arm, ModelPart part) {
		part.xRot = part.xRot * 0.5F - 0.9424778F;
		part.yRot = (arm == HumanoidArm.RIGHT ? 1 : -1) * -0.5235988F;
	}
}
