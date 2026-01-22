package com.myudog.tuozhurpg.client.gui.widgets.base;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public abstract class ControlWidget extends BaseWidget {
    public ControlWidget(int w, int h) {
        super(w, h);
    }

    @Override
    protected int[] onMeasure(int availW, int availH) {
        return new int[]{
                paddingLeft + paddingRight,
                paddingTop + paddingBottom
        };
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.w &&
                mouseY >= this.y && mouseY < this.y + this.h;
    }

    public void playDownSound(MinecraftClient client) {
        client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
