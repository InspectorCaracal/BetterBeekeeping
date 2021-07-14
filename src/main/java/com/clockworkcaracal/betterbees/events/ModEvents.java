package com.clockworkcaracal.betterbees.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import com.clockworkcaracal.betterbees.blocks.ModBeehiveBlock;
import com.clockworkcaracal.betterbees.config.BetterBeeConfig;
import com.clockworkcaracal.betterbees.config.ConfigHolder;
import com.clockworkcaracal.betterbees.tileentity.ModBeehiveTileEntity;

@Mod.EventBusSubscriber(modid = "betterbeekeeping")
public class ModEvents {
	public static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent
	public static void beeSpawn(EntityJoinWorldEvent event) {
//		if (BetterBeeConfig.hibernate) {
			World world = event.getWorld();
			if (!world.isClientSide() && event.getEntity() instanceof BeeEntity) {
				BeeEntity bee = (BeeEntity)event.getEntity();
				if (bee.hasHive()) {
					BlockPos hive_pos = bee.getHivePos();
					if (world.getChunkSource().getChunkNow(hive_pos.getX() >> 4, hive_pos.getY() >> 4) != null) {
							if (world.getBiome(hive_pos).getTemperature(hive_pos) < 0.15F) {
								
								// since it's only the server this is sorta redundant but i like redundancy
								ThreadTaskExecutor<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(world.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
								executor.tell(new TickDelayedTask(0, () -> beeHibernation(hive_pos, bee, world, event)));
						}
						
					}
				}
			}
			// this whole method has the problem that the game will still play the sound effects for the bee leaving/entering the hive
			// there doesn't seem to be a good way to link the two events together, so i might just have to deal
//		}
	}
	
	private static void beeHibernation(BlockPos hive_pos, BeeEntity bee, World world, EntityJoinWorldEvent event) {
		Block hive_block =  world.getBlockState(hive_pos).getBlock();
		if (hive_block instanceof BeehiveBlock) {
			BeehiveTileEntity hiveEntity = (BeehiveTileEntity)world.getBlockEntity(hive_pos);
			
			if (!hiveEntity.isFull()) {
				event.setCanceled(true);
				hiveEntity.addOccupantWithPresetTicks(bee, false, -23500);
				LOGGER.debug("Bee has returned to its hive.");
			}
		}
	}
	
	@SubscribeEvent
	public static void depositHoney(EntityLeaveWorldEvent event) {
//		if (BetterBeeConfig.flavoredHoney) {
			World world = event.getWorld();
			if (!world.isClientSide() && event.getEntity() instanceof BeeEntity) {
				BeeEntity bee = (BeeEntity)event.getEntity();
				if (bee.hasHive() && bee.getSavedFlowerPos() != null) {
					BlockPos hive_pos = bee.getHivePos();
					BlockPos flower_pos = bee.getSavedFlowerPos();
					if (world.getChunkSource().getChunkNow(hive_pos.getX() >> 4, hive_pos.getY() >> 4) != null && world.getChunkSource().getChunkNow(flower_pos.getX() >> 4, flower_pos.getY() >> 4) != null) {
						// since it's only the server this is sorta redundant but i like redundancy
						ThreadTaskExecutor<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(world.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER);
						executor.tell(new TickDelayedTask(0, () -> saveFlower(hive_pos, flower_pos, world)));
					}
				}
			}
//		} else {
//			LOGGER.debug("sad boop :(");
//		}
	}
	
	private static void saveFlower(BlockPos hive_pos, BlockPos flower_pos, World world) {
		Block hive_block =  world.getBlockState(hive_pos).getBlock();
		if (hive_block instanceof ModBeehiveBlock) {
			ModBeehiveTileEntity hiveEntity = (ModBeehiveTileEntity)world.getBlockEntity(hive_pos);
			Block flowerBlock = world.getBlockState(flower_pos).getBlock();
			if (flowerBlock instanceof FlowerBlock || flowerBlock instanceof TallFlowerBlock) {
				hiveEntity.addNectar(flowerBlock);
			}
		}
	}

/*
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		// Rebake the configs when they change
		if (config.getSpec() == ConfigHolder.CONFIG_SPEC) {
			BetterBeeConfig.bakeConfig(config);
			LOGGER.debug("Baked config");
		}
	}
*/
}