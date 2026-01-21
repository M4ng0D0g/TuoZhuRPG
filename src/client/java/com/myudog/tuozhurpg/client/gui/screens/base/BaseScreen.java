package com.myudog.tuozhurpg.client.gui.screens.base;

import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.PanelWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class BaseScreen extends Screen {

    protected boolean isLayoutDirty;
    protected PanelWidget rootPanel;

    protected BaseScreen(Text title) {
        super(title);
    }


    @Override
    protected void init() {
        isLayoutDirty = true;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.width = width;
        this.height = height;

        if (rootPanel != null) {
            rootPanel.measure();
            rootPanel.layout(0, 0, this.width, this.height);
        }
    }

    // ----------------------------------------------------------------------------------------------------

    public int centerX() {
        return this.width / 2;
    }
    public int centerY() {
        return this.height / 2;
    }




    // ----------------------------------------------------------------------------------------------------


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.rootPanel == null) return;

        if (this.isLayoutDirty) {
            rootPanel.measure();
            rootPanel.layout(0, 0, this.width, this.height);
            this.isLayoutDirty = false;
        }
        this.rootPanel.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {

    }

    // TODO 一些百分比大小

}
