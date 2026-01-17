package com.myudog.tuozhurpg.client.gui.widgets.panels;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

public class PanelWidget extends BaseWidget {

    public PanelWidget(int x, int y, int w, int h) {
        super(x, y, w, h, Text.empty());
        this.active = false;
    }
    public PanelWidget(int w, int h) {
        super(0, 0, w, h, Text.empty());
        this.active = false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getX() + width, getY() + height, this.backgroundColor);

        // 畫邊框 (上下左右)
        context.fillGradient(getX(), getY(), getX() + borderSize, getY() + height, this.borderStartColor, this.borderEndColor); // 左
        context.fillGradient(getX() + width - borderSize, getY(), getX() + width, getY() + height, this.borderStartColor, this.borderEndColor); // 右
        context.fill(getX(), getY(), getX() + width, getY() + borderSize, this.borderStartColor); // 上
        context.fill(getX(), getY() + height - borderSize, getX() + width, getY() + height, this.borderEndColor); // 下
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        // 面板不需要朗讀，留空
    }
}