package com.dragn0007.dragnlivestock;

import com.dragn0007.dragnlivestock.blocks.LOBlocks;
import com.dragn0007.dragnlivestock.common.event.ForgeEvent;
import com.dragn0007.dragnlivestock.common.event.LivestockOverhaulCommonEvent;
import com.dragn0007.dragnlivestock.common.gui.LOMenuTypes;
import com.dragn0007.dragnlivestock.common.network.LOPackets;
import com.dragn0007.dragnlivestock.client.event.LivestockOverhaulClientEvent;
import com.dragn0007.dragnlivestock.datagen.JsonDataGenerator;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOMemoryTypes;
import com.dragn0007.dragnlivestock.entities.ai.LOSensorTypes;
import com.dragn0007.dragnlivestock.items.LOItemGroup;
import com.dragn0007.dragnlivestock.items.LOItems;
import com.dragn0007.dragnlivestock.util.LONetwork;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.Month;

@Mod(LivestockOverhaul.MODID)
public class LivestockOverhaul {

    public static final String MODID = "dragnlivestock";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LivestockOverhaul(IEventBus eventBus, ModContainer modContainer) {
        EntityTypes.register(eventBus);
        LOMemoryTypes.register(eventBus);
        LOSensorTypes.register(eventBus);
        LOItems.register(eventBus);
        LOItemGroup.register(eventBus);
        LOBlocks.register(eventBus);
        LOSoundEvents.register(eventBus);
        LOMenuTypes.register(eventBus);
        eventBus.addListener(LOPackets::register);
        eventBus.addListener(LONetwork::register);
        eventBus.addListener(JsonDataGenerator::gatherData);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            LivestockOverhaulClientEvent.register(eventBus);
        }

        eventBus.register(LivestockOverhaulCommonEvent.class);
        NeoForge.EVENT_BUS.register(ForgeEvent.class);
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent warn) -> warn(warn.getEntity()));

        modContainer.registerConfig(ModConfig.Type.COMMON, LivestockOverhaulCommonConfig.SPEC, "livestock-overhaul-common.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, LivestockOverhaulClientConfig.SPEC, "livestock-overhaul-client.toml");

        System.out.println("[DragN's Livestock Overhaul!] Registered Livestock Overhaul.");
        System.out.println("[DragN's Livestock Overhaul!] Do not remove this mod without running the Failsafe Config!");
    }

    public static void warn(Player entity){

        LocalDate date = LocalDate.now();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();

        if ((month == Month.OCTOBER && (day == 31)) || (month == Month.NOVEMBER && (day == 1 || day == 2))) {
            entity.displayClientMessage(Component.empty().append
                    (Component.literal("[DragN's Livestock Overhaul!] Spooky souls fill the air, defeat the Headless Horseman to make your horse his steed's heir...")
                            .withStyle(ChatFormatting.DARK_RED)), false);
        }

        if (LivestockOverhaulCommonConfig.DEBUG_LOGS.get()) {
            entity.displayClientMessage(Component.empty().append
                    (Component.literal(
                                    "[DragN's Livestock Overhaul!] Debug Logs are turned on! You can disable this message by switching Debug Logs to False in the livestock-overhaul-common.toml." +
                                            "\nChecking for compatible mods...")
                            .withStyle(ChatFormatting.GOLD)), false);

            if (ModList.get().isLoaded("deadlydinos") || ModList.get().isLoaded("medievalembroidery") ||
                    ModList.get().isLoaded("tfc") || ModList.get().isLoaded("jade")) {
                if (ModList.get().isLoaded("deadlydinos")) {
                    entity.displayClientMessage(Component.empty().append
                            (Component.literal(
                                            "[DragN's Livestock Overhaul!] Found DragN's Deadly Dinos!")
                                    .withStyle(ChatFormatting.AQUA)), false);
                }

                if (ModList.get().isLoaded("medievalembroidery")) {
                    entity.displayClientMessage(Component.empty().append
                            (Component.literal(
                                            "[DragN's Livestock Overhaul!] Found Medieval Embroidery!")
                                    .withStyle(ChatFormatting.AQUA)), false);
                }

                if (ModList.get().isLoaded("tfc")) {
                    entity.displayClientMessage(Component.empty().append
                            (Component.literal(
                                            "[DragN's Livestock Overhaul!] Found TerraFirmaCraft!")
                                    .withStyle(ChatFormatting.AQUA)), false);
                }

                if (ModList.get().isLoaded("jade")) {
                    entity.displayClientMessage(Component.empty().append
                            (Component.literal(
                                            "[DragN's Livestock Overhaul!] Found Jade!")
                                    .withStyle(ChatFormatting.AQUA)), false);
                }

                entity.displayClientMessage(Component.empty().append
                        (Component.literal(
                                        "Found directly-compatible mods!")
                                .withStyle(ChatFormatting.GOLD)), false);
            } else {
                entity.displayClientMessage(Component.empty().append
                        (Component.literal(
                                        "Found no directly-compatible mods.")
                                .withStyle(ChatFormatting.GOLD)), false);
            }
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
