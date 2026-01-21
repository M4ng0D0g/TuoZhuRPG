package com.myudog.tuozhurpg.block.entity;

import com.myudog.tuozhurpg.TuoZhuRpg;
import com.myudog.tuozhurpg.block.ModBlocks;
import com.myudog.tuozhurpg.block.entity.custom.CounterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
	public static void init() {

	}

	public static final BlockEntityType<CounterBlockEntity> COUNTER_BE = register(
			"counter",
			CounterBlockEntity::new,
			ModBlocks.COUNTER_BLOCK
	);

	private static <T extends BlockEntity> BlockEntityType<T> register(
			String name,
			FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
			Block... blocks
	) {
		Identifier id = Identifier.of(TuoZhuRpg.MOD_ID, name);
		return net.minecraft.registry.Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				id,
				FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build()
		);
	}

}
