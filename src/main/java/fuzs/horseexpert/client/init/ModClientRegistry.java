package fuzs.horseexpert.client.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry LAYER_REGISTRY = ModelLayerRegistry.of(HorseExpert.MOD_ID);
    public static final ModelLayerLocation PLAYER_MONOCLE = LAYER_REGISTRY.register("player", "monocle");
}
