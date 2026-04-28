package com.dragn0007.dragnlivestock;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.Month;

@Mod(LivestockOverhaul.MODID)
public class LivestockOverhaul {

    public static final String MODID = "dragnlivestock";
    public static final Logger LOGGER = LogUtils.getLogger();

    public LivestockOverhaul(IEventBus eventBus) {
        // TODO: Re-enable content/system registration after registry + networking port.
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent warn) -> warn(warn.getEntity()));

        // TODO: Re-enable config registration once NeoForge 1.21 config bootstrap is reintroduced.

        NeoForge.EVENT_BUS.register(this);

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
