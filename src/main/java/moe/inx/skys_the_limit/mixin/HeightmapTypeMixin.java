package moe.inx.skys_the_limit.mixin;

import java.util.function.Predicate;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import moe.inx.skys_the_limit.block.SkyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;

@Mixin(Heightmap.Type.class)
public abstract class HeightmapTypeMixin {
    @WrapOperation(
        method = "<clinit>()V",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.GETSTATIC,
            target = "Lnet/minecraft/world/Heightmap;SUFFOCATES*:Ljava/util/function/Predicate;"
        )
    )
    private static Predicate<BlockState> skys_the_limit$SUFFOCATES(Operation<Predicate<BlockState>> original) {
        return (BlockState state) -> {
            return !state.isOf(SkyBlock.INSTANCE) && original.call().test(state);
        };
    }

    @Inject(method = "method_16685", at = @At("HEAD"), cancellable = true)
    private static void skys_the_limit$blocksMovement(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (state.isOf(SkyBlock.INSTANCE)) {
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "method_16686", at = @At("HEAD"), cancellable = true)
    private static void skys_the_limit$blocksMovementNoLeaves(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (state.isOf(SkyBlock.INSTANCE)) {
            ci.setReturnValue(false);
        }
    }
}
