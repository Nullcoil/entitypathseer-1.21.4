// EntityRenderDispatcherMixin.java
package net.nullcoil.entitypathseer.mixin;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.nullcoil.entitypathseer.network.TargetPositionStorage;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "renderHitbox", at = @At("TAIL"))
    private static void onRenderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity,
                                       float tickDelta, float red, float green, float blue, CallbackInfo ci) {
        if (entity instanceof MobEntity mob) {
            BlockPos targetPos = TargetPositionStorage.getTargetPos(mob);

            if (!targetPos.equals(BlockPos.ORIGIN)) {
                double vecX = targetPos.getX() - entity.getX();
                double vecY = targetPos.getY() - entity.getY();
                double vecZ = targetPos.getZ() - entity.getZ();

                float verticalOffset = entity.getHeight() * 0.5f;

                VertexRendering.drawVector(matrices, vertices,
                        new Vector3f(0f, verticalOffset, 0f),
                        new Vec3d(vecX, vecY, vecZ),
                        0xff00ff00);
            }
        }
    }
}