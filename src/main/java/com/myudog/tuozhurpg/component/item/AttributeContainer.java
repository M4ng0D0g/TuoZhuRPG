package com.myudog.tuozhurpg.component.item;

import com.mojang.serialization.Codec;
import com.myudog.tuozhurpg.api.attribute.IRpgAttribute;
import net.minecraft.util.StringIdentifiable;

import java.util.EnumMap;

public record AttributeContainer<T extends Enum<T> & IRpgAttribute>(
        EnumMap<T, Integer> values
) {
    public static <E extends Enum<E> & IRpgAttribute> Codec<AttributeContainer<E>> createCodec(Class<E> enumClass) {
        Codec<E> keyCodec = StringIdentifiable.createCodec(enumClass::getEnumConstants);

        return Codec.unboundedMap(keyCodec, Codec.INT)
                .xmap(
                        // 讀檔：Map -> EnumMap -> Container
                        map -> new AttributeContainer<>(new EnumMap<>(map)),
                        // 存檔：Container -> EnumMap
                        AttributeContainer::values
                );
    }

    public AttributeContainer {
        if (values == null) {
            throw new IllegalArgumentException("Values map cannot be null");
        }
    }

    public static <E extends Enum<E> & IRpgAttribute> AttributeContainer<E> empty(Class<E> enumClass) {
        return new AttributeContainer<>(new EnumMap<>(enumClass));
    }

    public int get(T attribute) {
        return values.getOrDefault(attribute, 0);
    }

    public AttributeContainer<T> set(T attribute, int value) {
        EnumMap<T, Integer> newMap = new EnumMap<>(this.values);
        newMap.put(attribute, value);
        return new AttributeContainer<>(newMap);
    }

    public AttributeContainer<T> add(T attribute, int amount) {
        EnumMap<T, Integer> newMap = new EnumMap<>(this.values);
        newMap.merge(attribute, amount, Integer::sum);
        return new AttributeContainer<>(newMap);
    }

}
