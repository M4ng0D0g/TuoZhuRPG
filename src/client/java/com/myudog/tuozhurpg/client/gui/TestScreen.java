package com.myudog.tuozhurpg.client.gui; // 改成你的 package

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TestScreen extends Screen {

    public TestScreen() {
        // 設定視窗標題
        super(Text.literal("UI Lib 測試"));
    }

    @Override
    protected void init() {
        // 計算中心點
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 添加一個簡單的按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("關閉測試"), (button) -> {
                    this.close(); // 點擊關閉
                })
                .dimensions(centerX - 50, centerY + 20, 100, 20) // 位置 (x, y) 和 大小 (寬, 高)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. 畫背景 (預設的黑色半透明)
//        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(0, 0, this.width, this.height, 0xC0000000);

        // 2. 畫文字 (置中)
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("§6★ Hello UI Lib ★"), // §6 是金色
                this.width / 2,
                this.height / 2 - 20,
                0xFFFFFF // 白色
        );

        // 3. 畫按鈕 (必須呼叫 super 否則按鈕不見)
        super.render(context, mouseX, mouseY, delta);
    }
}