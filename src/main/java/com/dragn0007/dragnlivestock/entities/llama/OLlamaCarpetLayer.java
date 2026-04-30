package com.dragn0007.dragnlivestock.entities.llama;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class OLlamaCarpetLayer extends GeoRenderLayer<OLlama> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/white.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/orange.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/magenta.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/light_blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/yellow.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/lime.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/pink.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/light_grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/cyan.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/purple.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/brown.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/green.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/red.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/llama/carpet/black.png")
    };

    public OLlamaCarpetLayer(GeoRenderer<OLlama> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, OLlama animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        DyeColor dyeColor = animatable.getSwag();
        ResourceLocation resourceLocation = null;

        if (dyeColor != null) {
            resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
        }

        if (resourceLocation == null) {
            return;
        }

        RenderType renderType1 = RenderType.entityCutout(resourceLocation);
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderType1,
                bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }
}