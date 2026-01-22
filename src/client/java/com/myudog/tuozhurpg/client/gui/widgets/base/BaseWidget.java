package com.myudog.tuozhurpg.client.gui.widgets.base;

import com.myudog.tuozhurpg.client.gui.widgets.panels.base.PanelWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

// 假設您有定義這些 Enum，若無請參考文末補充
import com.myudog.tuozhurpg.client.gui.enums.Anchor;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;
import com.myudog.tuozhurpg.client.gui.interfaces.ITheme;

public abstract class BaseWidget implements Drawable, Element, Selectable {

    // --- 1. 配置層 (Configuration) - 使用者設定的 "願望" ---
    // 這些變數決定了元件 "想要" 變成什麼樣子
    protected int fixedX = 0, fixedY = 0; // 僅在 Anchor 計算或 CanvasPanel 中有用
    protected int fixedW, fixedH;
    protected SizeMode widthMode = SizeMode.FIXED;
    protected SizeMode heightMode = SizeMode.FIXED;

    protected Anchor anchorFrom = Anchor.TOP_LEFT;
    protected Anchor anchorTo = Anchor.FREE; // 或 PARENT_TOP_LEFT
    protected int offsetX = 0;
    protected int offsetY = 0;

    // --- 2. 結果層 (Result State) - 系統計算後的 "現實" ---
    // 這些變數是 layout() 算出來的最終結果，渲染時使用
    // 設為 protected，禁止外部直接修改，確保數據流向單一
    protected int x, y;
    protected int w, h;
    protected int measuredW, measuredH; // 測量階段的暫存值

    protected boolean layoutDirty = true;
    protected boolean initialized = false;

    // --- 建構子 ---

    public BaseWidget(int w, int h) {
        this.fixedW = w;
        this.fixedH = h;
    }

    // --- 生命週期: Init ---

    public boolean isInitialized() {
        return initialized;
    }

    public void init() {
        if (initialized) return;
        this.initialized = true;
        this.onInit();
    }

    /** 子類別可覆寫此方法進行一次性邏輯綁定 */
    protected void onInit() {}

    // --- 核心流程 A: Measure (測量) ---
    // 由下而上 (Bottom-Up)：父元件詢問子元件大小

    public void measure(int parentAvailW, int parentAvailH) {
        // 1. 寬度測量
        if (this.widthMode == SizeMode.FIXED) {
            this.measuredW = this.fixedW;
        } else if (this.widthMode == SizeMode.MATCH_PARENT) {
            // 注意：這裡先暫時填滿，實際大小會在 layout 階段被父元件修正
            this.measuredW = Math.max(0, parentAvailW - marginLeft - marginRight);
        } else {
            // WRAP_CONTENT: 詢問具體實作
            // 傳入的 availW 已經扣除了 Margin，讓子類別知道最大邊界
            int contentAvailW = Math.max(0, parentAvailW - marginLeft - marginRight);
            int contentAvailH = Math.max(0, parentAvailH - marginTop - marginBottom);

            int[] size = onMeasure(contentAvailW, contentAvailH);
            this.measuredW = size[0];
            // 如果高度是 FIXED，即使 onMeasure 算出了高度，也強制覆寫 (視需求而定)
            if (this.heightMode == SizeMode.FIXED) {
                this.measuredH = this.fixedH;
            } else {
                this.measuredH = size[1];
            }
        }

        // 2. 高度測量 (邏輯同上，針對 FIXED 簡化處理)
        if (this.heightMode == SizeMode.FIXED) {
            this.measuredH = this.fixedH;
        } else if (this.heightMode == SizeMode.MATCH_PARENT) {
            this.measuredH = Math.max(0, parentAvailH - marginTop - marginBottom);
        } else if (this.widthMode != SizeMode.WRAP_CONTENT) {
            // 如果寬度不是 Wrap (已經在上面算過了)，這裡針對高度 Wrap 再算一次
            // (通常 onMeasure 會一次算好寬高，這裡只是防禦性編程)
            int contentAvailW = Math.max(0, parentAvailW - marginLeft - marginRight);
            int contentAvailH = Math.max(0, parentAvailH - marginTop - marginBottom);
            int[] size = onMeasure(contentAvailW, contentAvailH);
            this.measuredH = size[1];
        }
    }

    /**
     * 子類別必須實作：根據內容計算大小
     * @return int[]{width, height} 內容區想要的寬高 (含 Padding，不含 Margin)
     */
    protected abstract int[] onMeasure(int availW, int availH);

    // --- 核心流程 B: Layout (佈局) ---
    // 由上而下 (Top-Down)：父元件指定子元件位置

    /**
     * 接收父元件的命令，設定最終位置與大小
     * @param absoluteX 父元件決定的絕對 X
     * @param absoluteY 父元件決定的絕對 Y
     * @param actualW   父元件分配的最終寬度
     * @param actualH   父元件分配的最終高度
     */
    public void layout(int absoluteX, int absoluteY, int actualW, int actualH) {
        this.x = absoluteX;
        this.y = absoluteY;
        this.w = actualW;
        this.h = actualH;

        this.layoutDirty = false; // 標記為已處理

        // 注意：BaseWidget 沒有 children，所以這裡不遞迴。
        // PanelWidget 繼承此方法後，必須 Override 並呼叫 super.layout(...) 然後處理 children
    }

    // --- Getters & Setters (配置層) ---

    public int getX() { return x; } // 回傳最終結果
    public int getY() { return y; }
    public int getW() { return w; } // 回傳最終結果
    public int getH() { return h; }

