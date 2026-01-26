package com.myudog.tuozhurpg.api.attribute.custom;

import com.mojang.serialization.Codec;
import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.api.attribute.IRpgAttribute;
import net.minecraft.util.StringIdentifiable;

public enum RpgInnate implements StringIdentifiable, IRpgAttribute {
    VITALITY("vitality"),         // 體力/生命力
    STRENGTH("strength"),         // 力量
    AGILITY("agility"),           // 敏捷
    INTELLIGENCE("intelligence"), // 智力
    LUCK("luck"),                 // 幸運
    SPIRIT("spirit"),             // 精神 (常影響回魔或魔抗)
    CHARISMA("charisma"),         // 魅力 (常影響交易或召喚物)
    PERCEPTION("perception");     // 感知 (常影響遠程或偵測)
    // RANGE = -100 ~ 100

    public static final Codec<RpgInnate> CODEC = StringIdentifiable.createCodec(RpgInnate::values);
    private final String id;

    RpgInnate(String id) {
        this.id = id;
    }

    @Override
    public String asString() {
        return this.id; // 存檔時會存成 "strength"
    }

}
