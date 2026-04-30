package com.dragn0007.dragnlivestock.common.event;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

public class ForgeEvent {
    // Legacy name retained intentionally for migration continuity with existing registrations.

    @SubscribeEvent
    public static void leashHandler(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (event.getTarget() instanceof Mob mob) {
            if (stack.is(Items.LEAD) && player.isShiftKeyDown()) {
                List<Mob> currentlyLeading = mob.level().getEntitiesOfClass(
                        Mob.class,
                        player.getBoundingBox().inflate(10.0D),
                        animal -> animal.getLeashHolder() == player
                );

                if (!currentlyLeading.isEmpty()) {
                    for (Mob sourceMob : currentlyLeading) {
                        mob.setLeashedTo(sourceMob, true);
                    }
                } else {
                    mob.setLeashedTo(player, true);
                }
            }

            if (mob.isLeashed() && !(mob.getLeashHolder() instanceof Player) && stack.is(Items.SHEARS)) {
                mob.dropLeash(true, !player.isCreative());
                mob.setLeashedTo(null, true);
            }
        }
    }
}
