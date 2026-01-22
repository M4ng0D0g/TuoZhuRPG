package com.myudog.tuozhurpg.item;

import com.myudog.tuozhurpg.TuoZhuRpg;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModArmorMaterials {
	public static final TagKey<Item> REPAIRS_TEST_ARMOR = TagKey.of(
            Registries.ITEM.getKey(),
			Identifier.of(TuoZhuRpg.MOD_ID, "repairs_test_armor")
	);
}
