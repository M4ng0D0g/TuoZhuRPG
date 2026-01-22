package com.myudog.tuozhurpg.client.gui.screens.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.PanelWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;

public abstract class BaseScreen extends Screen {

    protected PanelWidget rootPanel;
    protected boolean isLayoutDirty = true;

    protected BaseScreen(Text title) {
        super(title);
    }

    public int centerX() {
        return this.width / 2;
    }
    public int centerY() {
        return this.height / 2;
    }

    public int vw(float percent) {
        return (int) (this.width * percent);
    }

    /** Viewport Height Percentage: 螢幕高度的 % */
    public int vh(float percent) {
        return (int) (this.height * percent);
    }

    // ----------------------------------------------------------------------------------------------------

    protected abstract PanelWidget buildContent();

    @Override
    protected void init() {
        if (this.rootPanel == null) this.rootPanel = buildContent();
        if (this.rootPanel != null) this.rootPanel.init();
        this.isLayoutDirty = true;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.width = width;
        this.height = height;

        this.isLayoutDirty = true;
        performLayout();
    }

    // ----------------------------------------------------------------------------------------------------

    private void performLayout() {
        if (rootPanel != null) {
            rootPanel.measure(this.width, this.height);
            rootPanel.layout(0, 0, this.width, this.height);
            this.isLayoutDirty = false;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.rootPanel == null) return;

        if (this.isLayoutDirty) {
            performLayout();
        }
        this.rootPanel.render(context, mouseX, mouseY, delta);

        // 4. 畫 Tooltip (這是 Minecraft 原生的功能，通常放在最後畫)
        // 您可能需要實作自己的 Tooltip 邏輯，或者將 Widget 的 tooltip 轉發給 Screen
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (rootPanel != null) {
            return rootPanel.mouseClicked(click, doubled);
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (rootPanel != null) {
            return rootPanel.mouseReleased(click);
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        if (rootPanel != null) {
            return rootPanel.mouseDragged(click, offsetX, offsetY);
        }
        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (rootPanel != null) {
            // 注意：這裡是 1.20.2+ 的簽名，舊版只有 verticalAmount
            return rootPanel.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (super.keyPressed(input)) return true;
        if (rootPanel != null) return rootPanel.keyPressed(input);
        return false;
    }

    @Override
    public boolean charTyped(CharInput input) {
        if (rootPanel != null) {
            return rootPanel.charTyped(input);
        }
        return super.charTyped(input);
    }

    @Override
    public void tick() {
        // 如果有動畫需求，可以在這裡呼叫 rootPanel.tick()
    }


    // ----------------------------------------------------------------------------------------------------

    @Override
    public boolean shouldPause() {
        return false; // 單人遊戲時是否暫停遊戲，通常設為 false
    }

}
