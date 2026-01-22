package com.myudog.tuozhurpg.client.gui.widgets.base;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class PanelWidget extends BaseWidget {

    protected List<BaseWidget> children = new ArrayList<>();

    public PanelWidget(int fixedW, int fixedH) {
        super(fixedW, fixedH);
    }

    // --- 子元件管理 (Tree Management) ---

    public void addChild(BaseWidget child) {
        if (child == this) return;
        if (this.children.contains(child)) return;

        if (child.getParent() instanceof PanelWidget oldParent) {
            oldParent.removeChild(child);
        }
        this.children.add(child);
        child.setParent(this);

        if (this.isInitialized()) {
            child.init();
        }
        this.markDirty();
    }

    public void removeChild(BaseWidget child) {
        if (children.remove(child)) {
            child.setParent(null);
            this.markDirty();
        }
    }

    public void clearChildren() {
        for (BaseWidget child : children) {
            child.setParent(null);
        }
        children.clear();
        this.markDirty();
    }

    public List<BaseWidget> getChildren() {
        return children;
    }

    // --- 生命週期 ---

    @Override
    public void init() {
        super.init();
        for (BaseWidget child : children) {
            child.init();
        }
    }

    // --- 渲染流程 (包含剪裁與遞迴) ---

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. 畫 Panel 自己的背景與邊框 (呼叫 BaseWidget 的方法)
        drawBackground(context);
        drawBorder(context);

        boolean scissorEnabled = enableScissor(context);

        try {
            // 2. 渲染孩子 (不管這裡發生什麼事，甚至報錯)
            for (BaseWidget child : children) {
                child.render(context, mouseX, mouseY, delta);
            }
        } finally {
            // 3. 【關鍵】確保一定要關閉 (只有在成功開啟的前提下)
            if (scissorEnabled) {
                disableScissor(context);
            }
        }
    }

    // --- 剪裁輔助方法 (Scissor Helper) ---

    /**
     * 開啟剪裁區域。
     * 注意：Minecraft 的 Scissor 需要計算物理視窗座標，受 GUI Scale 影響。
     */
    protected boolean enableScissor(DrawContext context) {
        double scale = MinecraftClient.getInstance().getWindow().getScaleFactor();

        // 計算剪裁區域 (基於 Panel 自己的絕對位置與大小)
        // 這裡需要加上 Padding，不然內容會蓋住邊框
        int scissorX = this.getX() + getPaddingLeft();
        int scissorY = this.getY() + getPaddingTop();
        int scissorW = getW() - getPaddingLeft() - getPaddingRight();
        int scissorH = getH() - getPaddingTop() - getPaddingBottom();

        if (scissorW <= 0 || scissorH <= 0) return false;
        context.enableScissor(scissorX, scissorY, scissorX + scissorW, scissorY + scissorH);
        return true;
    }

    protected void disableScissor(DrawContext context) {
        context.disableScissor();
    }

    // --- 佈局抽象化 ---

    // PanelWidget 本身不知道怎麼排孩子，必須由子類別 (VBox, Grid) 實作
    // 所以這裡維持 abstract，強迫子類別去寫邏輯

    @Override
    protected abstract int[] onMeasure(int availW, int availH);

    @Override
    public abstract void layout(int pX, int pY, int pW, int pH);
}