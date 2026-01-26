package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.creature.IRpgArmor;
import com.myudog.tuozhurpg.api.creature.IRpgWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BaseRpgWeapon extends BaseRpgEquipment implements IRpgWeapon {

    public BaseRpgWeapon(Settings settings) {
        super(settings);
    }

    // 有種強化是吃更高的天賦係數

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (isBroken(stack)) return;

        applyDamage(stack, 1, attacker);
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
