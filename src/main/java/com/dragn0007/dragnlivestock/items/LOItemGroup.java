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
                        output.accept(LOItems.LIVESTOCK_OVERHAUL.get());
                        output.accept(LOItems.COVERED_WAGON.get());
                        output.accept(LOItems.LIVESTOCK_WAGON.get());
                        output.accept(LOItems.LUMBER_WAGON.get());
                        output.accept(LOItems.GOODS_CART.get());
                        output.accept(LOItems.DOG_SLED.get());
                        output.accept(LOItems.MINING_CART.get());
                        output.accept(LOItems.TRANSPORT_CART.get());
                        output.accept(LOItems.PLOW.get());
                        output.accept(LOItems.MOWER.get());
                        output.accept(LOItems.COUPE.get());
                        output.accept(LOItems.CABRIOLET.get());
                        output.accept(LOItems.SLEIGH.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
