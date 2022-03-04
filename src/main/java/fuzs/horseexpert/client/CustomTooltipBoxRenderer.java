package fuzs.horseexpert.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;

import java.util.List;

public class CustomTooltipBoxRenderer {
    private final Font font;
    private final ItemRenderer itemRenderer;
    private int width;
    private int height;

    public CustomTooltipBoxRenderer(Font font, ItemRenderer itemRenderer) {
        this.font = font;
        this.itemRenderer = itemRenderer;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void renderTooltipInternal(PoseStack poseStack, List<ClientTooltipComponent> list, int mouseX, int mouseY) {
        ClientTooltipComponent clientTooltipComponent2;
        int u;
        int boxWidth2;
        int boxWidth = 0;
        int boxHeight = list.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent clientTooltipComponent : list) {
            boxWidth2 = clientTooltipComponent.getWidth(this.font);
            if (boxWidth2 > boxWidth) {
                boxWidth = boxWidth2;
            }
            boxHeight += clientTooltipComponent.getHeight();
        }
        int startX = mouseX + 12;
        int startY = mouseY - 12;
        boxWidth2 = boxWidth;
        int boxHeight2 = boxHeight;
        if (startX + boxWidth > this.width) {
            startX -= 28 + boxWidth;
        }
        if (startY + boxHeight2 + 6 > this.height) {
            startY = this.height - boxHeight2 - 6;
        }
        poseStack.pushPose();
        float f = this.itemRenderer.blitOffset;
        this.itemRenderer.blitOffset = 400.0f;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix4f = poseStack.last().pose();
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY - 4, startX + boxWidth2 + 3, startY - 3, 400, -267386864, -267386864);
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY + boxHeight2 + 3, startX + boxWidth2 + 3, startY + boxHeight2 + 4, 400, -267386864, -267386864);
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY - 3, startX + boxWidth2 + 3, startY + boxHeight2 + 3, 400, -267386864, -267386864);
        fillGradient(matrix4f, bufferBuilder, startX - 4, startY - 3, startX - 3, startY + boxHeight2 + 3, 400, -267386864, -267386864);
        fillGradient(matrix4f, bufferBuilder, startX + boxWidth2 + 3, startY - 3, startX + boxWidth2 + 4, startY + boxHeight2 + 3, 400, -267386864, -267386864);
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY - 3 + 1, startX - 3 + 1, startY + boxHeight2 + 3 - 1, 400, 0x505000FF, 1344798847);
        fillGradient(matrix4f, bufferBuilder, startX + boxWidth2 + 2, startY - 3 + 1, startX + boxWidth2 + 3, startY + boxHeight2 + 3 - 1, 400, 0x505000FF, 1344798847);
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY - 3, startX + boxWidth2 + 3, startY - 3 + 1, 400, 0x505000FF, 0x505000FF);
        fillGradient(matrix4f, bufferBuilder, startX - 3, startY + boxHeight2 + 2, startX + boxWidth2 + 3, startY + boxHeight2 + 3, 400, 1344798847, 1344798847);
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        poseStack.translate(0.0, 0.0, 400.0);
        int t = startY;
        for (u = 0; u < list.size(); ++u) {
            clientTooltipComponent2 = list.get(u);
            clientTooltipComponent2.renderText(this.font, startX, t, matrix4f, bufferSource);
            t += clientTooltipComponent2.getHeight() + (u == 0 ? 2 : 0);
        }
        bufferSource.endBatch();
        poseStack.popPose();
        t = startY;
        for (u = 0; u < list.size(); ++u) {
            clientTooltipComponent2 = list.get(u);
            clientTooltipComponent2.renderImage(this.font, startX, t, poseStack, this.itemRenderer, 400);
            t += clientTooltipComponent2.getHeight() + (u == 0 ? 2 : 0);
        }
        this.itemRenderer.blitOffset = f;
    }

    protected static void fillGradient(Matrix4f p_93124_, BufferBuilder p_93125_, int p_93126_, int p_93127_, int p_93128_, int p_93129_, int p_93130_, int p_93131_, int p_93132_) {
        float f = (float)(p_93131_ >> 24 & 255) / 255.0F;
        float f1 = (float)(p_93131_ >> 16 & 255) / 255.0F;
        float f2 = (float)(p_93131_ >> 8 & 255) / 255.0F;
        float f3 = (float)(p_93131_ & 255) / 255.0F;
        float f4 = (float)(p_93132_ >> 24 & 255) / 255.0F;
        float f5 = (float)(p_93132_ >> 16 & 255) / 255.0F;
        float f6 = (float)(p_93132_ >> 8 & 255) / 255.0F;
        float f7 = (float)(p_93132_ & 255) / 255.0F;
        p_93125_.vertex(p_93124_, (float)p_93128_, (float)p_93127_, (float)p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93126_, (float)p_93127_, (float)p_93130_).color(f1, f2, f3, f).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93126_, (float)p_93129_, (float)p_93130_).color(f5, f6, f7, f4).endVertex();
        p_93125_.vertex(p_93124_, (float)p_93128_, (float)p_93129_, (float)p_93130_).color(f5, f6, f7, f4).endVertex();
    }
}
