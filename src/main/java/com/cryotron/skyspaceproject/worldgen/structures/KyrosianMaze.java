package com.cryotron.skyspaceproject.worldgen.structures;

//import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
//import com.cryotron.skyspaceproject.setup.SSStructures;
//import com.google.common.collect.ImmutableList;
//import com.mojang.serialization.Codec;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobCategory;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.NoiseColumn;
//import net.minecraft.world.level.biome.MobSpawnSettings;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.levelgen.GenerationStep;
//import net.minecraft.world.level.levelgen.Heightmap;
//import net.minecraft.world.level.levelgen.feature.StructureFeature;
//import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
//import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
//import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
//import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
//import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;
//import net.minecraftforge.common.util.Lazy;
//import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
//
//import java.util.List;
//import java.util.Optional;
import java.util.Random;
//
import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.plexus.util.StringUtils;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.util.GeneralUtils;
import com.cryotron.skyspaceproject.util.Mutable;
import com.cryotron.skyspaceproject.worldgen.structures.codeconfigs.GenericJigsawStructureCodeConfig;
import com.cryotron.skyspaceproject.worldgen.structures.pieces.PieceLimitedJigsawManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class KyrosianMaze extends GenericJigsawStructure {

//extends StructureFeature<JigsawConfiguration> {
	
    static Random rand = new Random();
    public static int MazeSize = 1024;
    public static String[][][] ChunkNodeI = new String[MazeSize][32][MazeSize]; //1875000
    public static String[][][] ChunkNodeII = new String[MazeSize][32][MazeSize]; //1875000
    public static String[][][] ChunkNodeIII = new String[MazeSize][32][MazeSize]; //1875000
    public static String[][][] ChunkNodeIV = new String[MazeSize][32][MazeSize]; //1875000
	static int dirSel;
	static int elev;
	
    public KyrosianMaze(Predicate<PieceGeneratorSupplier.Context<NoneFeatureConfiguration>> locationCheckPredicate, Function<PieceGeneratorSupplier.Context<NoneFeatureConfiguration>, Optional<PieceGenerator<NoneFeatureConfiguration>>> pieceCreationPredicate) {
        super(locationCheckPredicate, pieceCreationPredicate);
    }

    // Need this constructor wrapper so we can hackly call `this` in the predicates that Minecraft requires in constructors
    public static KyrosianMaze create(GenericJigsawStructureCodeConfig genericJigsawStructureCodeConfig) {
        final Mutable<KyrosianMaze> box = new Mutable<>();
        final KyrosianMaze finalInstance = new KyrosianMaze(
                (context) -> box.getValue().isFeatureChunk(context, genericJigsawStructureCodeConfig),
                (context) -> box.getValue().generatePieces(context, genericJigsawStructureCodeConfig)
        );
        box.setValue(finalInstance);
        return finalInstance;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
    

    public Optional<PieceGenerator<NoneFeatureConfiguration>> generatePieces(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context, GenericJigsawStructureCodeConfig config, int x, int y, int z) {

        		
        		//new BlockPos(context.chunkPos().getMinBlockX(), context.chunkGenerator().getSeaLevel(), context.chunkPos().getMinBlockZ());

        ResourceLocation structureID = ForgeRegistries.STRUCTURE_FEATURES.getKey(this);
        
        // Quadrant I
		if (context.chunkPos().x >= 18 && context.chunkPos().z >= 18 && context.chunkPos().x <  200 && context.chunkPos().z < 200) { // 17  + MazeSize - 2
	    	if (ChunkNodeI[x][y][z] != null) {
	        	Optional<PieceGenerator<NoneFeatureConfiguration>> piece;
	        	piece = PiecesGenerator.GenerateII(context, config, structureID, ChunkNodeI[x][y][z]);	
	        	return piece;
	    	}
		}
		
		// Quadrant II
		if (context.chunkPos().x <= -19 && context.chunkPos().z >= 18 && context.chunkPos().x >  -201 && context.chunkPos().z < 200) { // 17  + MazeSize - 2
	    	if (ChunkNodeII[x][y][z] != null) {
	        	Optional<PieceGenerator<NoneFeatureConfiguration>> piece;
	        	piece = PiecesGenerator.GenerateII(context, config, structureID, ChunkNodeII[(x*(-1))+(-1)][y][z]);	
	        	return piece;
	    	}
		}
		
		// Quadrant III
		if (context.chunkPos().x >= 18 && context.chunkPos().z <= -19 && context.chunkPos().x <  200 && context.chunkPos().z > -201) { // 17  + MazeSize - 2
	    	if (ChunkNodeIII[x][y][z] != null) {
	        	Optional<PieceGenerator<NoneFeatureConfiguration>> piece;
	        	piece = PiecesGenerator.GenerateII(context, config, structureID, ChunkNodeIII[x][y][(z*(-1))+(-1)]);	
	        	return piece;
	    	}
		}
		
		// Quadrant IV
		if (context.chunkPos().x >= -19 && context.chunkPos().z >= -19 && context.chunkPos().x >  -201 && context.chunkPos().z > -201) { // 17  + MazeSize - 2
	    	if (ChunkNodeIV[x][y][z] != null) {
	        	Optional<PieceGenerator<NoneFeatureConfiguration>> piece;
	        	piece = PiecesGenerator.GenerateII(context, config, structureID, ChunkNodeIV[(x*(-1))+(-1)][y][(z*(-1))+(-1)]);	
	        	return piece;
	    	}
		}
		
    	return Optional.empty();
        
//		if (context.chunkPos().x >= 18 && context.chunkPos().z >= 18 && context.chunkPos().x < 18 + MazeSize-2 && context.chunkPos().z < 18 + MazeSize-2) {
//			
//			

//		}
//		else {
//			return Optional.empty();
//		}
    }
    
	public static void mapChunkNodes() {
  	Skyspace.LOGGER.info("Mapping Q1 Kyrosian Maze!");
    	

    	for (int y = 0; y < 32; y++) {		
    		if (y == 16) {
            	ChunkNodeI[0][y][0] = "continuous/kyrosian_4-way_road";
        		
            	ChunkNodeI[0][y][1] = "continuous/kyrosian_z_road";    			
            	
    	    	ChunkNodeI[1][y][0] = "continuous/kyrosian_x_road";
    	    	
        	    ChunkNodeI[1][y][1] =  "obstruction/kyrosian_chunk_cube";           			
    		}
    		if (y != 16) {
            	ChunkNodeI[0][y][0] = "obstruction/kyrosian_chunk_cube";
        		
            	ChunkNodeI[0][y][1] = "obstruction/kyrosian_chunk_cube";    			
            	
    	    	ChunkNodeI[1][y][0] = "obstruction/kyrosian_chunk_cube";
    	    	
        	    ChunkNodeI[1][y][1] =  "obstruction/kyrosian_chunk_cube";      
    		}
	    	for (int z = 2; z < 1000; z++) {
	    		if (ChunkNodeI[0][y][z] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous Z.
	        		if (	ChunkNodeI[0][y][z-1] == "continuous/kyrosian_z_road" ||
	        				ChunkNodeI[0][y][z-1] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
	        				ChunkNodeI[0][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" ||
	        				ChunkNodeI[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_x" ||
	        				ChunkNodeI[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx" ||
	        				ChunkNodeI[0][y][z-1] == "continuous/kyrosian_4-way_road" ||
	        				ChunkNodeI[0][y][z-1] == "terminus/kyrosian_terminus_z" ) 
	        		{        			
	        			// Add appropriate pieces that connects to Z-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");
	        			
	        			// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeI[0][y][z-1] != "terminus/kyrosian_terminus_z"  ) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeI[0][y][z] = selectedPiece;
	        			
	        		}      		        		
	        		
	        		// If there is an obstruction from a previous Z.
	        		if (	ChunkNodeI[0][y][z-1] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add a pieces that starts a path from this node.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeI[0][y][z] = selectedPiece;
	        			
	        		}
	        					
	        		// If there is a disconnect from a previous Z.
	        		if (ChunkNodeI[0][y][z-1] == "terminus/kyrosian_terminus_negz" ||
	        				ChunkNodeI[0][y][z-1] == "terminus/kyrosian_terminus_x"||
	                		ChunkNodeI[0][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeI[0][y][z] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	

	    	for (int x = 2; x < 1000; x++) {
	    		if (ChunkNodeI[x][y][0] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous X.
	        		if (ChunkNodeI[x-1][y][0] == "continuous/kyrosian_x_road" ||
		         			ChunkNodeI[x-1][y][0] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
		         			ChunkNodeI[x-1][y][0] == "continuous/kyrosian_2-way_xtonegz_corner" ||
		         			ChunkNodeI[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_z" ||
		         			ChunkNodeI[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_negz" ||
		         			ChunkNodeI[x-1][y][0] == "continuous/kyrosian_4-way_road" ||
		         			ChunkNodeI[x-1][y][0] == "terminus/kyrosian_terminus_x" ) 
	        		{ 			
	        			// Add appropriate pieces that connects to X-1.
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			
						// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeI[x-1][y][0] != "terminus/kyrosian_terminus_x"  ) {
	    					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeI[x][y][0] = selectedPiece;
	        		}        		
	        		
	        		// If there is an obstruction from a previous X.
	        		if (	ChunkNodeI[x-1][y][0] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add appropriate pieces that DOESN'T connect to X-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeI[x][y][0] = selectedPiece;
	        		}
	        		       		
	        		// If there is a disconnect from a previous X.
	        		if (ChunkNodeI[x-1][y][0] == "terminus/kyrosian_terminus_negx" ||
	        				ChunkNodeI[x-1][y][0] == "terminus/kyrosian_terminus_z"||
	                		ChunkNodeI[x-1][y][0] == "continuous/kyrosian_2-way_ztox_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeI[x][y][0] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	
	    	for (int x = 1; x < 1000; x++) {
	        	for (int z = 1; z < 1000; z++) {	

	        		
	        		if (ChunkNodeI[x][y][z] == null) {
	            		
	        			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	        			int pieceSel;
	        			String selectedPiece;   			
	            		
	        			boolean ConnectionFromNegZ = (
		        				ChunkNodeI[x][y][z-1] == "terminus/kyrosian_terminus_z"	 	||
		            				
		            			ChunkNodeI[x][y][z-1] == "continuous/kyrosian_z_road" 			||
		
		                		ChunkNodeI[x][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" 	||
		                		ChunkNodeI[x][y][z-1] == "continuous/kyrosian_2-way_ztox_corner" 			||
		                		
		                        ChunkNodeI[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_x"         ||
		                        ChunkNodeI[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx"  ||
		                        ChunkNodeI[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negz"  ||
		                        
		                        ChunkNodeI[x][y][z-1] == "continuous/kyrosian_4-way_road"
	                        );
	        			
	        			boolean ConnectionFromNegX = (
		    					ChunkNodeI[x-1][y][z] == "terminus/kyrosian_terminus_x" 	||
		        				
		                		ChunkNodeI[x-1][y][z] == "continuous/kyrosian_x_road" 		||
		
		                    	ChunkNodeI[x-1][y][z] == "continuous/kyrosian_2-way_xtonegz_corner" 			||
		                    	ChunkNodeI[x-1][y][z] == "continuous/kyrosian_2-way_negztonegx_corner" 	||
		                    		
		                        ChunkNodeI[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_z"        		||
		                        ChunkNodeI[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negx" 	 	||
		                        ChunkNodeI[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negz" 	 	||
		                            
		                        ChunkNodeI[x-1][y][z] == "continuous/kyrosian_4-way_road"
	        				);
	        			
	        			boolean DisconnectionFromNegZ = (
		    					ChunkNodeI[x][y][z-1] == "terminus/kyrosian_terminus_negz"		||
		            			ChunkNodeI[x][y][z-1] == "terminus/kyrosian_terminus_x"			||
		            			ChunkNodeI[x][y][z-1] == "terminus/kyrosian_terminus_negx"		||
		            			
		            			ChunkNodeI[x][y][z-1] == "continuous/kyrosian_x_road" 				||
		            			
		                    	ChunkNodeI[x][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	||
		                    	ChunkNodeI[x][y][z-1] == "continuous/kyrosian_2-way_negxtoz_corner" 			||
		                    	
		                    	ChunkNodeI[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_z" 
	                    	);
	        			
	        			boolean DisconnectionFromNegX = (
	        					ChunkNodeI[x-1][y][z] == "terminus/kyrosian_terminus_negx"		||
	                			ChunkNodeI[x-1][y][z] == "terminus/kyrosian_terminus_z"			||
	                        	ChunkNodeI[x-1][y][z] == "terminus/kyrosian_terminus_negz"		||
	                        	
	                        	ChunkNodeI[x-1][y][z] == "continuous/kyrosian_z_road" 						||
	                        	
	                        	ChunkNodeI[x-1][y][z] == "continuous/kyrosian_2-way_ztox_corner" 	||
	                            ChunkNodeI[x-1][y][z] == "continuous/kyrosian_2-way_negxtoz_corner" 			||                    	
	                        	
	                        	ChunkNodeI[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_x" 
	        				);
	        			
	        			boolean ObstructionFromNegZ = (ChunkNodeI[x][y][z-1] == "obstruction/kyrosian_chunk_cube");
	        			
	        			boolean ObstructionFromNegX = (ChunkNodeI[x-1][y][z] == "obstruction/kyrosian_chunk_cube");
	        			
	        			if (ConnectionFromNegZ && ConnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_4-way_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_x");
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_z");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negxtoz_corner");   
	        			}
	        			
	        			if (ConnectionFromNegZ && DisconnectionFromNegX) {
	        				if (ChunkNodeI[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");       				
	        			}
	        			
	        			if (ConnectionFromNegZ && ObstructionFromNegX) {
	        				if (ChunkNodeI[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	            				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");        					
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");        		  
	        			}
	        			
	        			if (DisconnectionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeI[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			}
	        			
	        			if (DisconnectionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");        				
	        			}
	        			
	        			if (DisconnectionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");           				  
	        			}
	        			
	        			if (ObstructionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeI[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");        
	        				 
	        			}
	        			
	        			if(ObstructionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");      	        				   
	        			}
	        			
	        			if (ObstructionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");     			        				  
	        			}
	            		

	        			
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeI[x][y][z] = selectedPiece; 
	            		
	        		}   		        		    
	        	}
	    	}
    	}
    }

	public static void mapChunkNodesII() {
  	Skyspace.LOGGER.info("Mapping Q2 Kyrosian Maze!");
    	

    	for (int y = 0; y < 32; y++) {		
    		if (y == 16) {
            	ChunkNodeII[0][y][0] = "continuous/kyrosian_4-way_road";
        		
            	ChunkNodeII[0][y][1] = "continuous/kyrosian_z_road";    			
            	
    	    	ChunkNodeII[1][y][0] = "continuous/kyrosian_x_road";
    	    	
        	    ChunkNodeII[1][y][1] =  "obstruction/kyrosian_chunk_cube";           			
    		}
    		if (y != 16) {
            	ChunkNodeII[0][y][0] = "obstruction/kyrosian_chunk_cube";
        		
            	ChunkNodeII[0][y][1] = "obstruction/kyrosian_chunk_cube";    			
            	
    	    	ChunkNodeII[1][y][0] = "obstruction/kyrosian_chunk_cube";
    	    	
        	    ChunkNodeII[1][y][1] =  "obstruction/kyrosian_chunk_cube";      
    		}
	    	for (int z = 2; z < 1000; z++) {
	    		if (ChunkNodeII[0][y][z] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous Z.
	        		if (	ChunkNodeII[0][y][z-1] == "continuous/kyrosian_z_road" ||
	        				ChunkNodeII[0][y][z-1] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
	        				ChunkNodeII[0][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" ||
	        				ChunkNodeII[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_x" ||
	        				ChunkNodeII[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx" ||
	        				ChunkNodeII[0][y][z-1] == "continuous/kyrosian_4-way_road" ||
	        				ChunkNodeII[0][y][z-1] == "terminus/kyrosian_terminus_z" ) 
	        		{        			
	        			// Add appropriate pieces that connects to Z-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");
	        			
	        			// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeII[0][y][z-1] != "terminus/kyrosian_terminus_z"  ) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeII[0][y][z] = selectedPiece;
	        			
	        		}      		        		
	        		
	        		// If there is an obstruction from a previous Z.
	        		if (	ChunkNodeII[0][y][z-1] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add a pieces that starts a path from this node.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeII[0][y][z] = selectedPiece;
	        			
	        		}
	        					
	        		// If there is a disconnect from a previous Z.
	        		if (ChunkNodeII[0][y][z-1] == "terminus/kyrosian_terminus_negz" ||
	        				ChunkNodeII[0][y][z-1] == "terminus/kyrosian_terminus_x"||
	                		ChunkNodeII[0][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeII[0][y][z] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	

	    	for (int x = 2; x < 1000; x++) {
	    		if (ChunkNodeII[x][y][0] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous X.
	        		if (ChunkNodeII[x-1][y][0] == "continuous/kyrosian_x_road" ||
		         			ChunkNodeII[x-1][y][0] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
		         			ChunkNodeII[x-1][y][0] == "continuous/kyrosian_2-way_xtonegz_corner" ||
		         			ChunkNodeII[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_z" ||
		         			ChunkNodeII[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_negz" ||
		         			ChunkNodeII[x-1][y][0] == "continuous/kyrosian_4-way_road" ||
		         			ChunkNodeII[x-1][y][0] == "terminus/kyrosian_terminus_x" ) 
	        		{ 			
	        			// Add appropriate pieces that connects to X-1.
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			
						// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeII[x-1][y][0] != "terminus/kyrosian_terminus_x"  ) {
	    					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeII[x][y][0] = selectedPiece;
	        		}        		
	        		
	        		// If there is an obstruction from a previous X.
	        		if (	ChunkNodeII[x-1][y][0] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add appropriate pieces that DOESN'T connect to X-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeII[x][y][0] = selectedPiece;
	        		}
	        		       		
	        		// If there is a disconnect from a previous X.
	        		if (ChunkNodeII[x-1][y][0] == "terminus/kyrosian_terminus_negx" ||
	        				ChunkNodeII[x-1][y][0] == "terminus/kyrosian_terminus_z"||
	                		ChunkNodeII[x-1][y][0] == "continuous/kyrosian_2-way_ztox_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeII[x][y][0] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	
	    	for (int x = 1; x < 1000; x++) {
	        	for (int z = 1; z < 1000; z++) {	

	        		
	        		if (ChunkNodeII[x][y][z] == null) {
	            		
	        			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	        			int pieceSel;
	        			String selectedPiece;   			
	            		
	        			boolean ConnectionFromNegZ = (
		        				ChunkNodeII[x][y][z-1] == "terminus/kyrosian_terminus_z"	 	||
		            				
		            			ChunkNodeII[x][y][z-1] == "continuous/kyrosian_z_road" 			||
		
		                		ChunkNodeII[x][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" 	||
		                		ChunkNodeII[x][y][z-1] == "continuous/kyrosian_2-way_ztox_corner" 			||
		                		
		                        ChunkNodeII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_x"         ||
		                        ChunkNodeII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx"  ||
		                        ChunkNodeII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negz"  ||
		                        
		                        ChunkNodeII[x][y][z-1] == "continuous/kyrosian_4-way_road"
	                        );
	        			
	        			boolean ConnectionFromNegX = (
		    					ChunkNodeII[x-1][y][z] == "terminus/kyrosian_terminus_x" 	||
		        				
		                		ChunkNodeII[x-1][y][z] == "continuous/kyrosian_x_road" 		||
		
		                    	ChunkNodeII[x-1][y][z] == "continuous/kyrosian_2-way_xtonegz_corner" 			||
		                    	ChunkNodeII[x-1][y][z] == "continuous/kyrosian_2-way_negztonegx_corner" 	||
		                    		
		                        ChunkNodeII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_z"        		||
		                        ChunkNodeII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negx" 	 	||
		                        ChunkNodeII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negz" 	 	||
		                            
		                        ChunkNodeII[x-1][y][z] == "continuous/kyrosian_4-way_road"
	        				);
	        			
	        			boolean DisconnectionFromNegZ = (
		    					ChunkNodeII[x][y][z-1] == "terminus/kyrosian_terminus_negz"		||
		            			ChunkNodeII[x][y][z-1] == "terminus/kyrosian_terminus_x"			||
		            			ChunkNodeII[x][y][z-1] == "terminus/kyrosian_terminus_negx"		||
		            			
		            			ChunkNodeII[x][y][z-1] == "continuous/kyrosian_x_road" 				||
		            			
		                    	ChunkNodeII[x][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	||
		                    	ChunkNodeII[x][y][z-1] == "continuous/kyrosian_2-way_negxtoz_corner" 			||
		                    	
		                    	ChunkNodeII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_z" 
	                    	);
	        			
	        			boolean DisconnectionFromNegX = (
	        					ChunkNodeII[x-1][y][z] == "terminus/kyrosian_terminus_negx"		||
	                			ChunkNodeII[x-1][y][z] == "terminus/kyrosian_terminus_z"			||
	                        	ChunkNodeII[x-1][y][z] == "terminus/kyrosian_terminus_negz"		||
	                        	
	                        	ChunkNodeII[x-1][y][z] == "continuous/kyrosian_z_road" 						||
	                        	
	                        	ChunkNodeII[x-1][y][z] == "continuous/kyrosian_2-way_ztox_corner" 	||
	                            ChunkNodeII[x-1][y][z] == "continuous/kyrosian_2-way_negxtoz_corner" 			||                    	
	                        	
	                        	ChunkNodeII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_x" 
	        				);
	        			
	        			boolean ObstructionFromNegZ = (ChunkNodeII[x][y][z-1] == "obstruction/kyrosian_chunk_cube");
	        			
	        			boolean ObstructionFromNegX = (ChunkNodeII[x-1][y][z] == "obstruction/kyrosian_chunk_cube");
	        			
	        			if (ConnectionFromNegZ && ConnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_4-way_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_x");
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_z");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negxtoz_corner");   
	        			}
	        			
	        			if (ConnectionFromNegZ && DisconnectionFromNegX) {
	        				if (ChunkNodeII[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");       				
	        			}
	        			
	        			if (ConnectionFromNegZ && ObstructionFromNegX) {
	        				if (ChunkNodeII[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	            				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");        					
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");        		  
	        			}
	        			
	        			if (DisconnectionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeII[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			}
	        			
	        			if (DisconnectionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");        				
	        			}
	        			
	        			if (DisconnectionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");           				  
	        			}
	        			
	        			if (ObstructionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeII[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");        
	        				 
	        			}
	        			
	        			if(ObstructionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");      	        				   
	        			}
	        			
	        			if (ObstructionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");     			        				  
	        			}
	            		

	        			
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeII[x][y][z] = selectedPiece; 
	            		
	        		}   		        		    
	        	}
	    	}
    	}
	}

	public static void mapChunkNodesIII() {
  	Skyspace.LOGGER.info("Mapping Q3 Kyrosian Maze!");
    	

    	for (int y = 0; y < 32; y++) {		
    		if (y == 16) {
            	ChunkNodeIII[0][y][0] = "continuous/kyrosian_4-way_road";
        		
            	ChunkNodeIII[0][y][1] = "continuous/kyrosian_z_road";    			
            	
    	    	ChunkNodeIII[1][y][0] = "continuous/kyrosian_x_road";
    	    	
        	    ChunkNodeIII[1][y][1] =  "obstruction/kyrosian_chunk_cube";           			
    		}
    		if (y != 16) {
            	ChunkNodeIII[0][y][0] = "obstruction/kyrosian_chunk_cube";
        		
            	ChunkNodeIII[0][y][1] = "obstruction/kyrosian_chunk_cube";    			
            	
    	    	ChunkNodeIII[1][y][0] = "obstruction/kyrosian_chunk_cube";
    	    	
        	    ChunkNodeIII[1][y][1] =  "obstruction/kyrosian_chunk_cube";      
    		}
	    	for (int z = 2; z < 1000; z++) {
	    		if (ChunkNodeIII[0][y][z] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous Z.
	        		if (	ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_z_road" ||
	        				ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
	        				ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" ||
	        				ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_x" ||
	        				ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx" ||
	        				ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_4-way_road" ||
	        				ChunkNodeIII[0][y][z-1] == "terminus/kyrosian_terminus_z" ) 
	        		{        			
	        			// Add appropriate pieces that connects to Z-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");
	        			
	        			// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeIII[0][y][z-1] != "terminus/kyrosian_terminus_z"  ) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIII[0][y][z] = selectedPiece;
	        			
	        		}      		        		
	        		
	        		// If there is an obstruction from a previous Z.
	        		if (	ChunkNodeIII[0][y][z-1] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add a pieces that starts a path from this node.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIII[0][y][z] = selectedPiece;
	        			
	        		}
	        					
	        		// If there is a disconnect from a previous Z.
	        		if (ChunkNodeIII[0][y][z-1] == "terminus/kyrosian_terminus_negz" ||
	        				ChunkNodeIII[0][y][z-1] == "terminus/kyrosian_terminus_x"||
	                		ChunkNodeIII[0][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeIII[0][y][z] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	

	    	for (int x = 2; x < 1000; x++) {
	    		if (ChunkNodeIII[x][y][0] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous X.
	        		if (ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_x_road" ||
		         			ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
		         			ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_2-way_xtonegz_corner" ||
		         			ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_z" ||
		         			ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_negz" ||
		         			ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_4-way_road" ||
		         			ChunkNodeIII[x-1][y][0] == "terminus/kyrosian_terminus_x" ) 
	        		{ 			
	        			// Add appropriate pieces that connects to X-1.
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			
						// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeIII[x-1][y][0] != "terminus/kyrosian_terminus_x"  ) {
	    					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIII[x][y][0] = selectedPiece;
	        		}        		
	        		
	        		// If there is an obstruction from a previous X.
	        		if (	ChunkNodeIII[x-1][y][0] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add appropriate pieces that DOESN'T connect to X-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIII[x][y][0] = selectedPiece;
	        		}
	        		       		
	        		// If there is a disconnect from a previous X.
	        		if (ChunkNodeIII[x-1][y][0] == "terminus/kyrosian_terminus_negx" ||
	        				ChunkNodeIII[x-1][y][0] == "terminus/kyrosian_terminus_z"||
	                		ChunkNodeIII[x-1][y][0] == "continuous/kyrosian_2-way_ztox_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeIII[x][y][0] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	
	    	for (int x = 1; x < 1000; x++) {
	        	for (int z = 1; z < 1000; z++) {	

	        		
	        		if (ChunkNodeIII[x][y][z] == null) {
	            		
	        			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	        			int pieceSel;
	        			String selectedPiece;   			
	            		
	        			boolean ConnectionFromNegZ = (
		        				ChunkNodeIII[x][y][z-1] == "terminus/kyrosian_terminus_z"	 	||
		            				
		            			ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_z_road" 			||
		
		                		ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" 	||
		                		ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_2-way_ztox_corner" 			||
		                		
		                        ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_x"         ||
		                        ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx"  ||
		                        ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negz"  ||
		                        
		                        ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_4-way_road"
	                        );
	        			
	        			boolean ConnectionFromNegX = (
		    					ChunkNodeIII[x-1][y][z] == "terminus/kyrosian_terminus_x" 	||
		        				
		                		ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_x_road" 		||
		
		                    	ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_2-way_xtonegz_corner" 			||
		                    	ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_2-way_negztonegx_corner" 	||
		                    		
		                        ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_z"        		||
		                        ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negx" 	 	||
		                        ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negz" 	 	||
		                            
		                        ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_4-way_road"
	        				);
	        			
	        			boolean DisconnectionFromNegZ = (
		    					ChunkNodeIII[x][y][z-1] == "terminus/kyrosian_terminus_negz"		||
		            			ChunkNodeIII[x][y][z-1] == "terminus/kyrosian_terminus_x"			||
		            			ChunkNodeIII[x][y][z-1] == "terminus/kyrosian_terminus_negx"		||
		            			
		            			ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_x_road" 				||
		            			
		                    	ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	||
		                    	ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_2-way_negxtoz_corner" 			||
		                    	
		                    	ChunkNodeIII[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_z" 
	                    	);
	        			
	        			boolean DisconnectionFromNegX = (
	        					ChunkNodeIII[x-1][y][z] == "terminus/kyrosian_terminus_negx"		||
	                			ChunkNodeIII[x-1][y][z] == "terminus/kyrosian_terminus_z"			||
	                        	ChunkNodeIII[x-1][y][z] == "terminus/kyrosian_terminus_negz"		||
	                        	
	                        	ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_z_road" 						||
	                        	
	                        	ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_2-way_ztox_corner" 	||
	                            ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_2-way_negxtoz_corner" 			||                    	
	                        	
	                        	ChunkNodeIII[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_x" 
	        				);
	        			
	        			boolean ObstructionFromNegZ = (ChunkNodeIII[x][y][z-1] == "obstruction/kyrosian_chunk_cube");
	        			
	        			boolean ObstructionFromNegX = (ChunkNodeIII[x-1][y][z] == "obstruction/kyrosian_chunk_cube");
	        			
	        			if (ConnectionFromNegZ && ConnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_4-way_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_x");
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_z");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negxtoz_corner");   
	        			}
	        			
	        			if (ConnectionFromNegZ && DisconnectionFromNegX) {
	        				if (ChunkNodeIII[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");       				
	        			}
	        			
	        			if (ConnectionFromNegZ && ObstructionFromNegX) {
	        				if (ChunkNodeIII[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	            				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");        					
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");        		  
	        			}
	        			
	        			if (DisconnectionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeIII[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			}
	        			
	        			if (DisconnectionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");        				
	        			}
	        			
	        			if (DisconnectionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");           				  
	        			}
	        			
	        			if (ObstructionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeIII[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");        
	        				 
	        			}
	        			
	        			if(ObstructionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");      	        				   
	        			}
	        			
	        			if (ObstructionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");     			        				  
	        			}
	            		

	        			
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIII[x][y][z] = selectedPiece; 
	            		
	        		}   		        		    
	        	}
	    	}
    	}
	}

	public static void mapChunkNodesIV() {
  	Skyspace.LOGGER.info("Mapping Q4 Kyrosian Maze!");
    	

    	for (int y = 0; y < 32; y++) {		
    		if (y == 16) {
            	ChunkNodeIV[0][y][0] = "continuous/kyrosian_4-way_road";
        		
            	ChunkNodeIV[0][y][1] = "continuous/kyrosian_z_road";    			
            	
    	    	ChunkNodeIV[1][y][0] = "continuous/kyrosian_x_road";
    	    	
        	    ChunkNodeIV[1][y][1] =  "obstruction/kyrosian_chunk_cube";           			
    		}
    		if (y != 16) {
            	ChunkNodeIV[0][y][0] = "obstruction/kyrosian_chunk_cube";
        		
            	ChunkNodeIV[0][y][1] = "obstruction/kyrosian_chunk_cube";    			
            	
    	    	ChunkNodeIV[1][y][0] = "obstruction/kyrosian_chunk_cube";
    	    	
        	    ChunkNodeIV[1][y][1] =  "obstruction/kyrosian_chunk_cube";      
    		}
	    	for (int z = 2; z < 1000; z++) {
	    		if (ChunkNodeIV[0][y][z] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous Z.
	        		if (	ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_z_road" ||
	        				ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
	        				ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" ||
	        				ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_x" ||
	        				ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx" ||
	        				ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_4-way_road" ||
	        				ChunkNodeIV[0][y][z-1] == "terminus/kyrosian_terminus_z" ) 
	        		{        			
	        			// Add appropriate pieces that connects to Z-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");
	        			
	        			// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeIV[0][y][z-1] != "terminus/kyrosian_terminus_z"  ) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIV[0][y][z] = selectedPiece;
	        			
	        		}      		        		
	        		
	        		// If there is an obstruction from a previous Z.
	        		if (	ChunkNodeIV[0][y][z-1] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add a pieces that starts a path from this node.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIV[0][y][z] = selectedPiece;
	        			
	        		}
	        					
	        		// If there is a disconnect from a previous Z.
	        		if (ChunkNodeIV[0][y][z-1] == "terminus/kyrosian_terminus_negz" ||
	        				ChunkNodeIV[0][y][z-1] == "terminus/kyrosian_terminus_x"||
	                		ChunkNodeIV[0][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeIV[0][y][z] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	

	    	for (int x = 2; x < 1000; x++) {
	    		if (ChunkNodeIV[x][y][0] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous X.
	        		if (ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_x_road" ||
		         			ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
		         			ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_2-way_xtonegz_corner" ||
		         			ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_z" ||
		         			ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_negz" ||
		         			ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_4-way_road" ||
		         			ChunkNodeIV[x-1][y][0] == "terminus/kyrosian_terminus_x" ) 
	        		{ 			
	        			// Add appropriate pieces that connects to X-1.
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			
						// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNodeIV[x-1][y][0] != "terminus/kyrosian_terminus_x"  ) {
	    					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIV[x][y][0] = selectedPiece;
	        		}        		
	        		
	        		// If there is an obstruction from a previous X.
	        		if (	ChunkNodeIV[x-1][y][0] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add appropriate pieces that DOESN'T connect to X-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIV[x][y][0] = selectedPiece;
	        		}
	        		       		
	        		// If there is a disconnect from a previous X.
	        		if (ChunkNodeIV[x-1][y][0] == "terminus/kyrosian_terminus_negx" ||
	        				ChunkNodeIV[x-1][y][0] == "terminus/kyrosian_terminus_z"||
	                		ChunkNodeIV[x-1][y][0] == "continuous/kyrosian_2-way_ztox_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNodeIV[x][y][0] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	
	    	for (int x = 1; x < 1000; x++) {
	        	for (int z = 1; z < 1000; z++) {	

	        		
	        		if (ChunkNodeIV[x][y][z] == null) {
	            		
	        			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	        			int pieceSel;
	        			String selectedPiece;   			
	            		
	        			boolean ConnectionFromNegZ = (
		        				ChunkNodeIV[x][y][z-1] == "terminus/kyrosian_terminus_z"	 	||
		            				
		            			ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_z_road" 			||
		
		                		ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" 	||
		                		ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_2-way_ztox_corner" 			||
		                		
		                        ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_x"         ||
		                        ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx"  ||
		                        ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negz"  ||
		                        
		                        ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_4-way_road"
	                        );
	        			
	        			boolean ConnectionFromNegX = (
		    					ChunkNodeIV[x-1][y][z] == "terminus/kyrosian_terminus_x" 	||
		        				
		                		ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_x_road" 		||
		
		                    	ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_2-way_xtonegz_corner" 			||
		                    	ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_2-way_negztonegx_corner" 	||
		                    		
		                        ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_z"        		||
		                        ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negx" 	 	||
		                        ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negz" 	 	||
		                            
		                        ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_4-way_road"
	        				);
	        			
	        			boolean DisconnectionFromNegZ = (
		    					ChunkNodeIV[x][y][z-1] == "terminus/kyrosian_terminus_negz"		||
		            			ChunkNodeIV[x][y][z-1] == "terminus/kyrosian_terminus_x"			||
		            			ChunkNodeIV[x][y][z-1] == "terminus/kyrosian_terminus_negx"		||
		            			
		            			ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_x_road" 				||
		            			
		                    	ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	||
		                    	ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_2-way_negxtoz_corner" 			||
		                    	
		                    	ChunkNodeIV[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_z" 
	                    	);
	        			
	        			boolean DisconnectionFromNegX = (
	        					ChunkNodeIV[x-1][y][z] == "terminus/kyrosian_terminus_negx"		||
	                			ChunkNodeIV[x-1][y][z] == "terminus/kyrosian_terminus_z"			||
	                        	ChunkNodeIV[x-1][y][z] == "terminus/kyrosian_terminus_negz"		||
	                        	
	                        	ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_z_road" 						||
	                        	
	                        	ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_2-way_ztox_corner" 	||
	                            ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_2-way_negxtoz_corner" 			||                    	
	                        	
	                        	ChunkNodeIV[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_x" 
	        				);
	        			
	        			boolean ObstructionFromNegZ = (ChunkNodeIV[x][y][z-1] == "obstruction/kyrosian_chunk_cube");
	        			
	        			boolean ObstructionFromNegX = (ChunkNodeIV[x-1][y][z] == "obstruction/kyrosian_chunk_cube");
	        			
	        			if (ConnectionFromNegZ && ConnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_4-way_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_x");
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_z");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negxtoz_corner");   
	        			}
	        			
	        			if (ConnectionFromNegZ && DisconnectionFromNegX) {
	        				if (ChunkNodeIV[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");       				
	        			}
	        			
	        			if (ConnectionFromNegZ && ObstructionFromNegX) {
	        				if (ChunkNodeIV[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	            				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");        					
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");        		  
	        			}
	        			
	        			if (DisconnectionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeIV[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			}
	        			
	        			if (DisconnectionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");        				
	        			}
	        			
	        			if (DisconnectionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");           				  
	        			}
	        			
	        			if (ObstructionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNodeIV[x-1][y][z] != "terminus/kyrosian_terminus_x") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");        
	        				 
	        			}
	        			
	        			if(ObstructionFromNegZ && DisconnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");      	        				   
	        			}
	        			
	        			if (ObstructionFromNegZ && ObstructionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");     			        				  
	        			}
	            		

	        			
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNodeIV[x][y][z] = selectedPiece; 
	            		
	        		}   		        		    
	        	}
	    	}
    	}
	}

}
