package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;

public class ScrollPanel extends PanelWidget {
    private int scrollAmount = 0; // 當前捲動值
    private int contentHeight = 0; // 內容實際高度
    private boolean showScrollbar = true;

    public ScrollPanel(int fixedW, int fixedH) {
        super(fixedW, fixedH);
    }

    // ScrollPanel 通常只能有一個直接子元件 (通常是一個 VBox)
    public void setContent(BaseWidget content) {
        this.children.clear();
        this.addChild(content);
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        if (children.isEmpty()) return new int[]{0, 0};

        BaseWidget content = children.getFirst();

        // 關鍵：告訴孩子高度無限 (Integer.MAX_VALUE)，讓它盡情伸展
        content.measure(availW, Integer.MAX_VALUE);

        this.contentHeight = content.getMeasuredH();

        // ScrollPanel 自身的大小通常是填滿父元件 (MATCH_PARENT)
        // 或者固定大小。這裡簡單回傳可用大小。
        return new int[]{ availW, availH };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        this.x = pX;
        this.y = pY;
        this.w = pW;
        this.h = pH;
        this.layoutDirty = false;

        if (children.isEmpty()) return;
        BaseWidget content = children.getFirst();

        // 防止捲動過頭 (簡單的 Clamp 邏輯)
        int maxScroll = Math.max(0, contentHeight - pH);
        this.scrollAmount = Math.max(0, Math.min(scrollAmount, maxScroll));

        // 關鍵：座標欺騙
        // 內容的 Y 座標 = 視窗頂部 - 捲動量
        // 這樣當 scrollAmount 增加時，內容就會往上跑
        int virtualY = pY + paddingTop - scrollAmount;

        content.layout(pX + paddingLeft, virtualY, pW - paddingLeft - paddingRight, contentHeight);
    }

    // 必須實作滑鼠滾輪事件來改變 scrollAmount
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!isMouseOver(mouseX, mouseY)) return false;
        return handleScroll(verticalAmount);
    }
    private boolean handleScroll(double amount) {
        // 只有內容比視窗高的時候才允許捲動
        int viewportH = h - paddingTop - paddingBottom;
        if (contentHeight <= viewportH) return false;

        // 調整捲動速度 (20 像素/格)
        this.scrollAmount -= (int) (amount * 20);

        // 觸發重新佈局 (Recalculate positions)
        // 【修正 2】不需要 measure()，因為大小沒變，只有位置變了
        // 直接呼叫 layout 更新孩子位置
        this.layout(this.x, this.y, this.w, this.h);

        return true; // 告訴系統我們消費了這個事件，不要再傳給後面了
    }
    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        // 1. 如果滑鼠根本不在 ScrollPanel 顯示範圍內，就不准點裡面的東西
        // 這樣可以防止點擊到 "被剪裁掉" 的隱形按鈕
        if (!isMouseOver(click.x(), click.y())) return false;

        // 2. 轉發給內容 (VBox)
        // 內容的 x/y 已經在 layout() 階段被 ScrollPanel 修改過 (加上了虛擬偏移)
        // 所以直接呼叫內容的 mouseClicked 即可
        if (!children.isEmpty()) {
            return children.getFirst().mouseClicked(click, doubled);
        }

        return false;
    }
    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (!isMouseOver(click.x(), click.y())) return false;
        if (!children.isEmpty()) {
            return children.getFirst().mouseDragged(click, offsetX, offsetY);
        }
        return false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta); // 畫背景、開剪裁、畫孩子

        // 畫捲軸 (在剪裁之後畫，或者在 disableScissor 之後畫，看你想不想讓捲軸被剪裁)
        // 這裡示範簡單的捲軸條
        if (showScrollbar && contentHeight > h) {
            drawScrollbar(context);
        }
    }

    private void drawScrollbar(DrawContext context) {
        int viewportH = h - paddingTop - paddingBottom;
        if (contentHeight <= viewportH) return;

        // 計算比例
        float ratio = (float) viewportH / contentHeight;
        int barHeight = (int) (viewportH * ratio);
        int barTop = (int) (this.y + paddingTop + (scrollAmount * ratio));

        // 確保捲軸最小高度，以免太小看不到
        if (barHeight < 10) barHeight = 10;

        // 畫一個簡單的白色捲軸在右側
        int barX = this.x + this.w - 6; // 靠右 6px
        int barW = 4; // 寬 4px

        context.fill(barX, barTop, barX + barW, barTop + barHeight, 0xFFFFFFFF);
    }
}