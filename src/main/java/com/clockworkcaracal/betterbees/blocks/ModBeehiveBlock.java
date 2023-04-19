package com.clockworkcaracal.betterbees.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.clockworkcaracal.betterbees.blockentity.ModBeehiveBlockEntity;
import com.clockworkcaracal.betterbees.register.ModBlockEntityTypes;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class ModBeehiveBlock extends BeehiveBlock {

	public ModBeehiveBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

//	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ModBeehiveBlockEntity(pos, state);
	}

	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(hand);
		int i = state.getValue(HONEY_LEVEL);
		if (itemstack.is(Items.GLASS_BOTTLE) && i >= 5) {
			BeehiveBlockEntity te = (BeehiveBlockEntity)world.getBlockEntity(pos);
			ItemStack honey;
			if (te instanceof ModBeehiveBlockEntity) {
				honey = ((ModBeehiveBlockEntity)te).getHoney();
			} else {
				honey = new ItemStack(Items.HONEY_BOTTLE);
			}
			
			itemstack.shrink(1);
			world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
			if (itemstack.isEmpty()) {
				player.setItemInHand(hand, honey);
			} else if (!player.getInventory().add(honey)) {
				player.drop(honey, false);
			}

			if (!te.isSedated()) {
				List<Bee> list = world.getEntitiesOfClass(Bee.class, (new AABB(pos)).inflate(8.0D, 6.0D, 8.0D));
				if (!list.isEmpty()) {
					for(Bee beeentity : list) {
						if (beeentity.getTarget() == null) {
							beeentity.setTarget(player);
						}
					}
				}
				if (!te.isEmpty()) {
					te.emptyAllLivingFromHive(player, state, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY); 
				}
			}
			
			resetHoneyLevel(world, state, pos);
			return InteractionResult.sidedSuccess(world.isClientSide);

		} else {
			return super.use(state,world,pos,player,hand,hit);	    	   
		}
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
		return p_152180_.isClientSide ? null : createTickerHelper(p_152182_, ModBlockEntityTypes.MOD_BEEHIVE.get(), BeehiveBlockEntity::serverTick);
	}
}
