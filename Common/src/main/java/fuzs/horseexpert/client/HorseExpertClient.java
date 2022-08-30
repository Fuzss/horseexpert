package fuzs.horseexpert.client;

import fuzs.horseexpert.client.gui.screens.inventory.tooltip.ClientHorseAttributeTooltip;
import fuzs.horseexpert.client.init.ModClientRegistry;
import fuzs.horseexpert.client.renderer.entity.layers.MonocleRenderer;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.Executor;

public class HorseExpertClient implements ClientModConstructor {

    @Override
    public void onRegisterClientTooltipComponents(ClientTooltipComponentsContext context) {
        context.registerClientTooltipComponent(HorseAttributeTooltip.class, ClientHorseAttributeTooltip::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.PLAYER_MONOCLE, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.02F), 0.0F), 64, 32));
    }

    @Override
    public void onRegisterClientReloadListeners(ClientReloadListenersContext context) {
        context.registerReloadListener("monocle_model", (PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) -> {
            return preparationBarrier.wait(Unit.INSTANCE).thenRunAsync(() -> {
                EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                MonocleRenderer.bakeModel(entityModels);
            }, executor2);
        });
    }
}
