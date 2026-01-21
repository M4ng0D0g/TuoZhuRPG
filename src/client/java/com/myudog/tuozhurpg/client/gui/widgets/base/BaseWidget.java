package com.myudog.tuozhurpg.client.gui.widgets.base;

import com.myudog.tuozhurpg.client.gui.widgets.panels.PanelWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public abstract class BaseWidget implements Drawable, Element, Selectable {

    public BaseWidget(int w, int h) {
        this.w = w;
        this.h = h;
    }

    // ----------------------------------------------------------------------------------------------------

    protected boolean initialized = false;
    public boolean isInitialized() {
        return initialized;
    }

    public void init() {
        if (initialized) return;

        this.initialized = true;
        this.onInit();
    }
    protected void onInit() {}

    // ----------------------------------------------------------------------------------------------------

    protected boolean layoutDirty = true;

    public void ensureLayout() {
        if (this.layoutDirty) {
            this.measure();
            this.layout();
            this.layoutDirty = false;
        }
    }

    // ----------------------------------------------------------------------------------------------------

    protected int x, y, w, h;

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
        this.layoutDirty = true;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
        this.layoutDirty = true;
    }
    public int getW() {
        return this.w;
    }
    public void setW(int w) {
        this.w = Math.clamp(w, 0, Integer.MAX_VALUE);
        this.layoutDirty = true;
    }
    public int getH() {
        return this.x;
    }
    public void setH(int h) {
        this.h = Math.clamp(h, 0, Integer.MAX_VALUE);
        this.layoutDirty = true;
    }

    // ----------------------------------------------------------------------------------------------------


    protected Anchor anchorFrom = Anchor.TOP_LEFT;
    protected Anchor anchorTo = Anchor.FREE;
    protected int offsetX = 0;
    protected int offsetY = 0;

    // TODO: 分開調控
    protected int backgroundColor = 0x11111188;
    protected int borderStartColor = 0xFFFFFFDD;
    protected int borderEndColor = 0xFFFFFFDD;
    protected int borderSize = 1;

    protected SizeMode widthMode = SizeMode.FIXED;
    protected SizeMode heightMode = SizeMode.FIXED;

    protected int paddingLeft, paddingRight, paddingTop, paddingBottom;
    protected int marginLeft, marginRight, marginTop, marginBottom;

    // ----------------------------------------------------------------------------------------------------

    protected PanelWidget parent;
    public PanelWidget getParent() {
        return parent;
    }
    public void setParent(@Nullable PanelWidget parent) {
        if (this instanceof PanelWidget panel) {
            // TODO: dsu 避免循環引用
            if (panel != parent) {
                this.parent = parent;
            }
        } else {
            this.parent = parent;
        }
    }



    // ----------------------------------------------------------------------------------------------------

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderWidget(context, mouseX, mouseY, delta);
    }
    protected abstract void renderWidget(DrawContext context, int mouseX, int mouseY, float delta);

    public abstract void measure();
    public abstract void layout(int pX, int pY, int pW, int pH);


    // ----------------------------------------------------------------------------------------------------

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

    public void updateLayout() {
        if (anchorFrom == Anchor.FREE) return;

        int newX = offsetX - Anchor.calX(anchorFrom, getWidth());
        int newY = offsetY - Anchor.calY(anchorFrom, getHeight());

        if (anchorTo != Anchor.FREE && parent != null) {
            newX += Anchor.calX(anchorTo, parent.getWidth()) + parent.getX();
            newY += Anchor.calY(anchorTo, parent.getHeight()) + parent.getY();
        }

        this.setX(newX);
        this.setY(newY);

        for (BaseWidget child : children) {
            child.updateLayout();
        }

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