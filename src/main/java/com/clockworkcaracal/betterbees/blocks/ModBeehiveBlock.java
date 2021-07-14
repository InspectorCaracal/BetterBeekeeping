package com.clockworkcaracal.betterbees.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.clockworkcaracal.betterbees.tileentity.ModBeehiveTileEntity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ModBeehiveBlock extends BeehiveBlock {

	public ModBeehiveBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	@Nullable
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new ModBeehiveTileEntity();
	}

	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult raytrace) {
		ItemStack itemstack = player.getItemInHand(hand);
		int i = state.getValue(HONEY_LEVEL);
		if (itemstack.getItem() == Items.GLASS_BOTTLE && i >= 5) {
			BeehiveTileEntity te = (BeehiveTileEntity)world.getBlockEntity(pos);
			ItemStack honey;
			if (te instanceof ModBeehiveTileEntity) {
				honey = ((ModBeehiveTileEntity)te).getHoney();
			} else {
				honey = new ItemStack(Items.HONEY_BOTTLE);
			}
			
			itemstack.shrink(1);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			if (itemstack.isEmpty()) {
				player.setItemInHand(hand, honey);
			} else if (!player.inventory.add(honey)) {
				player.drop(honey, false);
			}

			if (!te.isSedated()) {
				List<BeeEntity> list = world.getEntitiesOfClass(BeeEntity.class, (new AxisAlignedBB(pos)).inflate(8.0D, 6.0D, 8.0D));
				if (!list.isEmpty()) {
					for(BeeEntity beeentity : list) {
						if (beeentity.getTarget() == null) {
							beeentity.setTarget(player);
						}
					}
				}
				if (!te.isEmpty()) {
					te.emptyAllLivingFromHive(player, state, BeehiveTileEntity.State.EMERGENCY); 
				}
			}
			
			resetHoneyLevel(world, state, pos);
			return ActionResultType.sidedSuccess(world.isClientSide);

		} else {
			return super.use(state,world,pos,player,hand,raytrace);	    	   
		}
	}

}
