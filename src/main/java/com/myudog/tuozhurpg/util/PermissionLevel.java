package com.myudog.tuozhurpg.util;

public enum PermissionLevel {
    NONE(0),
    VISITOR(1),   // 只能看
    MEMBER(2),      // 能互動
    OPERATOR(3),  // 能破壞/設定
    EDITOR(99);   // 世界編輯者

    public final int id;
    PermissionLevel(int id) { this.id = id; }

    public boolean isAtLeast(PermissionLevel other) {
        return this.id >= other.id;
    }
}
