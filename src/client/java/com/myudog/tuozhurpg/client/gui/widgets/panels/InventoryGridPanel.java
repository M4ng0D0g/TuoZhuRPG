package com.myudog.tuozhurpg.client.gui.widgets.panels;

import com.myudog.tuozhurpg.client.gui.widgets.panels.base.PanelWidget;
import net.minecraft.client.gui.DrawContext;

public class InventoryGridPanel extends PanelWidget {

    public static final int SLOT_SIZE = 18;
    private static final int DEFAULT_PADDING = 7;

    private final int columns;
    private final int rows;
    private int gridPadding = DEFAULT_PADDING;

    public InventoryGridPanel(int columns, int rows) {
        super((columns * SLOT_SIZE) + (DEFAULT_PADDING * 2), (rows * SLOT_SIZE) + (DEFAULT_PADDING * 2));
        this.columns = columns;
        this.rows = rows;
    }

    public InventoryGridPanel setGridPadding(int padding) {
        this.gridPadding = padding;
        // 重新計算寬高
        this.w = (columns * SLOT_SIZE) + (padding * 2);
        this.h = (rows * SLOT_SIZE) + (padding * 2);
        return this;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        // A. 先畫面板底圖 (背景 + 邊框)
        super.renderWidget(context, mouseX, mouseY, delta);

        // B. 再畫格子
        // 取得面板的起始點 (考慮到 Panel 可能被放在螢幕的任何地方)
        int startX = this.getX() + gridPadding;
        int startY = this.getY() + gridPadding;

        // 取得格子顏色 (比背景更深一點，營造凹槽感)
        // 這裡我們簡單運算：把背景色的 Alpha 加重，或者直接用黑色半透明
        int slotColor = 0x80000000; // 半透明黑色凹槽
        int slotBorder = 0x30FFFFFF; // 微微發亮的邊框 (可選)

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {

                // 計算當前格子的座標
                int slotX = startX + (c * SLOT_SIZE);
                int slotY = startY + (r * SLOT_SIZE);

                // 畫格子凹槽 (18x18)
                // 這裡稍微縮 1px (1, 1, 16, 16) 讓格子之間有間隔，或者畫滿 18 也可以
                context.fill(slotX, slotY, slotX + SLOT_SIZE, slotY + SLOT_SIZE, slotBorder); // 邊框
                context.fill(slotX + 1, slotY + 1, slotX + SLOT_SIZE - 1, slotY + SLOT_SIZE - 1, slotColor); // 內凹
            }
        }
    }

    // === 6. 未來擴充預留 ===
    // 之後如果你要偵測滑鼠點擊格子，可以在這裡寫邏輯
    // public int getSlotIndexAt(double mouseX, double mouseY) { ... }
}
