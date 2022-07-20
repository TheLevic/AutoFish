package com.thelevic.autofish.mixin;

import com.thelevic.autofish.AutoFish;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin{
    @Shadow private boolean caughtFish;

    @Inject(at = @At("TAIL"), method = "onTrackedDataSet")
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci){
        MinecraftClient mc = MinecraftClient.getInstance();
        if (caughtFish && AutoFish.getEnabled()){
            mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
            AutoFish.setCasted(false);
        }

    }
}
