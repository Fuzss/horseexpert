package fuzs.horseexpert.world.inventory.tooltip;

import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

public class HorseAttributeTooltip implements TooltipComponent {
    private static final DecimalFormat ATTRIBUTE_VALUE_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    private final Item item;
    private final MobEffect icon;
    private final FormattedCharSequence line1;
    private final FormattedCharSequence line2;

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey) {
        this(null, icon, value, min, max, translationKey, DoubleUnaryOperator.identity());
    }

    private HorseAttributeTooltip(MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this(null, icon, value, min, max, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(Item item, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this(item, null, value, min, max, translationKey, valueConverter);
    }

    private HorseAttributeTooltip(@Nullable Item item, @Nullable MobEffect icon, double value, double min, double max, String translationKey, DoubleUnaryOperator valueConverter) {
        this.item = item;
        this.icon = icon;
        MutableComponent unitComponent = Component.translatable(translationKey.concat(".unit"), Component.literal(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(value))).withStyle(categorizeValue(value, min, max))).withStyle(ChatFormatting.GRAY);
        this.line1 = Component.translatable(translationKey, unitComponent).withStyle(ChatFormatting.WHITE).getVisualOrderText();
        MutableComponent minComponent = Component.translatable("horse.tooltip.min", Component.literal(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(min))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.get(ClientConfig.class).lowValueColor);
        MutableComponent maxComponent = Component.translatable("horse.tooltip.max", Component.literal(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(max))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.get(ClientConfig.class).highValueColor);
        MutableComponent separatorComponent = Component.literal("   ").withStyle(ChatFormatting.GRAY);
        this.line2 = minComponent.append(separatorComponent).append(maxComponent).getVisualOrderText();
    }

    public FormattedCharSequence line1() {
        return this.line1;
    }

    public FormattedCharSequence line2() {
        return this.line2;
    }

    @Nullable
    public Item item() {
        return this.item;
    }

    @Nullable
    public MobEffect icon() {
        return this.icon;
    }

    private static ChatFormatting categorizeValue(double value, double min, double max) {
        if (value < min + (max - min) * HorseExpert.CONFIG.get(ClientConfig.class).lowValuePercentage) {
            return HorseExpert.CONFIG.get(ClientConfig.class).lowValueColor;
        } else if (value >= min + (max - min) * HorseExpert.CONFIG.get(ClientConfig.class).highValuePercentage) {
            return HorseExpert.CONFIG.get(ClientConfig.class).highValueColor;
        }
        return HorseExpert.CONFIG.get(ClientConfig.class).mediumValueColor;
    }

    public static Optional<TooltipComponent> healthTooltip(double value) {
        // half health values as our translation string says hearts
        return Optional.of(new HorseAttributeTooltip(MobEffects.HEALTH_BOOST, value / 2.0, 7.5, 15.0, "horse.tooltip.health"));
    }

    public static Optional<TooltipComponent> speedTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.MOVEMENT_SPEED, value, 0.1125, 0.3375, "horse.tooltip.speed", d -> d * 43.17));
    }

    public static Optional<TooltipComponent> jumpHeightTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(MobEffects.JUMP, value, 0.4, 1.0, "horse.tooltip.jump_height", d -> Math.pow(d, 1.7) * 5.293));
    }

    public static Optional<TooltipComponent> strengthTooltip(double value) {
        return Optional.of(new HorseAttributeTooltip(Items.CHEST, value, 1.0, 5.0, "horse.tooltip.strength", d -> d * 3));
    }
}
