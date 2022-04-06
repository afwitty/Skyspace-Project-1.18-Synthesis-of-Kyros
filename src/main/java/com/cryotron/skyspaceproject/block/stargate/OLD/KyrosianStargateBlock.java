//package com.cryotron.skyspaceproject.block.stargate;
//
//import java.util.Optional;
//import java.util.Random;
//import java.util.function.Function;
//
//import javax.annotation.Nullable;
//
//import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
//import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.Registry;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.StateDefinition;
//import net.minecraft.world.level.block.state.properties.BlockStateProperties;
//import net.minecraft.world.level.block.state.properties.EnumProperty;
//import net.minecraft.world.level.levelgen.Heightmap;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//import net.minecraftforge.common.util.ITeleporter;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//
//public class KyrosianStargateBlock extends Block {
//	
//	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
//	protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
//	protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
//	static double savedX = 0;
//	static double savedY = 0;
//	static double savedZ = 0;
//
//	public KyrosianStargateBlock() {
//		super(BlockBehaviour.Properties.of(Material.PORTAL).strength(50).randomTicks().strength(-1.0F).sound(SoundType.GLASS).noCollission().lightLevel((p) -> 15).noDrops());
//		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
//	}
//	
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
//    {
//		switch ((Direction.Axis) state.getValue(AXIS))
//		{
//		case Z:
//		    return Z_AXIS_AABB;
//		default:
//		    return X_AXIS_AABB;
//		}
//    }
//
//    public BlockState rotate(BlockState state, Rotation rot)
//    {
//		switch (rot)
//		{
//			case COUNTERCLOCKWISE_90:
//			case CLOCKWISE_90:
//			    switch ((Direction.Axis) state.getValue(AXIS))
//			    {
//			    case Z:
//				return state.setValue(AXIS, Direction.Axis.X);
//			    case X:
//				return state.setValue(AXIS, Direction.Axis.Z);
//			    default:
//				return state;
//			    }
//			default:
//			    return state;
//		}
//    }
//    
//	public static void portalSpawn(Level world, BlockPos pos) {
//		Optional<KyrosianStargateShape> optional = KyrosianStargateShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
//		if (optional.isPresent()) {
//			optional.get().createPortalBlocks();
//		}
//	}
//    
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
//    {
//    	builder.add(AXIS);
//    }
//    
//	   public void animateTick(BlockState p_54920_, Level p_54921_, BlockPos p_54922_, Random p_54923_) {
//		      if (p_54923_.nextInt(100) == 0) {
//		         p_54921_.playLocalSound((double)p_54922_.getX() + 0.5D, (double)p_54922_.getY() + 0.5D, (double)p_54922_.getZ() + 0.5D, SkyspaceRegistration.AMBIENT_STARGATE.get(), SoundSource.BLOCKS, 0.5F, p_54923_.nextFloat() * 0.4F + 0.8F, false);
//		      }
//
//		      for(int i = 0; i < 4; ++i) {
//		         double d0 = (double)p_54922_.getX() + p_54923_.nextDouble();
//		         double d1 = (double)p_54922_.getY() + p_54923_.nextDouble();
//		         double d2 = (double)p_54922_.getZ() + p_54923_.nextDouble();
//		         double d3 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
//		         double d4 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
//		         double d5 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
//		         int j = p_54923_.nextInt(2) * 2 - 1;
//		         if (!p_54921_.getBlockState(p_54922_.west()).is(this) && !p_54921_.getBlockState(p_54922_.east()).is(this)) {
//		            d0 = (double)p_54922_.getX() + 0.5D + 0.25D * (double)j;
//		            d3 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
//		         } else {
//		            d2 = (double)p_54922_.getZ() + 0.5D + 0.25D * (double)j;
//		            d5 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
//		         }
//
//		         p_54921_.addParticle(SkyspaceRegistration.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), d0, d1, d2, d3, d4, d5);
//		      }
//
//		   }
//    
//	   
//	    // In Singleplayer, this works. In multiplayer, a Capability must be implemented for each player to return to their original positions they've entered to Kyros.
//	   // Also internal cooldown must be implemented. For now, it's not neccessary since players are not teleporting on top of the portal.
//	    @Override
//	    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn)
//		    {
//	    	
//	        if (entityIn instanceof ServerPlayer player) {
//	            if (worldIn.dimension().equals(Dimensions.KYROS)) {
//	                teleportToOW(player, pos.north(), Level.OVERWORLD);
//	            } else {
//	            	savedX = player.getX();
//	            	savedY = player.getY();
//	            	savedZ = player.getZ();
//	                teleportToKyros(player, pos.north(), Dimensions.KYROS);
//	            }
//	        }
//	    	
//		}
//	    
//	    private void teleportToOW(ServerPlayer player, BlockPos pos, ResourceKey<Level> id) {
//	        ServerLevel world = player.getServer().getLevel(id);
//	        teleport(player, world, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
//	    }
//	    
//	    private void teleportToKyros(ServerPlayer player, BlockPos pos, ResourceKey<Level> id) {
//	        ServerLevel world = player.getServer().getLevel(id);
//	        teleportK(player, world, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
//	    }
//	    
//		public static void teleport(ServerPlayer entity, ServerLevel destination, BlockPos pos, boolean findTop) {
//	        entity.changeDimension(destination, new ITeleporter() {
//	            @Override
//	            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
//	                entity = repositionEntity.apply(false);
//	                int y = pos.getY();
//	                if (findTop) {
//	                    y = destination.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
//	                }
//	                if ((savedX == 0) && (savedY == 0) && (savedZ == 0)) {
//	                	entity.teleportTo(pos.getX(), y, pos.getZ());
//	                	return entity;
//	                }
//	                entity.teleportTo(savedX+1, savedY, savedZ+1);
//	                return entity;
//	            }
//	        });
//	    }
//
//		public static void teleportK(ServerPlayer entity, ServerLevel destination, BlockPos pos, boolean findTop) {
//	        entity.changeDimension(destination, new ITeleporter() {
//	            @Override
//	            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
//	                entity = repositionEntity.apply(false);
//	                int y = pos.getY();
//	                if (findTop) {
//	                    y = destination.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
//	                }
//	                entity.teleportTo(0, 4, 0);	                
//	                return entity;
//	            }
//	        });
//	    }
//	    
//}