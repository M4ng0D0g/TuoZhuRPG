package com.myudog.tuozhurpg.api.attribute;

import com.myudog.tuozhurpg.TuoZhuRpg;
import net.minecraft.util.StringIdentifiable;

public interface IRpgAttribute extends StringIdentifiable {

    default String getTranslationKey() {
        return "attribute." + TuoZhuRpg.MOD_ID + "." + this.asString();
    }

    default boolean isPercentage() {
        return false;
    }

    default int getColor() {
        return 0xFFFFFF;
    }
}
