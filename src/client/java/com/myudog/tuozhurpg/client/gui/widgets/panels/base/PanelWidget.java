package com.myudog.tuozhurpg.client.gui.widgets.panels.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class PanelWidget extends BaseWidget {

    // 只有 Panel 擁有子元件列表
    protected List<BaseWidget> children = new ArrayList<>();

    public PanelWidget(int fixedW, int fixedH) {
        super(fixedW, fixedH);
    }

    // --- 子元件管理 (Tree Management) ---

    public void addChild(BaseWidget child) {
        if (child == this) return; // 防止自我循環

        // 防止重複添加
        if (this.children.contains(child)) return;

        // 如果孩子已經有父母，先從舊父母那邊移除
        if (child.getParent() instanceof PanelWidget oldParent) {
            oldParent.removeChild(child);
        }

        this.children.add(child);
        child.setParent(this);

        // 【追趕機制】如果 Panel 已經初始化過，新加入的孩子也要立刻初始化
        if (this.isInitialized()) {
            child.init();
        }

        // 標記佈局髒污，觸發重算
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
        super.init(); // 初始化自己

        // 遞迴初始化孩子
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

        // 2. 啟用剪裁 (Scissor Test)
        // 這會確保孩子不會畫出 Panel 的範圍 (例如長列表捲動時)
        enableScissor(context);

        // 3. 遞迴畫孩子
        for (BaseWidget child : children) {
            child.render(context, mouseX, mouseY, delta);
        }

        // 4. 關閉剪裁
        disableScissor(context);
    }

    // --- 剪裁輔助方法 (Scissor Helper) ---

    /**
     * 開啟剪裁區域。
     * 注意：Minecraft 的 Scissor 需要計算物理視窗座標，受 GUI Scale 影響。
     */
    protected void enableScissor(DrawContext context) {
        double scale = MinecraftClient.getInstance().getWindow().getScaleFactor();

        // 計算剪裁區域 (基於 Panel 自己的絕對位置與大小)
        // 這裡需要加上 Padding，不然內容會蓋住邊框
        int scissorX = this.getX() + getPaddingLeft();
        int scissorY = this.getY() + getPaddingTop();
        int scissorW = getW() - getPaddingLeft() - getPaddingRight();
        int scissorH = getH() - getPaddingTop() - getPaddingBottom();

        // 呼叫 DrawContext 的剪裁方法 (1.20+ 內建方法)
        // 如果您的版本沒有這個方法，需要使用 RenderSystem.enableScissor 並手動轉換座標
        context.enableScissor(scissorX, scissorY, scissorX + scissorW, scissorY + scissorH);
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