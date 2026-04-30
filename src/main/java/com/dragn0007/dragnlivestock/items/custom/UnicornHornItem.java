package com.dragn0007.dragnlivestock.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class UnicornHornItem extends Item {

    public MobEffectInstance[] effectInstances;

    public UnicornHornItem(MobEffectInstance... effectInstances) {
        super(new Properties().stacksTo(1).durability(30));
        this.effectInstances = effectInstances;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC.value();
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC.value();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 32;
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player playerEntity, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, playerEntity, hand);
    }
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            for (int i = 0; i < effectInstances.length; i++) {
                pLivingEntity.addEffect(effectInstances[i]);
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);

    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext pContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, pContext, tooltip, flag);

        for (MobEffectInstance effectInstance : effectInstances) {
            String effectName = effectInstance.getEffect().value().getDisplayName().getString();
            String amplifier = String.format(" Level %d", effectInstance.getAmplifier() + 1);
            String text = effectName + amplifier;

            tooltip.add(Component.translatable(text).withStyle(ChatFormatting.GOLD));
        }
    }
}
