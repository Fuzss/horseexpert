package fuzs.horseexpert.init;

import fuzs.horseexpert.world.item.ForgeMonocleItem;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class ForgeModRegistry {
    public static final RegistryReference<Item> MONOCLE_ITEM = ModRegistry.REGISTRY.registerItem("monocle", () -> new ForgeMonocleItem(new Item.Properties().stacksTo(1)));
    public static final TagKey<Item> CURIOS_HEAD_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(CuriosApi.MODID, "head"));
    public static final TagKey<Item> TRINKETS_HEAD_FACE_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("trinkets", "head/face"));

    public static void touch() {

    }
}
