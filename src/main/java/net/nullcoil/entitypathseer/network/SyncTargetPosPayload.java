package net.nullcoil.entitypathseer.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SyncTargetPosPayload(int entityId, BlockPos targetPos) implements CustomPayload {
    public static final CustomPayload.Id<SyncTargetPosPayload> ID = new CustomPayload.Id<>(Identifier.of("entitypathseer", "sync_target_pos"));

    public static final PacketCodec<PacketByteBuf, SyncTargetPosPayload> CODEC = CustomPayload.codecOf(
            SyncTargetPosPayload::write,
            SyncTargetPosPayload::new
    );

    public SyncTargetPosPayload(PacketByteBuf buf) {
        this(buf.readVarInt(), buf.readBlockPos());
    }

    public void write(PacketByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeBlockPos(targetPos);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}