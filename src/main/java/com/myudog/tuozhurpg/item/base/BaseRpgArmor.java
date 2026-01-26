package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.attribute.IRpgAttribute;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.api.creature.IPrefix;
import com.myudog.tuozhurpg.api.creature.IRpgArmor;
import com.myudog.tuozhurpg.api.creature.IRune;
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

    protected Stack<IRune> RUNES; // 鑲嵌符文，用來強化天賦係數
	protected Optional<IPrefix> PREFIX; // 前綴，主流天賦組合



    public int getInnate(ItemStack stack, RpgInnate innate) {
        AttributeContainer<RpgInnate> container = stack.getOrDefault(ModItemComponents.INNATES, AttributeContainer.empty(RpgInnate.class));
        return container.get(innate);
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        // 1. 顯示損壞警告
        if (isBroken(stack)) {
            tooltip.add(Text.literal("⚠ 耐久耗盡，功能失效").formatted(Formatting.RED));
        }

        // 2. 顯示 Innate (天生屬性) - 例如重量、耐久係數
        // 這裡假設你有註冊 ModItemComponents.INNATES
        AttributeContainer<RpgInnate> innates = stack.getOrDefault(ModItemComponents.INNATES, AttributeContainer.empty(RpgInnate.class));
        appendAttributeTooltip(tooltip, innates);

        super.appendTooltip(stack, context, tooltip, type);
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

    // ----------------------------------------------------------------------------------------------------


    // Set

    // Handlers
}
