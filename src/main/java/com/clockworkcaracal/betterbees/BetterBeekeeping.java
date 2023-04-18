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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;

@Mod("betterbeekeeping")
public class BetterBeekeeping {
	public static final String MOD_ID = "betterbeekeeping";
	public static final Logger LOGGER = LogManager.getLogger();

	public BetterBeekeeping() {
		
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Register the event handler
        MinecraftForge.EVENT_BUS.register(ModEvents.class);
        modEventBus.addListener(this::setup);

        // Run configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BetterBeeConfig.CONFIG);
        
        // Register Deferred Registers (Does not need to be before Configs)
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
		ModPoiTypes.POI_TYPES.register(modEventBus);
	}

	// overwrite the BEEHIVE field with vanilla+custom hives
    private void setup(final FMLCommonSetupEvent event) {
    	Runnable patch_hives = () -> {
    		PoiType.BEEHIVE = ModPoiTypes.BEEHIVE.get();
       	};
    	event.enqueueWork(patch_hives);
    }
}
