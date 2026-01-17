package com.myudog.tuozhurpg.client.gui.screens.test;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KitScreen extends Screen {

    // 1. 模擬一張羊皮紙背景 (暫時用原版的書本背景代替，你可以換成自己的)
    private static final Identifier BG_TEXTURE = Identifier.of("minecraft", "textures/gui/book.png");
    private TextFieldWidget nameField;
    private CyclingButtonWidget<String> classSelector;
    private String currentClass = "戰士"; // 預設值

    // 2. 屬性點數邏輯
    private int strValue = 18;
    private int dexValue = 14;

    public KitScreen() {
        super(Text.literal("角色屬性"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // ... 保留你原本的名字輸入框 ...

        // === 新增元件 1: 職業選擇器 (Cycling Button) ===
        // 這非常適合 RPG，按一下切換下一個選項
        this.classSelector = CyclingButtonWidget.<String>builder(value -> Text.literal(value)) // 修正點 1: 加入 <String>，並移除強制轉型
                .values("戰士", "法師", "盜賊", "牧師")
                .initially(this.currentClass)
                .build(
                        centerX + 60, centerY - 30, 80, 20,
                        Text.literal("職業"),
                        (button, newValue) -> {
                            this.currentClass = newValue; // 修正點 2: 這裡不需要 (String) 強制轉型了，它已經知道是 String
                            // System.out.println("切換職業為: " + newValue); // 測試用
                        }
                );
        this.addDrawableChild(classSelector);


        // === 新增元件 2: 屬性加減按鈕 ===
        // 為了簡單，我們用普通的 ButtonWidget，文字寫 "+" 或 "-"

        // [力量 STR] 減少按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-"), btn -> {
            this.strValue--;
        }).dimensions(centerX + 10, centerY + 55, 15, 15).build());

        // [力量 STR] 增加按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+"), btn -> {
            this.strValue++;
        }).dimensions(centerX + 90, centerY + 55, 15, 15).build());

        // [敏捷 DEX] 減少按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("-"), btn -> {
            this.dexValue--;
        }).dimensions(centerX + 10, centerY + 70, 15, 15).build());

        // [敏捷 DEX] 增加按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+"), btn -> {
            this.dexValue++;
        }).dimensions(centerX + 90, centerY + 70, 15, 15).build());


        // === 新增元件 3: 隱藏頭盔 (Checkbox) ===
        // 1.21 的 Checkbox 寫法
        CheckboxWidget hideHelmet = CheckboxWidget.builder(Text.literal("隱藏頭盔"), this.textRenderer)
                .pos(centerX - 80, centerY + 20)
                .checked(false) // 預設是否勾選
                .callback((checkbox, checked) -> {
                    // 處理勾選邏輯
                })
                .build();
        this.addDrawableChild(hideHelmet);

        // 3. 確認按鈕
        this.addDrawableChild(ButtonWidget.builder(Text.literal("保存設定"), button -> {
                    this.close();
                    // 這裡可以加入發送封包給伺服器的邏輯
                })
                .dimensions(centerX - 50, centerY + 50, 100, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. 畫背景 (黑色遮罩)
        context.fill(0, 0, this.width, this.height, 0xC0000000);

        int bgWidth = 192;
        int startX = (this.width - bgWidth) / 2;
        int startY = (this.height - 192) / 2;

        // ... 保留標題 ...

        // === 修改屬性顯示 logic ===
        // 不要寫死 "18"，而是顯示變數 strValue
        // 使用 String.format 來對齊文字
        context.drawText(this.textRenderer, "力量 (STR): " + this.strValue, startX + 30, startY + 60, 0x333333, false);
        context.drawText(this.textRenderer, "敏捷 (DEX): " + this.dexValue, startX + 30, startY + 75, 0x333333, false);


        // === 新增顯示: 3D 玩家模型 (Paper Doll) ===
        // 參數：x, y, 大小(縮放), 滑鼠X跟隨(0), 滑鼠Y跟隨(0), 實體物件
        // 我們讓模型看著滑鼠的位置 (mouseX, mouseY)

        // 調整這些數字讓模型站在適合的位置 (通常在左邊或中間空白處)
        int dollX = startX + 40;
        int dollY = startY + 120; // 腳的位置
        int size = 30; // 縮放大小

        // 這裡需要呼叫原版的 InventoryScreen 來幫忙畫
        // 注意：ClientPlayerEntity 就是 this.client.player
        if (this.client.player != null) {
            // 計算滑鼠相對位置，讓角色的頭跟著動
            float lookX = (float) (dollX) - mouseX;
            float lookY = (float) (dollY - 50) - mouseY;

            InventoryScreen.drawEntity(context, dollX, dollY, dollX + 5, dollY + 5, size, 1, lookX, lookY, this.client.player);
        }
        // 記得呼叫 super 讓按鈕和輸入框顯示出來
        super.render(context, mouseX, mouseY, delta);
    }
}