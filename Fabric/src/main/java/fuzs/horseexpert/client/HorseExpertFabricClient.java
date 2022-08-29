package fuzs.horseexpert.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(HorseExpert.MOD_ID).accept(new HorseExpertClient());
        registerHandlers();
        TrinketRendererRegistry.registerRenderer(ModRegistry.MONOCLE_ITEM.get(), (ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) -> {
            // we want a custom renderer type like on Forge (for preserving texture transparency), but Fabric Api does not seem to have an option to create that, and mixins are weird because there is a protected class in the constructor we need to access, not sure how to deal with that
            // right now only issue seems to be that it renders behind the helmet layer in the survival inventory
            MonocleRenderer.get().render(itemStack, poseStack, entityModel, multiBufferSource, i, textureLocation -> RenderType.entityTranslucent(textureLocation, false));
        });
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {

            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(HorseExpert.MOD_ID, "monocle_model");
            }

            @Override
            public Collection<ResourceLocation> getFabricDependencies() {
                return ImmutableList.of(ResourceReloadListenerKeys.MODELS);
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
                return preparationBarrier.wait(Unit.INSTANCE).thenRunAsync(() -> {
                    EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                    MonocleRenderer.bakeModel(entityModels);
                }, executor2);
            }
        });
    }

    private static void registerHandlers() {
        HudRenderCallback.EVENT.register((PoseStack matrixStack, float tickDelta) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Gui gui = minecraft.gui;
            // Forge has a dedicated method for this, so here it gets quite big
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.enableTexture();
            RenderSystem.setShaderTexture(0, Gui.GUI_ICONS_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            gui.setBlitOffset(-90);
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            AttributeOverlayHandler.renderAttributeOverlay(minecraft, matrixStack, screenWidth, screenHeight);
        });
    }
}