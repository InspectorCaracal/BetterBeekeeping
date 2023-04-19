package com.clockworkcaracal.betterbees.blockentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.clockworkcaracal.betterbees.register.ModItems;
import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.register.ModBlockEntityTypes;

import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBeehiveBlockEntity extends BeehiveBlockEntity {
	public static final Logger LOGGER = LogManager.getLogger();

	public static final TagKey<Block> SPICY_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "spicy_flowers"));
	public static final TagKey<Block> WARM_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "warm_flowers"));
	public static final TagKey<Block> BRIGHT_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "bright_flowers"));
	public static final TagKey<Block> LIVELY_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "lively_flowers"));
	public static final TagKey<Block> COOL_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "cool_flowers"));
	public static final TagKey<Block> STRANGE_FLOWERS = BlockTags.create(new ResourceLocation(BetterBeekeeping.MOD_ID, "strange_flowers"));

	private Item honeyType = null;

	public ModBeehiveBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}
	   
	@Override
	public BlockEntityType<?> getType() {
		return ModBlockEntityTypes.MOD_BEEHIVE.get();
	}
	
	public ItemStack getHoney() {
		ItemStack freshHoney = new ItemStack(honeyType);
		this.honeyType = null;
		this.setChanged();
		return freshHoney;
	}
	
	public void addNectar(BlockState flower) {
		if (this.honeyType != Items.HONEY_BOTTLE ) {
			Item newHoney = this.flowerToHoney(flower);
			if (this.honeyType == null) this.honeyType = newHoney;
			else if (this.honeyType != newHoney) this.honeyType = Items.HONEY_BOTTLE;
			this.setChanged();
		}
	}
	
	private Item flowerToHoney(BlockState flower) {
		if (flower.is(SPICY_FLOWERS)) { 
			return ModItems.SPICY_HONEY_BOTTLE.get();
		}
		if (flower.is(WARM_FLOWERS)) {
			return ModItems.WARM_HONEY_BOTTLE.get();
		}
		if (flower.is(BRIGHT_FLOWERS)) {
			return ModItems.BRIGHT_HONEY_BOTTLE.get();
		}
		if (flower.is(LIVELY_FLOWERS)) {
			return ModItems.LIVELY_HONEY_BOTTLE.get();
		}
		if (flower.is(COOL_FLOWERS)) {
			return ModItems.COOL_HONEY_BOTTLE.get();
		}
		if (flower.is(STRANGE_FLOWERS)) {
			return ModItems.STRANGE_HONEY_BOTTLE.get();
		}
		
		return Items.HONEY_BOTTLE;
	}
	
	
	// need to load flower list
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.honeyType = null;

		String saved_honey = nbt.getString("Honey");
		if (saved_honey != "null") {
			this.honeyType = ForgeRegistries.ITEMS.getValue(new ResourceLocation(saved_honey));
		}
	}

	// need to save flower list
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (this.honeyType == null) {
			nbt.putString("Honey", "null");
		}
		else nbt.putString("Honey", ForgeRegistries.ITEMS.getKey(this.honeyType).toString());
	}

}
