package com.dragn0007.dragnlivestock.entities.cow.moobloom.potato;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PotatoMoobloomModel extends DefaultedEntityGeoModel<PotatoMoobloom> {

    public PotatoMoobloomModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "potato_moobloom"), true);
    }

    @Override
    public void setCustomAnimations(PotatoMoobloom animatable, long instanceId, AnimationState<PotatoMoobloom> animationState) {

        GeoBone neck = getAnimationProcessor().getBone("neck");
        GeoBone head = getAnimationProcessor().getBone("head");
        GeoBone left_ear = getAnimationProcessor().getBone("left_ear");
        GeoBone right_ear = getAnimationProcessor().getBone("right_ear");
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (neck != null) {
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        if (head != null) {
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        
    }

    public enum Variant {
        DEFAULT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/moobloom/potato.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/moobloom/crop_moobloom.geo.json");
    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_cow.animation.json");

    public static final ResourceLocation BABY_MODEL = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "geo/baby_o_cow.geo.json");
    @Override
    public ResourceLocation getModelResource(PotatoMoobloom object) {
        if(object.isBaby())
            return BABY_MODEL;
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(PotatoMoobloom object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(PotatoMoobloom animatable) {
        return ANIMATION;
    }
}

