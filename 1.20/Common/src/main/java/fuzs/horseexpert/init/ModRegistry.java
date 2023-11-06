package fuzs.horseexpert.init;

import fuzs.horseexpert.HorseExpert;
import fuzs.puzzleslib.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import fuzs.puzzleslib.api.item.v2.ItemEquipmentFactories;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(HorseExpert.MOD_ID);
    public static final RegistryReference<Item> MONOCLE_ITEM;

    public static final TagKey<Item> INSPECTION_EQUIPMENT_ITEM_TAG = REGISTRY.registerItemTag("inspection_equipment");
    public static final TagKey<EntityType<?>> INSPECTABLE_ENTITY_TYPE_TAG = REGISTRY.registerEntityTypeTag("inspectable");

    static {
        if (ModLoaderEnvironment.INSTANCE.isModLoaded("curios") || ModLoaderEnvironment.INSTANCE.isModLoaded("trinkets")) {
            MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new Item(new Item.Properties().stacksTo(1)));
        } else {
            ArmorMaterial armorMaterial = ItemEquipmentFactories.registerArmorMaterial(HorseExpert.id("monocle"), Ingredient::of);
            MONOCLE_ITEM = REGISTRY.registerItem("monocle", () -> new ArmorItem(armorMaterial, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));
        }
    }

    public static void touch() {

    }
}
