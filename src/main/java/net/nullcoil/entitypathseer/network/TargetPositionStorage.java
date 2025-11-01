package net.nullcoil.entitypathseer.network;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TargetPositionStorage {
    private static final Map<Integer, BlockPos> TARGET_POSITIONS = new ConcurrentHashMap<>();

    public static void setTargetPos(MobEntity mob, BlockPos pos) {
        TARGET_POSITIONS.put(mob.getId(), pos);
    }
    public static BlockPos getTargetPos(MobEntity mob) {
        return TARGET_POSITIONS.getOrDefault(mob.getId(), BlockPos.ORIGIN);
    }

    public static void removeTargetPos(MobEntity mob) {
        TARGET_POSITIONS.remove(mob.getId());
    }

    public static void onEntityRemoved(int entityId) {
        TARGET_POSITIONS.remove(entityId);
    }
}
