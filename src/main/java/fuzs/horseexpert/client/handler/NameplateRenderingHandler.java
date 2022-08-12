package fuzs.horseexpert.client.handler;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.registry.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Optional;

public class NameplateRenderingHandler {

    public void onRenderNameplate(final RenderNameplateEvent evt) {
        if (!evt.getContent().getString().isEmpty()) {
            EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            if (evt.getEntity() instanceof AbstractHorse horse && entityRenderDispatcher.camera.getEntity() instanceof Player player) {
                if (!player.isSpectator() && CuriosApi.getCuriosHelper().findFirstCurio(player, ModRegistry.MONOCLE_ITEM.get()).isPresent() && (!HorseExpert.CONFIG.client().requiresSneaking || player.isShiftKeyDown())) {
                    if (!HorseExpert.CONFIG.client().mustBeTamed || horse.isTamed()) {
                        PoseStack poseStack = evt.getPoseStack();
                        poseStack.pushPose();
                        poseStack.translate(0.0D, evt.getEntity().getBbHeight() + 1.0, 0.0D);
                        poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
                        poseStack.scale(-0.025F, -0.025F, 0.025F);
//                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                        Font font = evt.getEntityRenderer().getFont();
                        float f2 = (float)(-font.width(evt.getContent()) / 2);
                        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                        int j = (int)(f1 * 255.0F) << 24;
                        font.drawInBatch(evt.getContent(), f2, (float)0, 553648127, false, poseStack.last().pose(), evt.getMultiBufferSource(), true, j, evt.getPackedLight());
                        renderHorseAttributeTooltips(HorseAttributeOverlayHandler.SCREEN_INSTANCE, poseStack, 0, 0, horse, evt.getMultiBufferSource());
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    public static void renderHorseAttributeTooltips(Screen screenInstance, PoseStack mStack, int screenWidth, int screenHeight, AbstractHorse animal, MultiBufferSource multiBufferSource) {
        List<Optional<TooltipComponent>> tooltipComponents = Lists.newArrayList();
        tooltipComponents.add(HorseAttributeTooltip.healthTooltip(animal.getAttributeValue(Attributes.MAX_HEALTH)));
        if (!(animal instanceof Llama) || HorseExpert.CONFIG.client().allLlamaAttributes) {
            tooltipComponents.add(HorseAttributeTooltip.speedTooltip(animal.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            tooltipComponents.add(HorseAttributeTooltip.jumpHeightTooltip(animal.getAttributeValue(Attributes.JUMP_STRENGTH)));
        }
        if (animal instanceof Llama llama) {
            tooltipComponents.add(HorseAttributeTooltip.strengthTooltip(llama.getStrength()));
        }
        int posX = 0;
        int posY = - (tooltipComponents.size() * 29 - 3) / 2;
        MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        for (int i = 0; i < tooltipComponents.size(); i++) {
//            screenInstance.renderTooltip(mStack, Lists.newArrayList(TextComponent.EMPTY), tooltipComponents.get(i), posX, posY + 29 * i);
            final int ii = i;
            tooltipComponents.get(i).map(ClientTooltipComponent::create).ifPresent(t -> {
                t.renderText(screenInstance.getMinecraft().font, posX, posY + 29 * ii, mStack.last().pose(), (MultiBufferSource.BufferSource) multiBufferSource);
                t.renderImage(screenInstance.getMinecraft().font, posX, posY + 29 * ii, mStack, screenInstance.getMinecraft().getItemRenderer(), 0);
            });
        }
    }
}
