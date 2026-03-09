package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.RegenerationMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RegenerationMobEffect.class)
public class RegenerationEffectMixin {

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;heal(F)V"), method = "applyEffectTick")
    public float celestial_overhaul$applyEffectTick(float healAmount, @Local(argsOnly = true) LivingEntity entity) {
        float config = COModConfig.SERVER.regenerationEffectTweak.get().floatValue();
        if (config > 0) {
            return entity.getMaxHealth() * config;
        }
        return healAmount;
    }
}
