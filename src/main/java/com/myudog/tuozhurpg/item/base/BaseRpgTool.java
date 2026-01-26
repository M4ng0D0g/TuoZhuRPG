package com.myudog.tuozhurpg.item.base;

import com.myudog.tuozhurpg.api.creature.IRpgEquipment;
import com.myudog.tuozhurpg.api.creature.IRpgTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseRpgTool extends BaseRpgItem implements IRpgTool {

    public BaseRpgTool(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (isBroken(stack)) {
            return false; // 壞掉時挖掘無效
        }
        // 如果不是破壞空氣或硬度為0的方塊，扣 2 點耐久
        if (!world.isClient() && state.getHardness(world, pos) != 0.0F) {
            applyDamage(stack, 2, miner);
        }
        return true;
    }
}
