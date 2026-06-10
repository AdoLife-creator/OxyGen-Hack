package com.oxygenclient.mixin;

import com.oxygenclient.OxygenClient;
import com.oxygenclient.module.Module;
import com.oxygenclient.module.render.XRay;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public class BlockRenderMixin {

    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(BlockState state, BlockPos pos, BlockRenderView world,
                                MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                boolean cull, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if (OxygenClient.moduleManager == null) return;
        Module xray = OxygenClient.moduleManager.getModules().stream()
            .filter(m -> m.getName().equals("XRay")).findFirst().orElse(null);
        if (xray != null && xray.isEnabled() && !XRay.BLOCKS.contains(state.getBlock())) {
            ci.cancel();
        }
    }
}
