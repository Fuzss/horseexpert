package fuzs.horseexpert.world.inventory.tooltip;

import fuzs.horseexpert.HorseExpert;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

public class HorseAttributeTooltip implements TooltipComponent {
    private static final DecimalFormat ATTRIBUTE_VALUE_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    private final MobEffect icon;
    private final FormattedCharSequence line1;
    private final FormattedCharSequence line2;

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey) {
        this(icon, value, min, max, translationKey, DoubleUnaryOperator.identity());
    }

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this.icon = icon;
        MutableComponent unitComponent = new TranslatableComponent(translationKey.concat(".unit"), new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(value))).withStyle(categorizeValue(value, min, max))).withStyle(ChatFormatting.GRAY);
        this.line1 = new TranslatableComponent(translationKey, unitComponent).withStyle(ChatFormatting.WHITE).getVisualOrderText();
        MutableComponent minComponent = new TranslatableComponent("horse.tooltip.min", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(min))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.client().lowValueColor);
        MutableComponent maxComponent = new TranslatableComponent("horse.tooltip.max", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(max))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.client().highValueColor);
        MutableComponent separatorComponent = new TextComponent(", ").withStyle(ChatFormatting.GRAY);
        this.line2 = minComponent.append(separatorComponent).append(maxComponent).getVisualOrderText();
    }

    public FormattedCharSequence line1() {
        return this.line1;
    }

    public FormattedCharSequence line2() {
        return this.line2;
    }

    public MobEffect icon() {
        return this.icon;
    }

    private static ChatFormatting categorizeValue(double value, double min, double max) {
        if (value < min + (max - min) * HorseExpert.CONFIG.client().lowValuePercentage) {
            return HorseExpert.CONFIG.client().lowValueColor;
        } else if (value >= min + (max - min) * HorseExpert.CONFIG.client().highValuePercentage) {
            return HorseExpert.CONFIG.client().highValueColor;
        }
        return HorseExpert.CONFIG.client().mediumValueColor;
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
