package com.myudog.tuozhurpg.block.entity.custom;

import com.myudog.tuozhurpg.block.entity.ModBlockEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CounterBlockEntity extends BlockEntity implements BlockEntityProvider {

	private int clicks = 0;
	int ticksSinceLast = 0;

	public CounterBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.COUNTER_BE, pos, state);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}


	public int getClicks() {
		return clicks;
	}

	public void incrementClicks() {
		clicks++;
		markDirty();
	}

	@Override
	protected void writeData(WriteView writeView) {
		writeView.putInt("clicks", clicks);
		writeView.putInt("ticksSinceLast", ticksSinceLast);

		super.writeData(writeView);
	}

	@Override
	protected void readData(ReadView readView) {
		super.readData(readView);

		clicks = readView.getInt("clicks", 0);
		ticksSinceLast = readView.getInt("ticksSinceLast", 0);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return createNbt(registryLookup);
	}



	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, ModBlockEntities.COUNTER_BE, CounterBlockEntity::tick);
	}

	public static void tick(World world, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
		entity.ticksSinceLast++;
		if (entity.ticksSinceLast < 10) return;
		entity.ticksSinceLast = 0;
	}

	private static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(
			BlockEntityType<T> givenType,
			BlockEntityType<? extends CounterBlockEntity> expectedType,
			BlockEntityTicker<? super CounterBlockEntity> ticker
	) {
		return null;
	}




}
