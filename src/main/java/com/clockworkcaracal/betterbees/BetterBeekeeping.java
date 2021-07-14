package com.clockworkcaracal.betterbees;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Set;

import com.clockworkcaracal.betterbees.config.ConfigHolder;
import com.clockworkcaracal.betterbees.events.ModEvents;
import com.clockworkcaracal.betterbees.register.ModBlocks;
import com.clockworkcaracal.betterbees.register.ModItems;
import com.clockworkcaracal.betterbees.register.ModTileEntityTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;

@Mod("betterbeekeeping")
public class BetterBeekeeping {
	public static final String MOD_ID = "betterbeekeeping";
	public static final Logger LOGGER = LogManager.getLogger();

	public BetterBeekeeping() {
		
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        
        //Register the event handler
        MinecraftForge.EVENT_BUS.register(ModEvents.class);

        // Run configs
//        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.CONFIG_SPEC);
        
        // Register Deferred Registers (Does not need to be before Configs)
		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
	}
	
    private void setup(final FMLCommonSetupEvent event) {
    	Runnable patch_hives = () -> {
    		try {
    	    	LOGGER.debug("Modifying POI register for beehives");
           	    final Set<BlockState> HIVES = ImmutableList.of(Blocks.BEEHIVE, ModBlocks.OAK_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get()).stream().flatMap((block) -> {
            		      return block.getStateDefinition().getPossibleStates().stream();
            		   }).collect(ImmutableSet.toImmutableSet());
           	    
//           	    LOGGER.debug(HIVES);
           	    
           	    ObfuscationReflectionHelper.setPrivateValue(PointOfInterestType.class, PointOfInterestType.BEEHIVE, HIVES, "field_221075_w");
    		} catch (Exception e) {
    			LOGGER.error(e);
    		}
    		
       	};
             		
    	event.enqueueWork(patch_hives);
    }


}
