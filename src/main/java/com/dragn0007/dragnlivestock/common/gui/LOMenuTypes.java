package com.dragn0007.dragnlivestock.common.gui;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IForgeMenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.function.Supplier;

public class LOMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, LivestockOverhaul.MODID);
    public static final Supplier<MenuType<OHorseMenu>> O_HORSE_MENU = registerMenuType("ohorse_menu", OHorseMenu::new);
    public static final Supplier<MenuType<OMountMenu>> O_MOUNT_MENU = registerMenuType("omount_menu", OMountMenu::new);
    public static final Supplier<MenuType<OMuleMenu>> O_MULE_MENU = registerMenuType("omule_menu", OMuleMenu::new);
    public static final Supplier<MenuType<ODonkeyMenu>> O_DONKEY_MENU = registerMenuType("odonkey_menu", ODonkeyMenu::new);
    public static final Supplier<MenuType<OCamelMenu>> O_CAMEL_MENU = registerMenuType("ocamel_menu", OCamelMenu::new);
    public static final Supplier<MenuType<CaribouMenu>> O_CARIBOU_MENU = registerMenuType("ocaribou_menu", CaribouMenu::new);
    public static final Supplier<MenuType<OxMenu>> OX_MENU = registerMenuType("ox_menu", OxMenu::new);
    public static final Supplier<MenuType<UnicornMenu>> UNICORN_MENU = registerMenuType("unicorn_menu", UnicornMenu::new);

    public static final Supplier<MenuType<HugeWagonMenu>> HUGE_INVENTORY_WAGON = registerMenuType("huge_wagon", HugeWagonMenu::new); //104
    public static final Supplier<MenuType<LargeWagonMenu>> LARGE_INVENTORY_WAGON = registerMenuType("large_wagon", LargeWagonMenu::new); //54
    public static final Supplier<MenuType<DefaultWagonMenu>> DEFAULT_INVENTORY_WAGON = registerMenuType("default_wagon", DefaultWagonMenu::new); //36
    public static final Supplier<MenuType<SmallWagonMenu>> SMALL_INVENTORY_WAGON = registerMenuType("small_wagon", SmallWagonMenu::new); //18
    public static final Supplier<MenuType<TinyWagonMenu>> TINY_INVENTORY_WAGON = registerMenuType("tiny_wagon", TinyWagonMenu::new); //9
    public static final Supplier<MenuType<LumberWagonMenu>> LUMBER_WAGON = registerMenuType("lumber_wagon", LumberWagonMenu::new);
    public static final Supplier<MenuType<MiningCartMenu>> MINING_CART = registerMenuType("mining_cart", MiningCartMenu::new);

    public static <T extends AbstractContainerMenu>Supplier<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
