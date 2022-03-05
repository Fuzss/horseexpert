package fuzs.horseexpert.world.inventory.tooltip;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

public record HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) implements TooltipComponent {
    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey) {
        this(icon, value, min, max, translationKey, DoubleUnaryOperator.identity());
    }

    public static Optional<TooltipComponent> healthTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.HEALTH_BOOST, value, 15.0, 30.0, "horse.tooltip.health"));
    }

    public static Optional<TooltipComponent> speedTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.MOVEMENT_SPEED, value, 0.1125, 0.3375, "horse.tooltip.speed", d -> d * 43.17));
    }

    public static Optional<TooltipComponent> jumpHeightTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.JUMP, value, 0.4, 1.0, "horse.tooltip.jump_height", d -> Math.pow(d, 1.7) * 5.293));
    }

    public static Optional<TooltipComponent> strengthTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.DAMAGE_BOOST, value, 1.0, 5.0, "horse.tooltip.strength", d -> d * 3));
    }
}
