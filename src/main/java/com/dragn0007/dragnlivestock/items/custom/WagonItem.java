package com.dragn0007.dragnlivestock.items.custom;

import com.dragn0007.dragnlivestock.client.WagonItemRenderer;
import com.dragn0007.dragnlivestock.entities.wagon.base.AbstractWagon;
import com.dragn0007.dragnlivestock.entities.wagon.base.AbstractWagon.Type;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class WagonItem extends Item {

    private final Supplier<? extends EntityType<? extends AbstractWagon>> entityType;

    public WagonItem(Supplier<? extends EntityType<? extends AbstractWagon>> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (context.getPlayer() == null) {
            return InteractionResult.PASS;
        }

        AbstractWagon wagon = entityType.get().create(level);
        if (wagon == null) {
            context.getPlayer().displayClientMessage(Component.literal("Failed to place wagon.").withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }

        wagon.setPos(Vec3.atBottomCenterOf(context.getClickedPos().above()).add(0, 0.05D, 0));
        wagon.setYRot(context.getPlayer().getYHeadRot());
        wagon.owner = context.getPlayer().getUUID();

        Type type = Type.OAK;
        CustomData customData = context.getItemInHand().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag nbt = customData.copyTag();
        if (nbt.contains("type")) {
            int rawType = nbt.getInt("type");
            if (rawType >= 0 && rawType < Type.values().length) {
                type = Type.values()[rawType];
            }
        }
        wagon.setWoodType(type);

        level.addFreshEntity(wagon);
        if (!context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WagonItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new WagonItemRenderer(entityType.get());
                }
                return renderer;
            }
        });
    }

    public static ItemStack setupNbt(ItemStack stack, Type type) {
        if (type != null) {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("type", type.ordinal());
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        }
        return stack;
    }

}
