package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;

public class CanvaPanel extends PanelWidget {

    public CanvaPanel(int fixedW, int fixedH) {
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
        for (BaseWidget child : children) {
            child.layout(x + paddingLeft, y + paddingTop, child.getMeasuredW(), child.getMeasuredH());
        }
    }
}
