package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.creature.IRpgEquipment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BaseRpgEquipment extends BaseRpgItem implements IRpgEquipment {

    public BaseRpgEquipment(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return false;
    }
}
