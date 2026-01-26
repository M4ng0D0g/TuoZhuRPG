package com.myudog.tuozhurpg.api.creature;

import com.myudog.tuozhurpg.api.attribute.custom.RpgRarity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IRpgItem {

    // rarity
//    RpgRarity getRarity(ItemStack stack);

    // durability

    void applyDamage(ItemStack stack, int amount, LivingEntity user);

    int getMaxDurability(ItemStack stack);
    void setMaxDurability(ItemStack stack, int value);
    int getDurability(ItemStack stack);
    void setDurability(ItemStack stack, int value);

    boolean isBroken(ItemStack stack);
    boolean isUnbreakable(ItemStack stack);

    //

}
