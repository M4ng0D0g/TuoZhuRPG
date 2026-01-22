package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;

public class HBoxPanel extends PanelWidget {

    int spacing = 0;

    public HBoxPanel(int fixedW, int fixedH, int spacing) {
        super(fixedW, fixedH);
        this.spacing = spacing;
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        int totalW = 0; // 改成累加寬度
        int maxH = 0;   // 改成取最大高度

        for (int i = 0; i < children.size(); ++i) {
            BaseWidget child = children.get(i);
            child.measure(availW, availH);

            // 寬度 = 累積孩子寬度 + 左右 Margin
            totalW += child.getMeasuredW() + child.getMarginLeft() + child.getMarginRight();

            // 高度 = 最高的那個孩子 + 上下 Margin
            maxH = Math.max(maxH, child.getMeasuredH() + child.getMarginTop() + child.getMarginBottom());

            if (i < children.size() - 1) totalW += spacing;
        }

        return new int[]{
                totalW + paddingLeft + paddingRight,
                maxH + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        // 記得先設定自己的位置 (補上 abstract class 沒做的事)
        this.x = pX;
        this.y = pY;
        this.w = pW;
        this.h = pH;
        this.layoutDirty = false;

        int cursorX = x + paddingLeft; // 游標變為 X 軸
        int contentY = y + paddingTop; // Y 軸固定 (或是基於對齊)
        int contentH = h - paddingTop - paddingBottom; // 計算可用高度

        for (BaseWidget child : children) {
            // HBox 的核心特性：檢查孩子是否想要填滿 "高度"
            int childH = (child.getHeightMode() == SizeMode.MATCH_PARENT)
                    ? contentH - child.getMarginTop() - child.getMarginBottom()
                    : child.getMeasuredH();

            int childW = child.getMeasuredW();

            // 這裡微調了座標計算：加上 Margin 讓位置更精確
            // X: 游標位置 + 左外距
            // Y: 頂部位置 + 上外距
            child.layout(cursorX + child.getMarginLeft(), contentY + child.getMarginTop(), childW, childH);
            cursorX += childW + child.getMarginLeft() + child.getMarginRight() + spacing;
        }
    }
}