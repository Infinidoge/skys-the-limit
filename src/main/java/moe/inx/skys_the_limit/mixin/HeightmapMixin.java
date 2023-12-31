package moe.inx.skys_the_limit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import moe.inx.skys_the_limit.block.SkyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;

@Mixin(Heightmap.class)
public abstract class HeightmapMixin {
    @Inject(method = "method_16682", at = @At("HEAD"), cancellable = true)
    private static void skys_the_limit$NOT_AIR(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (state.isOf(SkyBlock.INSTANCE)) {
            ci.setReturnValue(false);
        }
    }
}