    // 取得測量結果 (給父元件用)
    public int getMeasuredW() { return measuredW; }
    public int getMeasuredH() { return measuredH; }

    // 設定固定大小 (配置)
    public void setFixedSize(int w, int h) {
        this.fixedW = w;
        this.fixedH = h;
        this.widthMode = SizeMode.FIXED;
        this.heightMode = SizeMode.FIXED;
        markDirty();
    }

    public void setWidthMode(SizeMode mode) {
        this.widthMode = mode;
        markDirty();
    }

    public void setHeightMode(SizeMode mode) {
        this.heightMode = mode;
        markDirty();
    }

    public SizeMode getWidthMode() { return widthMode; }
    public SizeMode getHeightMode() { return heightMode; }

    protected void markDirty() {
        this.layoutDirty = true;
        // 如果有父元件，通知父元件它也髒了 (Bubbling up)
        if (this.parent != null) {
            // this.parent.markDirty(); // 視 Panel 實作而定
        }
    }

    // --- Anchors (定位) ---

    public void setAnchor(Anchor from, Anchor to) {
        this.anchorFrom = from;
        this.anchorTo = to;
        markDirty();
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        markDirty();
    }

    // 公開這些欄位供父元件的 layout 使用
    public Anchor getAnchorFrom() { return anchorFrom; }
    public int getOffsetX() { return offsetX; }
    public int getOffsetY() { return offsetY; }


    // --- 樣式與主題 ---

    protected int backgroundColor = 0x11111188;
    protected int borderStartColor = 0xFFFFFFDD;
    protected int borderEndColor = 0xFFFFFFDD;
    protected int borderSize = 1;

    public void setBorderSize(int size) {
        this.borderSize = MathHelper.clamp(size, 0, Integer.MAX_VALUE);
    }

    public void setColors(int bgColor, int bdColor) {
        this.backgroundColor = bgColor;
        this.borderStartColor = bdColor;
        this.borderEndColor = bdColor;
    }

    public void setTheme(ITheme theme) {
        this.backgroundColor = theme.getBackgroundColor();
        this.borderStartColor = theme.getBorderStartColor();
        this.borderEndColor = theme.getBorderEndColor();
    }

    // --- Margin & Padding ---

    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;
    protected int marginLeft, marginRight, marginTop, marginBottom;

    // Padding Setters
    public void setPadding(int all) { setPadding(all, all, all, all); }
    public void setPadding(int h, int v) { setPadding(h, h, v, v); }
    public void setPadding(int l, int r, int t, int b) {
        this.paddingLeft = l; this.paddingRight = r;
        this.paddingTop = t; this.paddingBottom = b;
        markDirty(); // Padding 改變會影響測量
    }

    // Margin Setters
    public void setMargin(int all) { setMargin(all, all, all, all); }
    public void setMargin(int h, int v) { setMargin(h, h, v, v); }
    public void setMargin(int l, int r, int t, int b) {
        this.marginLeft = l; this.marginRight = r;
        this.marginTop = t; this.marginBottom = b;
        markDirty(); // Margin 改變會影響父元件排列
    }

    // Getters for Margin/Padding
    public int getPaddingLeft() { return paddingLeft; }
    public int getPaddingRight() { return paddingRight; }
    public int getPaddingTop() { return paddingTop; }
    public int getPaddingBottom() { return paddingBottom; }
    public int getMarginLeft() { return marginLeft; }
    public int getMarginRight() { return marginRight; }
    public int getMarginTop() { return marginTop; }
    public int getMarginBottom() { return marginBottom; }

    public int getMarginHorizontal() { return marginLeft + marginRight; }
    public int getMarginVertical() { return marginTop + marginBottom; }


    // --- 父子關係 ---

    protected PanelWidget parent;

    public PanelWidget getParent() {
        return parent;
    }

    public void setParent(@Nullable PanelWidget parent) {
        // 簡單的防呆，防止自己成為自己的父親
        if (parent == (BaseWidget)this) return;
        this.parent = parent;
    }

    // --- 渲染 ---

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 可選：在渲染前檢查是否需要重新 layout (通常由 Screen.resize 控制，這裡只是保險)
        // if (layoutDirty && parent == null) { ... }

        renderWidget(context, mouseX, mouseY, delta);
    }

    /** 子類別實作具體渲染 */
    protected abstract void renderWidget(DrawContext context, int mouseX, int mouseY, float delta);

    protected void drawBackground(DrawContext context) {
        context.fill(x, y, x + w, y + h, backgroundColor);
    }

    protected void drawBorder(DrawContext context) {
        if (borderSize <= 0) return;

        // 上
        context.fill(x, y, x + w, y + borderSize, borderStartColor);
        // 下
        context.fill(x, y + h - borderSize, x + w, y + h, borderEndColor);
        // 左 (注意高度要扣掉上下，避免重疊繪製，雖然 fill 沒差)
        context.fillGradient(x, y, x + borderSize, y + h, borderStartColor, borderEndColor);
        // 右
        context.fillGradient(x + w - borderSize, y, x + w, y + h, borderStartColor, borderEndColor);
    }

    // --- Accessibility / Selection (Minecraft 介面必須實作) ---
    // 這部分通常需要根據具體需求實作，這裡給出預設空實作

    @Override
    public void setFocused(boolean focused) {}
    @Override
    public boolean isFocused() { return false; }
    @Override
    public SelectionType getType() { return SelectionType.NONE; }
    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, "Widget");
    }
}