package com.myudog.tuozhurpg.client.gui.widgets.components;


import com.myudog.tuozhurpg.client.gui.widgets.base.BaseWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

/*
* 用途： 調整購買數量（買 10 瓶藥水）、調整背景音樂大小、調整視角遠近。

新技術：Mouse Dragging (滑鼠拖曳事件)。

目前你只會 onClick (點一下)。滑桿需要處理 onDrag (按住不放並移動)。
* */
public class SliderWidget extends BaseWidget {

    private double value;
    private final Consumer<Double> onValueChange;

    public SliderWidget(int x, int y, int w, int h, double initialValue, Consumer<Double> onValueChange) {
        super(x, y, w, h, Text.empty());
        this.value = MathHelper.clamp(initialValue, 0.0, 1.0);
        this.onValueChange = onValueChange;
        this.updateMessage();
    }

    private void updateMessage() {
        int percent = (int) value * 100;
        this.setMessage(Text.literal(percent + "%"));
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        this.setValueFromMouse(click.x());
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.5F));
    }

    @Override
    public void onDrag(Click click, double offsetX, double offsetY) {
        this.setValueFromMouse(click.x());
        super.onDrag(click, offsetX, offsetY);
    }

    private void setValueFromMouse(double mouseX) {
        double relativeX = mouseX - getX();
        this.value = MathHelper.clamp(relativeX / (double) this.width, 0.0, 1.0);
        this.updateMessage();

        if (this.onValueChange != null) {
            this.onValueChange.accept(this.value);
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        this.setColors(0xFF000000, 0xFF555555);
        this.drawBackground(context);
        this.drawBorder(context);

        int handleWidth = 8;
        int handleX = getX() + (int) (this.value * (this.width - handleWidth));
        int handleColor = isHovered() ? 0xFFFFFFFF : 0xFFAAAAAA;
        context.fill(handleX, getY() + 1, handleX + handleWidth, getY() + height - 1, handleColor);

        context.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                this.getMessage(),
                getX() + width / 2,
                getY() + (height - 8) / 2,
                0xFFE0E0E0
        );
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
