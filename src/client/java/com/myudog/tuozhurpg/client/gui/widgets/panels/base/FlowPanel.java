package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;

public class FlowPanel extends PanelWidget {

    private int spacing = 5;

    public FlowPanel(int fixedW, int fixedH, int spacing) {
        super(fixedW, fixedH);
        this.spacing = spacing;
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        int maxWidth = availW - paddingLeft - paddingRight; // 實際可用寬度

        int cursorX = 0;
        int cursorY = 0;
        int rowMaxH = 0;
        int finalW = 0;

        for (BaseWidget child : children) {
            child.measure(availW, availH); // 先問孩子

            int childW = child.getMeasuredW();
            int childH = child.getMeasuredH();

            // 檢查換行
            if (cursorX + childW > maxWidth) {
                cursorX = 0;            // 重置 X
                cursorY += rowMaxH + spacing; // 增加 Y
                rowMaxH = 0;       // 重置行高
            }

            // 更新游標
            cursorX += childW + spacing;
            rowMaxH = Math.max(rowMaxH, childH);

            // 紀錄最寬的那一行
            finalW = Math.max(finalW, cursorX);
        }

        return new int[]{
                finalW + paddingLeft + paddingRight,
                (cursorY + rowMaxH) + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        int maxW = pW - paddingLeft - paddingRight;
        int startX = pX + paddingLeft;
        int startY = pY + paddingTop;

        int cursorX = 0; // 相對座標
        int cursorY = 0; // 相對座標
        int rowMaxH = 0;

        for (BaseWidget child : children) {
            int childW = child.getMeasuredW();
            int childH = child.getMeasuredH();

            // 檢查換行
            if (cursorX + childW > maxW) {
                cursorX = 0;
                cursorY += rowMaxH + spacing;
                rowMaxH = 0;
            }

            // 絕對座標 = 原點 + 相對游標
            child.layout(startX + cursorX, startY + cursorY, childW, childH);

            cursorX += childW + spacing;
            rowMaxH = Math.max(rowMaxH, childH);
        }
    }
}