package com.cryotron.skyspaceproject.worldgen.dimensions;

import com.cryotron.skyspaceproject.Skyspace;

import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianChunkCubeManual;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class KyrosChunkGenerator extends ChunkGenerator {
	    private static final Codec<Settings> SETTINGS_CODEC = RecordCodecBuilder.create(instance ->
	    instance.group(
	            Codec.INT.fieldOf("base").forGetter(Settings::baseHeight),
	            Codec.FLOAT.fieldOf("verticalvariance").forGetter(Settings::verticalVariance),
	            Codec.FLOAT.fieldOf("horizontalvariance").forGetter(Settings::horizontalVariance)
	    ).apply(instance, Settings::new));
	
	public static final Codec<KyrosChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
	    instance.group(
	            RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(KyrosChunkGenerator::getBiomeRegistry),
	            SETTINGS_CODEC.fieldOf("settings").forGetter(KyrosChunkGenerator::getTutorialSettings)
	    ).apply(instance, KyrosChunkGenerator::new));
	
	private final Settings settings;
//	private Level world;
	
	public KyrosChunkGenerator(Registry<Biome> registry, Settings settings) {
		super(new KyrosBiomeProvider(registry), new StructureSettings(false));
		this.settings = settings;
		Skyspace.LOGGER.info("[SSP] Chunk generator settings: " + settings.baseHeight() + ", " + settings.horizontalVariance() + ", " + settings.verticalVariance());
	}
	
	public Settings getTutorialSettings() {
		return settings;
	}
	
	public Registry<Biome> getBiomeRegistry() {
		return ((KyrosBiomeProvider)biomeSource).getBiomeRegistry();
	}
	
	@Override
	public void buildSurface(WorldGenRegion region, StructureFeatureManager featureManager, ChunkAccess chunk) {
		BlockState kyrosian = SkyspaceRegistration.KYROSIAN_GLASS_BLOCK.get().defaultBlockState();
//		BlockState kyrosianFrame = SkyspaceRegistration.KYROSIAN_EDGE_BLOCK.get().defaultBlockState();
//		BlockState debugBlock = Blocks.STONE.defaultBlockState();
		BlockState air = Blocks.AIR.defaultBlockState();
		ChunkPos chunkpos = chunk.getPos();
		

		
		//StructureFeature<?> chunkCube = SkyspaceRegistration.KYROSIAN_CHUNK_CUBE.get();
		
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		
		int x;
		int y;
		int z;

		// Central Axis Walls Between Quadrants
		//	(x,z) is Quadrant I - Lysny
		//  (-x,z) is Quadrant II - Angela
		//  (-x,-z) is Quadrant III - Scarlett
		//  (x, -z) is Quadrant IV - Melissa
		for(x = 0; x < 16; x++) {
			for (y = -256; y < 256 ; y++) {
				// Kyros Quadrant I & II
				if ((chunkpos.z == 16 && (chunkpos.x < -16 || chunkpos.x > 15))) {
					chunk.setBlockState(pos.set(x,y,0), kyrosian, false);
				}
				if ((chunkpos.z == 15 && (chunkpos.x < -16 || chunkpos.x > 15))) {
					chunk.setBlockState(pos.set(x,y,15), kyrosian, false);
				}
				if ((chunkpos.x == 15) && (chunkpos.z == 15)) {
					chunk.setBlockState(pos.set(15,y,15), kyrosian, false);	
				}	// Quadrant I Corner
				if ((chunkpos.x == -16) && (chunkpos.z == 15)) {
					chunk.setBlockState(pos.set(0,y,15), kyrosian, false);	
				}	// Quadrant II Corner				
				// Kyros Quadrant III & IV
				if ((chunkpos.z == -16 && (chunkpos.x < -16 || chunkpos.x > 15))) {
					chunk.setBlockState(pos.set(x,y,0), kyrosian, false);			
				}
				if ((chunkpos.z == -17 && (chunkpos.x < -16 || chunkpos.x > 15))) {
					chunk.setBlockState(pos.set(x,y,15), kyrosian, false);			
				}
			}
		}	
		for(z = 0; z < 16; z++) {
			for (y = -256; y < 256 ; y++) {
				// Kyros Quadrant I & IV
				if ((chunkpos.x == 16 && (chunkpos.z < -16 || chunkpos.z > 15))) {
					chunk.setBlockState(pos.set(0,y,z), kyrosian, false);
				}
				if ((chunkpos.x == 15 && (chunkpos.z < -16 || chunkpos.z > 15))) {
					chunk.setBlockState(pos.set(15,y,z), kyrosian, false);
				}
				// Kyros Quadrant II & III
				if ((chunkpos.x == -16 && (chunkpos.z < -16 || chunkpos.z > 15))) {
					chunk.setBlockState(pos.set(0,y,z), kyrosian, false);	
				}
				if ((chunkpos.x == -17 && (chunkpos.z < -16 || chunkpos.z > 15))) {
					chunk.setBlockState(pos.set(15,y,z), kyrosian, false);	
				}
				// Kyros Quadrant III Requires an extra pillar to make a complete axis walls.
				if ((chunkpos.x == -16) && (chunkpos.z == -16)) {
					chunk.setBlockState(pos.set(0,y,0), kyrosian, false);	
				}
				if ((chunkpos.x == 15) && (chunkpos.z == -16)) {
					chunk.setBlockState(pos.set(15,y,0), kyrosian, false);	
				}
			}
		}
		
		// Chunk Cubes at the edges.
		for(y = -256; y < 256; y++) {
			if (y == -256 || y == -240 || y == 240 || y == 224) {
				// Surely there is more efficient way to do this??
				KyrosianChunkCubeManual.genChunkCube(0, y,0, chunk, pos);
			}
			for (int d = 0; d < 28; d++) {
				  if (y == -224 + (16*d)) {
					  if (chunkpos.x == 14 && chunkpos.z >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 14 && chunkpos.z <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 15 && chunkpos.z >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 15 && chunkpos.z <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 16 && chunkpos.z >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 16 && chunkpos.z <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 17 && chunkpos.z >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == 17 && chunkpos.z <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  
					  if (chunkpos.x == -15 && chunkpos.z >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -15 && chunkpos.z <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -16 && chunkpos.z >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -16 && chunkpos.z <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -17 && chunkpos.z >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -17 && chunkpos.z <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -18 && chunkpos.z >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.x == -18 && chunkpos.z <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }

					  if (chunkpos.z == 14 && chunkpos.x >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 14 && chunkpos.x <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 15 && chunkpos.x >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 15 && chunkpos.x <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 16 && chunkpos.x >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 16 && chunkpos.x <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 17 && chunkpos.x >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == 17 && chunkpos.x <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }

					  if (chunkpos.z == -15 && chunkpos.x >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -15 && chunkpos.x <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -16 && chunkpos.x >= 14) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -16 && chunkpos.x <= -15) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -17 && chunkpos.x >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -17 && chunkpos.x <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -18 && chunkpos.x >= 16) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
					  if (chunkpos.z == -18 && chunkpos.x <= -17) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
					  }
				  }				
			}
		}

		// Memory Nexus (Corners)
		for (y = -32; y < 32; y++) {
			for (int xx = 0; xx < 16; xx++) {
				for (int zz = 0; zz < 16; zz++) {
					
					for (int xxx = 14; xxx <= 18; xxx++) {
						for (int zzz = 14; zzz <= 18; zzz++) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}		
						for (int zzz = -15; zzz >= -19; zzz--) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}								
					}
					
					for (int xxx = -15; xxx >= -19; xxx--) {
						for (int zzz = 14; zzz <= 18; zzz++) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}		
						for (int zzz = -15; zzz >= -19; zzz--) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}								
					}
					
					

					
					
					}				
				}					
			}
		
		// Memory Nexus (Pillars)
		for (y = -32; y < 32; y += 16) {
			if (
					
					// Quadrant I
					(chunkpos.x == 17 && chunkpos.z == 17) || 
					(chunkpos.x == 17 && chunkpos.z == 16) || 
					(chunkpos.x == 17 && chunkpos.z == 15) || 
					(chunkpos.x == 16 && chunkpos.z == 17) || 
					(chunkpos.x == 16 && chunkpos.z == 16) || 
					(chunkpos.x == 16 && chunkpos.z == 15) || 
					(chunkpos.x == 15 && chunkpos.z == 17) || 
					(chunkpos.x == 15 && chunkpos.z == 16) || 
					(chunkpos.x == 15 && chunkpos.z == 15) ||
					
					// Quadrant II
					(chunkpos.x == -18 && chunkpos.z == 17) || 
					(chunkpos.x == -18 && chunkpos.z == 16) || 
					(chunkpos.x == -18 && chunkpos.z == 15) || 
					(chunkpos.x == -17 && chunkpos.z == 17) || 
					(chunkpos.x == -17 && chunkpos.z == 16) || 
					(chunkpos.x == -17 && chunkpos.z == 15) || 
					(chunkpos.x == -16 && chunkpos.z == 17) || 
					(chunkpos.x == -16 && chunkpos.z == 16) || 
					(chunkpos.x == -16 && chunkpos.z == 15) ||
					
					// Quadrant III
					(chunkpos.x == 17 && chunkpos.z == -18) || 
					(chunkpos.x == 17 && chunkpos.z == -17) || 
					(chunkpos.x == 17 && chunkpos.z == -16) || 
					(chunkpos.x == 16 && chunkpos.z == -18) || 
					(chunkpos.x == 16 && chunkpos.z == -17) || 
					(chunkpos.x == 16 && chunkpos.z == -16) || 
					(chunkpos.x == 15 && chunkpos.z == -18) || 
					(chunkpos.x == 15 && chunkpos.z == -17) || 
					(chunkpos.x == 15 && chunkpos.z == -16) ||
					
					// Quadrant IV
					(chunkpos.x == -18 && chunkpos.z == -18) || 
					(chunkpos.x == -18 && chunkpos.z == -17) || 
					(chunkpos.x == -18 && chunkpos.z == -16) || 
					(chunkpos.x == -17 && chunkpos.z == -18) || 
					(chunkpos.x == -17 && chunkpos.z == -17) || 
					(chunkpos.x == -17 && chunkpos.z == -16) || 
					(chunkpos.x == -16 && chunkpos.z == -18) || 
					(chunkpos.x == -16 && chunkpos.z == -17) || 
					(chunkpos.x == -16 && chunkpos.z == -16) 
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}

		}
		
		
		for (y = -128; y < -64; y += 16) {
			if ( (chunkpos.x <= 14 && chunkpos.z <= 14)  &&
				 (chunkpos.x >= -15 && chunkpos.z >= -15)
					){
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= 20 && chunkpos.z <= 14)  &&
					 (chunkpos.x >= -21 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			
			if (	(chunkpos.x <= 14 && chunkpos.z <= 20)  &&
					 (chunkpos.x >= -15 && chunkpos.z >= -21)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
		}
		for (y = 64; y < 128; y += 16) {
			if ( (chunkpos.x <= 14 && chunkpos.z <= 14)  &&
				 (chunkpos.x >= -15 && chunkpos.z >= -15)
					){
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}		
			if (	(chunkpos.x <= 20 && chunkpos.z <= 14)  &&
					 (chunkpos.x >= -21 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			
			if (	(chunkpos.x <= 14 && chunkpos.z <= 20)  &&
					 (chunkpos.x >= -15 && chunkpos.z >= -21)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			
		}
		
		// Nexus Walls on x and z < 16
		for (y = -128; y <= 128; y += 16) {
			if (	(chunkpos.x <= 24 && chunkpos.z <= 14)  &&
					 (chunkpos.x > 20 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= 14 && chunkpos.z <= 24)  &&
					 (chunkpos.x >= -15 && chunkpos.z > 20)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x >= -25 && chunkpos.z <= 14)  &&
					 (chunkpos.x < -21 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= 14 && chunkpos.z >= -25)  &&
					 (chunkpos.x >= -15 && chunkpos.z < -21)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
		}
		
		// TEST
		// Chunk Cubes at the edges.
		
		for (y = -128; y <= 128; y += 16) {
			if (	(chunkpos.x <= 24 && chunkpos.z <= 14)  &&
					 (chunkpos.x > 20 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= 14 && chunkpos.z <= 24)  &&
					 (chunkpos.x >= -15 && chunkpos.z > 20)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= -25 && chunkpos.z <= 14)  &&
					 (chunkpos.x > -21 && chunkpos.z >= -15)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			if (	(chunkpos.x <= 14 && chunkpos.z <= -25)  &&
					 (chunkpos.x >= -15 && chunkpos.z > -21)
					) {
				KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
			}
			
			// Nexus Walls
			if ((chunkpos.x <= 21 && chunkpos.z <= 9) &&
					(chunkpos.x >= -22 && chunkpos.z >= 8)				
						) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
				}				
			if ((chunkpos.x <= 21 && chunkpos.z >= -10) &&
					(chunkpos.x >= -22 && chunkpos.z <= -9)				
						) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
				}
			if ((chunkpos.z <= 21 && chunkpos.x <= 9) &&
					(chunkpos.z >= -22 && chunkpos.x >= 8)				
						) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
				}				
			if ((chunkpos.z <= 21 && chunkpos.x >= -10) &&
					(chunkpos.z >= -22 && chunkpos.x <= -9)				
						) {
						KyrosianChunkCubeManual.genChunkCube(0, y, 0, chunk, pos);		
				}
		}
		
		// Nexus Entrance
		for (y = -32; y < 32; y++) {
			for (int xx = 0; xx < 16; xx++) {
				for (int zz = 0; zz < 16; zz++) {
					
					for (int xxx = 7; xxx <= 10; xxx++) {
						for (int zzz = 7; zzz <= 10; zzz++) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}		
						for (int zzz = -8; zzz >= -11; zzz--) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}								
					}
					
					for (int xxx = -8; xxx >= -11; xxx--) {
						for (int zzz = 7; zzz <= 10; zzz++) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}		
						for (int zzz = -8; zzz >= -11; zzz--) {
							if( chunkpos.x == xxx && chunkpos.z == zzz) {
								chunk.setBlockState(pos.set(xx,y,zz), air, false);				
							}							
						}								
					}
				}
			}
		}




		
	}
	
	private int getHeightAt(int baseHeight, float verticalVariance, float horizontalVariance, int x, int z) {
		return (int) (baseHeight + Math.sin(x / horizontalVariance) * verticalVariance + Math.cos(z / horizontalVariance) * verticalVariance);
	}
	
	@Override
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}
	
	@Override
	public ChunkGenerator withSeed(long seed) {
		return new KyrosChunkGenerator(getBiomeRegistry(), settings);
	}
	
	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager featureManager, ChunkAccess chunkAccess) {
		return CompletableFuture.completedFuture(chunkAccess);
	}
	
	// Make sure this is correctly implemented so that structures and features can use this
	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor) {
		int baseHeight = settings.baseHeight();
		float verticalVariance = settings.verticalVariance();
		float horizontalVariance = settings.horizontalVariance();
		return getHeightAt(baseHeight, verticalVariance, horizontalVariance, x, z);
	}
	
	// Make sure this is correctly implemented so that structures and features can use this
	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor) {
		int y = getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);
		BlockState stone = Blocks.STONE.defaultBlockState();
		BlockState[] states = new BlockState[y];
		states[0] = Blocks.BEDROCK.defaultBlockState();
		for (int i = 1 ; i < y ; i++) {
		    states[i] = stone;
		}
		return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), states);
	}
	
	// Carvers only work correctly in combination with NoiseBasedChunkGenerator so we keep this empty here
	@Override
	public void applyCarvers(WorldGenRegion level, long seed, BiomeManager biomeManager,
			StructureFeatureManager featureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
	}
	
	@Override
	public Climate.Sampler climateSampler() {
		return (x, y, z) -> Climate.target(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}
	
	// This makes sure passive mob spawning works for generated chunks. i.e. mobs that spawn during the creation of chunks themselves
	@Override
	public void spawnOriginalMobs(WorldGenRegion level) {
		ChunkPos chunkpos = level.getCenter();
		Biome biome = level.getBiome(chunkpos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
		worldgenrandom.setDecorationSeed(level.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
		NaturalSpawner.spawnMobsForChunkGeneration(level, biome, chunkpos, worldgenrandom);
	}
	
	@Override
	public int getMinY() {
		return -256;
	}
	
	@Override
	public int getGenDepth() {
		return 512;
	}
	
	@Override
	public int getSeaLevel() {
		return -1;
	}

	private record Settings(int baseHeight, float verticalVariance, float horizontalVariance) { }
	
	
	
}
