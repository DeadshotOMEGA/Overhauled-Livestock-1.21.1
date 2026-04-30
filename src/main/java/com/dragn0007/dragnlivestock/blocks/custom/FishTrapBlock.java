package com.dragn0007.dragnlivestock.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FishTrapBlock extends Block {

    public static final MapCodec<FishTrapBlock> CODEC = simpleCodec(properties -> new FishTrapBlock());

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    public FishTrapBlock() {
        super(Properties.ofFullCopy(Blocks.SCAFFOLDING).noOcclusion());
    }
}
