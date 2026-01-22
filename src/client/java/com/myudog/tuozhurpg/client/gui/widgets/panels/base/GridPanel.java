package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;

public class GridPanel extends PanelWidget {

    private int columns = 1;
    private int spacing = 0;

    public GridPanel(int columns, int spacing) {
        this.columns = columns;
        this.spacing= spacing;
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        if (children.isEmpty()) return new int[]{0, 0};

        int maxW = 0, maxH = 0;
        for (BaseWidget child : children) {
            child.measure(availW, availH);
            maxW = Math.max(maxW, child.getMeasuredW());
            maxH = Math.max(maxH, child.getMeasuredH());
        }

        int rows = (int) Math.ceil((double) children.size() / columns);
        int totalW = (columns * maxW) + (columns - 1) * spacing;
        int totalH = (rows * maxH) + (rows - 1) * spacing;

        return new int[]{
                totalW + paddingLeft + paddingRight,
                totalH + paddingTop + paddingBottom
        };
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        if (children.isEmpty()) return;

        int cellW = 0, cellH = 0;
        for (BaseWidget child : children) {
            cellW = Math.max(cellW, child.getMeasuredW());
            cellH = Math.max(cellH, child.getMeasuredH());
        }

        int startX = pX + paddingLeft;
        int startY = pY + paddingTop;
        for (int i = 0; i < children.size(); ++i) {
            BaseWidget child = children.get(i);

            int col = i % columns;
            int row = i / columns;

            int childX = startX + col * (cellW + spacing);
            int childY = startY + row * (cellH + spacing);

            child.layout(childX, childY, cellW, cellH);
        }
    }
}
