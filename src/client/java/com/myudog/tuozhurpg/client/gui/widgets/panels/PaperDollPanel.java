package com.myudog.tuozhurpg.client.gui.widgets.panels;

import net.minecraft.client.gui.DrawContext;

public class PaperDollPanel extends PanelWidget {

    public PaperDollPanel(int w, int h) {
        super(w, h);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        // drawEntity(...) // 畫人物
    }
}
