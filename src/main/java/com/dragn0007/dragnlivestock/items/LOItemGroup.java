package com.dragn0007.dragnlivestock.items;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOItemGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LivestockOverhaul.MODID);

    public static final Supplier<CreativeModeTab> LIVESTOCK_OVERHAUL_GROUP = CREATIVE_MODE_TABS.register("overhauled_livestock",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(LOItems.LIVESTOCK_OVERHAUL.get()))
                    .title(Component.translatable("itemGroup.overhauled_livestock"))
                    .displayItems((displayParameters, output) -> {
                        LOItems.ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
