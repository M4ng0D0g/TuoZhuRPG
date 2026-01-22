//package com.myudog.tuozhurpg.client.gui.widgets.panels;
//
//import com.myudog.tuozhurpg.client.gui.enums.Anchor;
//import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;
//import net.minecraft.client.gui.Click;
//import net.minecraft.client.gui.DrawContext;
//
//public class DraggablePanel extends PanelWidget {
//
//    private int headerHeight = 20;
//
//    private boolean isDragging = false;
//    private double dragStartX;
//    private double dragStartY;
//
//    public DraggablePanel(int width, int height) {
//        super(width, height);
//    }
//
//    public DraggablePanel setHeaderHeight(int height) {
//        this.headerHeight = height;
//        return this;
//    }
//
//    @Override
//    public boolean mouseClicked(Click click, boolean doubled) {
//        if (click.button() == 0) {
//            if (isMouseOver(click.x(), click.y())) {
//                if (click.y() <= this.getY() + headerHeight) {
//                    this.isDragging = true;
//                    this.dragStartX = click.x() - this.getX();
//                    this.dragStartY = click.y() - this.getY();
//
//                    // 播放個點擊音效 (選擇性)
//                    // MinecraftClient.getInstance().getSoundManager().play(...);
//
//                    return true;
//                }
//            }
//        }
//        return super.mouseClicked(click, doubled);
//    }
//
//    @Override
//    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
//        if (this.isDragging) {
//            int newX = (int) (click.x() - this.dragStartX);
//            int newY = (int) (click.y() - this.dragStartY);
//
//            // 重要：拖動後，這個面板就變成 "自由定位 (Free)" 了
//            // 如果不改 Anchor，下次 updateLayout 可能又會把它拉回原位
//            this.setAnchor(Anchor.TOP_LEFT, Anchor.FREE);
//            this.setOffset(0, 0);
//
//            this.setX(newX);
//            this.setY(newY);
//            return true;
//        }
//        return super.mouseDragged(click, offsetX, offsetY);
//    }
//
//    @Override
//    public boolean mouseReleased(Click click) {
//        if (click.button() == 0) {
//            this.isDragging = false;
//        }
//        return super.mouseReleased(click);
//    }
//
//    // 可以在這裡畫個簡單的標題列背景，讓使用者知道哪裡可以拖
//    @Override
//    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
//        super.renderWidget(context, mouseX, mouseY, delta);
//
//        // 畫標題列 (稍微亮一點的顏色)
//        context.fill(getX(), getY(), getX() + w, getY() + headerHeight, 0x40FFFFFF);
//    }
//}