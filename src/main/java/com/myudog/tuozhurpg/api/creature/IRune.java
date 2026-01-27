package com.myudog.tuozhurpg.api.creature;

import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import net.minecraft.util.StringIdentifiable;

// 鑲嵌符文，用來強化天賦係數
public interface IRune extends StringIdentifiable {

    String getTranslationKey();

    // 定義符文的強化邏輯
    // 這裡示範簡單的：針對某個 Innate 提供百分比或數值加成
    // 例如：針對 WEIGHT 給予 -10%
    RpgInnate getTargetInnate();
    double getMultiplier(); // 強化係數 (例如 1.1 代表增強 10%)

    // 符文的顏色 (顯示用)
    int getColor();
}
