package com.myudog.tuozhurpg.storage;

import com.mojang.serialization.Codec;
import com.myudog.tuozhurpg.TuoZhuRpg;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WorldPermissionState extends PersistentState {

    private static final String ID = "world_permissions";

    public Map<UUID, Integer> globalPermissions = new HashMap<>();
    public Map<Long, PermissionContainer> regionRules = new HashMap<>();
    public Map<Long, PermissionContainer> blockRules = new HashMap<>();

    public static final Codec<WorldPermissionState> CODEC = NbtCompound.CODEC.xmap(
            // 讀取邏輯: 拿到 NBT -> 呼叫我們的方法轉成物件
            WorldPermissionState::createFromNbt,

            // 寫入邏輯: 拿到物件 -> 建立空 NBT 並呼叫 writeNbt 填入資料
            state -> state.writeNbt(new NbtCompound())
    );

    // --- 【修改】定義 Type 物件 ---
    public static final PersistentStateType<WorldPermissionState> TYPE = new PersistentStateType<>(
            ID,
            WorldPermissionState::new,
            CODEC,
            null // DataFixTypes
    );

    // --- 讀取邏輯 (Deserialization) ---

    public static WorldPermissionState createFromNbt(NbtCompound tag) {
        WorldPermissionState state = new WorldPermissionState();

        // 讀取 Global
        if (tag.contains("Global")) {
            tag.getCompound("Global").ifPresent(globalTag -> {
                for (String key : globalTag.getKeys()) {
                    try {
                        UUID uuid = UUID.fromString(key);
                        globalTag.getInt(key).ifPresent(level -> state.globalPermissions.put(uuid, level));
                    } catch (Exception e) {
                        System.err.println("讀取全域權限失敗: " + key);
                    }
                }
            });
        }

        // 讀取 Regions
        if (tag.contains("Regions")) {
            tag.getCompound("Regions").ifPresent(regionTag -> {
                for (String key : regionTag.getKeys()) {
                    try {
                        long chunkKey = Long.parseLong(key);
                        regionTag.getCompound(key).ifPresent(compound -> {
                            PermissionContainer container = PermissionContainer.fromNbt(compound);
                            state.regionRules.put(chunkKey, container);
                        });

                    } catch (Exception e) {
                        System.err.println("讀取區域資料失敗: " + key);
                    }
                }
            });

        }

        // 讀取 Blocks
        if (tag.contains("Blocks")) {
            tag.getCompound("Blocks").ifPresent(blockTag -> {
                for (String key : blockTag.getKeys()) {
                    try {
                        long blockKey = Long.parseLong(key);
                        blockTag.getCompound(key).ifPresent(compound -> {
                            PermissionContainer container = PermissionContainer.fromNbt(compound);
                            state.blockRules.put(blockKey, container);
                        });
                    } catch (Exception e) {
                        System.err.println("讀取方塊資料失敗: " + key);
                    }
                }
            });
        }

        return state;
    }

    // --- 寫入邏輯 (Serialization) ---

    public NbtCompound writeNbt(NbtCompound tag) {

        // 儲存 Global
        NbtCompound globalTag = new NbtCompound();
        globalPermissions.forEach((uuid, level) -> {
            globalTag.putInt(uuid.toString(), level);
        });
        tag.put("Global", globalTag);

        // 儲存 Regions
        NbtCompound regionTag = new NbtCompound();
        regionRules.forEach((key, container) -> {
            regionTag.put(String.valueOf(key), container.writeNbt());
        });
        tag.put("Regions", regionTag);

        // 儲存 Blocks
        NbtCompound blockTag = new NbtCompound();
        blockRules.forEach((key, container) -> {
            blockTag.put(String.valueOf(key), container.writeNbt());
        });
        tag.put("Blocks", blockTag);

        return tag;
    }

    // --- 獲取實例的靜態方法 (Singleton access) ---

    public static WorldPermissionState getServerState(ServerWorld world) {
        ServerWorld overworld = world.getServer().getOverworld();
        return overworld.getPersistentStateManager().getOrCreate(TYPE);
    }
}
