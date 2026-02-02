package com.xiaoyue.celestial_overhaul.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRenderMixin {

	@Shadow
	protected abstract void applyItemArmTransform(PoseStack pPoseStack, HumanoidArm pHand, float pEquippedProg);

	@Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"), method = "renderArmWithItem", cancellable = true)
	public void celestial_overhaul$renderSwordBlockInHand(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
		if (WeaponBlockHandler.canBlock(pStack)) {
			pPoseStack.pushPose();
			boolean flag = pHand.equals(InteractionHand.MAIN_HAND);
			HumanoidArm arm = flag ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
			boolean right = arm.equals(HumanoidArm.RIGHT);
			this.applyItemArmTransform(pPoseStack, arm, pEquippedProgress);
			WeaponBlockHandler.renderBlock(pPoseStack, right);
			ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
			renderer.renderItem(pPlayer, pStack, right ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !right, pPoseStack, pBuffer, pCombinedLight);
			pPoseStack.popPose();
			ci.cancel();
		}
	}
}
