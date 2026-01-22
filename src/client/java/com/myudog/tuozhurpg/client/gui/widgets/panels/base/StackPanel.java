package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;

public class StackPanel extends PanelWidget {

    public StackPanel(int fixedW, int fixedH) {
        super(fixedW, fixedH);
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        int maxW = 0, maxH = 0;

        for (BaseWidget child : children) {
            child.measure(availW, availH);
            int childW = child.getMeasuredW() + child.getMarginLeft() + child.getMarginRight();
            int childH = child.getMeasuredH() + child.getMarginTop() + child.getMarginBottom();
            maxW = Math.max(maxW, childW);
            maxH = Math.max(maxH, childH);
        }

        return new int[]{
                maxW + paddingLeft + paddingRight,
                maxH + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        int contentX = pX - paddingLeft;
        int contentY = pY - paddingTop;
        int contentW = pW - paddingLeft - paddingRight;
        int contentH = pH - paddingTop - paddingBottom;

        for (BaseWidget child : children) {
            child.layout(contentX, contentY, contentW, contentH);
        }
    }


}
