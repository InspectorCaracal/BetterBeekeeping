package com.clockworkcaracal.betterbees.register;

import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.tileentity.ModBeehiveTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    //The TILE_ENTITIES deferred register in which you can register tile entities.
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BetterBeekeeping.MOD_ID);

    //TileEntities
	public static final RegistryObject<TileEntityType<ModBeehiveTileEntity>> MOD_BEEHIVE = TILE_ENTITY_TYPES.register("mod_beehive", () -> TileEntityType.Builder.of(ModBeehiveTileEntity::new, ModBlocks.OAK_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get()).build(null));

}
