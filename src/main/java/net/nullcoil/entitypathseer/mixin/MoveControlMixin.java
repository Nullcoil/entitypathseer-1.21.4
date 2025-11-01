package net.nullcoil.entitypathseer.mixin;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.nullcoil.entitypathseer.network.NetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoveControl.class)
public abstract class MoveControlMixin {

    @Shadow
    protected MobEntity entity;

    @Shadow
    public abstract double getTargetX();

    @Shadow
    public abstract double getTargetY();

    @Shadow
    public abstract double getTargetZ();

    private BlockPos lastSyncedTarget = BlockPos.ORIGIN;

    @Inject(method = "moveTo", at = @At("TAIL"))
    private void onMoveTo(double x, double y, double z, double speed, CallbackInfo ci) {
        if (entity != null && !entity.getWorld().isClient()) {
            BlockPos currentTarget = BlockPos.ofFloored(x, y, z);

            // Only sync if target changed
            if (!currentTarget.equals(lastSyncedTarget)) {
                NetworkHandler.syncTargetToClient(entity, currentTarget);
                lastSyncedTarget = currentTarget;
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (entity != null && !entity.getWorld().isClient() && entity.age % 10 == 0) {
            BlockPos currentTarget = BlockPos.ofFloored(getTargetX(), getTargetY(), getTargetZ());

            // Only sync if target changed and we have a valid target
            if (!currentTarget.equals(lastSyncedTarget) && (getTargetX() != 0 || getTargetY() != 0 || getTargetZ() != 0)) {
                NetworkHandler.syncTargetToClient(entity, currentTarget);
                lastSyncedTarget = currentTarget;
            }
        }
    }
}