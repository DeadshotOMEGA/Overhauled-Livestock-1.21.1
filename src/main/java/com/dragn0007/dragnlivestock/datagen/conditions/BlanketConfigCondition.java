package com.dragn0007.dragnlivestock.datagen.conditions;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

public final class BlanketConfigCondition implements ICondition {
    public static final BlanketConfigCondition INSTANCE = new BlanketConfigCondition();
    public static final MapCodec<BlanketConfigCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    private BlanketConfigCondition() {}

    @Override
    public boolean test(IContext context) {
        return LivestockOverhaulCommonConfig.ALLOW_SPECIAL_BLANKET_CRAFTING.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
