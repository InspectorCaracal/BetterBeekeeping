package com.clockworkcaracal.betterbees.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.entity.ai.village.poi.PoiType;

import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.server.TickTask;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import com.clockworkcaracal.betterbees.blocks.ModBeehiveBlock;
import com.clockworkcaracal.betterbees.config.BetterBeeConfig;
import com.clockworkcaracal.betterbees.blockentity.ModBeehiveBlockEntity;

@Mod.EventBusSubscriber(modid = "betterbeekeeping")
public class ModEvents {
	public static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent
	public static void beeSpawn(EntityJoinWorldEvent event) {
		if (BetterBeeConfig.BEE_HIBERNATION.get()) {
			Level world = event.getWorld();
			if (!world.isClientSide() && event.getEntity() instanceof Bee) {
				Bee bee = (Bee)event.getEntity();
				if (bee.hasHive()) {
					BlockPos hive_pos = bee.getHivePos();
					if (world.getChunkSource().getChunkNow(hive_pos.getX() >> 4, hive_pos.getY() >> 4) != null) {
						if (world.getBiome(hive_pos).value().coldEnoughToSnow(hive_pos)) {
							// since it's only the server this is sorta redundant but i like redundancy
							BlockableEventLoop<? super TickTask> executor = LogicalSidedProvider.WORKQUEUE.get(world.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
							executor.tell(new TickTask(0, () -> beeHibernation(hive_pos, bee, world, event)));
						}
					}
				}
			}
			// this whole method has the problem that the game will still play the sound effects for the bee leaving/entering the hive
			// there doesn't seem to be a good way to link the two events together, so i might just have to deal
		}
	}
	
	private static void beeHibernation(BlockPos hive_pos, Bee bee, Level world, EntityJoinWorldEvent event) {
		Block hive_block =  world.getBlockState(hive_pos).getBlock();
		if (hive_block instanceof BeehiveBlock) {
			BeehiveBlockEntity hiveEntity = (BeehiveBlockEntity)world.getBlockEntity(hive_pos);
			if (!hiveEntity.isFull()) {
				event.setCanceled(true);
				hiveEntity.addOccupantWithPresetTicks(bee, false, -23500);
			}
		}
	}
	
	@SubscribeEvent
	public static void depositHoney(EntityLeaveWorldEvent event) {
		if (BetterBeeConfig.FLAVORED_HONEY.get()) {
			Level world = event.getWorld();
			if (!world.isClientSide() && event.getEntity() instanceof Bee) {
				Bee bee = (Bee)event.getEntity();
				if (bee.hasHive() && bee.getSavedFlowerPos() != null) {
					BlockPos hive_pos = bee.getHivePos();
					BlockPos flower_pos = bee.getSavedFlowerPos();
					if (world.getChunkSource().getChunkNow(hive_pos.getX() >> 4, hive_pos.getY() >> 4) != null && world.getChunkSource().getChunkNow(flower_pos.getX() >> 4, flower_pos.getY() >> 4) != null) {
						// since it's only the server this is sorta redundant but i like redundancy
						BlockableEventLoop<? super TickTask> executor = LogicalSidedProvider.WORKQUEUE.get(world.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
						executor.tell(new TickTask(0, () -> saveFlower(hive_pos, flower_pos, world)));
					}
				}
			}
		}
	}
	
	private static void saveFlower(BlockPos hive_pos, BlockPos flower_pos, Level world) {
		Block hive_block =  world.getBlockState(hive_pos).getBlock();
		if (hive_block instanceof ModBeehiveBlock) {
			ModBeehiveBlockEntity hiveEntity = (ModBeehiveBlockEntity)world.getBlockEntity(hive_pos);
			BlockState flower_state = world.getBlockState(flower_pos);
			Block flower_block = flower_state.getBlock();
			if (flower_block instanceof FlowerBlock || flower_block instanceof TallFlowerBlock) {
				hiveEntity.addNectar(flower_state);
			}
		}
	}

}