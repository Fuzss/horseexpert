package fuzs.horseexpert.client.handler;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.registry.ModRegistry;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;

import java.util.List;
import java.util.Optional;

public class HorseAttributeOverlayHandler {
    public static final IIngameOverlay HORSE_INFO_ELEMENT;

    static {
        Screen screenInstance = new Screen(TextComponent.EMPTY) {};
        Minecraft minecraft = Minecraft.getInstance();
        // prevent tooltips from being rendered to the left when they would otherwise reach beyond screen border
        screenInstance.init(minecraft, Integer.MAX_VALUE, Integer.MAX_VALUE);
        HORSE_INFO_ELEMENT = OverlayRegistry.registerOverlayBelow(ForgeIngameGui.CROSSHAIR_ELEMENT, new ResourceLocation(HorseExpert.MOD_ID, "horse_info").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (!minecraft.options.hideGui) {
                gui.setupOverlayRenderState(true, false);
                gui.setBlitOffset(-90);
                Options options = minecraft.options;
                if (options.getCameraType().isFirstPerson() && minecraft.crosshairPickEntity instanceof AbstractHorse animal) {
                    if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR && minecraft.cameraEntity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD).is(ModRegistry.MONOCLE_ITEM.get())) {
                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        renderHorseAttributeTooltips(screenInstance, mStack, screenWidth, screenHeight, animal);
                    }
                }
            }});
    }

    private static void renderHorseAttributeTooltips(Screen screenInstance, PoseStack mStack, int screenWidth, int screenHeight, AbstractHorse animal) {
        List<Optional<TooltipComponent>> tooltipComponents = Lists.newArrayList();
        tooltipComponents.add(HorseAttributeTooltip.healthTooltip(animal.getAttributeValue(Attributes.MAX_HEALTH)));
        if (animal instanceof Llama llama) {
            tooltipComponents.add(HorseAttributeTooltip.strengthTooltip(llama.getStrength()));
        } else {
            tooltipComponents.add(HorseAttributeTooltip.speedTooltip(animal.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            tooltipComponents.add(HorseAttributeTooltip.jumpHeightTooltip(animal.getAttributeValue(Attributes.JUMP_STRENGTH)));
        }
        int posX = screenWidth / 2 - 12 + 22 + HorseExpert.CONFIG.client().offsetX;
        int posY = screenHeight / 2 + 15 - (tooltipComponents.size() * 29 - 3) / 2 + HorseExpert.CONFIG.client().offsetY;
        for (int i = 0; i < tooltipComponents.size(); i++) {
            screenInstance.renderTooltip(mStack, Lists.newArrayList(TextComponent.EMPTY), tooltipComponents.get(i), posX, posY + 29 * i);
        }
    }

    public static void touch() {

    }
}
