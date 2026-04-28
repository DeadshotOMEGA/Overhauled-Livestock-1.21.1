package com.dragn0007.dragnlivestock.common.network.packets;

import com.dragn0007.dragnlivestock.common.network.LivestockOverhaulPacket;
import net.minecraft.network.FriendlyByteBuf;

public record VehicleControlPacket(int id, float forwardImpulse, float leftImpulse) implements LivestockOverhaulPacket {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(id);
        buffer.writeFloat(forwardImpulse);
        buffer.writeFloat(leftImpulse);
    }

    public static VehicleControlPacket decode(FriendlyByteBuf buffer) {
        return new VehicleControlPacket(buffer.readInt(), buffer.readFloat(), buffer.readFloat());
    }

    @Override
    public void handle() {
        // TODO: Implement with NeoForge 1.21 payload context APIs.
    }

}
