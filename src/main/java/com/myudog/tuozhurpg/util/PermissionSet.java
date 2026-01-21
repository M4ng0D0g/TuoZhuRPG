package com.myudog.tuozhurpg.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.EnumSet;
import java.util.Optional;

public class PermissionSet {

    private final EnumSet<RegionAction> allowedActions;

    public PermissionSet() {
        this.allowedActions = EnumSet.noneOf(RegionAction.class);
    }

    // --- 核心功能 ---

    public void allow(RegionAction action) {
        allowedActions.add(action);
    }

    public void deny(RegionAction action) {
        allowedActions.remove(action);
    }

    public boolean has(RegionAction action) {
        if (allowedActions.contains(RegionAction.MANAGE)) {
            return true;
        }
        return allowedActions.contains(action);
    }

    public void clear() {
        allowedActions.clear();
    }

    // --- 預設模板 ---

    public void setAsVisitor() {
        this.clear();
        this.allow(RegionAction.ENTER);
    }

    public void setAsMember() {
        this.clear();
        this.allow(RegionAction.ENTER);
        this.allow(RegionAction.INTERACT_DOOR);
        this.allow(RegionAction.INTERACT_REDSTONE);
        this.allow(RegionAction.INTERACT_CONTAINER);
        this.allow(RegionAction.INTERACT_ENTITIES);
        this.allow(RegionAction.USE_WORKSTATION);
    }

    public void setAsOperator() {
        this.clear();
        this.allowedActions.addAll(EnumSet.allOf(RegionAction.class));
    }

    public void setAsEditor() {
        this.clear();
        this.allowedActions.addAll(EnumSet.allOf(RegionAction.class));
    }

    // --- NBT 存檔與讀檔邏輯 ---

    public void writeNbt(NbtCompound tag, String key) {
        NbtList list = new NbtList();

        for (RegionAction action : allowedActions) {
            list.add(NbtString.of(action.name()));
        }

        tag.put(key, list);
    }

    public static PermissionSet fromNbt(NbtCompound tag, String key) {
        PermissionSet set = new PermissionSet();

        if (tag.contains(key)) {
            Optional<NbtList> nbtList = tag.getList(key);
            nbtList.ifPresent(list -> {
                for (int i = 0; i < list.size(); i++) {
                    String actionName = String.valueOf(list.getString(i));

                    try {
                        set.allow(RegionAction.valueOf(actionName));
                    } catch (IllegalArgumentException e) {
                        // 如果未來刪除了某個 Enum，讀舊存檔時忽略錯誤
                    }
                }
            });
        }
        return set;
    }

    @Override
    public String toString() {
        return allowedActions.toString();
    }
}
