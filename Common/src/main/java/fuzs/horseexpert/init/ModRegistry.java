package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM = REGISTRY.placeholder(Registry.ITEM_REGISTRY, "monocle");

    public static void touch() {

    }
}
