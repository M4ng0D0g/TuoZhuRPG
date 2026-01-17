package com.myudog.tuozhurpg.client.gui.widgets.base;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public abstract class BaseWidget extends ClickableWidget {

//    protected int padding = 5; // 內距: 內容與邊框的距離 TODO
    protected Anchor anchorFrom = Anchor.TOP_LEFT;
    protected Anchor anchorTo = Anchor.FREE;

    protected int offsetX = 0;
    protected int offsetY = 0;

    protected int backgroundColor = 0x11111188;

    protected int borderStartColor = 0xFFFFFFDD;
    protected int borderEndColor = 0xFFFFFFDD;
    protected int borderSize = 1;

    // ----------------------------------------------------------------------------------------------------

    public BaseWidget(int x, int y, int w, int h, Text text) {
        super(x, y, w, h, text);
    }

//    public BaseWidget setPadding(int padding) {
//        this.padding = padding;
//        return this;
//    }

    public void setBorderSize(int size) {
        this.borderSize = MathHelper.clamp(size, 0, Integer.MAX_VALUE);
    }

    public void setAnchor(Anchor from, Anchor to) {
        this.anchorFrom = from;
        this.anchorTo = to;
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void setColors(int bgColor, int bdColor) {
        backgroundColor = bgColor;
        borderStartColor = bdColor;
        borderEndColor = bdColor;
    }
    public void setColors(int bgColor, int bdColorStart, int bdColorEnd) {
        backgroundColor = bgColor;
        borderStartColor = bdColorStart;
        borderEndColor = bdColorEnd;
    }
    public void setTheme(ITheme theme) {
        backgroundColor = theme.getBackgroundColor();
        borderStartColor = theme.getBorderStartColor();
        borderEndColor = theme.getBorderEndColor();
    }

    // ----------------------------------------------------------------------------------------------------

    public void updateLayout(@Nullable BaseWidget parent) {
        if (anchorFrom == Anchor.FREE) return;

        int newX = offsetX - Anchor.calX(anchorFrom, getWidth());
        int newY = offsetY - Anchor.calY(anchorFrom, getHeight());

        if (anchorTo != Anchor.FREE && parent != null) {
            newX += Anchor.calX(anchorTo, parent.getWidth()) + parent.getX();
            newY += Anchor.calY(anchorTo, parent.getHeight()) + parent.getY();
        }

        this.setX(newX);
        this.setY(newY);

    }
    public void updateLayout(int parentX, int parentY, int parentWidth, int parentHeight) {
        if (anchorFrom == Anchor.FREE) return;

        int newX = offsetX - Anchor.calX(anchorFrom, getWidth());
        int newY = offsetY - Anchor.calY(anchorFrom, getHeight());

        if (anchorTo != Anchor.FREE) {
            newX += Anchor.calX(anchorTo, parentWidth) + parentX;
            newY += Anchor.calY(anchorTo, parentHeight) + parentY;
        }

        this.setX(newX);
        this.setY(newY);
    }

    // ----------------------------------------------------------------------------------------------------


    @Override
    public void playDownSound(SoundManager soundManager) {

    }

    protected void drawBackground(DrawContext context) {
        context.fill(getX(), getY(), getX() + width, getY() + height, backgroundColor);
    }

    protected void drawBorder(DrawContext context) {
        context.fillGradient(getX(), getY(), getX() + borderSize, getY() + height, borderStartColor, borderEndColor); // 左
        context.fillGradient(getX() + width - borderSize, getY(), getX() + width, getY() + height, borderStartColor, borderEndColor); // 右
        context.fill(getX(), getY(), getX() + width, getY() + borderSize, borderStartColor); // 上
        context.fill(getX(), getY() + height - borderSize, getX() + width, getY() + height, borderEndColor); // 下
    }

    // 覆寫點擊事件 (這是 ClickableWidget 的核心)
}