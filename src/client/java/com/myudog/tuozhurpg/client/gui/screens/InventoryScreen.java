package com.myudog.tuozhurpg.client.gui.screens;

import com.myudog.tuozhurpg.client.gui.screens.base.BaseScreen;
import com.myudog.tuozhurpg.client.gui.enums.Anchor;
import com.myudog.tuozhurpg.client.gui.enums.DebugTheme;
import com.myudog.tuozhurpg.client.gui.widgets.panels.InventoryGridPanel;
import com.myudog.tuozhurpg.client.gui.widgets.panels.base.PanelWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.PaperDollPanel;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class InventoryScreen extends BaseScreen {

    public InventoryScreen() {
        super(Text.literal("Inventory"));
    }

    // ----------------------------------------------------------------------------------------------------

    private PanelWidget backgroundPanel;
    private PaperDollPanel dollPanel;
    private InventoryGridPanel inventoryPanel;
    private InventoryGridPanel hotbarPanel;
    private PanelWidget idkPanel1;
    private PanelWidget idkPanel2;

    private static final int PANEL_WIDTH = 450;
    private static final int PANEL_HEIGHT = 250;
    private static final int SIDE_PANEL_WIDTH = 130;
    private static final int MIDDLE_PANEL_WIDTH = PANEL_WIDTH - 2 * SIDE_PANEL_WIDTH;
    private static final int MIDDLE_PANEL_HEIGHT = PANEL_HEIGHT / 2;
    private static final int PADDING = 5;

    // ----------------------------------------------------------------------------------------------------

    @Override
    protected void init() {
        super.init();

        // BackgroundPanel
        backgroundPanel = new PanelWidget(PANEL_WIDTH, PANEL_HEIGHT);
        backgroundPanel.setAnchor(Anchor.CENTER, Anchor.CENTER);
        backgroundPanel.setTheme(DebugTheme.DEBUG_1);
        backgroundPanel.updateLayout(0, 0, this.width, this.height);
        addDrawableChild(backgroundPanel);

        // ****************************************************************************************************
        // DollPanel
        /*
         * TODO 加入生物模型，裝備欄位
         *  底部數值欄位 左側裝備效果
         *  主副手武器位置在哪? 背部? 四大裝飾品?
         *
         * */

        dollPanel = new PaperDollPanel(SIDE_PANEL_WIDTH, PANEL_HEIGHT);
        dollPanel.setAnchor(Anchor.TOP_LEFT, Anchor.TOP_LEFT);
        dollPanel.setTheme(DebugTheme.DEBUG_2);
        dollPanel.updateLayout(backgroundPanel);
        addDrawableChild(dollPanel);

        // ****************************************************************************************************
        // Hotbar
        /*
        * TODO 外層包panel，自適應
        *  stackPanel?
        *
        * */

        hotbarPanel = new InventoryGridPanel(9, 1);
        hotbarPanel.setAnchor(Anchor.BOTTOM, Anchor.BOTTOM);
        hotbarPanel.setTheme(DebugTheme.DEBUG_3);
        hotbarPanel.updateLayout(backgroundPanel);
        addDrawableChild(hotbarPanel);

        // InventoryPanel

        inventoryPanel = new InventoryGridPanel(9, 3);
        inventoryPanel.setAnchor(Anchor.BOTTOM, Anchor.TOP);
        inventoryPanel.setTheme(DebugTheme.DEBUG_3);
        inventoryPanel.updateLayout(hotbarPanel);
        addDrawableChild(inventoryPanel);

        // ****************************************************************************************************
        // IdkPanel
        idkPanel1 = new PanelWidget(MIDDLE_PANEL_WIDTH, MIDDLE_PANEL_HEIGHT);
        idkPanel1.setAnchor(Anchor.TOP, Anchor.TOP);
        idkPanel1.setTheme(DebugTheme.DEBUG_4);
        idkPanel1.updateLayout(backgroundPanel);
        addDrawableChild(idkPanel1);

        // ****************************************************************************************************
        idkPanel2 = new PanelWidget(SIDE_PANEL_WIDTH, PANEL_HEIGHT);
        idkPanel2.setAnchor(Anchor.TOP_RIGHT, Anchor.TOP_RIGHT);
        idkPanel2.setTheme(DebugTheme.DEBUG_5);
        idkPanel2.updateLayout(backgroundPanel);
        addDrawableChild(idkPanel2);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

}
