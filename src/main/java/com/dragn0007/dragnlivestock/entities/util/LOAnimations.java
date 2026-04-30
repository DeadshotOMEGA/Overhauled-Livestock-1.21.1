package com.dragn0007.dragnlivestock.entities.util;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;

public class LOAnimations {
    public static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");
    public static <T extends LivingEntity & GeoAnimatable> AnimationController<T> genericAttackAnimation(T animatable, RawAnimation attackAnimation) {
        return new AnimationController<>(animatable, "attack", 1, state -> {
            if (animatable.swinging)
                return state.setAndContinue(attackAnimation);

            state.getController().forceAnimationReset();

            return PlayState.STOP;
        });
    }
}