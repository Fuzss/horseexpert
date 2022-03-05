package fuzs.horseexpert.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public class ClientHorseAttributeTooltip implements ClientTooltipComponent {
   private static final DecimalFormat ATTRIBUTE_VALUE_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
      p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   });

   private final int textIndent = 4;
   private final int iconSize = 20;
   private final int firstLineHeight = 12;
   private final FormattedCharSequence line1;
   private final FormattedCharSequence line2;
   private final MobEffect icon;

   public ClientHorseAttributeTooltip(HorseAttributeTooltip tooltip) {
      this.icon = tooltip.icon();
      double value = tooltip.value();
      double min = tooltip.min();
      double max = tooltip.max();
      String translationKey = tooltip.translationKey();
      DoubleUnaryOperator valueConverter = tooltip.valueConverter();
      // we build all the components in here (vanilla does it in the common toolip component class) since we want the client to be able to control the appearance of the tooltips
      MutableComponent unitComponent = new TranslatableComponent(translationKey.concat(".unit"), new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(value))).withStyle(categorizeValue(value, min, max))).withStyle(ChatFormatting.GRAY);
      this.line1 = new TranslatableComponent(translationKey, unitComponent).withStyle(ChatFormatting.WHITE).getVisualOrderText();
      MutableComponent minComponent = new TranslatableComponent("horse.tooltip.min", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(min))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.client().lowValueColor);
      MutableComponent maxComponent = new TranslatableComponent("horse.tooltip.max", new TextComponent(ATTRIBUTE_VALUE_FORMAT.format(valueConverter.applyAsDouble(max))).withStyle(ChatFormatting.GRAY)).withStyle(HorseExpert.CONFIG.client().highValueColor);
      MutableComponent separatorComponent = new TextComponent(", ").withStyle(ChatFormatting.GRAY);
      this.line2 = minComponent.append(separatorComponent).append(maxComponent).getVisualOrderText();
   }

   private static ChatFormatting categorizeValue(double value, double min, double max) {
      if (value < min + (max - min) * HorseExpert.CONFIG.client().lowValuePercentage) {
         return HorseExpert.CONFIG.client().lowValueColor;
      } else if (value >= min + (max - min) * HorseExpert.CONFIG.client().highValuePercentage) {
         return HorseExpert.CONFIG.client().highValueColor;
      }
      return HorseExpert.CONFIG.client().mediumValueColor;
   }

   @Override
   public int getWidth(Font p_169941_) {
      return Math.max(p_169941_.width(this.line1), p_169941_.width(this.line2)) + this.textIndent * 2 + this.iconSize;
   }

   @Override
   public int getHeight() {
      return this.iconSize - this.firstLineHeight;
   }

   @Override
   public void renderText(Font font, int posX, int posY, Matrix4f p_169946_, MultiBufferSource.BufferSource p_169947_) {
      int width1 = font.width(this.line1);
      int width2 = font.width(this.line2);
      int startX1, startX2;
      startX1 = startX2 = this.textIndent;
      if (width2 > width1) {
         startX1 += (width2 - width1) / 2;
      } else {
         startX2 += (width1 - width2) / 2;
      }
      font.drawInBatch(this.line1, posX + this.iconSize + startX1, posY - this.firstLineHeight, -1, true, p_169946_, p_169947_, false, 0, 15728880);
      font.drawInBatch(this.line2, posX + this.iconSize + startX2, posY + 10 - this.firstLineHeight, -1, true, p_169946_, p_169947_, false, 0, 15728880);
   }

   @Override
   public void renderImage(Font p_194048_, int posX, int posY, PoseStack poseStack, ItemRenderer itemRenderer, int blitOffset) {
      MobEffectTextureManager mobeffecttexturemanager = Minecraft.getInstance().getMobEffectTextures();
      TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(this.icon);
      RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
      GuiComponent.blit(poseStack, posX + 1, posY + 1 - this.firstLineHeight, blitOffset, 18, 18, textureatlassprite);
   }
}