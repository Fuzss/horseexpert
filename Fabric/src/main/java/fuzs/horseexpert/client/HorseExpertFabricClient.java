package fuzs.horseexpert.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.handler.AttributeOverlayHandler;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class HorseExpertFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HorseExpert.MOD_ID, HorseExpertClient::new);
        registerHandlers();
        TrinketRendererRegistry.registerRenderer(ModRegistry.MONOCLE_ITEM.get(), (ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, LivingEntity livingEntity, float v, float v1, float v2, float v3, float v4, float v5) -> {
            MonocleRenderer.get().render(itemStack, poseStack, entityModel, multiBufferSource, i, ModRenderType::armorCutoutTranslucentNoCull);
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