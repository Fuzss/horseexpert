package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.item.ForgeMonocleItem;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class ForgeModRegistry {
    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new ForgeMonocleItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));

    public static final TagKey<Item> CURIOS_HEAD_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, "head"));
    public static final TagKey<Item> TRINKETS_HEAD_FACE_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("trinkets", "head/face"));

    public static void touch() {

    }
}
