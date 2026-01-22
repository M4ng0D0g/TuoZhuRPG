package com.myudog.tuozhurpg.client.gui.screens.test;

import com.myudog.tuozhurpg.client.gui.enums.Anchor;
import com.myudog.tuozhurpg.client.gui.enums.SizeMode;
import com.myudog.tuozhurpg.client.gui.screens.base.BaseScreen;
import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;
import com.myudog.tuozhurpg.client.gui.widgets.controls.base.TextWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.base.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

// 假設您有這些控制項 (若無，請用 TextWidget 代替測試)
// import com.myudog.tuozhurpg.client.gui.widgets.components.ButtonWidget;
// import com.myudog.tuozhurpg.client.gui.widgets.components.SliderWidget;

public class TestScreen extends BaseScreen {

    public TestScreen() {
        super(Text.literal("UI Framework Test"));
    }

    @Override
    protected PanelWidget buildContent() {
        // 1. 根容器：使用 CanvasPanel (絕對佈局) 填滿全螢幕
        // 這樣我們可以自由分割畫面
        CanvasPanel root = new CanvasPanel(0, 0);
        root.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.MATCH_PARENT);

        // 設定根背景 (半透明黑)
        root.setColors(0xAA000000, 0);

        // --- 左側：可捲動的設定欄 (Scroll + VBox) ---
        BaseWidget leftSidebar = createLeftSidebar();
        root.addChild(leftSidebar);

        // --- 右上：網格背包 (Grid) ---
        BaseWidget gridSection = createGridSection();
        root.addChild(gridSection);

        // --- 右中：流式標籤 (Flow) ---
        BaseWidget flowSection = createFlowSection();
        root.addChild(flowSection);

        // --- 右下：水平排列與堆疊 (HBox + Stack) ---
        BaseWidget hboxSection = createHBoxSection();
        root.addChild(hboxSection);

