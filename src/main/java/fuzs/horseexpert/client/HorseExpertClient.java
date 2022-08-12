package fuzs.horseexpert.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.handler.HorseAttributeOverlayHandler;
import fuzs.horseexpert.client.handler.NameplateRenderingHandler;
import fuzs.horseexpert.client.init.ModClientRegistry;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.registry.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        MinecraftForgeClient.registerTooltipComponentFactory(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
        HorseAttributeOverlayHandler.touch();
        registerHandlers();
        EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
        CuriosRendererRegistry.register(ModRegistry.MONOCLE_ITEM.get(), () -> new ICurioRenderer() {
            private static final ResourceLocation MONOCLE_LOCATION = new ResourceLocation(HorseExpert.MOD_ID, "textures/entity/monocle.png");

            /**
             * nice trick from artifacts mod
             */
            private final HumanoidModel<LivingEntity> monocleModel = new HumanoidModel<>(entityModels.bakeLayer(ModClientRegistry.PLAYER_MONOCLE)) {

                @Override
                protected Iterable<ModelPart> headParts() {
                    return ImmutableList.of(this.head);
                }

                @Override
                protected Iterable<ModelPart> bodyParts() {
                    return ImmutableList.of();
                }
            };

            @SuppressWarnings("unchecked")
            @Override
            public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                ((HumanoidModel<T>) renderLayerParent.getModel()).copyPropertiesTo((HumanoidModel<T>) this.monocleModel);
                VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, ModRenderType.armorCutoutTranslucentNoCull(MONOCLE_LOCATION), false, stack.hasFoil());
                this.monocleModel.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        });
    }

    private static void registerHandlers() {
        NameplateRenderingHandler nameplateRenderingHandler = new NameplateRenderingHandler();
        MinecraftForge.EVENT_BUS.addListener(nameplateRenderingHandler::onRenderNameplate);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        // make this a little bigger than armor, so it renders outside (taken from piglin armor renderer)
        evt.registerLayerDefinition(ModClientRegistry.PLAYER_MONOCLE, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.02F), 0.0F), 64, 32));
    }
}
