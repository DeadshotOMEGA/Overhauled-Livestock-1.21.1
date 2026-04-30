package com.dragn0007.dragnlivestock.entities.camel;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
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
public class OCamelCarpetLayer extends GeoRenderLayer<OCamel> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/white.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/orange.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/magenta.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/light_blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/yellow.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/lime.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/pink.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/light_grey.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/cyan.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/purple.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/blue.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/brown.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/green.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/red.png"),
            ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/carpet/black.png")
    };

    public OCamelCarpetLayer(GeoRenderer<OCamel> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, OCamel animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ItemStack itemStack = animatable.getDecorItem();

        if (LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
            return;
        }

        if(!itemStack.isEmpty()) {
            ResourceLocation resourceLocation = null;
            if (!itemStack.is(LOTags.Items.CAMEL_ARMOR)) {
                if (itemStack.is(LOTags.Items.CARPET_BLANKETS)) {
                    DyeColor dyeColor = ((WoolCarpetBlock) Block.byItem(itemStack.getItem())).getColor();
                    resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
                } else {
                    DyeColor dyeColor = ((DyeItem) itemStack.getItem()).getDyeColor();
                    resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
                }
            } else if (itemStack.is(LOTags.Items.CAMEL_ARMOR) && !LivestockOverhaulClientConfig.SIMPLE_MODELS.get()) {
                String armorId = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();
                resourceLocation = ResourceLocation.fromNamespaceAndPath(LivestockOverhaul.MODID, "textures/entity/camel/armor/" + armorId + ".png");
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
                    bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    0xFFFFFFFF);
        }
    }
}
