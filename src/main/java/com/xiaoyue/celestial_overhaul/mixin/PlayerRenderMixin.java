package com.xiaoyue.celestial_overhaul.mixin;

import com.xiaoyue.celestial_overhaul.COverhaulClient;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRenderMixin {

	@Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"), method = "getArmPose", cancellable = true)
	private static void celestial_overhaul$renderSwordBlock(AbstractClientPlayer pPlayer, InteractionHand pHand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
		ItemStack stack = pPlayer.getItemInHand(pHand);
		if (WeaponBlockHandler.canBlock(stack)) {
			cir.setReturnValue(COverhaulClient.SWORD_BLOCK);
		}
	}
}
