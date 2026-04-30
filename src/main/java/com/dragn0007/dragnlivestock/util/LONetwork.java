package com.dragn0007.dragnlivestock.util;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.wagon.Mower;
import com.dragn0007.dragnlivestock.entities.wagon.Plow;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class LONetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final ChannelShim INSTANCE = new ChannelShim();

    private LONetwork() {}

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(HandleHorseSpeedRequest.TYPE, HandleHorseSpeedRequest.STREAM_CODEC, (payload, context) -> payload.handle(context));
        registrar.playToServer(PlayEmoteRequest.TYPE, PlayEmoteRequest.STREAM_CODEC, (payload, context) -> payload.handle(context));
        registrar.playToClient(PlayEmoteResponse.TYPE, PlayEmoteResponse.STREAM_CODEC, (payload, context) -> payload.handle(context));
        registrar.playToServer(ToggleTillerPowerRequest.TYPE, ToggleTillerPowerRequest.STREAM_CODEC, (payload, context) -> payload.handle(context));
    }

    public static final class ChannelShim {
        public void sendToServer(Object packet) {
            if (packet instanceof CustomPacketPayload payload) {
                PacketDistributor.sendToServer(payload);
            } else if (packet != null) {
                LivestockOverhaul.LOGGER.warn("Skipping non-payload packet send for {}", packet.getClass().getName());
            }
        }
    }

    public record HandleHorseSpeedRequest(int speedMod) implements CustomPacketPayload {
        public static final Type<HandleHorseSpeedRequest> TYPE = new Type<>(LivestockOverhaul.id("handle_horse_speed_request"));
        public static final StreamCodec<RegistryFriendlyByteBuf, HandleHorseSpeedRequest> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                HandleHorseSpeedRequest::speedMod,
                HandleHorseSpeedRequest::new
        );

        @Override
        public Type<HandleHorseSpeedRequest> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }
            if (player.getVehicle() instanceof AbstractOMount mount) {
                mount.handleSpeedRequest(speedMod);
            }
        }
    }

    public record PlayEmoteRequest(String emoteName, String loopType) implements CustomPacketPayload {
        public static final Type<PlayEmoteRequest> TYPE = new Type<>(LivestockOverhaul.id("play_emote_request"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayEmoteRequest> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,
                PlayEmoteRequest::emoteName,
                ByteBufCodecs.STRING_UTF8,
                PlayEmoteRequest::loopType,
                PlayEmoteRequest::new
        );

        @Override
        public Type<PlayEmoteRequest> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }
            if (player.getVehicle() instanceof AbstractOMount mount) {
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(mount, new PlayEmoteResponse(mount.getId(), emoteName, loopType));
            }
        }
    }

    public record PlayEmoteResponse(int id, String emoteName, String loopType) implements CustomPacketPayload {
        public static final Type<PlayEmoteResponse> TYPE = new Type<>(LivestockOverhaul.id("play_emote_response"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PlayEmoteResponse> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                PlayEmoteResponse::id,
                ByteBufCodecs.STRING_UTF8,
                PlayEmoteResponse::emoteName,
                ByteBufCodecs.STRING_UTF8,
                PlayEmoteResponse::loopType,
                PlayEmoteResponse::new
        );

        @Override
        public Type<PlayEmoteResponse> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            Entity entity = context.player().level().getEntity(id);
            if (entity instanceof AbstractOMount mount) {
                mount.playEmote(emoteName, loopType);
            }
        }
    }

    public record ToggleTillerPowerRequest(int id) implements CustomPacketPayload {
        public static final Type<ToggleTillerPowerRequest> TYPE = new Type<>(LivestockOverhaul.id("toggle_tiller_power_request"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ToggleTillerPowerRequest> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT,
                ToggleTillerPowerRequest::id,
                ToggleTillerPowerRequest::new
        );

        @Override
        public Type<ToggleTillerPowerRequest> type() {
            return TYPE;
        }

        public void handle(IPayloadContext context) {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }
            Entity entity = player.level().getEntity(id);
            if (entity instanceof Plow plow) {
                plow.cycleMode();
            } else if (entity instanceof Mower mower) {
                mower.cycleMode();
            }
        }
    }
}
