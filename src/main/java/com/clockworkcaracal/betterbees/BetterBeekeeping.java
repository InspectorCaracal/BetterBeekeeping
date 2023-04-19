package com.clockworkcaracal.betterbees;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Set;

import com.clockworkcaracal.betterbees.config.BetterBeeConfig;
import com.clockworkcaracal.betterbees.events.ModEvents;
import com.clockworkcaracal.betterbees.register.ModBlocks;
import com.clockworkcaracal.betterbees.register.ModItems;
import com.clockworkcaracal.betterbees.register.ModPoiTypes;
import com.clockworkcaracal.betterbees.register.ModBlockEntityTypes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("betterbeekeeping")
public class BetterBeekeeping {
	public static final String MOD_ID = "betterbeekeeping";
	public static final Logger LOGGER = LogManager.getLogger();

	public BetterBeekeeping() {
		
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(ModEvents.class);

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, BetterBeeConfig.CONFIG);
        
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		ModPoiTypes.POI_TYPES.register(modEventBus);
	}
	
}
