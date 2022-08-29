package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.item.FabricMonocleItem;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FabricModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new FabricMonocleItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));

    public static void touch() {

    }
}
