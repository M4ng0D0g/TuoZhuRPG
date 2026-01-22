package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.enums.Anchor;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;
import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;

public class CanvasPanel extends PanelWidget {

    public CanvasPanel(int fixedW, int fixedH) {
        super(fixedW, fixedH);
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        int maxRight = 0;
        int maxBotom = 0;

        for (BaseWidget child : children) {
            child.measure(availW, availH);
            int childRight = child.getX() + child.getMeasuredW();
            int childBottom = child.getY() + child.getMeasuredH();
            maxRight = Math.max(maxRight, childRight);
            maxBotom = Math.max(maxBotom, childBottom);
        }

        return new int[]{
                maxRight + paddingLeft + paddingRight,
                maxBotom + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        this.x = pX;
        this.y = pY;
        this.w = pW;
        this.h = pH;
        this.layoutDirty = false;

        int availableW = pW - paddingLeft - paddingRight;
        int availableH = pH - paddingTop - paddingBottom;
        int startX = pX + paddingLeft;
        int startY = pY + paddingTop;
        for (BaseWidget child : children) {
            int childW = (child.getWidthMode() == SizeMode.MATCH_PARENT) ? availableW : child.getMeasuredW();
            int childH = (child.getHeightMode() == SizeMode.MATCH_PARENT) ? availableH : child.getMeasuredH();

            Anchor anchor = child.getAnchorFrom();
            int finalX = startX + Anchor.calX(anchor, availableW) - Anchor.calX(anchor, childW) + child.getOffsetX();
            int finalY = startY + Anchor.calY(anchor, availableH) - Anchor.calY(anchor, childH) + child.getOffsetY();

            // 套用 Margin (選擇性)
            // 在絕對佈局中，通常建議直接用 Offset 控制位置。
            // 但如果你希望 Margin 也能生效，可以在這裡簡單加上：
//            finalX += child.getMarginLeft();
//            finalY += child.getMarginTop();
            // 注意：這種寫法不管靠左靠右都是加 Left/Top Margin。
            // 如果你希望靠右時 MarginRight 生效，需要更複雜的判斷，
            // 但在 CanvasPanel 中，通常依賴 Offset 就足夠了。

            child.layout(finalX, finalY, childW, childH);
        }
    }
}
