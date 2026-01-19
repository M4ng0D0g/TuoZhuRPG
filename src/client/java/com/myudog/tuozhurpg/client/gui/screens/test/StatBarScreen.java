package com.myudog.tuozhurpg.client.gui.screens.test;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.client.gui.screens.base.BaseScreen;
import com.myudog.tuozhurpg.client.gui.widgets.components.ButtonWidget;
import com.myudog.tuozhurpg.client.gui.widgets.components.StatBarWidget;
import com.myudog.tuozhurpg.client.gui.widgets.panels.PanelWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public class StatBarScreen extends BaseScreen {

    public StatBarScreen() {
        super(Text.literal("StatBar Screen"));
    }

    private PanelWidget backgroundPanel;
    private StatBarWidget hpBar;
    private ButtonWidget closeButton;

    @Override
    protected void init() {
        super.init();

        //
        this.backgroundPanel = new PanelWidget(centerX - 100, centerY - 80, 200, 160);
        this.addDrawableChild(backgroundPanel);

        //
        this.hpBar = new StatBarWidget(centerX - 80, centerY - 40, 160, 10, 0xFFFF0000); // 紅色
        this.hpBar.setTooltip(Tooltip.of(Text.literal("生命值: 100/100"))); // 原版自帶功能
        this.addDrawableChild(hpBar);

        //
        this.closeButton = new ButtonWidget(centerX - 40, centerY + 55, 80, 20, Text.literal("關閉介面"),
                () -> {
                    TuoZhuRpg.LOGGER.info("ClickButton was clicked.");
                    close();
                }
        );
        this.addDrawableChild(closeButton);
    }
}
