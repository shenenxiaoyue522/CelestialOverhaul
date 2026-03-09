package com.xiaoyue.celestial_overhaul.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PoisonMobEffect.class)
public class PoisonEffectMixin {

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), method = "applyEffectTick", index = 1)
    public float celestial_overhaul$applyEffectTick(float amount, @Local(argsOnly = true) LivingEntity entity) {
        float config = COModConfig.SERVER.poisonEffectDamageTweak.get().floatValue();
        if (config > 0) {
            return entity.getHealth() * config;
        }
        return amount;
    }
}
