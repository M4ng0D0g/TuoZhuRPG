package com.myudog.tuozhurpg.item;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.api.attribute.custom.RpgInnate;
import com.myudog.tuozhurpg.block.ModBlocks;
import com.myudog.tuozhurpg.component.item.ModItemComponents;
import com.myudog.tuozhurpg.component.item.container.AttributeContainer;
import com.myudog.tuozhurpg.component.item.container.SocketContainer;
import com.myudog.tuozhurpg.item.base.BaseRpgTool;
import com.myudog.tuozhurpg.item.base.BaseRpgWeapon;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

	// --- 測試物品 ---
	public static final Item TEST_ITEM = register("test_item", Item::new, new Item.Settings());
	public static final Item TEST_TOOL = register(
            "test_tool",
            BaseRpgWeapon::new,
            new Item.Settings()
                    .sword(ModToolMaterials.TEST_TOOL, 1f, 1f)
                    .component(ModItemComponents.SOCKETS, SocketContainer.empty(3))
    );

    public static final Item DROP_1 = register("drop_1", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_2 = register("drop_2", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_3 = register("drop_3", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_4 = register("drop_4", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_5 = register("drop_5", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_6 = register("drop_6", Item::new, new Item.Settings().maxCount(16));
    public static final Item DROP_7 = register("drop_7", Item::new, new Item.Settings().maxCount(16));

	// --- 材料 (Ingredients) ---
	public static final Item ANCIENT_BOOK = register("ancient_book", Item::new, new Item.Settings());
	public static final Item ANCIENT_SCROLL = register("ancient_scroll", Item::new, new Item.Settings());
	public static final Item CARBON_DUST = register("carbon_dust", Item::new, new Item.Settings());
	public static final Item COAL = register("coal", Item::new, new Item.Settings());
	public static final Item COPPER_INGOT = register("copper_ingot", Item::new, new Item.Settings());
	public static final Item DIAMOND = register("diamond", Item::new, new Item.Settings());
	public static final Item DIAMOND_GEM = register("diamond_gem", Item::new, new Item.Settings());
	public static final Item EMERALD = register("emerald", Item::new, new Item.Settings());
	public static final Item EYE_OF_THE_VAULT = register("eye_of_the_vault", Item::new, new Item.Settings());
	public static final Item FLAX_LEAVES = register("flax_leaves", Item::new, new Item.Settings());
	public static final Item FLAX_ROPE = register("flax_rope", Item::new, new Item.Settings());
	public static final Item GLOWSTONE_DUST = register("glowstone_dust", Item::new, new Item.Settings());
	public static final Item GOLD_INGOT = register("gold_ingot", Item::new, new Item.Settings());
	public static final Item HEART_GEM = register("heart_gem", Item::new, new Item.Settings());
	public static final Item IRON_INGOT = register("iron_ingot", Item::new, new Item.Settings());
	public static final Item LEATHER = register("leather", Item::new, new Item.Settings());
	public static final Item PAPER = register("paper", Item::new, new Item.Settings());
	public static final Item REDSTONE_DUST = register("redstone_dust", Item::new, new Item.Settings());
	public static final Item ROCK = register("rock", Item::new, new Item.Settings());
	public static final Item SOUL = register("soul", Item::new, new Item.Settings());
	public static final Item THREAD = register("thread", Item::new, new Item.Settings());
	public static final Item WOODEN_STICK = register("wooden_stick", Item::new, new Item.Settings());

    // Food
    public static final Item BEEF = register("beef", Item::new, new Item.Settings());
    public static final Item PORK = register("pork", Item::new, new Item.Settings());
    public static final Item COOKED_BEEF = register("cooked_beef", Item::new, new Item.Settings());
    public static final Item COOKED_PORK = register("cooked_pork", Item::new, new Item.Settings());
    public static final Item BOTTLED_HONEY = register("bottled_honey", Item::new, new Item.Settings());



	// -------------------------------------------------------------------------
	// 2. 註冊與分類邏輯 (Initialization)
	// -------------------------------------------------------------------------
	public static void init() {
		// 這裡不需要做物品註冊了，因為上面的 static final 字段加載時就自動註冊了。
		// 我們只需要處理「把物品放進創造模式物品欄」這件事。

		// 把材料放進 Ingredients 分頁
		ItemGroupEvents.modifyEntriesEvent(ModItemGroups.MISC).register((itemGroup) -> {
			itemGroup.add(TEST_ITEM);
			itemGroup.add(DIAMOND);
			itemGroup.add(DIAMOND_GEM);
			itemGroup.add(EMERALD);
			itemGroup.add(HEART_GEM);
			itemGroup.add(EYE_OF_THE_VAULT);
			itemGroup.add(SOUL);
		});

		ItemGroupEvents.modifyEntriesEvent(ModItemGroups.ARCHEOLOGY).register((itemGroup) -> {
			itemGroup.add(PAPER);
			itemGroup.add(ANCIENT_BOOK);
			itemGroup.add(ANCIENT_SCROLL);
		});

		ItemGroupEvents.modifyEntriesEvent(ModItemGroups.INGREDIENTS).register((itemGroup) -> {
			itemGroup.add(WOODEN_STICK);
			itemGroup.add(FLAX_LEAVES);
			itemGroup.add(FLAX_ROPE);
			itemGroup.add(PAPER);
			itemGroup.add(LEATHER);
			itemGroup.add(THREAD);
			itemGroup.add(ROCK);
			itemGroup.add(COAL);
			itemGroup.add(COPPER_INGOT);
			itemGroup.add(IRON_INGOT);
			itemGroup.add(GOLD_INGOT);
			itemGroup.add(CARBON_DUST);
			itemGroup.add(GLOWSTONE_DUST);
			itemGroup.add(REDSTONE_DUST);
		});

        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.DROPS).register((itemGroup) -> {
            itemGroup.add(DROP_1);
            itemGroup.add(DROP_2);
            itemGroup.add(DROP_3);
            itemGroup.add(DROP_4);
            itemGroup.add(DROP_5);
            itemGroup.add(DROP_6);
            itemGroup.add(DROP_7);
        });

		ItemGroupEvents.modifyEntriesEvent(ModItemGroups.FOOD).register((itemGroup) -> {
			itemGroup.add(BEEF);
			itemGroup.add(PORK);
			itemGroup.add(COOKED_BEEF);
			itemGroup.add(COOKED_PORK);
			itemGroup.add(BOTTLED_HONEY);
		});

		// 如果你有工具，同樣的方法加到 Tools 分頁
		 ItemGroupEvents.modifyEntriesEvent(ModItemGroups.TOOLS).register((itemGroup) -> {
			 itemGroup.add(TEST_TOOL);
		 });

		// 方塊
		ItemGroupEvents.modifyEntriesEvent(ModItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
			itemGroup.add(ModBlocks.TEST_BLOCK.asItem());
		});
	}

	// -------------------------------------------------------------------------
	// 3. 幫忙幹髒活的 Helper 方法
	// -------------------------------------------------------------------------
	/**
	 * @param name 物品 ID 名稱
	 * @param factory 物品工廠 (例如 Item::new) - 這是一個函數，接收 Settings，返回 Item
	 * @param settings 物品設置
	 */
	private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
		// 1. 先生成 ID 和 Key
		Identifier itemID = Identifier.of(TuoZhuRpg.MOD_ID, name);
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, itemID);

		// 2. 關鍵修復：在創建 Item 之前，先把 Key 塞進 Settings 裡！
		// 這樣 Item 構造函數執行時，就能讀取到 ID 了，不會報 Item id not set。
		Item item = factory.apply(settings.registryKey(itemKey));

		// 3. 註冊到遊戲中
		return Registry.register(Registries.ITEM, itemKey, item);
	}
}