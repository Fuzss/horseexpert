package fuzs.horseexpert.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertClient {

    private static final Screen DUMMY = new Screen(TextComponent.EMPTY) {};
    public static final IIngameOverlay HORSE_INFO_ELEMENT = OverlayRegistry.registerOverlayBelow(ForgeIngameGui.CROSSHAIR_ELEMENT, new ResourceLocation(HorseExpert.MOD_ID, "horse_info").toString(), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();
        if (!minecraft.options.hideGui)
        {
            gui.setupOverlayRenderState(true, false);
            gui.setBlitOffset(-90);

            Options options = minecraft.options;
            if (options.getCameraType().isFirstPerson()) {
                if (minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR && minecraft.cameraEntity instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD).is(ModRegistry.MONOCLE_ITEM.get())) {
                    if (minecraft.crosshairPickEntity instanceof AbstractHorse animal) {
                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        // prevent tooltips from being rendered to the left when they would otherwise reach beyond screen border
                        DUMMY.init(minecraft, Integer.MAX_VALUE, Integer.MAX_VALUE);
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
                            DUMMY.renderTooltip(mStack, Lists.newArrayList(TextComponent.EMPTY), tooltipComponents.get(i), posX, posY + 29 * i);
                        }
                    }
                }
        }
    }});

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        registerHandlers();
    }

    private static void registerHandlers() {

    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        MinecraftForgeClient.registerTooltipComponentFactory(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }
}
