package com.myudog.tuozhurpg.event;

import com.myudog.tuozhurpg.storage.PermissionContainer;
import com.myudog.tuozhurpg.storage.WorldPermissionState;
import com.myudog.tuozhurpg.util.PermissionSet;
import com.myudog.tuozhurpg.util.RegionAction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class InteractionHandler {

    public static ActionResult onBlockInteract(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        // 1. 基本過濾 (只處理 Server 端、主手)
        if (world.isClient() || hand == Hand.OFF_HAND) return ActionResult.PASS;

        ServerWorld serverWorld = (ServerWorld) world;
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        // 2. 推斷這是一個什麼動作? (開箱? 開門?)
        RegionAction action = inferAction(blockState, blockEntity);

        // 3. 獲取資料狀態
        WorldPermissionState state = WorldPermissionState.getServerState(serverWorld);
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        // --- 優先級 1: Global Editor (世界編輯者) ---
        // 這裡假設 globalPermissions 存的是 PermissionLevel Enum 的 ID
        int globalLevel = state.globalPermissions.getOrDefault(player.getUuid(), 0);
        if (globalLevel >= 99) { // 99 = EDITOR
            return ActionResult.PASS;
        }

        // --- 準備變數 ---
        PermissionSet finalPermissionSet = null;
        String rejectReason = "";

        // --- 優先級 2: 單一方塊權限 (Block Specific) ---
        // 檢查這個座標是否有特殊設定
        PermissionContainer blockRules = state.blockRules.get(pos.asLong());

        if (blockRules != null) {
            // 如果有方塊設定，以此為準
            finalPermissionSet = blockRules.getPlayerPermission(player.getUuid());
            rejectReason = "這個方塊限制了您的權限";
        } else {
            // --- 優先級 3: 區域權限 (Region) ---
            long chunkKey = new ChunkPos(pos).toLong();
            PermissionContainer regionRules = state.regionRules.get(chunkKey);

            if (regionRules != null) {
                // 如果有區域設定
                finalPermissionSet = regionRules.getPlayerPermission(player.getUuid());
                rejectReason = "您在此區域沒有權限";
            }
        }

        // --- 最終決策 ---
        // 如果 finalPermissionSet 是 null，代表是無主之地 (Wilderness) -> PASS
        if (finalPermissionSet == null) {
            return ActionResult.PASS;
        }

        // 檢查是否有該動作的權限
        if (finalPermissionSet.has(action)) {
            return ActionResult.PASS; // 通過
        } else {
            // 失敗 -> 發送訊息並攔截
            player.sendMessage(Text.of("§c[權限不足] " + rejectReason + " (" + action.getDescription() + ")"), true);
            return ActionResult.FAIL;
        }
    }

    private static RegionAction inferAction(BlockState state, BlockEntity be) {
        Block block = state.getBlock();

        // 1. 判斷是否為「容器」 (箱子、木桶、熔爐、發射器...)
        // 大多數有庫存的方塊都繼承自 LockableContainerBlockEntity
        if (be instanceof LockableContainerBlockEntity) {
            return RegionAction.INTERACT_CONTAINER;
        }

        // 2. 判斷是否為「門/柵欄門/活板門」
        if (block instanceof DoorBlock || block instanceof TrapdoorBlock || block instanceof FenceGateBlock) {
            return RegionAction.INTERACT_DOOR;
        }

        // 3. 判斷是否為「紅石元件」 (按鈕、拉桿)
        if (block instanceof ButtonBlock || block instanceof LeverBlock) {
            return RegionAction.INTERACT_REDSTONE;
        }

        // 4. 判斷是否為「工作台類」 (有 GUI 但不是容器)
        // 透過檢查是否能建立 ScreenHandlerFactory
        // 注意：這是一個簡單的判斷，可能會誤判部分 Mod 方塊，但在原版通常有效
        if (state.createScreenHandlerFactory(null, null) != null) {
            return RegionAction.USE_WORKSTATION;
        }

        // 5. 預設歸類
        // 如果以上都不是，例如點擊告示牌、旋轉物品展示框等，視為一般紅石/互動
        return RegionAction.INTERACT_REDSTONE;
    }
}
