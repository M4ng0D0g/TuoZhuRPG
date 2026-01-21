package com.myudog.tuozhurpg.client.gui.widgets.panels;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.base.SizeMode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PanelWidget extends BaseWidget {


    @Override
    public void init() {
        super.init();

        for (BaseWidget child : children) {
            child.init();
        }
    }



    // ----------------------------------------------------------------------------------------------------

    protected List<BaseWidget> children = new ArrayList<>();

    public void addChild(BaseWidget child) {
        if (child == this) return;

        if (child.getParent() instanceof PanelWidget panel) {
            panel.removeChild(child);
        }

        this.children.add(child);
        child.setParent(this);

        if (this.initialized) {
            child.init();
        }
    }

    public void removeChild(BaseWidget child) {
        if (children.remove(child)) {
            child.setParent(null);
        }
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        drawBackground(context);
        enableScissor(context);

        for (BaseWidget child : children) {
            child.render(context, mouseX, mouseY, delta);
        }

        disableScissor(context);
    }

    @Override
    public void measure() {
        int desiredWidth = this.width;
        int desiredHeight = this.height;

        if (this.widthMode == SizeMode.WRAP_CONTENT || this.heightMode == SizeMode.WRAP_CONTENT) {
            int maxChildW = 0;
            int maxChildH = 0;

            for (BaseWidget child : children) {
                int[] childSize = child.measure();
                int childOccupyW = childSize[0] + child.marginLeft + child.marginRight;
                int childOccupyH = childSize[1] + child.marginTop + child.marginBottom;

                maxChildW = Math.max(maxChildW, childOccupyW);
                maxChildH = Math.max(maxChildH, childOccupyH);
            }

            if (this.widthMode == SizeMode.WRAP_CONTENT) {
                desiredWidth = maxChildW;
            }
            if (this.heightMode == SizeMode.WRAP_CONTENT) {
                desiredHeight = maxChildH;
            }
        }

        // 更新暫存大小 (還不是最終，因為 MATCH_PARENT 還沒處理)
        this.width = desiredWidth;
        this.height = desiredHeight;

        return new int[]{desiredWidth, desiredHeight};
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {
        if (this.widthMode == SizeMode.MATCH_PARENT) {
            this.width = parent.getWidth() - (this.marginLeft + this.marginRight);
        }
        if (this.heightMode == SizeMode.MATCH_PARENT) {
            this.height = parent.getHeight() - (this.marginTop + this.marginBottom);
        }

        // TODO: fix
        int cachedAbsX = parent.getX() + this.marginLeft;
        int cachedAbsY = parent.getY() + this.marginTop;

        // *若要結合 Anchor，這一步會變成：
        // parentX + (parentWidth * anchorX) + marginLeft - (width * pivotX) ...

        int contentX = cachedAbsX + this.paddingLeft;
        int contentY = cachedAbsY + this.paddingTop;
        int contentW = this.width - (this.paddingLeft + this.paddingRight);
        int contentH = this.height - (this.paddingTop + this.paddingBottom);

        for (BaseWidget child : children) {
            child.layout();
    }

    // ----------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------



    public PanelWidget(int x, int y, int w, int h) {
        super(x, y, w, h, Text.empty());
        this.active = false;
    }
    public PanelWidget(int w, int h) {
        super(0, 0, w, h, Text.empty());
        this.active = false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(getX(), getY(), getX() + width, getY() + height, this.backgroundColor);

        // 畫邊框 (上下左右)
        context.fillGradient(getX(), getY(), getX() + borderSize, getY() + height, this.borderStartColor, this.borderEndColor); // 左
        context.fillGradient(getX() + width - borderSize, getY(), getX() + width, getY() + height, this.borderStartColor, this.borderEndColor); // 右
        context.fill(getX(), getY(), getX() + width, getY() + borderSize, this.borderStartColor); // 上
        context.fill(getX(), getY() + height - borderSize, getX() + width, getY() + height, this.borderEndColor); // 下
    }

    @Override
    public void layout(int pX, int pY, int pW, int pH) {

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        // 面板不需要朗讀，留空
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public SelectionType getType() {
        return null;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}