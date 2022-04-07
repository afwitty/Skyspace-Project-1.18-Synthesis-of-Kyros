package com.cryotron.skyspaceproject.block.stargate;


import org.checkerframework.checker.units.qual.s;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.Random;
import java.util.function.Function;
import java.util.Optional;

public class KyrosianStargateBlock extends NetherPortalBlock {
	
	static BlockPos savedPoint;
	
	public KyrosianStargateBlock() {
		super(BlockBehaviour.Properties.of(Material.PORTAL).noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(s -> 15)
				.noDrops());
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
	}

	public static void portalSpawn(Level world, BlockPos pos) {
		Optional<KyrosianStargateShape> optional = KyrosianStargateShape.findEmptyPortalShape(world, pos, Direction.Axis.X);
		if (optional.isPresent()) {
			optional.get().createPortalBlocks();
		}
	}

	@Override
	public BlockState updateShape(BlockState p_54928_, Direction p_54929_, BlockState p_54930_, LevelAccessor p_54931_, BlockPos p_54932_,
			BlockPos p_54933_) {
		Direction.Axis direction$axis = p_54929_.getAxis();
		Direction.Axis direction$axis1 = p_54928_.getValue(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && !p_54930_.is(this) && !(new KyrosianStargateShape(p_54931_, p_54932_, direction$axis1)).isComplete()
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(p_54928_, p_54929_, p_54930_, p_54931_, p_54932_, p_54933_);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
	      if (random.nextInt(100) == 0) {
	    	  world.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SkyspaceRegistration.AMBIENT_STARGATE.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
	      }
		
		for (int i = 0; i < 4; i++) {
			double px = pos.getX() + random.nextFloat();
			double py = pos.getY() + random.nextFloat();
			double pz = pos.getZ() + random.nextFloat();
			double vx = (random.nextFloat() - 0.5) / 2.;
			double vy = (random.nextFloat() - 0.5) / 2.;
			double vz = (random.nextFloat() - 0.5) / 2.;
			int j = random.nextInt(4) - 1;
			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
				px = pos.getX() + 0.5 + 0.25 * j;
				vx = random.nextFloat() * 2 * j;
			} else {
				pz = pos.getZ() + 0.5 + 0.25 * j;
				vz = random.nextFloat() * 2 * j;
			}
			world.addParticle(SkyspaceRegistration.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), px, py, pz, vx, vy, vz);
		}
		if (random.nextInt(110) == 0)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
					ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(("block.portal.ambient"))), SoundSource.BLOCKS, 0.5f,
					random.nextFloat() * 0.4f + 0.8f);
	}

    // In Singleplayer, this works. In multiplayer, a Capability must be implemented for each player to return to their original positions they've entered to Kyros.
   // Also internal cooldown must be implemented. For now, it's not neccessary since players are not teleporting on top of the portal.
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity)
	    {
    	BlockPos kyrosianOrigin = new BlockPos(0, 4, 0);
    	
		if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && !entity.level.isClientSide() && true) {
			if (entity.isOnPortalCooldown()) {
				entity.setPortalCooldown();
			} else if (entity.level.dimension() != ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("skyspaceproject:kyros"))) {
				entity.setPortalCooldown();
				Skyspace.LOGGER.info("OLD SAVED POINT: " + savedPoint);
				savedPoint = pos;
				Skyspace.LOGGER.info("NEW SAVED POINT: " + savedPoint);
				entity.teleportTo(0,4,0);
				teleportToDimensionK(entity, kyrosianOrigin, ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("skyspaceproject:kyros")));
			} else {
				entity.setPortalCooldown();
				if (savedPoint != null) {
					teleportToDimensionO(entity, savedPoint, Level.OVERWORLD);
					entity.teleportTo(savedPoint.getX(), savedPoint.getY(), savedPoint.getZ());
				}
				else {
//					teleportToDimensionO(entity, pos, Level.OVERWORLD); // if the player has not entered the portal... Fix this.
//					entity.teleportTo(pos.getX(), pos.getY(), pos.getZ());
					if (entity instanceof ServerPlayer player) {
						teleportToVI(player, pos.north(), Level.OVERWORLD);
					}
				}
				Skyspace.LOGGER.info("RETURNING TO OVERWORLD: " + entity.getOnPos());
			}
		}
    	
	}
    
	private void teleportToDimensionK(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
		entity.changeDimension(entity.getServer().getLevel(destinationType),
				new KyrosianStargateTeleporter(entity.getServer().getLevel(destinationType), new BlockPos(0,4,0)));
	}
    
	private void teleportToDimensionO(Entity entity, BlockPos pos, ResourceKey<Level> destinationType) {
		entity.changeDimension(entity.getServer().getLevel(destinationType),
				new KyrosianStargateTeleporter(entity.getServer().getLevel(destinationType), pos));
	}

    private void teleportToVI(ServerPlayer player, BlockPos pos, ResourceKey<Level> id) {
        ServerLevel world = player.getServer().getLevel(id);
        teleportVII(player, world, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
    }
	
    public static void teleportVII(ServerPlayer entity, ServerLevel destination, BlockPos pos, boolean findTop) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                int y = pos.getY();
                if (findTop) {
                    y = destination.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
                }
                entity.teleportTo(pos.getX(), y, pos.getZ());
                return entity;
            }
        });
    }

}
