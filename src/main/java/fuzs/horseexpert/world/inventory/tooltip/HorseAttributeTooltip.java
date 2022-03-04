package fuzs.horseexpert.world.inventory.tooltip;

import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record HorseAttributeTooltip(FormattedCharSequence text, MobEffect icon) implements TooltipComponent {

}
