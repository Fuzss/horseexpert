package fuzs.horseexpert.fabric;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class HorseExpertFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
    }
}
