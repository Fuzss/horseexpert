package fuzs.horseexpert.client.handler;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.config.ClientConfig;
import fuzs.horseexpert.core.ModServices;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.GameType;

import java.util.List;
import java.util.Optional;

public class AttributeOverlayHandler {
    private static final Screen SCREEN;

    static {
        // a dummy screen instance we need  for access to the tooltip rendering method
        SCREEN = new Screen(Component.empty()) {};
        // prevent tooltips from being rendered to the left when they would otherwise reach beyond screen border
        SCREEN.init(Minecraft.getInstance(), Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static void renderAttributeOverlay(Minecraft minecraft, PoseStack poseStack, int screenWidth, int screenHeight) {
        isRenderingTooltipsAllowed(minecraft).ifPresent(abstractHorse -> {
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            actuallyRenderAttributeOverlay(poseStack, screenWidth, screenHeight, abstractHorse);
        });
    }

    private static Optional<AbstractHorse> isRenderingTooltipsAllowed(Minecraft minecraft) {
        if (!minecraft.options.hideGui) {
            Options options = minecraft.options;
            if (options.getCameraType().isFirstPerson() && minecraft.crosshairPickEntity instanceof AbstractHorse animal) {
                if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR && minecraft.cameraEntity instanceof Player player && ModServices.ABSTRACTIONS.findEquippedItem(player, ModRegistry.MONOCLE_ITEM.get()).isPresent() && (!HorseExpert.CONFIG.get(ClientConfig.class).requiresSneaking || player.isShiftKeyDown())) {
                    if (!HorseExpert.CONFIG.get(ClientConfig.class).mustBeTamed || animal.isTamed()) {
                        return Optional.of(animal);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private static void actuallyRenderAttributeOverlay(PoseStack poseStack, int screenWidth, int screenHeight, AbstractHorse animal) {
        List<Optional<TooltipComponent>> tooltipComponents = buildTooltipComponents(animal);
        int posX = screenWidth / 2 - 12 + 22 + HorseExpert.CONFIG.get(ClientConfig.class).offsetX;
        int posY = screenHeight / 2 + 15 - (tooltipComponents.size() * 29 - 3) / 2 + HorseExpert.CONFIG.get(ClientConfig.class).offsetY;
        for (int i = 0; i < tooltipComponents.size(); i++) {
            SCREEN.renderTooltip(poseStack, Lists.newArrayList(Component.empty()), tooltipComponents.get(i), posX, posY + 29 * i);
        }
    }

    private static List<Optional<TooltipComponent>> buildTooltipComponents(AbstractHorse animal) {
        List<Optional<TooltipComponent>> tooltipComponents = Lists.newArrayList();
        tooltipComponents.add(HorseAttributeTooltip.healthTooltip(animal.getAttributeValue(Attributes.MAX_HEALTH)));
        if (!(animal instanceof Llama) || HorseExpert.CONFIG.get(ClientConfig.class).allLlamaAttributes) {
            tooltipComponents.add(HorseAttributeTooltip.speedTooltip(animal.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            tooltipComponents.add(HorseAttributeTooltip.jumpHeightTooltip(animal.getAttributeValue(Attributes.JUMP_STRENGTH)));
        }
        if (animal instanceof Llama llama) {
            tooltipComponents.add(HorseAttributeTooltip.strengthTooltip(llama.getStrength()));
        }
        return tooltipComponents;
    }
}
