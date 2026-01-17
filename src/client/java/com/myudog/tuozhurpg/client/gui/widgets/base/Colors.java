package com.myudog.tuozhurpg.client.gui.widgets.base;

public class Colors {

    private Colors() {}

    public static final Color WHITE      = Color.of(0xFFFFFFFF); // 白色
    public static final Color ORANGE     = Color.of(0xFFD87F33); // 橘色
    public static final Color MAGENTA    = Color.of(0xFFB24CD8); // 品紅
    public static final Color LIGHT_BLUE = Color.of(0xFF6699D8); // 淡藍
    public static final Color YELLOW     = Color.of(0xFFE5E533); // 黃色
    public static final Color LIME       = Color.of(0xFF7FCC19); // 黃綠 (萊姆)
    public static final Color PINK       = Color.of(0xFFF27FA5); // 粉紅
    public static final Color GRAY       = Color.of(0xFF4C4C4C); // 灰色
    public static final Color LIGHT_GRAY = Color.of(0xFF999999); // 淡灰
    public static final Color CYAN       = Color.of(0xFF4C7F99); // 青色
    public static final Color PURPLE     = Color.of(0xFF7F3FB2); // 紫色
    public static final Color BLUE       = Color.of(0xFF334CB2); // 藍色
    public static final Color BROWN      = Color.of(0xFF664C33); // 棕色
    public static final Color GREEN      = Color.of(0xFF667F33); // 綠色
    public static final Color RED        = Color.of(0xFF993333); // 紅色
    public static final Color BLACK      = Color.of(0xFF191919); // 黑色

    /**
     * 取得半透明版本的標準色
     * @param color 標準色
     * @param alpha 透明度 (0-255)
     * @return 新的顏色
     */
    public static Color transparent(Color color, int alpha) {
        return color.withAlpha(alpha);
    }
}
