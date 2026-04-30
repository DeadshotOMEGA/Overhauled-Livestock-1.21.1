package com.dragn0007.dragnlivestock.entities.goat;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class OGoatCarpetLayer extends GeoRenderLayer<OGoat> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/white.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/orange.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/magenta.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/light_blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/yellow.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/lime.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/pink.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/light_grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/cyan.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/purple.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/brown.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/green.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/red.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/goat/carpet/black.png")
    };

    public OGoatCarpetLayer(GeoRenderer<OGoat> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, OGoat animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ItemStack itemStack = animatable.getDecorItem();
        if(!itemStack.isEmpty()) {
            ResourceLocation resourceLocation;
            if(itemStack.is(LOTags.Items.CARPET_BLANKETS)) {
                DyeColor dyeColor = ((WoolCarpetBlock) Block.byItem(itemStack.getItem())).getColor();
                resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
            } else {
                DyeColor dyeColor = ((DyeItem)itemStack.getItem()).getDyeColor();
                resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
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
}
