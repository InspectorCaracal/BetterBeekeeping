package com.clockworkcaracal.betterbees.blocks;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeeSmokerBlock extends Block implements SimpleWaterloggedBlock {
   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BeeSmokerBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
   		if (canLight(state)) {
            worldIn.setBlock(pos, state.setValue(LIT, Boolean.valueOf(true)), 3);
    	} else if (isLit(state)) {
    		worldIn.setBlock(pos, state.setValue(LIT, Boolean.valueOf(false)), 3);
    	}
    	return InteractionResult.SUCCESS;
     }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
      if (stateIn.getValue(LIT)) {
         if (rand.nextInt(3) == 0) {
            for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
            	makeParticles(worldIn, pos, false);
            }
         }
      }
   	}
    
    public static void makeParticles(Level worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        SimpleParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
     }

     public static boolean isSmokeyPos(Level world, BlockPos pos) {
        for(int i = 1; i <= 5; ++i) {
           BlockPos blockpos = pos.below(i);
           BlockState blockstate = world.getBlockState(blockpos);
           if (isLit(blockstate)) {
              return true;
           }
        }

        return false;
     }

     public static boolean canLight(BlockState state) {
    	 return state.hasProperty(LIT) && !state.getValue(LIT) && !state.getValue(WATERLOGGED);
    	 
     }
     public static boolean isLit(BlockState state) {
        return state.hasProperty(LIT) && state.is(BlockTags.CAMPFIRES) && state.getValue(LIT);
     }

     public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
         return SHAPE;
      }

     protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
         builder.add(LIT, WATERLOGGED);
      }
  
     public boolean placeLiquid(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
         if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            boolean flag = state.getValue(LIT);
            if (flag) {
               if (!worldIn.isClientSide()) {
                  worldIn.playSound((Player)null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
               }
            }

            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
            worldIn.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
            return true;
         } else {
            return false;
         }
      }

}
