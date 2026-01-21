package com.myudog.tuozhurpg.command.custom;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.myudog.tuozhurpg.storage.PermissionContainer;
import com.myudog.tuozhurpg.storage.WorldPermissionState;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Collection;

public class PermissionLogic {

    // --- 輔助方法：應用角色模板 ---
    // 根據玩家輸入的 role 字串 (例如 "user")，去修改 container 裡的權限
    private static void applyRole(PermissionContainer container, Collection<ServerPlayerEntity> targets, String role) {
        for (ServerPlayerEntity target : targets) {
            // 根據輸入的字串決定套用哪個模板
            // 這裡對應我們在 PermissionSet 寫好的方法
            switch (role.toLowerCase()) {
                case "visitor" -> container.getPlayerPermission(target.getUuid()).setAsVisitor();
                case "user" -> container.getPlayerPermission(target.getUuid()).setAsMember();
                case "operator" -> container.getPlayerPermission(target.getUuid()).setAsOperator();
                case "none" -> container.playerPermissions.remove(target.getUuid()); // 移除特權，回歸預設
                default -> { /* 未知角色不做動作，或者可以拋出錯誤 */ }
            }
        }
    }

    // =================================================================
    //  1. 全域指令 (Global)
    // =================================================================

    public static int executeGlobalSet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        // 獲取參數
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        int level = com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(context, "level");

        WorldPermissionState state = WorldPermissionState.getServerState(source.getWorld());

        for (ServerPlayerEntity target : targets) {
            state.globalPermissions.put(target.getUuid(), level);
            source.sendFeedback(() -> Text.of("已設定 " + target.getName().getString() + " 的全域權限等級為: " + level), true);
        }

        state.markDirty();
        return 1;
    }

    // =================================================================
    //  2. 區域指令 (Region)
    // =================================================================

    public static int executeRegionSet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        ServerWorld world = player.getEntityWorld().toServerWorld();
        WorldPermissionState state = WorldPermissionState.getServerState(world);

        // 1. 獲取當前腳下的區塊
        ChunkPos chunkPos = player.getChunkPos();
        long chunkKey = chunkPos.toLong();

        // 2. 獲取或創建該區域的設定檔 (若沒有則建立新的，預設擁有者是 NULL)
        PermissionContainer container = state.regionRules.computeIfAbsent(chunkKey, k -> new PermissionContainer(null));

        // 3. 讀取參數並修改
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        String role = StringArgumentType.getString(context, "role");

        applyRole(container, targets, role);
        state.markDirty();

        context.getSource().sendFeedback(() -> Text.of("已更新區域權限 (" + role + ")"), false);
        return 1;
    }

    // =================================================================
    //  3. 方塊指令 (Block)
    // =================================================================

    public static int executeBlockLock(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        // 1. 射線追蹤：獲取玩家看著的方塊 (距離 5 格)
        HitResult hit = player.raycast(5.0, 0, false);

        if (hit.getType() != HitResult.Type.BLOCK) {
            context.getSource().sendError(Text.of("請看著一個方塊！"));
            return 0;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        long blockKey = pos.asLong();

        WorldPermissionState state = WorldPermissionState.getServerState(player.getEntityWorld().toServerWorld());

        // 2. 檢查是否已經鎖定
        if (state.blockRules.containsKey(blockKey)) {
            context.getSource().sendError(Text.of("此方塊已經有設定檔了！"));
            return 0;
        }

        // 3. 建立新的鎖 (擁有者設為指令發送者)
        state.blockRules.put(blockKey, new PermissionContainer(player.getUuid()));
        state.markDirty();

        context.getSource().sendFeedback(() -> Text.of("方塊已鎖定/建立規則！"), false);
        return 1;
    }

    public static int executeBlockSet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        // 1. 射線追蹤
        HitResult hit = player.raycast(5.0, 0, false);
        if (hit.getType() != HitResult.Type.BLOCK) {
            context.getSource().sendError(Text.of("請看著一個方塊！"));
            return 0;
        }
        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        long blockKey = pos.asLong();

        WorldPermissionState state = WorldPermissionState.getServerState(player.getEntityWorld().toServerWorld());
        PermissionContainer container = state.blockRules.get(blockKey);

        // 2. 確保方塊有規則
        if (container == null) {
            context.getSource().sendError(Text.of("此方塊尚未設定規則，請先使用 /perm block lock"));
            return 0;
        }

        // 3. 修改權限
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        String role = StringArgumentType.getString(context, "role");

        applyRole(container, targets, role);
        state.markDirty();

        context.getSource().sendFeedback(() -> Text.of("方塊權限已更新！"), false);
        return 1;
    }
}