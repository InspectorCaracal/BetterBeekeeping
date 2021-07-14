package com.clockworkcaracal.betterbees.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeeSmokerBlock extends Block implements IWaterLoggable {
   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   public static final BooleanProperty LIT = BlockStateProperties.LIT;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BeeSmokerBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(true)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
   		if (canLight(state)) {
            worldIn.setBlock(pos, state.setValue(LIT, Boolean.valueOf(true)), 3);
    	} else if (isLit(state)) {
    		worldIn.setBlock(pos, state.setValue(LIT, Boolean.valueOf(false)), 3);
    	}
    	return ActionResultType.SUCCESS;
     }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (stateIn.getValue(LIT)) {
         if (rand.nextInt(3) == 0) {
            for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
            	makeParticles(worldIn, pos, false);
            }
         }
      }
   	}
    
    public static void makeParticles(World worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
     }

     public static boolean isSmokeyPos(World world, BlockPos pos) {
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

     public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
         return SHAPE;
      }

     protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
         builder.add(LIT, WATERLOGGED);
      }
  
     public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
         if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            boolean flag = state.getValue(LIT);
            if (flag) {
               if (!worldIn.isClientSide()) {
                  worldIn.playSound((PlayerEntity)null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
               }
            }

            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
            worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
         } else {
            return false;
         }
      }

}