        return root;
    }

    // =========================================================================================
    //  測試模組 1: ScrollPanel + VBoxPanel
    // =========================================================================================
    private BaseWidget createLeftSidebar() {
        // 1. 建立捲動容器 (固定寬度 200，高度填滿)
        ScrollPanel scrollPanel = new ScrollPanel(200, 0);
        scrollPanel.setSizeMode(SizeMode.FIXED, SizeMode.MATCH_PARENT);
        scrollPanel.setAnchor(Anchor.TOP_LEFT, Anchor.TOP_LEFT);
        scrollPanel.setColors(0x88000000, 0xFFFFFFFF); // 深色背景，白邊框
        scrollPanel.setPadding(10);
        scrollPanel.setMargin(10); // 跟螢幕邊緣留點距離

        // 2. 建立內容容器 (VBox)
        VBoxPanel content = new VBoxPanel(0, 0, 10); // 間距 10
        content.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.WRAP_CONTENT); // 寬填滿 Scroll，高自適應

        // 3. 塞入大量測試資料以測試捲動
        content.addChild(new TextWidget(Text.literal("=== Settings ===")));

        for (int i = 1; i <= 20; i++) {
            // 模擬一個設定項目
            VBoxPanel itemGroup = new VBoxPanel(0, 0, 2);
            itemGroup.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.WRAP_CONTENT);

            TextWidget label = new TextWidget(Text.literal("Option " + i));
            label.setColor(0xFFAAAAAA);

            // 假設有個按鈕 (如果還沒實作 ButtonWidget，用有背景的 Text 代替)
            TextWidget buttonMock = new TextWidget(Text.literal("[ Button " + i + " ]"));
            buttonMock.setPadding(5);
            buttonMock.setColors(0xFF444444, 0xFFAAAAAA);
            buttonMock.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.WRAP_CONTENT);

            itemGroup.addChild(label);
            itemGroup.addChild(buttonMock);

            content.addChild(itemGroup);
        }

        scrollPanel.setContent(content);
        return scrollPanel;
    }

    // =========================================================================================
    //  測試模組 2: GridPanel (物品欄模擬)
    // =========================================================================================
    private BaseWidget createGridSection() {
        // 外層容器 (為了加標題)
        VBoxPanel container = new VBoxPanel(0, 0, 5);
        container.setFixedSize(200, 0); // 寬度固定
        container.setSizeMode(SizeMode.FIXED, SizeMode.WRAP_CONTENT);
        container.setAnchor(Anchor.TOP_RIGHT, Anchor.TOP_RIGHT); // 靠右上
        container.setMargin(10, 20); // 留邊距
        container.setPadding(10);
        container.setColors(0x88000022, 0xFF0000FF); // 藍色系測試

        container.addChild(new TextWidget(Text.literal("Inventory (Grid 3 cols)")));

        // 核心：GridPanel
        GridPanel grid = new GridPanel(3, 4); // 3欄，間距4
        grid.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.WRAP_CONTENT);

        // 塞入 9 個格子
        for (int i = 0; i < 9; i++) {
            // 使用 StackPanel 模擬一個物品槽 (背景 + 物品)
            StackPanel slot = new StackPanel(32, 32); // 固定 32x32

            // 1. 槽背景
            CanvasPanel bg = new CanvasPanel(32, 32);
            bg.setColors(0xFF000000, 0xFF888888);
            slot.addChild(bg);

            // 2. 物品圖示 (如果有的話，這裡先用 Text 代替)
            if (i % 2 == 0) {
                TextWidget item = new TextWidget(Text.literal("A"));
                item.setAnchor(Anchor.CENTER, Anchor.CENTER); // 居中
                slot.addChild(item);
            }

            grid.addChild(slot);
        }

        container.addChild(grid);
        return container;
    }

    // =========================================================================================
    //  測試模組 3: FlowPanel (自動換行標籤)
    // =========================================================================================
    private BaseWidget createFlowSection() {
        VBoxPanel container = new VBoxPanel(0, 0, 5);
        // 設定它的位置在 Grid 下面 (這裡用 Canvas 的絕對定位，或者用 Anchor 計算)
        // 為了簡單，我們這裡把它靠右中
        container.setFixedSize(200, 0);
        container.setSizeMode(SizeMode.FIXED, SizeMode.WRAP_CONTENT);
        container.setAnchor(Anchor.CENTER_RIGHT, Anchor.CENTER_RIGHT);
        container.setMargin(10);
        container.setColors(0x88224400, 0xFF00FF00); // 綠色系

        container.addChild(new TextWidget(Text.literal("Tags (Flow Layout)")));

        // 核心：FlowPanel
        FlowPanel flow = new FlowPanel(0, 0, 5); // 間距 5
        flow.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.WRAP_CONTENT);

        String[] tags = {"RPG", "Magic", "Hardcore", "Java", "Modding", "UI", "Layout", "Test", "Long Tag Name"};

        for (String tag : tags) {
            TextWidget tagWidget = new TextWidget(Text.literal(tag));
            tagWidget.setPadding(4);
            tagWidget.setColors(0xFF444444, 0xFFAAAAAA); // 像個標籤一樣有背景
            flow.addChild(tagWidget);
        }

        container.addChild(flow);
        return container;
    }

    // =========================================================================================
    //  測試模組 4: HBoxPanel (狀態列)
    // =========================================================================================
    private BaseWidget createHBoxSection() {
        // 一個橫向的條
        HBoxPanel hbox = new HBoxPanel(0, 40, 10); // 高度固定 40
        hbox.setSizeMode(SizeMode.MATCH_PARENT, SizeMode.FIXED); // 寬填滿，高固定

        // 定位在底部
        hbox.setAnchor(Anchor.BOTTOM_LEFT, Anchor.BOTTOM_LEFT);
        // 因為寬度是 Match Parent，我們透過 Margin 來讓它不要貼邊
        hbox.setMargin(230, 10, 0, 10); // 左邊留 230 給 ScrollPanel，右下留 10

        hbox.setPadding(5);
        hbox.setColors(0xCC333333, 0xFFFFFF00); // 黃框

        // 左邊：HP 條 (模擬)
        CanvasPanel hpBar = new CanvasPanel(100, 10);
        hpBar.setColors(0xFFFF0000, 0);
        hpBar.setSizeMode(SizeMode.FIXED, SizeMode.FIXED);
        hpBar.setAnchor(Anchor.CENTER_LEFT, Anchor.CENTER_LEFT); // 垂直居中
        hbox.addChild(hpBar);

        // 中間：填充區 (用 Weight 或 Match Parent)
        // 假設 TextWidget 是 Wrap Content
        TextWidget status = new TextWidget(Text.literal("Status: Online"));
        status.setAnchor(Anchor.CENTER, Anchor.CENTER); // 垂直居中
        hbox.addChild(status);

        // 右邊：功能按鈕
        TextWidget btnExit = new TextWidget(Text.literal("[ EXIT ]"));
        btnExit.setPadding(5);
        btnExit.setColors(0xFF990000, 0xFFFF0000);
        btnExit.setAnchor(Anchor.CENTER_RIGHT, Anchor.CENTER_RIGHT);
        hbox.addChild(btnExit);

        return hbox;
    }
}