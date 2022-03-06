package fuzs.horseexpert.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonocleItem extends ArmorItem {
    public MonocleItem(Properties p_40388_) {
        super(MonocleArmorMaterial.INSTANCE, EquipmentSlot.HEAD, p_40388_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(new TranslatableComponent("item.horseexpert.monocle.tooltip").withStyle(ChatFormatting.GRAY));
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
