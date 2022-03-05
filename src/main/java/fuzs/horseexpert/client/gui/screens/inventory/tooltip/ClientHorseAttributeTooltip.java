package fuzs.horseexpert.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import fuzs.horseexpert.world.inventory.tooltip.HorseAttributeTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHorseAttributeTooltip implements ClientTooltipComponent {
   private final int textIndent = 4;
   private final int iconSize = 20;
   private final int firstLineHeight = 12;
   private final FormattedCharSequence line1;
   private final FormattedCharSequence line2;
   private final MobEffect icon;

   public ClientHorseAttributeTooltip(HorseAttributeTooltip tooltip) {
      this.line1 = tooltip.line1();
      this.line2 = tooltip.line2();
      this.icon = tooltip.icon();
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