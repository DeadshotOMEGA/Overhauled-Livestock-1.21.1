package com.dragn0007.dragnlivestock.common.network.packets;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.wagon.base.AbstractGeckolibVehicle;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record VehicleControlPacket(int id, float forwardImpulse, float leftImpulse) implements CustomPacketPayload {
    public static final Type<VehicleControlPacket> TYPE = new Type<>(LivestockOverhaul.id("vehicle_control"));
    public static final StreamCodec<RegistryFriendlyByteBuf, VehicleControlPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            VehicleControlPacket::id,
            ByteBufCodecs.FLOAT,
            VehicleControlPacket::forwardImpulse,
            ByteBufCodecs.FLOAT,
            VehicleControlPacket::leftImpulse,
            VehicleControlPacket::new
    );

    @Override
    public Type<VehicleControlPacket> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }
        if (!(sender.level().getEntity(id) instanceof AbstractGeckolibVehicle vehicle)) {
            return;
        }
        if (sender != vehicle.getControllingPassenger()) {
            return;
        }
        vehicle.setImpulses(Mth.clamp(forwardImpulse, -1.0F, 1.0F), Mth.clamp(leftImpulse, -1.0F, 1.0F));
    }

}
