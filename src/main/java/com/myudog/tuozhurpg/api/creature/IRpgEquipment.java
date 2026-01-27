package com.myudog.tuozhurpg.api.creature;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IRpgEquipment extends IRpgItem {

    boolean canEquip(ItemStack stack, LivingEntity entity);
    // boolean canEquip(SlotType slot);


}
