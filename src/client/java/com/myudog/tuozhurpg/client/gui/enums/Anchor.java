package com.myudog.tuozhurpg.client.gui.enums;

public enum Anchor {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM,
    BOTTOM_RIGHT,
    FREE;

    public static int calX(Anchor anchor, int width) {
        return switch (anchor) {
            case TOP, CENTER, BOTTOM -> width / 2;
            case TOP_RIGHT, CENTER_RIGHT, BOTTOM_RIGHT -> width;
            default -> 0;
        };
    }
    public static int calY(Anchor anchor, int height) {
        return switch (anchor) {
            case CENTER_LEFT, CENTER, CENTER_RIGHT -> height / 2;
            case BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT -> height;
            default -> 0;
        };
    }
}
