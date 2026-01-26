package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.creature.IRpgItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.component.DataComponentTypes;

public abstract class BaseRpgItem extends Item implements IRpgItem {

    public BaseRpgItem(Settings settings) {
        super(settings);
    }

    @Override
    public void applyDamage(ItemStack stack, int amount, LivingEntity user) {
        if (stack.getMaxDamage() <= 0) return;

        int currentDamage = stack.getDamage();
        int maxDamage = stack.getMaxDamage();
        int newDamage = currentDamage + amount;

        if (newDamage >= maxDamage) {
            // 強制鎖在上限值 (代表損壞)
            stack.setDamage(maxDamage);
            // 可選：播放損壞音效
            // if (user instanceof PlayerEntity) { ... }
        } else {
            stack.setDamage(newDamage);
        }
    }

    @Override
    public int getMaxDurability(ItemStack stack) {
        int baseMax = stack.getItem().getComponents().getOrDefault(DataComponentTypes.MAX_DAMAGE, 100);

        // 2. 獲取 Innate 的耐久加成 (例如 +50)
//        int bonus = getRpgInnate(stack, RpgInnate.DURABILITY_BONUS); // 假設你有這個 Enum

        return baseMax;
    }

    @Override
    public void setMaxDurability(ItemStack stack, int value) {
        // 1. 取得這把武器「原始」的基礎耐久 (從模具讀取，不要改它)
        // 例如：鑽石劍是 1561
        int baseMax = stack.getItem().getComponents().getOrDefault(DataComponentTypes.MAX_DAMAGE, 0);

        // 如果原版物品沒有設定耐久 (例如木棒)，給個預設值避免計算錯誤
        if (baseMax == 0) baseMax = 100;

        // 2. 計算需要的加成值 (RPG 邏輯)
        // 目標 2000 - 基礎 1561 = 需要 +439 的加成
//        int neededBonus = value - baseMax;

        // 3. 更新你的 RPG Innate 數據 (這會讓你的 Tooltip 顯示 "耐久加成: +439")
        // 使用我們之前寫好的輔助方法
//        setRpgInnate(stack, RpgInnate.DURABILITY_BONUS, neededBonus);

        // 4. ★ 關鍵：同步更新原版 MAX_DAMAGE 組件
        // 這會告訴客戶端：「這把劍現在的上限真的是 2000」，綠色血條才會正確縮放
        stack.set(DataComponentTypes.MAX_DAMAGE, value);
    }

    @Override
    public int getDurability(ItemStack stack) {
        int max = getMaxDurability(stack);
        int damageTaken = stack.getDamage();
        return Math.max(0, max - damageTaken);
    }

    @Override
    public void setDurability(ItemStack stack, int value) {
        int max = getMaxDurability(stack);
        int targetDurability = Math.max(0, Math.min(value, max));
        int newDamage = max - targetDurability;

        stack.setDamage(newDamage);

        // 4. (重要) 如果這是動態修改了最大耐久，可能需要確保不會出錯
        // 但因為我們是透過 damage 值反推，所以只要 setDamage 就好，
        // Minecraft 會自動更新下方的綠色耐久條。
    }

    @Override
    public boolean isBroken(ItemStack stack) {
        return stack.getMaxDamage() > 0 && stack.getDamage() >= stack.getMaxDamage();
    }

    @Override
    public boolean isUnbreakable(ItemStack stack) {
        return stack.getMaxDamage() <= 0;
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return false;
    }
    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {}

    // ----------------------------------------------------------------------------------------------------

//    @Override
//    public Text getName(ItemStack stack) {
//        if (isBroken(stack)) {
//            return Text.translatable(this.getTranslationKey())
//                    .append(Text.literal(" (已損壞)").formatted(Formatting.RED));
//        }
//        return super.getName(stack);
//    }

    // ----------------------------------------------------------------------------------------------------



}
