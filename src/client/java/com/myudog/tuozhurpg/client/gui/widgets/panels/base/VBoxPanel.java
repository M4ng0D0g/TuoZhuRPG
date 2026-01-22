package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;

public class VBoxPanel extends PanelWidget {

    int spacing = 0;

    public VBoxPanel(int fixedW, int fixedH, int spacing) {
        super(fixedW, fixedH);
        this.spacing = spacing;
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        int totalH = 0;
        int maxW = 0;

        for (int i = 0; i < children.size(); ++i) {
            BaseWidget child = children.get(i);
            child.measure(availW, availH);

            totalH += child.getMeasuredH() + child.getMarginTop() + child.getMarginBottom();
            maxW =  Math.max(maxW, child.getMeasuredW() + child.getMarginLeft() + child.getMarginRight());

            if (i < children.size() - 1) totalH += spacing;
        }

        return new int[]{
                maxW + paddingLeft + paddingRight,
                totalH + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        this.x = pX;
        this.y = pY;
        this.w = pW;
        this.h = pH;
        this.layoutDirty = false;

        int cursorY = y + paddingTop;
        int contentX = x + paddingLeft;
        int contentW = w - paddingLeft - paddingRight;

        for (BaseWidget child : children) {
            int childW = (child.getWidthMode() == SizeMode.MATCH_PARENT)
                    ? contentW - child.getMarginLeft() - child.getMarginRight()
                    : child.getMeasuredW();
            child.layout(contentX + child.getMarginLeft(), cursorY + child.getMarginTop(), childW, child.getMeasuredH());
            cursorY += child.getMeasuredH() + child.getMarginTop() + child.getMarginBottom() + spacing;
        }
    }
}
