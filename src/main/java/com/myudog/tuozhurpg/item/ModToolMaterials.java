package com.myudog.tuozhurpg.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class ModToolMaterials {
	public static final ToolMaterial TEST_TOOL = new ToolMaterial(
			BlockTags.INCORRECT_FOR_WOODEN_TOOL, // forDrops
			455, // durability
			5.0F, // speed
			1.5F, // attackDamage
			22, // enchantmentValue
			ModArmorMaterials.REPAIRS_TEST_ARMOR // repairs
	);
}
