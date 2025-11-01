package net.nullcoil.entitypathseer.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class NetworkHandler {
    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(SyncTargetPosPayload.ID, SyncTargetPosPayload.CODEC);
    }

    public static void initializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SyncTargetPosPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                if(context.client().world != null) {
                    var entity = context.client().world.getEntityById(payload.entityId());
                    if(entity instanceof MobEntity mob) {
                        TargetPositionStorage.setTargetPos(mob, payload.targetPos());
                    }
                }
            });
        });
    }

    public static void syncTargetToClient(MobEntity mob, BlockPos targetPos) {
        if(!mob.getWorld().isClient()) {
            var payload = new SyncTargetPosPayload(mob.getId(), targetPos);

            for(var player : mob.getWorld().getPlayers()) {
                if(player instanceof ServerPlayerEntity serverPlayer &&
                   serverPlayer.canSee(mob) &&
                   serverPlayer.squaredDistanceTo(mob) < 1024) {
                    ServerPlayNetworking.send(serverPlayer, payload);
                }
            }
        }
    }
}
