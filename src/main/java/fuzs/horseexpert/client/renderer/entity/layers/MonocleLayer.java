package fuzs.horseexpert.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.horseexpert.HorseExpert;
import fuzs.horseexpert.client.renderer.ModRenderType;
import fuzs.horseexpert.world.item.MonocleItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MonocleLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation MONOCLE_LOCATION = new ResourceLocation(HorseExpert.MOD_ID, "textures/entity/monocle.png");

   private final A monocleModel;

   public MonocleLayer(RenderLayerParent<T, M> p_117075_, A p_117076_) {
      super(p_117075_);
      this.monocleModel = p_117076_;
   }

   @Override
   public void render(PoseStack p_117096_, MultiBufferSource p_117097_, int p_117098_, T p_117099_, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
      ItemStack itemstack = p_117099_.getItemBySlot(EquipmentSlot.HEAD);
      if (itemstack.getItem() instanceof MonocleItem) {
         this.getParentModel().copyPropertiesTo(this.monocleModel);
         this.setPartVisibility(this.monocleModel);
         VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(p_117097_, ModRenderType.armorCutoutTranslucentNoCull(MONOCLE_LOCATION), false, itemstack.hasFoil());
         this.monocleModel.renderToBuffer(p_117096_, vertexconsumer, p_117098_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   private void setPartVisibility(A p_117126_) {
      p_117126_.setAllVisible(false);
      p_117126_.head.visible = true;
      p_117126_.hat.visible = true;
   }
}
