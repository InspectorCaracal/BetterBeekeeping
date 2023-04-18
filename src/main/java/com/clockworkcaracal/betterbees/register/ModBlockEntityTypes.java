package com.clockworkcaracal.betterbees.register;

import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.blockentity.ModBeehiveBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntityTypes {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BetterBeekeeping.MOD_ID);

	public static final RegistryObject<BlockEntityType<ModBeehiveBlockEntity>> MOD_BEEHIVE = BLOCK_ENTITY_TYPES.register("mod_beehive", () -> BlockEntityType.Builder.of(ModBeehiveBlockEntity::new, ModBlocks.OAK_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get(), ModBlocks.CRIMSON_BEEHIVE.get(), ModBlocks.WARPED_BEEHIVE.get()).build(null));

}
