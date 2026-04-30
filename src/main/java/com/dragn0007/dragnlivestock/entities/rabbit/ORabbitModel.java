package com.dragn0007.dragnlivestock.entities.rabbit;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class ORabbitModel extends DefaultedEntityGeoModel<ORabbit> {

    public ORabbitModel() {
        super(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "o_rabbit"), true);
    }

    @Override
    public void setCustomAnimations(ORabbit animatable, long instanceId, AnimationState<ORabbit> animationState) {

        GeoBone neck = getAnimationProcessor().getBone("neck");
        GeoBone head = getAnimationProcessor().getBone("head");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        BLACK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/black.png")),
        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/blue.png")),
        BROWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/brown.png")),
        CHOCOLATE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/chocolate.png")),
        GOLD_RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/gold_red.png")),
        LILAC(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/lilac.png")),
        MAHOGANY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/mahogany.png")),
        RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/red.png")),
        SEAL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/seal.png")),
        SILVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/silver.png")),
        TAN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/tan.png")),
        WHITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/white.png")),
        CLOVER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/clover_brown.png")),
        JACKIE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/jackie.png")),
        CREAM(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/cream.png")),
        BLUE_TORTISHELL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/blue_tortishell.png")),
        CHESTNUT_AGOUTI(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/chestnut_agouti.png")),
        CHOCOLATE_AGOUTI(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/chocolate_agouti.png")),
        CHOCOLATE_TORTISHELL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/chocolate_tortishell.png")),
        DARK_CHIN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/dark_chin.png")),
        FAWN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/fawn.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/gold.png")),
        GOLDEN_STEEL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/golden_steel.png")),
        LIGHT_CHIN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/light_chin.png")),
        LYNX(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/lynx.png")),
        SABLE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/sable.png")),
        SALLANDER(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/sallander.png")),
        SMOKE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/smoke.png")),
        STEEL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/steel.png")),
        TORTISHELL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/rabbit/tortishell.png")),
        ;

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "animations/o_rabbit.animation.json");

    @Override
    public ResourceLocation getModelResource(ORabbit object) {
        return RabbitBreed.Breed.breedFromOrdinal(object.getBreed()).resourceLocation;
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();


    @Override
    public ResourceLocation getTextureResource(ORabbit object) {
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(ORabbit animatable) {
        return ANIMATION;
    }
}
