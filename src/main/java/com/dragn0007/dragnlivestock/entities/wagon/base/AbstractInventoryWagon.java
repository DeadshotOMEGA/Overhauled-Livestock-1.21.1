package com.dragn0007.dragnlivestock.entities.wagon.base;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class AbstractInventoryWagon extends AbstractWagon implements MenuProvider {

    protected final NonNullList<ItemStack> inventory;
    protected final ItemStackHandler itemHandler;

    public AbstractInventoryWagon(EntityType<? extends AbstractWagon> type, Level level, double maxSpeed, double acceleration, float turnRate,
                                  int maxHealth, int capacity, Vec3[] draughtAnimalPositions, double wheelWidth, double wheelLength, Vec3[] riders) {
        super(type, level, maxSpeed, acceleration, turnRate, maxHealth, draughtAnimalPositions, wheelWidth, wheelLength, riders);
        this.inventory = NonNullList.withSize(capacity, ItemStack.EMPTY);
        this.itemHandler = new ItemStackHandler(inventory);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult superResult = super.interact(player, hand);
        if(superResult != InteractionResult.PASS)
            return superResult;

        if (isAlive() && !level().isClientSide && player.isSecondaryUseActive()) {
            ((ServerPlayer) player).openMenu(this, buf -> buf.writeInt(getId()));
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    public ItemStackHandler getItems() {
        return itemHandler;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ContainerHelper.saveAllItems(tag, inventory, registryAccess());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        ContainerHelper.loadAllItems(tag, inventory, registryAccess());
    }

    @Override
    protected void onDestroyed(boolean dropItem) {
        super.onDestroyed(dropItem);
        if(!level().isClientSide)
            Containers.dropContents(level(), blockPosition(), inventory);
    }

}
