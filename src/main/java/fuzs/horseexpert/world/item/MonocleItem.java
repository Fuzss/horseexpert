package fuzs.horseexpert.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class MonocleItem extends ArmorItem {
    public MonocleItem(Properties p_40388_) {
        super(MonocleArmorMaterial.INSTANCE, EquipmentSlot.HEAD, p_40388_);
    }

    @Override
    public boolean isValidRepairItem(ItemStack p_40392_, ItemStack p_40393_) {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_41388_) {
        return ImmutableMultimap.of();
    }
}
