package com.myudog.tuozhurpg.client.gui.screens.base;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class BaseScreen extends Screen {

    protected BaseScreen(Text title) {
        super(title);
    }

    protected int centerX;
    protected int centerY;

    @Override
    protected void init() {
        centerX = this.width / 2;
        centerY = this.height / 2;
    }

    // TODO 一些百分比大小

}
