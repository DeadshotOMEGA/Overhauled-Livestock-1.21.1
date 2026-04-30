package com.dragn0007.dragnlivestock.items.custom;

import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public class MountRegistryItem extends Item {
   private static final String KEY_MOUNT_NAME = "mount_name";
   private static final String KEY_HAS_MOUNT = "has_mount";
   private static final String KEY_OWNER_UUID = "ownerUUID";
   private static final String KEY_OWNER_NAME = "owner_name";

   public MountRegistryItem() {
      super(new Properties().stacksTo(1));
   }

   @Override
   public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
      CompoundTag tag = getCustomData(pStack);
      boolean hasMount = tag.getBoolean(KEY_HAS_MOUNT);

      if (pInteractionTarget instanceof AbstractOMount mount && mount.getOwner() == pPlayer && !hasMount) {
         tag.putString(KEY_MOUNT_NAME, mount.getName().getString());
         tag.putBoolean(KEY_HAS_MOUNT, true);
         tag.putString(KEY_OWNER_UUID, pPlayer.getUUID().toString());
         tag.putString(KEY_OWNER_NAME, pPlayer.getName().getString());
         setCustomData(pStack, tag);

         if (!pPlayer.level().isClientSide) {
            pPlayer.displayClientMessage(
                    Component.literal(mount.getName().getString() + " has been registered in your name!").withStyle(ChatFormatting.GOLD),
                    true
            );
         }
         return InteractionResult.sidedSuccess(pPlayer.level().isClientSide);
      }

      return super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand);
   }

   @Override
   public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
      CompoundTag tag = getCustomData(pStack);
      if (tag.getBoolean(KEY_HAS_MOUNT) && tag.contains(KEY_MOUNT_NAME) && tag.contains(KEY_OWNER_NAME)) {
         pTooltipComponents.add(Component.literal(tag.getString(KEY_MOUNT_NAME)).withStyle(ChatFormatting.GOLD));
         pTooltipComponents.add(Component.literal(tag.getString(KEY_OWNER_NAME)).withStyle(ChatFormatting.GRAY));
      } else {
         pTooltipComponents.add(Component.literal("No mount registered.").withStyle(ChatFormatting.GRAY));
      }
   }

   private static CompoundTag getCustomData(ItemStack stack) {
      CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      return customData.copyTag();
   }

   private static void setCustomData(ItemStack stack, CompoundTag tag) {
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }
}
