package com.myudog.tuozhurpg.client.gui.screens.test;


import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.client.gui.screens.base.BaseScreen;
import com.myudog.tuozhurpg.client.gui.widgets.components.IconButton;
import com.myudog.tuozhurpg.client.gui.widgets.components.SliderWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.DraggablePanel;
import com.myudog.tuozhurpg.client.gui.widgets.panels.base.PanelWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.ScrollContainerWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TestScreen extends BaseScreen {

    private PanelWidget backgroundPanel;
    private SliderWidget slider;
    private IconButton iconButton;
    private DraggablePanel questWindow;
    private ScrollContainerWidget questList;

    public TestScreen() {
        super(Text.literal("Text Screen"));
    }

    @Override
    protected void init() {
        super.init();

        //
        this.backgroundPanel = new PanelWidget(centerX - 100, centerY - 80, 200, 160);
        this.addDrawableChild(backgroundPanel);

        //
        this.slider = new SliderWidget(centerX - 80, centerY - 10, 160, 10, 0.5f,
                val -> TuoZhuRpg.LOGGER.info(val.toString())
        );
        this.addDrawableChild(slider);

//        this.iconButton = new IconButton(centerX - 80, centerY + 5, 160, 10, , 0, 0);

        // === 建立可拖動視窗 ===
//        questWindow = new DraggablePanel(200, 150);
//        questWindow.setAnchor(Anchor.CENTER, Anchor.CENTER); // 一開始置中
//        questWindow.setHeaderHeight(20); // 頂部 20px 可以抓著拖
//        questWindow.setTheme(DebugTheme.DEBUG_16); // 黑色風格
//
//        // 計算初始位置
//        questWindow.updateLayout(0, 0, this.width, this.height);
//        this.addDrawableChild(questWindow);


        // === 建立內部滾動區 ===
        // 滾動區要比視窗小一點 (扣掉標題列和邊框)
//        questList = new ScrollContainerWidget(190, 120);

        // 告訴它內容很長 (例如 500px)
//        questList.setContentHeight(500);

        // 設定滾動區在視窗裡面的位置 (相對座標)
        // 這裡我們不能直接用 addDrawableChild 加入 screen，因為我們希望它跟著視窗動
        // 但因為我們還沒實作完整的父子層級事件傳遞，這裡先用簡單做法：

        // *重要*：目前你的架構中，Widget 都是平行的 (都在 Screen 的 children 裡)
        // 要讓 questList 跟著 questWindow 動，你需要 override questWindow 的 updateLayout 或 setX/Y
        // 讓它去推動裡面的 questList。

        // 暫時做法：把 questList 也加到 Screen，但每一幀 update 都去對齊 Window
//        this.addDrawableChild(questList);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

//        questList.setX(questWindow.getX() + 5);
//        questList.setY(questWindow.getY() + 25); // 避開標題列

        // 只需要呼叫 super.render，所有的 widgets (面板、血條、按鈕) 都會自動畫出來！
        // 不需要手動在這裡寫 context.fill ...
        super.render(context, mouseX, mouseY, delta);

        // 如果有額外的文字要疊在最上面，才寫在這裡
        context.drawCenteredTextWithShadow(textRenderer, "角色面板", width / 2, height / 2 - 70, 0xFFFFFF);
    }
}