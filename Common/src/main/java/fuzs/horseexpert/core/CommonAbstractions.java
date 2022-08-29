package fuzs.horseexpert.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface CommonAbstractions {

    Optional<ItemStack> findEquippedItem(LivingEntity entity, Item item);
}
