package com.clockworkcaracal.betterbees.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clockworkcaracal.betterbees.register.ModItems;
import com.clockworkcaracal.betterbees.register.ModTileEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBeehiveTileEntity extends BeehiveTileEntity {
	public static final Logger LOGGER = LogManager.getLogger();

	private Item honeyType = null;

	public ModBeehiveTileEntity() {
		super();
	}
	   
	@Override
	public TileEntityType<?> getType() {
		return ModTileEntityTypes.MOD_BEEHIVE.get();
	}
	
	public ItemStack getHoney() {
		ItemStack freshHoney = new ItemStack(honeyType);
		this.honeyType = null;
		this.setChanged();
		return freshHoney;
	}
	
	public void addNectar(Block flower) {
		if (this.honeyType != Items.HONEY_BOTTLE ) {
			Item newHoney = this.flowerToHoney(flower);
			if (this.honeyType == null) this.honeyType = newHoney;
			else if (this.honeyType != newHoney) this.honeyType = Items.HONEY_BOTTLE;
			this.setChanged();
		}
	}
	
	private Item flowerToHoney(Block flower) {
		if (flower == Blocks.AZURE_BLUET || flower == Blocks.POPPY || flower == Blocks.SUNFLOWER) {
			return ModItems.BRIGHT_HONEY_BOTTLE.get(); // cure blindness
		}
		if (flower == Blocks.BLUE_ORCHID || flower == Blocks.DANDELION || flower == Blocks.PEONY) {
			return ModItems.COOL_HONEY_BOTTLE.get(); // cure hunger
		}
		if (flower == Blocks.CORNFLOWER || flower == Blocks.ALLIUM || flower == Blocks.LILAC) {
			return ModItems.SPICY_HONEY_BOTTLE.get(); // cure slowness
		}
		if (flower == Blocks.RED_TULIP || flower == Blocks.ORANGE_TULIP || flower == Blocks.WHITE_TULIP || flower == Blocks.PINK_TULIP || flower == Blocks.ROSE_BUSH) {
			return ModItems.WARM_HONEY_BOTTLE.get(); // cure weakness
		}
		if (flower == Blocks.OXEYE_DAISY || flower == Blocks.LILY_OF_THE_VALLEY /* || flower == Blocks.FLOWERING_AZALEA */) {
			return ModItems.LIVELY_HONEY_BOTTLE.get(); // regeneration
		}
		if (flower == Blocks.WITHER_ROSE) {
			return ModItems.STRANGE_HONEY_BOTTLE.get(); // cure wither
		}
		return Items.HONEY_BOTTLE;
	}
	
	
	// need to load flower list
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.honeyType = null;

		String saved_honey = nbt.getString("Honey");
		this.honeyType = ForgeRegistries.ITEMS.getValue(new ResourceLocation(saved_honey));
	}

	// need to save flower list
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		nbt.putString("Honey", this.honeyType.getRegistryName().toString());

		return nbt;
	}

}
