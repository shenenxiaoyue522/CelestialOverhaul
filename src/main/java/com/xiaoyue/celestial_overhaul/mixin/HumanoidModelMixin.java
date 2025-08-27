package com.xiaoyue.celestial_overhaul.mixin;

import com.xiaoyue.celestial_overhaul.COverhaulClient;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

	@Shadow
	public HumanoidModel.ArmPose rightArmPose;

	@Shadow
	@Final
	public ModelPart rightArm;

	@Shadow
	@Final
	public ModelPart leftArm;

	@Shadow
	public HumanoidModel.ArmPose leftArmPose;

	@Inject(at = @At("HEAD"), method = "poseRightArm", cancellable = true)
	public void celestial_overhaul$renderRight(T pLivingEntity, CallbackInfo ci) {
		if (this.rightArmPose.equals(COverhaulClient.SWORD_BLOCK)) {
			COverhaulClient.renderArm(pLivingEntity.getMainArm(), this.rightArm);
			ci.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "poseLeftArm", cancellable = true)
	public void celestial_overhaul$renderLeft(T pLivingEntity, CallbackInfo ci) {
		if (this.leftArmPose.equals(COverhaulClient.SWORD_BLOCK)) {
			COverhaulClient.renderArm(pLivingEntity.getMainArm(), this.leftArm);
			ci.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "setupAttackAnimation", cancellable = true)
	public void celestial_overhaul$renderCancel(T pLivingEntity, float pAgeInTicks, CallbackInfo ci) {
		if (pLivingEntity.getMainArm().equals(HumanoidArm.RIGHT) && this.rightArmPose.equals(COverhaulClient.SWORD_BLOCK)) {
			ci.cancel();
		} else if (pLivingEntity.getMainArm().equals(HumanoidArm.LEFT) && this.leftArmPose.equals(COverhaulClient.SWORD_BLOCK)) {
			ci.cancel();
		}
	}
}
