package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM = REGISTRY.placeholder(Registries.ITEM, "monocle");

    public static void touch() {

    }
}
