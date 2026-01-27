package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.attribute.IRpgAttribute;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.api.creature.IPrefix;
import com.myudog.tuozhurpg.api.creature.IRpgArmor;
import com.myudog.tuozhurpg.api.creature.IRune;
import com.myudog.tuozhurpg.api.creature.custom.RpgPrefix;
import com.myudog.tuozhurpg.api.creature.custom.RpgRune;
import com.myudog.tuozhurpg.component.item.AttributeContainer;
import com.myudog.tuozhurpg.component.item.ModItemComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;

public abstract class BaseRpgArmor extends BaseRpgEquipment implements IRpgArmor {

    public BaseRpgArmor(Settings settings) {
        super(settings);
    }

    // ----------------------------------------------------------------------------------------------------
    // Innate

    public int getInnate(ItemStack stack, RpgInnate innate) {
        AttributeContainer<RpgInnate> container = stack.getOrDefault(ModItemComponents.INNATES, AttributeContainer.empty(RpgInnate.class));
        return container.get(innate);
    }

    public int getFinalInnate(ItemStack stack, RpgInnate innate) {
//        // 1. 獲取基礎白值 (Base)
//        AttributeContainer<RpgInnate> container = stack.getOrDefault(ModItemComponents.INNATES, AttributeContainer.empty(RpgInnate.class));
//        double totalValue = container.get(innate);
//
//        // 2. 加上前綴加成 (Prefix Bonus)
//        // 注意：這裡假設 ModItemComponents.PREFIX 回傳的是 RpgPrefix Enum (nullable)
//        RpgPrefix prefix = stack.get(ModItemComponents.PREFIX);
//        if (prefix != null) {
//            // 假設 IPrefix 有 getInnateModifier 方法回傳 Map<RpgInnate, Integer>
//            totalValue += prefix.getInnateModifiers().getOrDefault(innate, 0);
//        }
//
//        // 3. 乘上符文倍率 (Rune Multiplier)
//        SocketContainer sockets = stack.getOrDefault(ModItemComponents.SOCKETS, SocketContainer.empty(0));
//        for (IRune rune : sockets.runes()) {
//            if (rune.getTargetInnate() == innate) {
//                // 假設 IRune 有 getMultiplier()，例如 1.1 代表 +10%
//                // 這裡示範簡單的乘法，你也可以改成累積加成
//                totalValue *= rune.getMultiplier();
//            }
//        }
//
//        return (int) totalValue;
        return 0;
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    public Text getName(ItemStack stack) {
//        Text originalName = super.getName(stack);
//        RpgPrefix prefix = stack.get(ModItemComponents.PREFIX);
//
//        if (prefix != null) {
//            return Text.translatable(prefix.getTranslationKey())
//                    .append(" ")
//                    .append(originalName);
//        }
//        return originalName;
        return Text.empty();
    }

//    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
//        // 1. 顯示損壞警告
//        if (isBroken(stack)) {
//            tooltip.add(Text.literal("⚠ 耐久耗盡，功能失效").formatted(Formatting.RED));
//        }
//
//        // 2. 顯示 Innate (天生屬性) - 例如重量、耐久係數
//        // 這裡假設你有註冊 ModItemComponents.INNATES
//        AttributeContainer<RpgInnate> innates = stack.getOrDefault(ModItemComponents.INNATES, AttributeContainer.empty(RpgInnate.class));
//        appendAttributeTooltip(tooltip, innates);
//
//        super.appendTooltip(stack, context, tooltip, type);
    }

    protected <T extends Enum<T> & IRpgAttribute> void appendAttributeTooltip(List<Text> tooltip, AttributeContainer<T> container) {
        container.values().forEach((attr, value) -> {
            if (value != 0) {
                // 1. 翻譯名稱
                MutableText text = Text.translatable(attr.getTranslationKey());

                // 2. 數值格式化
                if (attr.isPercentage()) {
                    text.append(": " + value + "%");
                } else {
                    // 正數加 + 號，負數自帶 - 號
                    text.append(": " + (value > 0 ? "+" : "") + value);
                }

                // 3. 顏色設定
                text.setStyle(Style.EMPTY.withColor(attr.getColor()));

                tooltip.add(text);
            }
        });
    }

    protected void appendSocketTooltip(ItemStack stack, List<Text> tooltip) {
//        SocketContainer sockets = stack.getOrDefault(ModItemComponents.SOCKETS, SocketContainer.empty(0));
//
//        // 如果這件裝備有孔 (maxSockets > 0)
//        if (sockets.maxSockets() > 0) {
//            tooltip.add(Text.literal("--- 符文鑲嵌 ---").formatted(Formatting.GOLD));
//
//            // A. 顯示已鑲嵌的符文
//            for (IRune rune : sockets.runes()) {
//                MutableText runeText = Text.literal("◉ ")
//                        .append(Text.translatable(rune.getTranslationKey()));
//
//                // 顯示這顆符文的效果提示 (可選)
//                if (context.isAdvanced()) {
//                    runeText.append(Text.literal(" (" + rune.getTargetInnate().asString() + " x" + rune.getMultiplier() + ")").formatted(Formatting.GRAY));
//                }
//
//                runeText.setStyle(Style.EMPTY.withColor(rune.getColor()));
//                tooltip.add(runeText);
//            }
//
//            // B. 顯示空孔位
//            int emptyCount = sockets.maxSockets() - sockets.runes().size();
//            for (int i = 0; i < emptyCount; i++) {
//                tooltip.add(Text.literal("○ 空鑲嵌孔").formatted(Formatting.DARK_GRAY));
//            }
//
//            tooltip.add(Text.empty()); // 空一行分隔
//        }
    }

    protected void appendSingleInnateLine(List<Text> tooltip, RpgInnate attr, int value) {
//        MutableText text = Text.translatable(attr.getTranslationKey());
//        if (attr.isPercentage()) {
//            text.append(": " + value + "%");
//        } else {
//            text.append(": " + (value > 0 ? "+" : "") + value);
//        }
//        text.setStyle(Style.EMPTY.withColor(attr.getColor()));
//        tooltip.add(text);
    }

    // ----------------------------------------------------------------------------------------------------


    // Set

    // Handlers

    /**
     * 鑲嵌符文
     * @return true 如果成功鑲嵌
     */
    public boolean addRune(ItemStack stack, RpgRune rune) {
//        SocketContainer sockets = stack.getOrDefault(ModItemComponents.SOCKETS, SocketContainer.empty(0));
//
//        // 呼叫 SocketContainer 的邏輯 (記得它是 Record，要寫回 stack)
//        SocketContainer newSockets = sockets.addRune(rune);
//
//        // 如果容器內容有變 (代表成功加入)，則寫回
//        if (newSockets.runes().size() > sockets.runes().size()) {
//            stack.set(ModItemComponents.SOCKETS, newSockets);
//            return true;
//        }
        return false;
    }

    /**
     * 設定前綴
     */
    public void setPrefix(ItemStack stack, RpgPrefix prefix) {
//        stack.set(ModItemComponents.PREFIX, prefix);
    }
}
