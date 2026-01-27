package com.myudog.tuozhurpg.component.item.container;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.myudog.tuozhurpg.api.creature.custom.RpgRune;

import java.util.ArrayList;
import java.util.List;

public record SocketContainer(
        List<RpgRune> runes,
        int maxSockets
) {
    // Codec: 存檔格式
    public static final Codec<SocketContainer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RpgRune.CODEC.listOf().fieldOf("runes").forGetter(SocketContainer::runes),
            Codec.INT.fieldOf("max_sockets").forGetter(SocketContainer::maxSockets)
    ).apply(instance, SocketContainer::new));

    public SocketContainer {
        if (maxSockets < 0) maxSockets = 0;
        // 確保 runes 不為 null，並且轉為不可變 List
        runes = runes != null ? List.copyOf(runes) : List.of();
    }

    public static SocketContainer empty(int maxSockets) {
        return new SocketContainer(List.of(), maxSockets);
    }

    public SocketContainer addRune(RpgRune rune) {
        if (runes.size() >= maxSockets) return this;

        List<RpgRune> newRunes = new ArrayList<>(runes);
        newRunes.add(rune);

        return new SocketContainer(newRunes, maxSockets);
    }

    public SocketContainer removeLastRune() {
        if (runes.isEmpty()) return this;

        List<RpgRune> newRunes = new ArrayList<>(this.runes);
        newRunes.removeLast();

        return new SocketContainer(newRunes, maxSockets);
    }

    public SocketContainer unlockSocket(int amount) {
        return new SocketContainer(this.runes, this.maxSockets + amount);
    }

    public boolean hasEmptySlot() {
        return runes.size() < maxSockets;
    }
}