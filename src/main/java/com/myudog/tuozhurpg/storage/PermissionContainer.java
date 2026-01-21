package com.myudog.tuozhurpg.storage;

import com.myudog.tuozhurpg.util.PermissionSet;
import com.myudog.tuozhurpg.util.RegionAction;
import net.minecraft.nbt.NbtCompound;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PermissionContainer {

    public UUID owner;
    public PermissionSet defaultPermission = new PermissionSet();
    public Map<UUID, PermissionSet> playerPermissions = new HashMap<>();

    public PermissionContainer(UUID owner) {
        this.owner = owner;
        this.defaultPermission.setAsVisitor();
    }

    // --- 核心查詢方法 ---

    public PermissionSet getPlayerPermission(UUID player) {
        if (player.equals(owner)) {
            PermissionSet ownerSet = new PermissionSet();
            ownerSet.setAsOperator();
            return ownerSet;
        }
        if (playerPermissions.containsKey(player)) {
            return playerPermissions.get(player);
        }
        return defaultPermission;
    }

    // --- NBT 序列化 (存檔) ---

    public NbtCompound writeNbt() {
        NbtCompound tag = new NbtCompound();

        // 1. 存擁有者
        if (owner != null) tag.putString("Owner", owner.toString());

        // 2. 存預設權限
        defaultPermission.writeNbt(tag, "DefaultPerms");

        // 3. 存特定玩家名單
        NbtCompound playersTag = new NbtCompound();
        playerPermissions.forEach((uuid, permSet) -> {
            permSet.writeNbt(playersTag, uuid.toString());
        });
        tag.put("PlayerPerms", playersTag);

        return tag;
    }

    // --- NBT 反序列化 (讀檔) ---

    public static PermissionContainer fromNbt(NbtCompound tag) {
        UUID owner = null;

        if (tag.contains("Owner")) {
            Optional<String> optStr = tag.getString("Owner");
            if (optStr.isPresent()) owner = UUID.fromString(optStr.get());
        }
        PermissionContainer container = new PermissionContainer(owner);

        // 1. 讀預設權限
        if (tag.contains("DefaultPerms")) {
            container.defaultPermission = PermissionSet.fromNbt(tag, "DefaultPerms");
        }

        // 2. 讀特定玩家名單
        if (tag.contains("PlayerPerms")) {
            tag.getCompound("PlayerPerms").ifPresent(playersTag -> {
                for (String key : playersTag.getKeys()) {
                    UUID playerUuid = UUID.fromString(key);
                    PermissionSet pSet = PermissionSet.fromNbt(playersTag, key);
                    container.playerPermissions.put(playerUuid, pSet);
                }
            });
        }

        return container;
    }
}