package com.myudog.tuozhurpg.client.gui.widgets.controls.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.ControlWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TextWidget extends ControlWidget {
    private Text text;
    private int color = 0xFFFFFFFF; // 白色
    private boolean shadow = true;

    public TextWidget(Text text) {
        super(0, 0);
        this.text = text;
    }

    public void setText(Text text) {
        this.text = text;
        this.markDirty();
    }

    public void setColor(int color) { this.color = color; }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        int textWidth = tr.getWidth(text);
        int textHeight = tr.fontHeight;

        return new int[]{
                textWidth + paddingLeft + paddingRight,
                textHeight + paddingTop + paddingBottom
        };
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // 畫背景 (如果有設的話)
        drawBackground(context);
        drawBorder(context);

        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        // 計算文字渲染的起點 (考慮 Padding)
        int textX = this.x + paddingLeft;
        int textY = this.y + paddingTop;

        // 渲染文字
        context.drawText(tr, text, textX, textY, color, shadow);
    }
}
