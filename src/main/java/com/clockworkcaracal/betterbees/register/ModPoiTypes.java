package com.clockworkcaracal.betterbees.register;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.Registry;

import java.util.Set;
import java.util.stream.Collectors;
import java.lang.reflect.Constructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.register.ModBlocks;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;



public final class ModPoiTypes {
	public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, BetterBeekeeping.MOD_ID);

    public static final RegistryObject<PoiType> BEEHIVE = POI_TYPES.register("mod_beehive", () -> new PoiType(getPoiHiveStates(), 0, 1));

    private static Set<BlockState> getPoiHiveStates() {
    	final Set<BlockState> HIVES = Set.of(ModBlocks.OAK_BEEHIVE.get(), ModBlocks.MANGROVE_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get()).stream().flatMap((block) -> {
		      return block.getStateDefinition().getPossibleStates().stream();
		   }).collect(ImmutableSet.toImmutableSet());
//    	LOGGER.debug(HIVES);
    	return HIVES;
    }
}
