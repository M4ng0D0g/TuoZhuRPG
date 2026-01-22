package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;

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
    public void layout(int pX, int pY , int pW, int pH) {
        int cursorY = y + paddingTop;
        int contentX = x + paddingLeft;
        int contentW = w - paddingLeft - paddingRight;

        for (BaseWidget child : children) {
            int childW = (child.getWidthMode() == SizeMode.MATCH_PARENT)
                    ? contentW - child.getMarginLeft() - child.getMarginRight()
                    : child.getMeasuredW();
            child.layout(contentX, cursorY, childW, child.getMeasuredH());
            cursorY += child.getMeasuredH() + child.getMarginTop() + child.getMarginBottom() + spacing;
        }
    }


//    @Override
//    protected int[] onMeasure(int availW, int availH) {
//        int desiredWidth = this.width;
//        int desiredHeight = this.height;
//
//        if (this.widthMode == SizeMode.WRAP_CONTENT || this.heightMode == SizeMode.WRAP_CONTENT) {
//            int maxChildW = 0;
//            int maxChildH = 0;
//
//            for (BaseWidget child : children) {
//                int[] childSize = child.onMeasure();
//                int childOccupyW = childSize[0] + child.marginLeft + child.marginRight;
//                int childOccupyH = childSize[1] + child.marginTop + child.marginBottom;
//
//                maxChildW = Math.max(maxChildW, childOccupyW);
//                maxChildH = Math.max(maxChildH, childOccupyH);
//            }
//
//            if (this.widthMode == SizeMode.WRAP_CONTENT) {
//                desiredWidth = maxChildW;
//            }
//            if (this.heightMode == SizeMode.WRAP_CONTENT) {
//                desiredHeight = maxChildH;
//            }
//        }
//
//        // 更新暫存大小 (還不是最終，因為 MATCH_PARENT 還沒處理)
//        this.width = desiredWidth;
//        this.height = desiredHeight;
//
//        return new int[]{desiredWidth, desiredHeight};
//    }
//
//    @Override
//    public void layout(int pX, int pY, int pW, int pH) {
//        if (this.widthMode == SizeMode.MATCH_PARENT) {
//            this.w = parent.getW() - (this.marginLeft + this.marginRight);
//        } else {
//
//        }
//        if (this.heightMode == SizeMode.MATCH_PARENT) {
//            this.h = parent.getH() - (this.marginTop + this.marginBottom);
//        }
//
//        // TODO: fix
//        int cachedAbsX = parent.getX() + this.marginLeft;
//        int cachedAbsY = parent.getY() + this.marginTop;
//
//        // *若要結合 Anchor，這一步會變成：
//        // parentX + (parentWidth * anchorX) + marginLeft - (width * pivotX) ...
//
//        int contentX = cachedAbsX + this.paddingLeft;
//        int contentY = cachedAbsY + this.paddingTop;
//        int contentW = this.width - (this.paddingLeft + this.paddingRight);
//        int contentH = this.height - (this.paddingTop + this.paddingBottom);
//
//        for (BaseWidget child : children) {
//            child.layout();
//        }
//    }
}
