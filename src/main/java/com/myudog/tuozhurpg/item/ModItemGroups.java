package com.myudog.tuozhurpg.item;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

	public static final RegistryKey<ItemGroup> MISC = register("0_misc", ModItems.PAPER);

	public static final RegistryKey<ItemGroup> BUILDING_BLOCKS = register("1_building_blocks", ModBlocks.TEST_BLOCK.asItem());
	public static final RegistryKey<ItemGroup> FUNCTIONAL = register("1_functional", ModItems.PAPER);

	public static final RegistryKey<ItemGroup> COMBAT = register("2_combat", ModItems.PAPER);
	public static final RegistryKey<ItemGroup> TOOLS = register("2_tools", ModItems.TEST_TOOL);

	public static final RegistryKey<ItemGroup> INGREDIENTS = register("3_ingredients", ModItems.FLAX_LEAVES);
    public static final RegistryKey<ItemGroup> DROPS = register("4_drops", ModItems.DROP_1);
	public static final RegistryKey<ItemGroup> FOOD = register("4_food", ModItems.BEEF);

	public static final RegistryKey<ItemGroup> ARCHEOLOGY = register("5_archeology", ModItems.ANCIENT_BOOK);


	public static void init() {

	}

	public static RegistryKey<ItemGroup> register(String name, Item iconItem) {
		Identifier itemGroupID = Identifier.of(TuoZhuRpg.MOD_ID, name);
		RegistryKey<ItemGroup> itemGroupKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, itemGroupID);
		ItemGroup itemGroup = FabricItemGroup.builder().icon(() -> new ItemStack(iconItem))
				.displayName(Text.translatable("itemgroup.tuozhurpg." + name))
				.build();

		Registry.register(Registries.ITEM_GROUP, itemGroupID, itemGroup);
		return itemGroupKey;
	}
}
