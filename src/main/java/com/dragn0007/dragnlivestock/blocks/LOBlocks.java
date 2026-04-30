package com.dragn0007.dragnlivestock.blocks;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.blocks.custom.*;
import com.dragn0007.dragnlivestock.items.LOItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LOBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, LivestockOverhaul.MODID);

    public static final Supplier<Block> RABBIT_HUTCH = registerBlock("rabbit_hutch", RabbitHutch::new);

    public static final Supplier<Block> RAW_BEEF_JERKY_HANGING = registerBlock("raw_beef_jerky_hanging", RawBeefJerky::new);
    public static final Supplier<Block> BEEF_JERKY_HANGING = registerBlock("beef_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_CHICKEN_JERKY_HANGING = registerBlock("raw_chicken_jerky_hanging", RawChickenJerky::new);
    public static final Supplier<Block> CHICKEN_JERKY_HANGING = registerBlock("chicken_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_PORK_JERKY_HANGING = registerBlock("raw_pork_jerky_hanging", RawPorkJerky::new);
    public static final Supplier<Block> PORK_JERKY_HANGING = registerBlock("pork_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_MUTTON_JERKY_HANGING = registerBlock("raw_mutton_jerky_hanging", RawMuttonJerky::new);
    public static final Supplier<Block> MUTTON_JERKY_HANGING = registerBlock("mutton_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_FISH_JERKY_HANGING = registerBlock("raw_fish_jerky_hanging", RawFishJerky::new);
    public static final Supplier<Block> FISH_JERKY_HANGING = registerBlock("fish_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_GAME_JERKY_HANGING = registerBlock("raw_game_jerky_hanging", RawGameJerky::new);
    public static final Supplier<Block> GAME_JERKY_HANGING = registerBlock("game_jerky_hanging", DriedJerky::new);
    public static final Supplier<Block> RAW_GENERIC_JERKY_HANGING = registerBlock("raw_generic_jerky_hanging", RawGenericJerky::new);
    public static final Supplier<Block> GENERIC_JERKY_HANGING = registerBlock("generic_jerky_hanging", DriedJerky::new);

    public static final Supplier<Block> RAW_CHEESE = registerBlock("raw_cheese", RawCheese::new);
    public static final Supplier<Block> CHEESE = registerBlock("cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_SHEEP_CHEESE = registerBlock("raw_sheep_cheese", RawSheepCheese::new);
    public static final Supplier<Block> SHEEP_CHEESE = registerBlock("sheep_cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_LLAMA_CHEESE = registerBlock("raw_llama_cheese", RawLlamaCheese::new);
    public static final Supplier<Block> LLAMA_CHEESE = registerBlock("llama_cheese", AgedCheese::new);
    public static final Supplier<Block> RAW_GOAT_CHEESE = registerBlock("raw_goat_cheese", RawGoatCheese::new);
    public static final Supplier<Block> GOAT_CHEESE = registerBlock("goat_cheese", AgedCheese::new);

    public static <T extends Block> Supplier<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        LOItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
