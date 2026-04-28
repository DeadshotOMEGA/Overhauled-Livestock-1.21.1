package com.dragn0007.dragnlivestock.common.network;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.common.network.packets.VehicleControlPacket;

public class LOPackets {

    public static final PacketChannelShim INSTANCE = new PacketChannelShim();

    public static void register() {
        // TODO: Rewire to NeoForge 1.21 payload registration API.
        LivestockOverhaul.LOGGER.info("LOPackets shim active; packet registration deferred during compile-first port.");
    }

    public static final class PacketChannelShim {
        public void sendToServer(VehicleControlPacket packet) {
            // TODO: Implement NeoForge payload send.
        }
    }

}
