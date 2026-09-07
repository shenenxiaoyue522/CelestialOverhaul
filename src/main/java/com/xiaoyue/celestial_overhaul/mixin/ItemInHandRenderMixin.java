package com.xiaoyue.celestial_overhaul.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRenderMixin {

	@Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	public void celestial_overhaul$renderSwordBlockInHand(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
		if (pPlayer.isUsingItem() && pPlayer.getUsedItemHand() == pHand && WeaponBlockHandler.canBlock(pStack)) {
			boolean flag = pHand == InteractionHand.MAIN_HAND;
			HumanoidArm arm = flag ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
			WeaponBlockHandler.renderBlock(pPoseStack, arm == HumanoidArm.RIGHT);
		}
	}
}
