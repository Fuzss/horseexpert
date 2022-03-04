package fuzs.horseexpert.registry;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.item.MonocleItem;
import fuzs.puzzleslib.registry.RegistryManager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(HorseExpert.MOD_ID);
    public static final RegistryObject<Item> MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new MonocleItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static void touch() {

    }
}
