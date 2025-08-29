package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.xiaoyue.celestial_overhaul.content.WeaponBlockHandler;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageBonus(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/MobType;)F"), method = "getTooltipLines")
	public float celestial_overhaul$getInfoLine(ItemStack stack, MobType type, Operation<Float> original) {
		Double config = COModConfig.COMMON.sharpnessEnchantmentBonus.get();
		if (config > 0) {
			return 0;
		}
		return original.call(stack, type);
	}

	@Inject(at = @At("HEAD"), method = "getUseAnimation", cancellable = true)
	public void celestial_overhaul$getUseAnimation(CallbackInfoReturnable<UseAnim> cir) {
		ItemStack stack = (ItemStack) (Object) this;
		if (WeaponBlockHandler.isCurrentItem(stack)) {
			cir.setReturnValue(UseAnim.BLOCK);
		}
	}

	@Inject(at = @At("HEAD"), method = "getUseDuration", cancellable = true)
	public void celestial_overhaul$getUseDuration(CallbackInfoReturnable<Integer> cir) {
		ItemStack stack = (ItemStack) (Object) this;
		if (WeaponBlockHandler.isCurrentItem(stack)) {
			cir.setReturnValue(WeaponBlockHandler.getBlockTime(stack));
		}
	}

	@Inject(at = @At("HEAD"), method = "use")
	public void celestial_overhaul$use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		boolean flag = pPlayer.getOffhandItem().getItem().getUseAnimation(pPlayer.getOffhandItem()).equals(UseAnim.NONE);
		if (pUsedHand.equals(InteractionHand.MAIN_HAND) && WeaponBlockHandler.canBlock(pPlayer.getMainHandItem()) && flag) {
			pPlayer.startUsingItem(pUsedHand);
		}
	}
}
