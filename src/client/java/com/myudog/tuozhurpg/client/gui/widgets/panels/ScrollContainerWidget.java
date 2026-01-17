package com.myudog.tuozhurpg.client.gui.widgets.panels;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public class ScrollContainerWidget extends PanelWidget {

    private double scrollAmount = 0; // 目前往下捲了多少像素
    private int contentHeight = 0;   // 內容物的總高度 (必須比面板高才會有滾動條)

    public ScrollContainerWidget(int width, int height) {
        super(width, height);
    }

    // 設定內容高度 (這決定了滾動條多長)
    public void setContentHeight(int height) {
        this.contentHeight = height;
    }

    // === 1. 滾輪事件 ===
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // 如果內容比面板短，不需要滾動
        if (contentHeight <= this.height) return false;

        // verticalAmount 通常是 1.0 (往上) 或 -1.0 (往下)
        // 設定滾動速度，例如一次 20 像素
        double scrollSpeed = 20.0;

        // 更新滾動量 (注意方向：滾輪往下是負的，所以要減)
        this.scrollAmount -= (verticalAmount * scrollSpeed);

        // 限制範圍 (Clamp)
        // 最小是 0 (最頂端)
        // 最大是 (內容高度 - 面板高度) (最底端)
        double maxScroll = Math.max(0, contentHeight - this.height);
        this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0, maxScroll);

        return true;
    }

    // === 2. 渲染邏輯 (最難的部分) ===
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // A. 畫背景 (不用剪裁)
        drawBackground(context);

        // B. 開啟剪裁 (Scissor)
        // 這會告訴顯卡：只准畫在我的 Panel 範圍內，超出的部分切掉！
        // enableScissor 需要絕對座標和寬高
        context.enableScissor(getX(), getY(), getX() + width, getY() + height);

        // C. 位移矩陣 (Translate)
        // 為了模擬「往下捲」，我們其實是把內容物「往上推」 (Y 減去 scrollAmount)
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(0.0f, (float) -scrollAmount);

        // D. 渲染子元件
        // 這裡要稍微騙一下 mouseX/Y，因為子元件不知道自己被位移了
        // 不過 renderChildren 通常處理渲染，滑鼠偵測要另外處理 (這裡先簡化)
        // 注意：這裡只會渲染 addDrawableChild 加入的東西

        // 這裡我們假設 ScrollContainer 內部管理了一組自己的 children
        // 因為 PanelWidget 原本的 children 是由 Screen 管理的
        // 為了簡單起見，我們這裡先用一個簡單的文字當示範，稍後教你怎麼放真正的 Widget

        // (模擬畫很長的內容)
        context.drawText(
                net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                "Top of content", getX() + 5, getY() + 5, 0xFFFFFFFF, false
        );
        context.drawText(
                net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                "Bottom of content", getX() + 5, getY() + contentHeight - 10, 0xFFFFFFFF, false
        );

        // E. 還原矩陣與關閉剪裁
        context.getMatrices().popMatrix();
        context.disableScissor();

        // F. 畫滾動條 (Scrollbar)
        renderScrollbar(context);
    }

    private void renderScrollbar(DrawContext context) {
        if (contentHeight <= this.height) return;

        int barWidth = 4;
        int barHeight = (int) ((float) this.height / contentHeight * this.height); // 比例尺
        int barX = this.getX() + this.width - barWidth - 2;
        int barY = this.getY() + (int) ((float) scrollAmount / (contentHeight - this.height) * (this.height - barHeight));

        // 畫軌道
        context.fill(barX, getY(), barX + barWidth, getY() + height, 0x20000000);
        // 畫拉桿
        context.fill(barX, barY, barX + barWidth, barY + barHeight, 0x80FFFFFF);
    }
}