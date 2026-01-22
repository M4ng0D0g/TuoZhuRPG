package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;

public class ScrollPanel extends PanelWidget {
    private int scrollAmount = 0; // 當前捲動值
    private int contentHeight = 0; // 內容實際高度

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
        super.layout(pX, pY, pW, pH); // 設定自己的位置

        if (children.isEmpty()) return;
        BaseWidget content = children.get(0);

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
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.scrollAmount -= amount * 20; // 捲動速度
        this.measure(); // 觸發重新計算
        this.layout(x, y, w, h);
        return true;
    }
}