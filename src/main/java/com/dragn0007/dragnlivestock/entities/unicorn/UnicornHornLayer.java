package com.dragn0007.dragnlivestock.entities.unicorn;

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

public class UnicornHornLayer extends GeoRenderLayer<Unicorn> {
    public UnicornHornLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Unicorn animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType renderMarkingType = RenderType.entityCutout(((Unicorn)animatable).getHornTextureResource());
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderMarkingType,
                bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    public enum Overlay {

        BLUE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/blue.png")),
        DIAMOND(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/diamond.png")),
        EMERALD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/emerald.png")),
        GREEN(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/green.png")),
        LAPIS(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/lapis.png")),
        PEARL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/pearl.png")),
        PINK(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/pink.png")),
        YELLOW(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/yellow.png")),

        FIRE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/fire.png")),
        GOLD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/gold.png")),
        MANGROVE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/mangrove.png")),
        NETHERITE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/netherite.png")),
        RED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/red.png")),
        REDSTONE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/redstone.png")),
        WARPED(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/warped.png")),
        NAVY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/navy.png")),

        END_CRYSTAL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/end_crystal.png")),
        ENDER_DRAGON(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/ender_dragon.png")),
        ENDER_EYE(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/ender_eye.png")),
        ENDER_PEARL(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/ender_pearl.png")),
        END_GATEWAY(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/end_gateway.png")),
        END_ROD(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/end_rod.png")),
        PURPUR(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/purpur.png")),
        VOID(ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/unicorn/horn/void.png"));

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
