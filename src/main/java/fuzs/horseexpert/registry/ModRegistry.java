package fuzs.horseexpert.registry;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.item.MonocleItem;
import fuzs.puzzleslib.registry.RegistryManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;

public class ModRegistry {
    private static final RegistryManager REGISTRY = RegistryManager.of(HorseExpert.MOD_ID);
    public static final RegistryObject<Item> MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new MonocleItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));

    public static final TagKey<Item> CURIOS_HEAD_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, "head"));

    public static void touch() {

    }
}
