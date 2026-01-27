package com.myudog.tuozhurpg.api.creature.custom;

import com.mojang.serialization.Codec;
import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.api.creature.IRune;
import net.minecraft.util.StringIdentifiable;

public enum RpgRune implements IRune {
    //    FEATHER_RUNE("feather_rune", RpgInnate.WEIGHT, 0.5), // 減輕重量 50%
//    IRON_RUNE("iron_rune", RpgInnate.DURABILITY_BONUS, 1.2); // 耐久 1.2 倍
    ;

    public static final Codec<RpgRune> CODEC = StringIdentifiable.createCodec(RpgRune::values);
    private final String id;
    private final RpgInnate targetInnate;
    private final double multiplier;
    private final int color;

    RpgRune(String id, RpgInnate targetInnate, double multiplier, int color) {
        this.id = id;
        this.targetInnate = targetInnate;
        this.multiplier = multiplier;
        this.color = color;
    }

    @Override
    public String getTranslationKey() {
        return "rune." + TuoZhuRpg.MOD_ID + "." + this.id;
    }

    @Override
    public RpgInnate getTargetInnate() {
        return this.targetInnate;
    }

    @Override
    public double getMultiplier() {
        return this.multiplier;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public String asString() {
        return this.id;
    }
}