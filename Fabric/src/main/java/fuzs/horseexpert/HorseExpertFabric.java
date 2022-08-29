package fuzs.horseexpert;

import fuzs.horseexpert.init.FabricModRegistry;
import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;

public class HorseExpertFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(HorseExpert.MOD_ID).accept(new HorseExpert());
        FabricModRegistry.touch();
    }
}
