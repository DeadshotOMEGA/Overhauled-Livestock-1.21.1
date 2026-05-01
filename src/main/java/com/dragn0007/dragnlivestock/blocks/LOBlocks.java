package com.dragn0007.dragnlivestock.blocks;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.blocks.custom.*;
import com.dragn0007.dragnlivestock.items.LOItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.BLOCK, LivestockOverhaul.MODID);


//    public static final Supplier<Block> FISH_TRAP = registerBlock("fish_trap",
//            () -> new FishTrapBlock());


    public static final Supplier<Block> ACACIA_RABBIT_HUTCH = registerBlock("acacia_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> BAMBOO_RABBIT_HUTCH = registerBlock("bamboo_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> BIRCH_RABBIT_HUTCH = registerBlock("birch_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> CHERRY_RABBIT_HUTCH = registerBlock("cherry_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> CRIMSON_RABBIT_HUTCH = registerBlock("crimson_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> DARK_OAK_RABBIT_HUTCH = registerBlock("dark_oak_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> JUNGLE_RABBIT_HUTCH = registerBlock("jungle_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> MANGROVE_RABBIT_HUTCH = registerBlock("mangrove_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> OAK_RABBIT_HUTCH = registerBlock("oak_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> SPRUCE_RABBIT_HUTCH = registerBlock("spruce_rabbit_hutch", RabbitHutch::new);
    public static final Supplier<Block> WARPED_RABBIT_HUTCH = registerBlock("warped_rabbit_hutch", RabbitHutch::new);

    public static final Supplier<Block> RAW_CHEESE = registerBlockWithoutItem("raw_cheese", RawCheese::new);
    public static final Supplier<Block> CHEESE = registerBlockWithoutItem("cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_SHEEP_CHEESE = registerBlockWithoutItem("raw_sheep_cheese", RawSheepCheese::new);
    public static final Supplier<Block> SHEEP_CHEESE = registerBlockWithoutItem("sheep_cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_LLAMA_CHEESE = registerBlockWithoutItem("raw_llama_cheese", RawLlamaCheese::new);
    public static final Supplier<Block> LLAMA_CHEESE = registerBlockWithoutItem("llama_cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_GOAT_CHEESE = registerBlockWithoutItem("raw_goat_cheese", RawGoatCheese::new);
    public static final Supplier<Block> GOAT_CHEESE = registerBlockWithoutItem("goat_cheese", AgedCheese::new);

    public static final Supplier<Block> RAW_BEEF_JERKY_HANGING = registerBlockWithoutItem("raw_beef_jerky_hanging", RawBeefJerky::new);
    public static final Supplier<Block> BEEF_JERKY_HANGING = registerBlockWithoutItem("beef_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_CHICKEN_JERKY_HANGING = registerBlockWithoutItem("raw_chicken_jerky_hanging", RawChickenJerky::new);
    public static final Supplier<Block> CHICKEN_JERKY_HANGING = registerBlockWithoutItem("chicken_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_PORK_JERKY_HANGING = registerBlockWithoutItem("raw_pork_jerky_hanging", RawPorkJerky::new);
    public static final Supplier<Block> PORK_JERKY_HANGING = registerBlockWithoutItem("pork_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_MUTTON_JERKY_HANGING = registerBlockWithoutItem("raw_mutton_jerky_hanging", RawMuttonJerky::new);
    public static final Supplier<Block> MUTTON_JERKY_HANGING = registerBlockWithoutItem("mutton_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_FISH_JERKY_HANGING = registerBlockWithoutItem("raw_fish_jerky_hanging", RawFishJerky::new);
    public static final Supplier<Block> FISH_JERKY_HANGING = registerBlockWithoutItem("fish_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_GAME_JERKY_HANGING = registerBlockWithoutItem("raw_game_jerky_hanging", RawGameJerky::new);
    public static final Supplier<Block> GAME_JERKY_HANGING = registerBlockWithoutItem("game_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_GENERIC_JERKY_HANGING = registerBlockWithoutItem("raw_generic_jerky_hanging", RawGenericJerky::new);
    public static final Supplier<Block> GENERIC_JERKY_HANGING = registerBlockWithoutItem("generic_jerky_hanging", DriedJerky::new);


    public static <T extends Block>Supplier<T> registerBlockWithoutItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    public static <T extends Block>Supplier<T> registerBlock(String name, Supplier<T> block){
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        LOItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
