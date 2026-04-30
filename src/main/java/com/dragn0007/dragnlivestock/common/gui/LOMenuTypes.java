package com.dragn0007.dragnlivestock.common.gui;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, LivestockOverhaul.MODID);

    public static final Supplier<MenuType<OHorseMenu>> O_HORSE_MENU =
            MENU_TYPES.register("o_horse_menu", () -> IMenuTypeExtension.create(OHorseMenu::new));
    public static final Supplier<MenuType<OxMenu>> OX_MENU =
            MENU_TYPES.register("ox_menu", () -> IMenuTypeExtension.create(OxMenu::new));
    public static final Supplier<MenuType<OMountMenu>> O_MOUNT_MENU =
            MENU_TYPES.register("o_mount_menu", () -> IMenuTypeExtension.create(OMountMenu::new));
    public static final Supplier<MenuType<OCamelMenu>> O_CAMEL_MENU =
            MENU_TYPES.register("o_camel_menu", () -> IMenuTypeExtension.create(OCamelMenu::new));

    public static final Supplier<MenuType<OMuleMenu>> O_MULE_MENU =
            MENU_TYPES.register("o_mule_menu", () -> IMenuTypeExtension.create(OMuleMenu::new));
    public static final Supplier<MenuType<ODonkeyMenu>> O_DONKEY_MENU =
            MENU_TYPES.register("o_donkey_menu", () -> IMenuTypeExtension.create(ODonkeyMenu::new));
    public static final Supplier<MenuType<CaribouMenu>> O_CARIBOU_MENU =
            MENU_TYPES.register("o_caribou_menu", () -> IMenuTypeExtension.create(CaribouMenu::new));
    public static final Supplier<MenuType<UnicornMenu>> UNICORN_MENU =
            MENU_TYPES.register("unicorn_menu", () -> IMenuTypeExtension.create(UnicornMenu::new));

    public static final Supplier<MenuType<DefaultWagonMenu>> DEFAULT_INVENTORY_WAGON =
            MENU_TYPES.register("default_inventory_wagon", () -> IMenuTypeExtension.create(DefaultWagonMenu::new));
    public static final Supplier<MenuType<SmallWagonMenu>> SMALL_INVENTORY_WAGON =
            MENU_TYPES.register("small_inventory_wagon", () -> IMenuTypeExtension.create(SmallWagonMenu::new));
    public static final Supplier<MenuType<TinyWagonMenu>> TINY_INVENTORY_WAGON =
            MENU_TYPES.register("tiny_inventory_wagon", () -> IMenuTypeExtension.create(TinyWagonMenu::new));
    public static final Supplier<MenuType<LargeWagonMenu>> LARGE_INVENTORY_WAGON =
            MENU_TYPES.register("large_inventory_wagon", () -> IMenuTypeExtension.create(LargeWagonMenu::new));
    public static final Supplier<MenuType<HugeWagonMenu>> HUGE_INVENTORY_WAGON =
            MENU_TYPES.register("huge_inventory_wagon", () -> IMenuTypeExtension.create(HugeWagonMenu::new));
    public static final Supplier<MenuType<LumberWagonMenu>> LUMBER_WAGON =
            MENU_TYPES.register("lumber_wagon", () -> IMenuTypeExtension.create(LumberWagonMenu::new));
    public static final Supplier<MenuType<MiningCartMenu>> MINING_CART =
            MENU_TYPES.register("mining_cart", () -> IMenuTypeExtension.create(MiningCartMenu::new));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
