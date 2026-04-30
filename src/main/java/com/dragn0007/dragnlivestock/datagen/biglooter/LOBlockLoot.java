package com.dragn0007.dragnlivestock.datagen.biglooter;

import com.dragn0007.dragnlivestock.blocks.LOBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class LOBlockLoot extends BlockLootSubProvider {
    public LOBlockLoot(HolderLookup.Provider registries) {
        super(Set.<Item>of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        // Keep datagen deterministic by emitting a drop mapping for every registered LO block.
        for (var entry : LOBlocks.BLOCKS.getEntries()) {
            dropSelf(entry.value());
        }
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return LOBlocks.BLOCKS.getEntries().stream().map(entry -> (Block) entry.value())::iterator;
    }
}
