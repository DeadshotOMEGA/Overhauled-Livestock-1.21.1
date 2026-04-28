package com.dragn0007.dragnlivestock.util;

public final class LONetwork {
    private LONetwork() {}

    public static final ChannelShim INSTANCE = new ChannelShim();

    public static final class ChannelShim {
        public void sendToServer(Object packet) {
            // TODO: Re-implement with NeoForge 1.21 payload handlers.
        }
    }

    public static final class HandleHorseSpeedRequest {
        public final int speedMod;

        public HandleHorseSpeedRequest(int speedMod) {
            this.speedMod = speedMod;
        }
    }

    public static final class PlayEmoteRequest {
        public final String emoteName;
        public final String loopType;

        public PlayEmoteRequest(String emoteName, String loopType) {
            this.emoteName = emoteName;
            this.loopType = loopType;
        }
    }

    public static final class ToggleTillerPowerRequest {
        public final int id;

        public ToggleTillerPowerRequest(int id) {
            this.id = id;
        }
    }
}
