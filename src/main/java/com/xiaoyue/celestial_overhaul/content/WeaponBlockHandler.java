package com.xiaoyue.celestial_overhaul.content;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.xiaoyue.celestial_overhaul.api.CanBlockItem;
import com.xiaoyue.celestial_overhaul.api.WeaponBlockedEvent;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import com.xiaoyue.celestial_overhaul.data.COTagGen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.common.MinecraftForge;

public class WeaponBlockHandler {

	public static boolean isCurrentItem(ItemStack stack) {
		if (!COModConfig.COMMON.canUseSwordBlock.get()) return false;
		return stack.getItem() instanceof SwordItem || stack.getItem() instanceof CanBlockItem || stack.is(COTagGen.CAN_BLOCK_SWORD);
	}

	public static int getItemDamaged(ItemStack stack) {
		if (stack.getItem() instanceof CanBlockItem item) {
			return item.getItemDamaged(stack);
		}
		return 3;
	}

	public static float getHurtModifier(ItemStack stack, LivingEntity entity, DamageSource source) {
		float baseModifier;
		if (stack.getItem() instanceof CanBlockItem item) {
			baseModifier = item.getHurtModifier(stack, entity, source);
		} else {
			baseModifier = 0.5f;
		}
		var event = new WeaponBlockedEvent(stack, entity, source, baseModifier);
		return MinecraftForge.EVENT_BUS.post(event) ? 0f : event.getDamageModifier();
	}

	public static int getBlockTime(ItemStack stack) {
		if (stack.getItem() instanceof CanBlockItem item) {
			return item.getBlockTime(stack);
		}
		return 72000;
	}

	public static boolean isBlocking(LivingEntity entity) {
		if (entity instanceof Player player && player.isUsingItem()) {
			return canBlock(player.getMainHandItem());
		}
		return false;
	}

	public static boolean canBlock(ItemStack stack) {
		if (!COModConfig.COMMON.canUseSwordBlock.get()) return false;
		if (stack.is(COTagGen.CANNOT_BLOCK_SWORD)) return false;
		return stack.getUseAnimation().equals(UseAnim.BLOCK) && isCurrentItem(stack);
	}

	public static void renderBlock(PoseStack poseStack, boolean right) {
		int i = right ? 1 : -1;
		poseStack.translate(i * -0.14142136F, 0.08F, 0.14142136F);
		poseStack.mulPose(Axis.XP.rotationDegrees(-102.25F));
		poseStack.mulPose(Axis.YP.rotationDegrees(i * 13.365F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(i * 78.05F));
	}
}
