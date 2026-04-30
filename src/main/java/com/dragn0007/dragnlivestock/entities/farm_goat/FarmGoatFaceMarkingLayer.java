package com.dragn0007.dragnlivestock.entities.farm_goat;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class FarmGoatFaceMarkingLayer extends GeoRenderLayer<FarmGoat> {
    public FarmGoatFaceMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, FarmGoat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (!animatable.isBaby()) {
            RenderType renderMarkingType = RenderType.entityCutout(animatable.getFaceOverlayLocation());
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0d, 0.0d, 0.0d);
            poseStack.popPose();
            getRenderer().reRender(getDefaultBakedModel(animatable),
                    poseStack,
                    bufferSource,
                    animatable,
                    renderMarkingType,
                    bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        }
    }

    public enum Overlay {
        NONE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/sheep/overlay/none.png")),
        BALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/bald.png")),
        BIG_BLAZE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/big_blaze.png")),
        BLAZE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/blaze.png")),
        BOER_STRIPE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/boer_stripe.png")),
        BROCKLES(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/brockles.png")),
        FROSTED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/frosted.png")),
        LIPSTICK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/lipstick.png")),
        LIPSTICK_BROCKLES(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/lipstick_brockles.png")),
        POLL_SPOT(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/poll_spot.png")),
        RED_CHEEK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/red_cheek.png")),
        SNIP(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/snip.png")),
        STAR(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/farm_goat/face_overlay/star.png")),
        ;

        //Add new entries to bottom when mod is public, else goats will change textures during update.

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
