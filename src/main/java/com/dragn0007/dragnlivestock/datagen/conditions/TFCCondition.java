package com.dragn0007.dragnlivestock.datagen.conditions;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.fml.ModList;

public final class TFCCondition implements ICondition {
    public static final TFCCondition INSTANCE = new TFCCondition();
    public static final MapCodec<TFCCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    private TFCCondition() {}

    @Override
    public boolean test(IContext context) {
        return ModList.get().isLoaded("tfc");
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
