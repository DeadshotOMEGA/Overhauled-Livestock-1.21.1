package com.dragn0007.dragnlivestock.blocks.custom;

import com.dragn0007.dragnlivestock.blocks.LOBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.MapCodec;

public class RawLlamaCheese extends CheeseBase {

    public static final MapCodec<RawLlamaCheese> CODEC = simpleCodec(properties -> new RawLlamaCheese());

    @Override
    protected MapCodec<? extends CheeseBase> codec() {
        return CODEC;
    }

    public RawLlamaCheese() {
        super();
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeTimeProperty(), 0));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource source) {

        if (!level.isAreaLoaded(pos, 1)) return;

        int i = this.getAgeTime(state);

        if (i <= this.getMaxAgeTime()) {
            BlockState newState = this.getStateForAgeTime(i + 1);
            level.setBlockAndUpdate(pos, newState);
        }

        if (i >= this.getMaxAgeTime()) {
            level.setBlockAndUpdate(pos, LOBlocks.LLAMA_CHEESE.get().defaultBlockState());
        }

    }

}
