package com.myudog.tuozhurpg.client.gui.screens.test;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.Scaling;
import net.minecraft.client.texture.Scaling.NineSlice;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.daqem.uilib.gui.component.text.ScrollingTextComponent;

public class QuestLogScreen extends Screen {

    // 1. 定義材質
    // 請準備一張 256x256 的羊皮紙圖，四周要有邊框
    private static final Identifier PARCHMENT = Identifier.of("tuozhurpg", "textures/gui/parchment_9slice.png");

    private NineSlice backgroundPanel;
    private ScrollingTextComponent questDescription;

    private int guiLeft;
    private int guiTop;
    private int guiWidth = 200;
    private int guiHeight = 160;

    public QuestLogScreen() {
        super(Text.literal("任務日誌"));
    }

    @Override
    protected void init() {
        int w = 200; // 視窗寬
        int h = 160; // 視窗高
        int x = (this.width - w) / 2;
        int y = (this.height - h) / 2;

        this.backgroundPanel = new Scaling.NineSlice(x, y, new NineSlice.Border(x, y, w, h), false);

        String longStory = """
            §4[主線任務] 失落的村莊
            
            §0村長委託你去調查北方的廢墟。
            據說那裡最近出現了奇怪的聲響...
            
            §1目標：
            §0- 擊敗 3 隻骷髏
            §0- 找到失落的信件
            §0- (可選) 帶回一瓶麥酒
            
            §8(這裡可以寫非常非常長，
            它會自動生成捲軸讓你查看...)
            """;

        // 參數通常是：(TextRenderer, x, y, width, height, content)
        this.questDescription = new ScrollingTextComponent(
                x + 15, y + 40,  // 稍微往內縮，不要蓋到邊框
                w - 30,  // 設定顯示區域的大小
                Text.literal(longStory)
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x80000000);

        // 2. 繪製九宮格羊皮紙
        // 這樣無論視窗怎麼拉，邊角的裝飾都不會變形！
//        this.backgroundPanel.draw(context, mouseX, mouseY, delta);
//        context.drawTexture(RenderPipeline.builder().build(), PARCHMENT, guiLeft, guiTop, 0, 0, guiWidth, guiHeight);

        // 3. 繪製標題
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, (this.height / 2) - 70, 0x553311);

        // 4. 繪製捲動文字
        this.questDescription.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
//        // 將滾輪事件傳遞給文字元件，這樣才能捲動！
//        if (this.questDescription.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
//            return true;
//        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}