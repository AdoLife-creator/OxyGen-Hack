package com.oxygenclient.mixin;

import com.oxygenclient.OxygenClient;
import com.oxygenclient.bypass.AntiCheatBypass;
import com.oxygenclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    // Hitbox genişletme (bypass ile)
    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void onGetBoundingBox(CallbackInfoReturnable<Box> cir) {
        Module hitbox = OxygenClient.moduleManager.getModule("HitBox");
        if (hitbox != null && hitbox.isEnabled()) {
            Box original = cir.getReturnValue();
            // Sadece saldırı anında, güvenli genişletme
            Box expanded = original.expand(0.12, 0.12, 0.12);
            cir.setReturnValue(expanded);
        }
    }

    // Velocity modifier - AntiKB
    @Inject(method = "isPushable", at = @At("RETURN"), cancellable = true)
    private void onIsPushable(CallbackInfoReturnable<Boolean> cir) {
        Module velocity = OxygenClient.moduleManager.getModule("Velocity");
        if (velocity != null && velocity.isEnabled()) {
            cir.setReturnValue(false);
        }
    }
}
