package com.dragn0007.dragnlivestock.client.event;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.client.entities.wagon.WagonRenderer;
import com.dragn0007.dragnlivestock.client.gui.*;
import com.dragn0007.dragnlivestock.common.gui.LOMenuTypes;
import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.bee.OBeeRenderer;
import com.dragn0007.dragnlivestock.entities.camel.OCamelRender;
import com.dragn0007.dragnlivestock.entities.caribou.CaribouRender;
import com.dragn0007.dragnlivestock.entities.chicken.OChickenRender;
import com.dragn0007.dragnlivestock.entities.cod.OCodRender;
import com.dragn0007.dragnlivestock.entities.cow.OCowRender;
import com.dragn0007.dragnlivestock.entities.cow.mooshroom.OMooshroomRender;
import com.dragn0007.dragnlivestock.entities.donkey.ODonkeyRender;
import com.dragn0007.dragnlivestock.entities.farm_goat.FarmGoatRender;
import com.dragn0007.dragnlivestock.entities.frog.OFrogRender;
import com.dragn0007.dragnlivestock.entities.frog.food.GrubRender;
import com.dragn0007.dragnlivestock.entities.goat.OGoatRender;
import com.dragn0007.dragnlivestock.entities.horse.OHorseRender;
import com.dragn0007.dragnlivestock.entities.llama.OLlamaRender;
import com.dragn0007.dragnlivestock.entities.mule.OMuleRender;
import com.dragn0007.dragnlivestock.entities.pig.OPigRender;
import com.dragn0007.dragnlivestock.entities.rabbit.ORabbitRender;
import com.dragn0007.dragnlivestock.entities.salmon.OSalmonRender;
import com.dragn0007.dragnlivestock.entities.sheep.OSheepRender;
import com.dragn0007.dragnlivestock.entities.unicorn.UnicornRender;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class LivestockOverhaulClientEvent {

    private LivestockOverhaulClientEvent() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(LivestockOverhaulClientEvent::registerEntityRenderers);
        modEventBus.addListener(LivestockOverhaulClientEvent::registerMenuScreens);
        modEventBus.addListener(LivestockOverhaulClientEvent::registerKeyBindings);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypes.O_COD_ENTITY.get(), OCodRender::new);
        event.registerEntityRenderer(EntityTypes.O_SALMON_ENTITY.get(), OSalmonRender::new);
        event.registerEntityRenderer(EntityTypes.O_CHICKEN_ENTITY.get(), OChickenRender::new);
        event.registerEntityRenderer(EntityTypes.O_SHEEP_ENTITY.get(), OSheepRender::new);
        event.registerEntityRenderer(EntityTypes.O_PIG_ENTITY.get(), OPigRender::new);
        event.registerEntityRenderer(EntityTypes.O_RABBIT_ENTITY.get(), ORabbitRender::new);
        event.registerEntityRenderer(EntityTypes.O_BEE_ENTITY.get(), OBeeRenderer::new);
        event.registerEntityRenderer(EntityTypes.O_LLAMA_ENTITY.get(), OLlamaRender::new);
        event.registerEntityRenderer(EntityTypes.O_MOOSHROOM_ENTITY.get(), OMooshroomRender::new);
        event.registerEntityRenderer(EntityTypes.O_GOAT_ENTITY.get(), OGoatRender::new);
        event.registerEntityRenderer(EntityTypes.O_FROG_ENTITY.get(), OFrogRender::new);
        event.registerEntityRenderer(EntityTypes.GRUB_ENTITY.get(), GrubRender::new);
        event.registerEntityRenderer(EntityTypes.FARM_GOAT_ENTITY.get(), FarmGoatRender::new);
        event.registerEntityRenderer(EntityTypes.CARIBOU_ENTITY.get(), CaribouRender::new);

        event.registerEntityRenderer(EntityTypes.COVERED_WAGON.get(), context -> new WagonRenderer<>(context, "covered_wagon"));
        event.registerEntityRenderer(EntityTypes.LIVESTOCK_WAGON.get(), context -> new WagonRenderer<>(context, "livestock_wagon"));
        event.registerEntityRenderer(EntityTypes.LUMBER_WAGON.get(), context -> new WagonRenderer<>(context, "lumber_wagon"));
        event.registerEntityRenderer(EntityTypes.GOODS_CART.get(), context -> new WagonRenderer<>(context, "goods_cart"));
        event.registerEntityRenderer(EntityTypes.DOG_SLED.get(), context -> new WagonRenderer<>(context, "dog_sled"));
        event.registerEntityRenderer(EntityTypes.MINING_CART.get(), context -> new WagonRenderer<>(context, "mining_cart"));
        event.registerEntityRenderer(EntityTypes.TRANSPORT_CART.get(), context -> new WagonRenderer<>(context, "transport_cart"));
        event.registerEntityRenderer(EntityTypes.PLOW.get(), context -> new WagonRenderer<>(context, "plow"));
        event.registerEntityRenderer(EntityTypes.MOWER.get(), context -> new WagonRenderer<>(context, "mower"));
        event.registerEntityRenderer(EntityTypes.COUPE.get(), context -> new WagonRenderer<>(context, "coupe"));
        event.registerEntityRenderer(EntityTypes.CABRIOLET.get(), context -> new WagonRenderer<>(context, "cabriolet"));
        event.registerEntityRenderer(EntityTypes.SLEIGH.get(), context -> new WagonRenderer<>(context, "sleigh"));
        event.registerEntityRenderer(EntityTypes.O_COW_ENTITY.get(), OCowRender::new);
        event.registerEntityRenderer(EntityTypes.O_HORSE_ENTITY.get(), OHorseRender::new);
        event.registerEntityRenderer(EntityTypes.UNICORN_ENTITY.get(), UnicornRender::new);
        event.registerEntityRenderer(EntityTypes.O_MULE_ENTITY.get(), OMuleRender::new);
        event.registerEntityRenderer(EntityTypes.O_DONKEY_ENTITY.get(), ODonkeyRender::new);
        event.registerEntityRenderer(EntityTypes.O_CAMEL_ENTITY.get(), OCamelRender::new);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(LOMenuTypes.O_HORSE_MENU.get(), OHorseScreen::new);
        event.register(LOMenuTypes.OX_MENU.get(), OxScreen::new);
        event.register(LOMenuTypes.O_MOUNT_MENU.get(), OMountScreen::new);
        event.register(LOMenuTypes.O_MULE_MENU.get(), OMuleScreen::new);
        event.register(LOMenuTypes.O_DONKEY_MENU.get(), ODonkeyScreen::new);
        event.register(LOMenuTypes.O_CAMEL_MENU.get(), OCamelScreen::new);
        event.register(LOMenuTypes.O_CARIBOU_MENU.get(), CaribouScreen::new);
        event.register(LOMenuTypes.UNICORN_MENU.get(), UnicornScreen::new);
        event.register(LOMenuTypes.HUGE_INVENTORY_WAGON.get(), HugeWagonScreen::new);
        event.register(LOMenuTypes.DEFAULT_INVENTORY_WAGON.get(), DefaultWagonScreen::new);
        event.register(LOMenuTypes.SMALL_INVENTORY_WAGON.get(), SmallWagonScreen::new);
        event.register(LOMenuTypes.TINY_INVENTORY_WAGON.get(), TinyWagonScreen::new);
        event.register(LOMenuTypes.LUMBER_WAGON.get(), LumberWagonScreen::new);
        event.register(LOMenuTypes.MINING_CART.get(), MiningCartScreen::new);
    }

    public static final KeyMapping HORSE_SPEED_UP = new KeyMapping("key.dragnlivestock.horse_speed_up", InputConstants.KEY_LCONTROL, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_SLOW_DOWN = new KeyMapping("key.dragnlivestock.horse_slow_down", InputConstants.KEY_LALT, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_BOW = new KeyMapping("key.dragnlivestock.horse_bow", InputConstants.KEY_B, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_PIAFFE = new KeyMapping("key.dragnlivestock.horse_piaffe", InputConstants.KEY_P, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_SPANISH_WALK_TOGGLE = new KeyMapping("key.dragnlivestock.horse_spanish_walk_toggle", InputConstants.KEY_DOWN, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_WAVE = new KeyMapping("key.dragnlivestock.horse_wave", InputConstants.KEY_G, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_LEVADE = new KeyMapping("key.dragnlivestock.horse_levade", InputConstants.KEY_L, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping PLOW_MODE = new KeyMapping("key.dragnlivestock.plow_mode", InputConstants.KEY_SPACE, "key.dragnlivestock.categories.dragnlivestock");
    public static final KeyMapping HORSE_WALK_BACKWARDS = new KeyMapping("key.dragnlivestock.horse_walk_backwards", InputConstants.KEY_S, "key.dragnlivestock.categories.dragnlivestock");

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        KeyMapping[] keyMappings = {
                HORSE_SPEED_UP,
                HORSE_SLOW_DOWN,
                HORSE_BOW,
                HORSE_PIAFFE,
                HORSE_SPANISH_WALK_TOGGLE,
                HORSE_WAVE,
                HORSE_LEVADE,
                HORSE_WALK_BACKWARDS,
                PLOW_MODE
        };

        for (KeyMapping keyMapping : keyMappings) {
            event.register(keyMapping);
        }
    }
}
