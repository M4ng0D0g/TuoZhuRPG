//package com.myudog.tuozhurpg.client.gui.widgets.components.base;
//
//import com.myudog.tuozhurpg.client.gui.widgets.base.ControlWidget;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.Click;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
//import net.minecraft.client.sound.PositionedSoundInstance;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.text.Text;
//
//// 定義一個功能介面，用來傳遞 "按下去要執行的程式碼"
//public class ButtonWidget extends ControlWidget {
//
//    // 儲存按下去後的動作 (Callback)
//    private final Runnable onPress;
//
//    // 建構子
//    public ButtonWidget(int x, int y, int w, int h, Text text, Runnable onPress) {
//        super(x, y, w, h, text);
//        this.onPress = onPress;
//    }
//
//    // 覆寫點擊事件 (這是 ClickableWidget 的核心)
//    @Override
//    public void onClick(Click click, boolean doubled) {
//        // 1. 播放點擊音效 (RPG 風格可以用 UI_BUTTON_CLICK 或更特殊的聲音)
//        MinecraftClient.getInstance().getSoundManager().play(
//                PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F)
//        );
//
//        // 2. 執行傳入的動作
//        if (this.onPress != null) {
//            this.onPress.run();
//        }
//    }
//
//    @Override
//    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
//        // === 1. 處理互動狀態 ===
//        // isHovered() 是 ClickableWidget 內建的方法，判斷滑鼠是否在按鈕上
//        boolean hovered = this.isHovered(mouseX, mouseY);
//
//        // 根據狀態決定顏色
//        // 如果 hover，邊框變亮一點，或者背景變亮
//        if (hovered) {
//            setColors(0xF0201020, 0xFFFFFFA0, 0xFFA0A000);
//        } else {
//            setColors(0xF0100010, 0x505000FF, 0x5028007F);
//        }
//
//        // === 2. 繪製背景 (直接畫，不需要依賴 PanelWidget) ===
//        this.drawBackground(context);
//        this.drawBorder(context);
//
//        // === 3. 繪製文字 (置中) ===
//        // 計算文字顏色 (hover 時變金色，平常白色)
//        int textColor = hovered ? 0xFFFFD700 : 0xFFE0E0E0;
//
//        // drawCenteredTextWithShadow 幫你自動算中心點
//        context.drawCenteredTextWithShadow(
//                MinecraftClient.getInstance().textRenderer,
//                this.getMessage(),
//                this.getX() + this.w / 2,
//                this.getY() + (this.h - 8) / 2, // 8 是字體高度的一半
//                textColor
//        );
//    }
//
//    @Override
//    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
//        // 這裡通常呼叫 default 實現即可，除非你要做無障礙閱讀
//        this.appendDefaultNarrations(builder);
//    }
//}