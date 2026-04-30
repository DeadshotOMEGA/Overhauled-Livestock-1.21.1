package com.dragn0007.dragnlivestock.client.event;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.client.ClientProxy;
import com.dragn0007.dragnlivestock.entities.wagon.base.AbstractGeckolibVehicle;
import com.dragn0007.dragnlivestock.entities.wagon.base.AbstractWagon;
import com.dragn0007.dragnlivestock.items.custom.LightHorseArmorItem;
import com.dragn0007.dragnlivestock.util.LONetwork;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber(modid = LivestockOverhaul.MODID, value = Dist.CLIENT)
public class ForgeClientEvents {
    // Legacy class name retained intentionally; class already targets NeoForge events/API.
    private static int activeWagonSoundId = -1;

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();

        if (event.getItemStack().getItem() instanceof LightHorseArmorItem item) {
            tooltip.add(Component.translatable("Protection: " + item.getProtection()).withStyle(ChatFormatting.GOLD));
        }
    }

    @SubscribeEvent
    public static void onKeyPressEvent(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_SPEED_UP.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.HandleHorseSpeedRequest(1));
        }

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_SLOW_DOWN.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.HandleHorseSpeedRequest(-1));
        }

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_BOW.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.PlayEmoteRequest("bow", "play_once"));
        }

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_PIAFFE.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.PlayEmoteRequest("piaffe", "loop"));
        }

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_WAVE.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.PlayEmoteRequest("wave", "play_once"));
        }

        if (event.getAction() == InputConstants.RELEASE && event.getKey() == LivestockOverhaulClientEvent.HORSE_LEVADE.getKey().getValue()) {
            LONetwork.INSTANCE.sendToServer(new LONetwork.PlayEmoteRequest("levade", "play_once"));
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            activeWagonSoundId = -1;
            return;
        }

        Entity vehicle = player.getVehicle();
        if (vehicle instanceof AbstractGeckolibVehicle geckolibVehicle && geckolibVehicle.getControllingPassenger() == player) {
            ClientProxy.controlVehicleLocal(geckolibVehicle);
        }

        if (vehicle instanceof AbstractWagon wagon) {
            if (activeWagonSoundId != wagon.getId()) {
                ClientProxy.createWagonSound(wagon);
                activeWagonSoundId = wagon.getId();
            }
        } else {
            activeWagonSoundId = -1;
        }
    }
}
