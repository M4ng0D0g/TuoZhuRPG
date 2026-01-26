package com.myudog.tuozhurpg.api.creature.custom;

import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.api.creature.IRune;

public enum RpgRune implements IRune {
    //    FEATHER_RUNE("feather_rune", RpgInnate.WEIGHT, 0.5), // 減輕重量 50%
//    IRON_RUNE("iron_rune", RpgInnate.DURABILITY_BONUS, 1.2); // 耐久 1.2 倍
    ;

    @Override
    public String getTranslationKey() {
        return "";
    }

    @Override
    public RpgInnate getTargetInnate() {
        return null;
    }

    @Override
    public double getMultiplier() {
        return 0;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public String asString() {
        return "";
    }
}