package com.myudog.tuozhurpg.api.creature.custom;

import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.api.creature.IPrefix;

import java.util.Map;

public enum RpgPrefix implements IPrefix {
    FIERY("fiery"),   // 猛烈的
    ANCIENT("ancient"); // 遠古的

    private final String id;

    RpgPrefix(String id) {
        this.id = id;
    }

    @Override
    public Map<RpgInnate, Integer> getInnateModifiers() {
        if (this == FIERY) return Map.of(RpgInnate.STRENGTH, 5);
        return Map.of();
    }

    @Override
    public String asString() {
        return this.id.toLowerCase();
    }
}
