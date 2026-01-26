package com.myudog.tuozhurpg.api.creature;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import net.minecraft.util.StringIdentifiable;

import java.util.Map;

public interface IPrefix extends StringIdentifiable {

    default String getTranslationKey() {
        return "prefix." + TuoZhuRpg.MOD_ID + "." + this.asString();
    }

    Map<RpgInnate, Integer> getInnateModifiers();
}
