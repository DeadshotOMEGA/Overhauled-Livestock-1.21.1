package com.dragn0007.dragnlivestock.common.network;

import com.dragn0007.dragnlivestock.common.network.packets.VehicleControlPacket;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class LOPackets {
    private static final String PROTOCOL_VERSION = "1";
    public static final PacketChannel INSTANCE = new PacketChannel();

    private LOPackets() {}

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(VehicleControlPacket.TYPE, VehicleControlPacket.STREAM_CODEC, (payload, context) -> payload.handle(context));
    }

    public static final class PacketChannel {
        public void sendToServer(VehicleControlPacket packet) {
            PacketDistributor.sendToServer(packet);
        }
    }
}
