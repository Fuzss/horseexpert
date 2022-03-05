package fuzs.horseexpert.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import net.minecraft.ChatFormatting;

public class ClientConfig extends AbstractConfig {
    @Config(description = "Offset on x-axis from original position.")
    public int offsetX = 0;
    @Config(description = "Offset on y-axis from original position.")
    public int offsetY = 0;
    @Config(description = "Color used for highlighting low attribute values.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting lowValueColor = ChatFormatting.RED;
    @Config(description = "Color used for highlighting medium attribute values.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting mediumValueColor = ChatFormatting.GOLD;
    @Config(description = "Color used for highlighting high attribute values.")
    @Config.AllowedValues(values = {"BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"})
    public ChatFormatting highValueColor = ChatFormatting.GREEN;
    @Config(description = "Percentage value below which a value is considered to be low.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double lowValuePercentage = 0.25;
    @Config(description = "Percentage value above which a value is considered to be high.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double highValuePercentage = 0.75;

    public ClientConfig() {
        super("");
    }
}
