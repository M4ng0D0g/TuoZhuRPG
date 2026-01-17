package com.myudog.tuozhurpg.client.gui.widgets.base;

public enum DebugTheme implements ITheme {
    DEBUG_1(Colors.WHITE),      // 白色
    DEBUG_2(Colors.ORANGE),     // 橘色
    DEBUG_3(Colors.MAGENTA),    // 品紅
    DEBUG_4(Colors.LIGHT_BLUE), // 淡藍
    DEBUG_5(Colors.YELLOW),     // 黃色
    DEBUG_6(Colors.LIME),       // 萊姆
    DEBUG_7(Colors.PINK),       // 粉紅
    DEBUG_8(Colors.GRAY),       // 灰色
    DEBUG_9(Colors.LIGHT_GRAY), // 淡灰
    DEBUG_10(Colors.CYAN),      // 青色
    DEBUG_11(Colors.PURPLE),    // 紫色
    DEBUG_12(Colors.BLUE),      // 藍色
    DEBUG_13(Colors.BROWN),     // 棕色
    DEBUG_14(Colors.GREEN),     // 綠色
    DEBUG_15(Colors.RED),       // 紅色
    DEBUG_16(Colors.BLACK);     // 黑色

    private final int bgColor;
    private final int bdColor;

    DebugTheme(Color baseColor) {
        this.bgColor = baseColor.withAlpha(80).getValue();
        this.bdColor = baseColor.withAlpha(255).getValue();
    }

    @Override
    public int getBackgroundColor() {
        return bgColor;
    }

    @Override
    public int getBorderStartColor() {
        return bdColor;
    }

    @Override
    public int getBorderEndColor() {
        return bdColor;
    }

    private static final DebugTheme[] VALUES = values();

    public static DebugTheme byId(int id) {
        return VALUES[Math.abs(id) % VALUES.length];
    }
}
