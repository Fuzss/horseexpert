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
   private final int iconSize = 20;
   private final FormattedCharSequence text;
   private final MobEffect icon;

   public ClientHorseAttributeTooltip(HorseAttributeTooltip tooltip) {
      this.text = tooltip.text();
      this.icon = tooltip.icon();
   }

   @Override
   public int getWidth(Font p_169941_) {
      return p_169941_.width(this.text) + this.iconSize;
   }

   @Override
   public int getHeight() {
      return this.iconSize;
   }

   @Override
   public void renderText(Font p_169943_, int p_169944_, int p_169945_, Matrix4f p_169946_, MultiBufferSource.BufferSource p_169947_) {
      p_169943_.drawInBatch(this.text, (float)this.iconSize + p_169944_, (float)p_169945_, -1, true, p_169946_, p_169947_, false, 0, 15728880);
   }

   @Override
   public void renderImage(Font p_194048_, int posX, int posY, PoseStack poseStack, ItemRenderer itemRenderer, int blitOffset) {
      MobEffectTextureManager mobeffecttexturemanager = Minecraft.getInstance().getMobEffectTextures();
      TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(this.icon);
      RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
      GuiComponent.blit(poseStack, posX, posY, blitOffset, 18, 18, textureatlassprite);
   }
}