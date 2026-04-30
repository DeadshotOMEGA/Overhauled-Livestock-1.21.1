package com.dragn0007.dragnlivestock.items.custom;

import com.dragn0007.dragnlivestock.entities.EntityTypes;
import com.dragn0007.dragnlivestock.entities.salmon.OSalmon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SalmonRoeItem extends Item {

   public SalmonRoeItem(Properties properties) {
      super(properties);
   }

   @Override
   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (!(level instanceof ServerLevel serverLevel)) {
         return InteractionResult.SUCCESS;
      }

      Player player = context.getPlayer();
      if (player == null) {
         return InteractionResult.PASS;
      }

      ItemStack stack = context.getItemInHand();
      Vec3 targetPos = player.getEyePosition().add(player.getLookAngle().scale(3.0D));

      Optional<OSalmon> spawned = spawnFishFry(serverLevel, targetPos);
      if (spawned.isEmpty()) {
         return InteractionResult.PASS;
      }

      if (!player.getAbilities().instabuild) {
         stack.shrink(1);
      }

      return InteractionResult.CONSUME;
   }

   private Optional<OSalmon> spawnFishFry(ServerLevel level, Vec3 pos) {
      OSalmon fish = EntityTypes.O_SALMON_ENTITY.get().create(level);
      if (fish == null) {
         return Optional.empty();
      }

      fish.moveTo(pos.x(), pos.y(), pos.z(), 0.0F, 0.0F);
      level.addFreshEntity(fish);
      return Optional.of(fish);
   }
}
