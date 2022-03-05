package fuzs.horseexpert.world.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class MonocleArmorMaterial implements ArmorMaterial {
    public static final ArmorMaterial INSTANCE = new MonocleArmorMaterial();

    @Override
    public int getDurabilityForSlot(EquipmentSlot p_40410_) {
        return 0;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot p_40411_) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.GOLD_INGOT);
    }

    @Override
    public String getName() {
        return "monocle";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
