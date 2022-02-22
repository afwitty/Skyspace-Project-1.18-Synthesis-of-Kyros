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
    public static String[][][] ChunkNode = new String[MazeSize][32][MazeSize]; //1875000
	static int dirSel;
	static int elev;

//    public KyrosianMaze(Codec<JigsawConfiguration> codec) {
//        // Create the pieces layout of the structure and give it to the game
//        super(codec, KyrosianMaze::createPiecesGenerator, PostPlacementProcessor.NONE);
//    }
	
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
    
    protected boolean isFeatureChunk(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context, GenericJigsawStructureCodeConfig config) {
//        ChunkPos chunkPos = context.chunkPos();
//
//        // do cheaper checks first
//        if(super.isFeatureChunk(context, config)) {
//
//            // make sure land is open enough for city
//            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
//            for (int curChunkX = chunkPos.x - 1; curChunkX <= chunkPos.x + 1; curChunkX++) {
//                for (int curChunkZ = chunkPos.z - 1; curChunkZ <= chunkPos.z + 1; curChunkZ++) {
//                    mutable.set(curChunkX << 4, context.chunkGenerator().getSeaLevel() + 10, curChunkZ << 4);
//                    NoiseColumn blockView = context.chunkGenerator().getBaseColumn(mutable.getX(), mutable.getZ(), context.heightAccessor());
//                    int minValidSpace = 65;
//                    int maxHeight = Math.min(GeneralUtils.getMaxTerrainLimit(context.chunkGenerator()), context.chunkGenerator().getSeaLevel() + minValidSpace);
//
//                    while(mutable.getY() < maxHeight) {
//                        BlockState state = blockView.getBlock(mutable.getY());
//                        if(!state.isAir()) {
//                            return false;
//                        }
//                        mutable.move(Direction.UP);
//                    }
//                }
//            }
//        }
//        else {
//            return false;
//        }

        return true;
    }

    public Optional<PieceGenerator<NoneFeatureConfiguration>> generatePieces(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context, GenericJigsawStructureCodeConfig config, int x, int y, int z) {

        		
        		//new BlockPos(context.chunkPos().getMinBlockX(), context.chunkGenerator().getSeaLevel(), context.chunkPos().getMinBlockZ());

        ResourceLocation structureID = ForgeRegistries.STRUCTURE_FEATURES.getKey(this);
        
		if (context.chunkPos().x >= 18 && context.chunkPos().z >= 18 && context.chunkPos().x < 18 + MazeSize-2 && context.chunkPos().z < 18 + MazeSize-2) {
	    	if (ChunkNode[x][y][z] != null) {
	        	Optional<PieceGenerator<NoneFeatureConfiguration>> piece;
	        	piece = PiecesGenerator.GenerateII(context, config, structureID, ChunkNode[x][y][z]);	
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
    
    /**
     * The StructureSpawnListGatherEvent event allows us to have mobs that spawn naturally over time in our structure.
     * No other mobs will spawn in the structure of the same entity classification.
     * The reason you want to match the classifications is so that your structure's mob
     * will contribute to that classification's cap. Otherwise, it may cause a runaway
     * spawning of the mob that will never stop.
     *
     * We use Lazy so that if you classload this class before you register your entities, you will not crash.
     * Instead, the field and the entities inside will only be referenced when StructureSpawnListGatherEvent
     * fires much later after entity registration.
     */
//    private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_MONSTERS = Lazy.of(() -> ImmutableList.of(
//            new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 100, 4, 9),
//            new MobSpawnSettings.SpawnerData(EntityType.VINDICATOR, 100, 4, 9)
//    ));
//    private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_CREATURES = Lazy.of(() -> ImmutableList.of(
//            new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 30, 10, 15),
//            new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 100, 1, 2)
//    ));
//
//    // Hooked up in StructureTutorialMain. You can move this elsewhere or change it up.
//    public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
//        if(event.getStructure() == SSStructures.KYROSIAN_MAZE.get()) {
//            event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS.get());
//            event.addEntitySpawns(MobCategory.CREATURE, STRUCTURE_CREATURES.get());
//        }
//    }

    /*
     * This is where extra checks can be done to determine if the structure can spawn here.
     * This only needs to be overridden if you're adding additional spawn conditions.
     *
     * Fun fact, if you set your structure separation/spacing to be 0/1, you can use
     * isFeatureChunk to return true only if certain chunk coordinates are passed in
     * which allows you to spawn structures only at certain coordinates in the world.
     *
     * Basically, this method is used for determining if the land is at a suitable height,
     * if certain other structures are too close or not, or some other restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village.
     * (Bedrock Edition seems to not have the same check)
     *
     * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
     * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
     * Then loop through it and look for land with air above it and set blockpos's Y value to it.
     * Make sure to set the final boolean in JigsawPlacement.addPieces to false so
     * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
     *
     * Also, please for the love of god, do not do dimension checking here. If you do and
     * another mod's dimension is trying to spawn your structure, the locate
     * command will make minecraft hang forever and break the game.
     *
     * Instead, use the addDimensionalSpacing method in StructureTutorialMain class.
     * If you check for the dimension there and do not add your structure's
     * spacing into the chunk generator, the structure will not spawn in that dimension!
     */
    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        // Grab height of land. Will stop at first non-air block.
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

        // Combine the column of blocks with land height and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

        // Check if the spot is valid for our structure. This is just as another method for cleanness.
        // Returning an empty optional tells the game to skip this spot as it will not generate the structure.
        if (!KyrosianMaze.isFeatureChunk(context)) {
            return Optional.empty();
        }
        
        /*
         * Note, you are always free to make your own JigsawPlacement class and implementation of how the structure
         * should generate. It is tricky but extremely powerful if you are doing something that vanilla's jigsaw system cannot do.
         * Such as for example, forcing 3 pieces to always spawn every time, limiting how often a piece spawns, or remove the intersection limitation of pieces.
         *
         * An example of a custom JigsawPlacement.addPieces in action can be found here (warning, it is using Mojmap mappings):
         * https://github.com/TelepathicGrunt/RepurposedStructures/blob/1.18/src/main/java/com/telepathicgrunt/repurposedstructures/world/structures/pieces/PieceLimitedJigsawManager.java
         */

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        
        /* 
         * MEMORY NEXUS
         */
        
//		if (context.chunkPos().x < 18 && context.chunkPos().z < 18 && context.chunkPos().x > -19 && context.chunkPos().z > -19) {
//			return generateMemoryNexus(context, context.chunkPos().x, context.chunkPos().z);
//		} 
        
        /*
         *  QUADRANT I
         */
		int cnxi =context.chunkPos().x - 18;
		int cnzi = context.chunkPos().z - 18;
		
		Skyspace.LOGGER.info("Create Pieces Generator Called!");
		
		if (context.chunkPos().x >= 18 && context.chunkPos().z >= 18 && context.chunkPos().x < 18 + MazeSize-2 && context.chunkPos().z < 18 + MazeSize-2) {
				return generateKyrosianMaze(context, cnxi, 16, cnzi, ChunkNode[cnxi][16][cnzi]);
		}
        
        // End of Quadrant I
        
        
        else {
        	return Optional.empty();
        }
    }

	public static void mapChunkNodes() {
  	Skyspace.LOGGER.info("Mapping Kyrosian Maze!");
    	

    	for (int y = 0; y < 28; y++) {
    		
    		if (y == 16) {
            	ChunkNode[0][y][0] = "continuous/kyrosian_4-way_road";
        		
            	ChunkNode[0][y][1] = "continuous/kyrosian_z_road";    			
            	
    	    	ChunkNode[1][y][0] = "continuous/kyrosian_x_road";
    	    	
        	    ChunkNode[1][y][1] =  "obstruction/kyrosian_chunk_cube";           			
    		}
    		if (y != 16) {
            	ChunkNode[0][y][0] = "obstruction/kyrosian_chunk_cube";
        		
            	ChunkNode[0][y][1] = "obstruction/kyrosian_chunk_cube";    			
            	
    	    	ChunkNode[1][y][0] = "obstruction/kyrosian_chunk_cube";
    	    	
        	    ChunkNode[1][y][1] =  "obstruction/kyrosian_chunk_cube";      
    		}
	    	for (int z = 2; z < 1000; z++) {
	    		if (ChunkNode[0][y][z] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous Z.
	        		if (	ChunkNode[0][y][z-1] == "continuous/kyrosian_z_road" ||
	        				ChunkNode[0][y][z-1] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
	        				ChunkNode[0][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" ||
	        				ChunkNode[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_x" ||
	        				ChunkNode[0][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx" ||
	        				ChunkNode[0][y][z-1] == "continuous/kyrosian_4-way_road" ||
	        				ChunkNode[0][y][z-1] == "terminus/kyrosian_terminus_z" ) 
	        		{        			
	        			// Add appropriate pieces that connects to Z-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");
	        			
	        			// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNode[0][y][z-1] != "terminus/kyrosian_terminus_z"  ) {
	        				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNode[0][y][z] = selectedPiece;
	        			
	        		}      		        		
	        		
	        		// If there is an obstruction from a previous Z.
	        		if (	ChunkNode[0][y][z-1] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add a pieces that starts a path from this node.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNode[0][y][z] = selectedPiece;
	        			
	        		}
	        					
	        		// If there is a disconnect from a previous Z.
	        		if (ChunkNode[0][y][z-1] == "terminus/kyrosian_terminus_negz" ||
	        				ChunkNode[0][y][z-1] == "terminus/kyrosian_terminus_x"||
	                		ChunkNode[0][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNode[0][y][z] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	

	    	for (int x = 2; x < 1000; x++) {
	    		if (ChunkNode[x][y][0] == null) {
	    			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	    			int pieceSel;
	    			String selectedPiece;   			
	    			
	    			// If there is a connection from a previous X.
	        		if (ChunkNode[x-1][y][0] == "continuous/kyrosian_x_road" ||
		         			ChunkNode[x-1][y][0] == "continuous/kyrosian_2-way_negxtonegx_corner" ||
		         			ChunkNode[x-1][y][0] == "continuous/kyrosian_2-way_xtonegz_corner" ||
		         			ChunkNode[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_z" ||
		         			ChunkNode[x-1][y][0] == "continuous/kyrosian_3-way_road_facing_negz" ||
		         			ChunkNode[x-1][y][0] == "continuous/kyrosian_4-way_road" ||
		         			ChunkNode[x-1][y][0] == "terminus/kyrosian_terminus_x" ) 
	        		{ 			
	        			// Add appropriate pieces that connects to X-1.
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_x_road");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_ztox_corner");
						availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negz");
	        			
						// If the previous piece is a terminus, do not include another terminus.
	        			if ( ChunkNode[x-1][y][0] != "terminus/kyrosian_terminus_x"  ) {
	    					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negx");
	        			}
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNode[x][y][0] = selectedPiece;
	        		}        		
	        		
	        		// If there is an obstruction from a previous X.
	        		if (	ChunkNode[x-1][y][0] == "obstruction/kyrosian_chunk_cube")  {
	        			
	        			// Add appropriate pieces that DOESN'T connect to X-1.
	        			availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_xtonegz_corner");
	        			availablePieces = ArrayUtils.add(availablePieces, "obstruction/kyrosian_chunk_cube");			
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_z");	     
	        			availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_x");	
	        			
	        			// Randomly choose a piece and set that node into a selected piece.
	        			pieceSel = rand.nextInt(availablePieces.length);
	        			selectedPiece = availablePieces[pieceSel];
	        			ChunkNode[x][y][0] = selectedPiece;
	        		}
	        		       		
	        		// If there is a disconnect from a previous X.
	        		if (ChunkNode[x-1][y][0] == "terminus/kyrosian_terminus_negx" ||
	        				ChunkNode[x-1][y][0] == "terminus/kyrosian_terminus_z"||
	                		ChunkNode[x-1][y][0] == "continuous/kyrosian_2-way_ztox_corner"	) {
	        			
	        			// This one is a Chunk Cube.
	        			ChunkNode[x][y][0] = "obstruction/kyrosian_chunk_cube";   	   			
	        		}
	
	    		}
	    	}
	    	
	    	for (int x = 1; x < 1000; x++) {
	        	for (int z = 1; z < 1000; z++) {	

	        		
	        		if (ChunkNode[x][y][z] == null) {
	            		
	        			String[] availablePieces = { /* Instead of rerolling from this array recursively, trim down what pieces the computer can select and then pick a piece. */ };
	        			int pieceSel;
	        			String selectedPiece;   			
	            		
	        			boolean ConnectionFromNegZ = (
		        				ChunkNode[x][y][z-1] == "terminus/kyrosian_terminus_z"	 	||
		            				
		            			ChunkNode[x][y][z-1] == "continuous/kyrosian_z_road" 			||
		
		                		ChunkNode[x][y][z-1] == "continuous/kyrosian_2-way_xtonegz_corner" 	||
		                		ChunkNode[x][y][z-1] == "continuous/kyrosian_2-way_ztox_corner" 			||
		                		
		                        ChunkNode[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_x"         ||
		                        ChunkNode[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negx"  ||
		                        ChunkNode[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_negz"  ||
		                        
		                        ChunkNode[x][y][z-1] == "continuous/kyrosian_4-way_road"
	                        );
	        			
	        			boolean ConnectionFromNegX = (
		    					ChunkNode[x-1][y][z] == "terminus/kyrosian_terminus_x" 	||
		        				
		                		ChunkNode[x-1][y][z] == "continuous/kyrosian_x_road" 		||
		
		                    	ChunkNode[x-1][y][z] == "continuous/kyrosian_2-way_xtonegz_corner" 			||
		                    	ChunkNode[x-1][y][z] == "continuous/kyrosian_2-way_negztonegx_corner" 	||
		                    		
		                        ChunkNode[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_z"        		||
		                        ChunkNode[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negx" 	 	||
		                        ChunkNode[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_negz" 	 	||
		                            
		                        ChunkNode[x-1][y][z] == "continuous/kyrosian_4-way_road"
	        				);
	        			
	        			boolean DisconnectionFromNegZ = (
		    					ChunkNode[x][y][z-1] == "terminus/kyrosian_terminus_negz"		||
		            			ChunkNode[x][y][z-1] == "terminus/kyrosian_terminus_x"			||
		            			ChunkNode[x][y][z-1] == "terminus/kyrosian_terminus_negx"		||
		            			
		            			ChunkNode[x][y][z-1] == "continuous/kyrosian_x_road" 				||
		            			
		                    	ChunkNode[x][y][z-1] == "continuous/kyrosian_2-way_negztonegx_corner"	||
		                    	ChunkNode[x][y][z-1] == "continuous/kyrosian_2-way_negxtoz_corner" 			||
		                    	
		                    	ChunkNode[x][y][z-1] == "continuous/kyrosian_3-way_road_facing_z" 
	                    	);
	        			
	        			boolean DisconnectionFromNegX = (
	        					ChunkNode[x-1][y][z] == "terminus/kyrosian_terminus_negx"		||
	                			ChunkNode[x-1][y][z] == "terminus/kyrosian_terminus_z"			||
	                        	ChunkNode[x-1][y][z] == "terminus/kyrosian_terminus_negz"		||
	                        	
	                        	ChunkNode[x-1][y][z] == "continuous/kyrosian_z_road" 						||
	                        	
	                        	ChunkNode[x-1][y][z] == "continuous/kyrosian_2-way_ztox_corner" 	||
	                            ChunkNode[x-1][y][z] == "continuous/kyrosian_2-way_negxtoz_corner" 			||                    	
	                        	
	                        	ChunkNode[x-1][y][z] == "continuous/kyrosian_3-way_road_facing_x" 
	        				);
	        			
	        			boolean ObstructionFromNegZ = (ChunkNode[x][y][z-1] == "obstruction/kyrosian_chunk_cube");
	        			
	        			boolean ObstructionFromNegX = (ChunkNode[x-1][y][z] == "obstruction/kyrosian_chunk_cube");
	        			
	        			if (ConnectionFromNegZ && ConnectionFromNegX) {
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_4-way_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_x");
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_z");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negxtoz_corner");   
	        			}
	        			
	        			if (ConnectionFromNegZ && DisconnectionFromNegX) {
	        				if (ChunkNode[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	        					availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");
	        				}
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");       				
	        			}
	        			
	        			if (ConnectionFromNegZ && ObstructionFromNegX) {
	        				if (ChunkNode[x][y][z-1] != "terminus/kyrosian_terminus_z") {
	            				availablePieces = ArrayUtils.add(availablePieces, "terminus/kyrosian_terminus_negz");        					
	        				}
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_z_road");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_2-way_negztonegx_corner");
	        				
	        				availablePieces = ArrayUtils.add(availablePieces, "continuous/kyrosian_3-way_road_facing_negx");        		  
	        			}
	        			
	        			if (DisconnectionFromNegZ && ConnectionFromNegX) {
	        				if (ChunkNode[x-1][y][z] != "terminus/kyrosian_terminus_x") {
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
	        				if (ChunkNode[x-1][y][z] != "terminus/kyrosian_terminus_x") {
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
	        			ChunkNode[x][y][z] = selectedPiece; 
	            		
	        		}   		        		    
	        	}
	    	}
    	}
    }
//    public static Optional<PieceGenerator<JigsawConfiguration>> generateMemoryNexus(
//			Context<JigsawConfiguration> context, int x, int z) {
//		// TODO Auto-generated method stub
//    	
//    	
//		return null;
//	}
      
    // TODO: Try mapping the Chunk Nodes before initializing the game.
    public static Optional<PieceGenerator<JigsawConfiguration>> generateKyrosianMaze(PieceGeneratorSupplier.Context<JigsawConfiguration> context,  int x, int y, int z, String chunkNodeRef) {
    	if (ChunkNode[x][y][z] != null) {
        	Optional<PieceGenerator<JigsawConfiguration>> piece;
        	piece = PiecesGenerator.Generate(context, ChunkNode[x][y][z]);	
        	return piece;
    	}
    	else {
    		return Optional.empty();
    	}
    }


}
