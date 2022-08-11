package fuzs.horseexpert.client;

import com.google.common.base.Function;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.handler.HorseAttributeOverlayHandler;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleLayer;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HorseExpert.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HorseExpertClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        MinecraftForgeClient.registerTooltipComponentFactory(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
        HorseAttributeOverlayHandler.touch();
    }

    @SubscribeEvent
    public static void onAddLayers(final EntityRenderersEvent.AddLayers evt) {
        Function<ModelLayerLocation, ModelPart> bakeLayer = Minecraft.getInstance().getEntityModels()::bakeLayer;
        evt.getSkins().stream().map(evt::getSkin).forEach(renderer -> {
            ((PlayerRenderer) renderer).addLayer(new MonocleLayer<>((PlayerRenderer) renderer, new HumanoidModel<>(bakeLayer.apply(ModelLayers.PLAYER_OUTER_ARMOR))));
        });
    }
}
