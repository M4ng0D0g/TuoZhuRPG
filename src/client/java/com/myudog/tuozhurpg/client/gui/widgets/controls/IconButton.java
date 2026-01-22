//package com.myudog.tuozhurpg.client.gui.widgets.components;
//
//
//import com.myudog.tuozhurpg.client.gui.widgets.base.ControlWidget;
//import net.minecraft.client.gl.RenderPipelines;
//import net.minecraft.client.gui.Click;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//
///*
//* 用途： 技能欄、裝備欄、天賦樹節點。
//
//新技術：UV Mapping (紋理座標映射)。
//
//目前你只會畫整張圖，但技能圖示通常是塞在同一張大圖 (Atlas) 裡的。你需要學會「只畫圖片的某一個小方塊」。
//* */
//public class IconButton extends ControlWidget {
//
//    private final Identifier texture;
//    private final int u, v;
//    private final Runnable onPress;
//
//    public IconButton(int x, int y, int w, int h, Identifier texture, int u, int v, Runnable onPress) {
//        super(x, y, w, h, Text.empty());
//        this.texture = texture;
//        this.u = u;
//        this.v = v;
//        this.onPress = onPress;
//    }
//
//    @Override
//    public void onClick(Click click, boolean doubled) {
//        if (this.onPress != null) this.onPress.run();
//    }
//
//    @Override
//    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
//        boolean hovered = this.isHovered();
//
//        if (hovered) {
//            this.setColors(0xFFFFFF00, borderStartColor, borderEndColor);
//        }
//        this.drawBackground(context);
//        this.drawBorder(context);
//
//        int iconSize = 16;
//        int offset = (this.w - iconSize) / 2;
//
//        context.drawTexture(
//                RenderPipelines.GUI_TEXTURED,
//                this.texture,
//                getX() + offset, getY() + offset,
//                (float) this.u, (float) this.v,
//                this.w, this.h,
//                iconSize, iconSize
//        );
//
//
//    }
//
//    @Override
//    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
//
//    }
//}
