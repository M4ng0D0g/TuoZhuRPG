package com.myudog.tuozhurpg.client.gui.widgets.base;

import net.minecraft.util.math.MathHelper;

public class Color implements IColor {

    int value;

    private Color(int value) {
        this.value = value;
    }

    public static Color of(int colorValue) {
        return new Color(colorValue);
    }

    public static Color rgb(int r, int g, int b) {
        return argb(255, r, g, b);
    }

    public static Color argb(int a, int r, int g, int b) {
        int value = (MathHelper.clamp(a, 0, 255) << 24)
                | (MathHelper.clamp(r, 0, 255) << 16)
                | (MathHelper.clamp(g, 0, 255) << 8)
                | (MathHelper.clamp(b, 0, 255));

        return new Color(value);
    }

    @Override
    public int getValue() {
        return value;
    }
    public int getAlpha() { return (value >> 24) & 0xFF; }
    public int getRed()   { return (value >> 16) & 0xFF; }
    public int getGreen() { return (value >> 8)  & 0xFF; }
    public int getBlue()  { return value & 0xFF; }


    public Color withAlpha(int a) {
        int originalRGB = this.value & 0x00FFFFFF;
        int newAlpha = (MathHelper.clamp(a, 0, 255) << 24);
        return Color.of(newAlpha | originalRGB);
    }
}
