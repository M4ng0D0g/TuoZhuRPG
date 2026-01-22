//package com.myudog.tuozhurpg.client.gui.widgets.components.base;
//
//import com.myudog.tuozhurpg.client.gui.widgets.base.ControlWidget;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
//import net.minecraft.text.Text;
//
//public class StatBarWidget extends ControlWidget {
//
//    private float progress; // 0.0 ~ 1.0
//    private int barColor;
//
//    private int thickness = 1;
//    private int borderColor = 0xFFFFFFFF;
//
//    public StatBarWidget(int x, int y, int width, int height, int color) {
//        super(x, y, width, height, Text.empty());
//        this.barColor = color;
//        this.progress = 1.0f;
//    }
//
//    public void setProgress(float current, float max) {
//        this.progress = Math.clamp(current / max, 0.0f, 1.0f);
//    }
//
//    @Override
//    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
//        // 1. 畫黑底槽
//        context.fill(getX(), getY(), getX() + w, getY() + h, 0xFF000000);
//
//        // 2. 畫有顏色的進度
//        int barWidth = (int)(this.w * progress);
//        if (barWidth > 0) {
//            context.fill(getX() + 1, getY() + 1, getX() + barWidth - 1, getY() + h - 1, barColor);
//        }
//
//        // 3. (可選) 畫邊框，呼叫父類方法，或者只畫簡單的線
//        context.fill(getX(), getY(), getX() + w, getY() + thickness, borderColor);
//        context.fill(getX(), getY() + h - thickness, getX() + w, getY() + h, borderColor);
//        context.fill(getX(), getY(), getX() + thickness, getY() + h, borderColor);
//        context.fill(getX() + w - thickness, getY(), getX() + w, getY() + h, borderColor);
//    }
//
//    @Override
//    protected void appendClickableNarrations(NarrationMessageBuilder builder) { }
//}