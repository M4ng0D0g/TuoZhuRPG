package com.myudog.tuozhurpg.util;

public enum RegionAction {
    // --- 基礎互動 ---
    ENTER("進入區域"),             // 是否能進入該區域 (預留功能)

    // --- 方塊互動 ---
    BREAK_BLOCK("破壞方塊"),       // 左鍵挖掘
    PLACE_BLOCK("放置方塊"),       // 右鍵放置

    // --- 功能性互動 ---
    INTERACT_REDSTONE("紅石互動"), // 按鈕、拉桿、壓力板
    INTERACT_DOOR("開關門"),       // 門、柵欄門、活板門

    // 【新增】非容器功能方塊
    // 包含: 工作台、切石機、製圖桌、鍛造台、砂輪...
    // 特點: 通常沒有儲存空間，或者不被視為貴重物品儲存點
    USE_WORKSTATION("使用功能方塊"),

    // 容器 (通常涉及貴重物品)
    // 包含: 箱子、木桶、界伏盒、熔爐、發射器、漏斗
    INTERACT_CONTAINER("打開容器"),

    // --- 實體互動 ---
    HURT_ENTITIES("攻擊生物"),     // 攻擊牛、羊或怪物
    INTERACT_ENTITIES("實體互動"), // 與村民交易、給動物餵食、騎馬

    // --- 管理權限 ---
    MANAGE("管理區域");            // 修改權限設定、轉讓區域

    private final String description;

    RegionAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
