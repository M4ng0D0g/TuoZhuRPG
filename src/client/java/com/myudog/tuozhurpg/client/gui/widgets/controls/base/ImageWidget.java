package com.myudog.tuozhurpg.client.gui.widgets.controls.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.ControlWidget;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ImageWidget extends ControlWidget {
    private final Identifier texture;
    private int u = 0, v = 0;
    private int regionW = 0, regionH = 0; // 紋理區域大小
    private int textureW = 256, textureH = 256;

    // 簡單建構子：畫整張圖
    public ImageWidget(Identifier texture, int w, int h) {
        super(w, h); // 設定 fixedWidth, fixedHeight
        this.texture = texture;
        this.regionW = w;
        this.regionH = h;
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        // 圖片通常有固定大小，或者填滿
        // 如果使用者有設定 fixedW/H，BaseWidget 會自動處理
        // 這裡只要回傳 "如果不受限，我想要多大"

        return new int[]{
                regionW + paddingLeft + paddingRight,
                regionH + paddingTop + paddingBottom
        };
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // 使用 drawTexture 繪製
        // 注意：這裡使用 this.width 和 this.height，這樣圖片可以被拉伸以適應佈局
        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                texture,
                this.x + paddingLeft,
                this.y + paddingTop,
                this.w - paddingLeft - paddingRight, // 拉伸寬度
                this.h - paddingTop - paddingBottom, // 拉伸高度
                u, v,
                regionW, regionH,
                textureW, textureH
        );
    }
}