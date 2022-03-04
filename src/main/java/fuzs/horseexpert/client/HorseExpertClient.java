package fuzs.horseexpert.client;

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
import net.minecraft.world.entity.player.Player;
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

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertClient {

    private static final Screen DUMMY = new Screen(TextComponent.EMPTY) { };
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
                        DUMMY.init(minecraft, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight());
//                        DUMMY.renderTooltip(mStack, );
                        animal.getAttributeValue(Attributes.MOVEMENT_SPEED);
                        animal.getAttributeValue(Attributes.MAX_HEALTH);
                        animal.getAttributeValue(Attributes.JUMP_STRENGTH);
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
