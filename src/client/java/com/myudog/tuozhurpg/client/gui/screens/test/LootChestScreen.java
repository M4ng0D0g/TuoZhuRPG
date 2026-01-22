//package com.myudog.tuozhurpg.client.gui.screens.test;
//
//import net.minecraft.client.gl.RenderPipelines;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.widget.ButtonWidget;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LootChestScreen extends Screen {
//
//    // 1. 引用原版的大箱子材質 (54格)
//    // 注意：在 1.21 中，路徑通常是 textures/gui/container/generic_54.png
//    private static final Identifier CHEST_TEXTURE = Identifier.of("minecraft", "textures/gui/container/generic_54.png");
//
//    // 介面大小 (原版大箱子標準尺寸)
//    private final int backgroundWidth = 176;
//    private final int backgroundHeight = 222;
//
//    // 儲存起始座標
//    private int guiLeft;
//    private int guiTop;
//
//    // 模擬的戰利品列表 (用 List 來存，這樣我們才知道哪一格有什麼)
//    private final List<ItemStack> fakeLoot = new ArrayList<>();
//
//    public LootChestScreen() {
//        super(Text.literal("地下城戰利品"));
//
//        // 初始化一些假資料 (模擬 RPG 掉落物)
//        // 這裡我們只填滿前幾格做示範
//        fakeLoot.add(new ItemStack(Items.DIAMOND_SWORD));
//        fakeLoot.add(new ItemStack(Items.GOLDEN_APPLE, 5));
//        fakeLoot.add(new ItemStack(Items.ENCHANTED_BOOK));
//        fakeLoot.add(new ItemStack(Items.EMERALD, 64));
//        fakeLoot.add(ItemStack.EMPTY); // 空格
//        fakeLoot.add(new ItemStack(Items.POTION));
//
//        // 填滿剩下的格子避免 IndexOutOfBounds (總共 54 格)
//        while (fakeLoot.size() < 54) {
//            fakeLoot.add(ItemStack.EMPTY);
//        }
//    }
//
//    @Override
//    protected void init() {
//        // 計算置中位置
//        this.guiLeft = (this.width - this.backgroundWidth) / 2;
//        this.guiTop = (this.height - this.backgroundHeight) / 2;
//
//        // 加入一個 RPG 風格按鈕 "全部拾取"
//        // 按鈕位置設在箱子右側或是下方
//        this.addDrawableChild(ButtonWidget.builder(Text.literal("全部拾取 (Take All)"), button -> {
//                    this.close();
//                    // 這裡可以寫邏輯：發送封包給伺服器說「給我錢！」
//                    if (this.client.player != null) {
//                        this.client.player.sendMessage(Text.literal("§a你拿走了所有寶藏！"), false);
//                    }
//                })
//                .dimensions(guiLeft + backgroundWidth - 100, guiTop - 25, 100, 20)
//                .build());
//    }
//
//    @Override
//    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        // 1. 畫黑色半透明背景 (遮住遊戲畫面)
////        this.renderBackground(context, mouseX, mouseY, delta);
//        context.fill(0, 0, this.width, this.height, 0x80000000);
//
//        // 2. 繪製箱子底圖
//        // 參數：材質, x, y, u, v, width, height
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, CHEST_TEXTURE, guiLeft, guiTop, 0, 0, backgroundWidth, backgroundHeight, 22, 22);
//
//        // 3. 繪製標題
//        context.drawText(this.textRenderer, this.title, guiLeft + 8, guiTop + 6, 0x404040, false);
//        context.drawText(this.textRenderer, Text.literal("玩家背包"), guiLeft + 8, guiTop + backgroundHeight - 94, 0x404040, false);
//
//        // 4. 繪製戰利品 (核心邏輯)
//        // 大箱子有 6 行，每行 9 格
//        for (int row = 0; row < 6; row++) {
//            for (int col = 0; col < 9; col++) {
//                // 計算每一格在螢幕上的座標
//                // 8 和 18 是 Minecraft GUI 的標準邊距
//                int slotX = guiLeft + 8 + col * 18;
//                int slotY = guiTop + 18 + row * 18;
//
//                // 算出 List 中的索引 (0 ~ 53)
//                int index = row * 9 + col;
//
//                ItemStack item = fakeLoot.get(index);
//
//                if (!item.isEmpty()) {
//                    // 畫物品
//                    context.drawItem(item, slotX, slotY);
//                    // 畫物品數量 (右下角的數字)
//                    context.drawItemTooltip(this.textRenderer, item, slotX, slotY);
//                }
//
//                // 5. 處理滑鼠懸浮提示 (Tooltip)
//                // 檢查滑鼠是否在這個格子內 (16x16 大小)
//                if (isPointWithinBounds(slotX, slotY, 16, 16, mouseX, mouseY) && !item.isEmpty()) {
//                    // 顯示物品名稱與 Lore
//                    context.drawTooltip(this.textRenderer, getTooltipFromItem(client, item), mouseX, mouseY);
//                }
//            }
//        }
//
//        // 呼叫 super 繪製按鈕
//        super.render(context, mouseX, mouseY, delta);
//    }
//
//    // 輔助方法：檢查滑鼠是否在某個區域內
//    private boolean isPointWithinBounds(int x, int y, int width, int height, int mouseX, int mouseY) {
//        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
//    }
//}