package com.cryotron.skyspaceproject.worldgen.structures;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.util.Mutable;
import com.cryotron.skyspaceproject.worldgen.structures.codeconfigs.GenericJigsawStructureCodeConfig;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.Level;

public class KyrosianNexus extends StructureFeature<JigsawConfiguration> {
    public KyrosianNexus(Codec<JigsawConfiguration> codec) {
        // Create the pieces layout of the structure and give it to the game
        super(codec, KyrosianNexus::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
    	
    	if ( 
    		 // Entrance Starting Points
    		 (context.chunkPos().x == 14 && context.chunkPos().z == 14)   ||	// Quadrant I
    	  	 (context.chunkPos().x == -15 && context.chunkPos().z == 14)  || 	// Quadrant II
    		 (context.chunkPos().x == 14 && context.chunkPos().z == -15)  || 	// Quadrant III
    		 (context.chunkPos().x == -15 && context.chunkPos().z == -15) ||	// Quadrant IV
    		 
    		// Quadrant I Paths
     		(context.chunkPos().x == 15 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == 16 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == 17 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == 18 && context.chunkPos().z == 14)  ||		// Z-to-X Corner
     		(context.chunkPos().x == 18 && context.chunkPos().z == 15)  ||		// Z-Road
     		(context.chunkPos().x == 18 && context.chunkPos().z == 16)  ||		// Z-Downstairs
     		(context.chunkPos().x == 18 && context.chunkPos().z == 17)  ||		// Z-Road Below -15
     		(context.chunkPos().x == 14 && context.chunkPos().z == 15)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == 16)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == 17)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == 18)  || 	// nZ-to-nX Corner
     		(context.chunkPos().x == 15 && context.chunkPos().z == 18)  || 	// X-Road
     		(context.chunkPos().x == 16 && context.chunkPos().z == 18)  || 	// X-Downstairs
     		(context.chunkPos().x == 17 && context.chunkPos().z == 18)  || 	// X-Road Below -15
     		
    		// Quadrant II Paths
     		(context.chunkPos().x == -16 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == -17 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == -18 && context.chunkPos().z == 14)  ||		// X-Road
     		(context.chunkPos().x == -19 && context.chunkPos().z == 14)  ||		// nX-to-Z Corner
     		(context.chunkPos().x == -19 && context.chunkPos().z == 15)  ||		// Z-Road
     		(context.chunkPos().x == -19 && context.chunkPos().z == 16)  ||		// Z-Downstairs
     		(context.chunkPos().x == -19 && context.chunkPos().z == 17)  ||		// Z-Road Below -15
     		(context.chunkPos().x == -15 && context.chunkPos().z == 15)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == 16)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == 17)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == 18)  || 		// X-to-nZ Corner
     		(context.chunkPos().x == -16 && context.chunkPos().z == 18)  || 		// X-Road
     		(context.chunkPos().x == -17 && context.chunkPos().z == 18)  || 		// X-Downstairs
     		(context.chunkPos().x == -18 && context.chunkPos().z == 18)  || 		// X-Road Below -15     	
     		
    		// Quadrant III Paths
     		(context.chunkPos().x == 15 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == 16 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == 17 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == 18 && context.chunkPos().z == -15)  ||		// X-to-nZ Corner
     		(context.chunkPos().x == 18 && context.chunkPos().z == -16)  ||		// Z-Road
     		(context.chunkPos().x == 18 && context.chunkPos().z == -17)  ||		// Z-Downstairs
     		(context.chunkPos().x == 18 && context.chunkPos().z == -18)  ||		// Z-Road Below -15
     		(context.chunkPos().x == 14 && context.chunkPos().z == -16)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == -17)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == -18)  ||		// Z-Road
     		(context.chunkPos().x == 14 && context.chunkPos().z == -19)  || 		// nX-to-Z Corner
     		(context.chunkPos().x == 15 && context.chunkPos().z == -19)  || 		// X-Road
     		(context.chunkPos().x == 16 && context.chunkPos().z == -19)  || 		// X-Downstairs
     		(context.chunkPos().x == 17 && context.chunkPos().z == -19)  || 		// X-Road Below -15
     		
     		// Quadrant IV Paths
     		(context.chunkPos().x == -16 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == -17 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == -18 && context.chunkPos().z == -15)  ||		// X-Road
     		(context.chunkPos().x == -19 && context.chunkPos().z == -15)  ||		// Z-to-X Corner
     		(context.chunkPos().x == -19 && context.chunkPos().z == -16)  ||		// Z-Road
     		(context.chunkPos().x == -19 && context.chunkPos().z == -17)  ||		// Z-Downstairs
     		(context.chunkPos().x == -19 && context.chunkPos().z == -18)  ||		// Z-Road Below -15
     		(context.chunkPos().x == -15 && context.chunkPos().z == -16)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == -17)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == -18)  ||		// Z-Road
     		(context.chunkPos().x == -15 && context.chunkPos().z == -19)  || 		// nZ-to-nX Corner
     		(context.chunkPos().x == -16 && context.chunkPos().z == -19)  || 		// X-Road
     		(context.chunkPos().x == -17 && context.chunkPos().z == -19)  || 		// X-Downstairs
     		(context.chunkPos().x == -18 && context.chunkPos().z == -19)   		// X-Road Below -15    		 
    		 
    		 
    		 )    		{
    		
    		// Initialize to the 4-way and adjust the seeds with blockpos.
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(15, 1, 15);	        
	        if (context.chunkPos().x == -15 && context.chunkPos().z == 14) {
	        	blockpos = context.chunkPos().getBlockAt(15, 1, 0);
	        }
	        if (context.chunkPos().x == 14 && context.chunkPos().z == -15) {
	        	blockpos = context.chunkPos().getBlockAt(0, 1, 0);	        	
	        }
	        if (context.chunkPos().x == -15 && context.chunkPos().z == -15) {
	        	blockpos = context.chunkPos().getBlockAt(15, 1, 0);	        	
	        }
	        
	        // Adjust more structures and paths here.
	        // For X-Roads
	        if (
	         		(context.chunkPos().x == 15 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == 16 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == 17 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == 15 && context.chunkPos().z == 18)  || 	// X-Road
	         		(context.chunkPos().x == 17 && context.chunkPos().z == 18)  || 	// X-Road Below -15
	         		
	         		(context.chunkPos().x == -16 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == -17 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == -18 && context.chunkPos().z == 14)  ||		// X-Road
	         		(context.chunkPos().x == -16 && context.chunkPos().z == 18)  || 		// X-Road
	         		(context.chunkPos().x == -18 && context.chunkPos().z == 18)  ||	// X-Road Below -15     	
	         		
	         		(context.chunkPos().x == 15 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == 16 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == 17 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == 15 && context.chunkPos().z == -19)  || 		// X-Road
	         		(context.chunkPos().x == 17 && context.chunkPos().z == -19)  || 		// X-Road Below -15
	         		
	         		(context.chunkPos().x == -16 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == -17 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == -18 && context.chunkPos().z == -15)  ||		// X-Road
	         		(context.chunkPos().x == -16 && context.chunkPos().z == -19)  ||		// X-Road
	         		(context.chunkPos().x == -18 && context.chunkPos().z == -19)   		// X-Road Below -15    		 
	         		
	        		) {
	        	
					        	newConfig = new JigsawConfiguration(
						                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
						                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
						                1
						        );
					        	
					        	// Seed Fixes
					        	if (
					        			(context.chunkPos().x == 17 && context.chunkPos().z == 18)  || 		// X-Road Below -15
					        			(context.chunkPos().x == -18 && context.chunkPos().z == 18)  || 		// X-Road Below -15     
					        			(context.chunkPos().x == 17 && context.chunkPos().z == -19)  || 		// X-Road Below -15
					        			(context.chunkPos().x == -18 && context.chunkPos().z == -19)   		// X-Road Below -15    
					        			) {
					        		blockpos = context.chunkPos().getBlockAt(15,-15,0);
						        	newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
						        	if (context.chunkPos().x == -18 && context.chunkPos().z == 18) {
						        		blockpos = context.chunkPos().getBlockAt(0,-15,0);
							        	newConfig = new JigsawConfiguration(
								                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
								                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
								                1
								        );
						        	}
						        	if (context.chunkPos().x == -18 && context.chunkPos().z == -19)   {
						        		blockpos = context.chunkPos().getBlockAt(0,-15,15);
						        	}
						        	if (context.chunkPos().x == 17 && context.chunkPos().z == -19) {
						        		blockpos = context.chunkPos().getBlockAt(15,-15,15);
						        		newConfig = new JigsawConfiguration(
								                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
								                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
								                1
								        );
						        	}
					        	}
					        	if ((context.chunkPos().x == 17 && context.chunkPos().z == 14)	) 	// Quadrant I Seed Fix
					        	{
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
						        	newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	
					        	if ((context.chunkPos().x == -16 && context.chunkPos().z == -15)	) 	// Quadrant IV Seed Fix
					        	{
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
						        	newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == -16 && context.chunkPos().z == -19)  {
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == -16 && context.chunkPos().z == 18) {
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == -17 && context.chunkPos().z == 14) {
					        		blockpos = context.chunkPos().getBlockAt(15,1,15);
					        	}
					        	if (context.chunkPos().x == -18 && context.chunkPos().z == 14) {
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == 16 && context.chunkPos().z == -15) {
					        		blockpos = context.chunkPos().getBlockAt(0, 1, 0);	     
					        	}
					        	if (context.chunkPos().x == -16 && context.chunkPos().z == 14) {
					        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);	  
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == 17 && context.chunkPos().z == -15) {
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == 15 && context.chunkPos().z == -19) {
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
							                1
							        );
					        	}

	        }
	        // For Z-Roads
	        if (
	        		(context.chunkPos().x == 18 && context.chunkPos().z == 15)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == 15)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == 16)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == 17)  ||		// Z-Road
	         		(context.chunkPos().x == 18 && context.chunkPos().z == 17)  ||		// Z-Road Below -15
	         		
	         		(context.chunkPos().x == -19 && context.chunkPos().z == 15)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == 15)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == 16)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == 17)  ||		// Z-Road
	         		(context.chunkPos().x == -19 && context.chunkPos().z == 17)  ||		// Z-Road Below -15
	         		
	         		(context.chunkPos().x == 18 && context.chunkPos().z == -16)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == -16)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == -17)  ||		// Z-Road
	         		(context.chunkPos().x == 14 && context.chunkPos().z == -18)  ||		// Z-Road
	         		(context.chunkPos().x == 18 && context.chunkPos().z == -18)  ||		// Z-Road Below -15
	         		
	         		(context.chunkPos().x == -19 && context.chunkPos().z == -16)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == -16)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == -17)  ||		// Z-Road
	         		(context.chunkPos().x == -15 && context.chunkPos().z == -18)  ||		// Z-Road
	         		(context.chunkPos().x == -19 && context.chunkPos().z == -18)  		// Z-Road Below -15
	         		
	        		) {
	        	
					        	newConfig = new JigsawConfiguration(
						                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
						                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
						                1
						        );
					        	
					        	if (
					        			(context.chunkPos().x == 18 && context.chunkPos().z == 17)  ||		// Z-Road Below -15
					        			(context.chunkPos().x == -19 && context.chunkPos().z == 17)  ||	// Z-Road Below -15
					        			(context.chunkPos().x == 18 && context.chunkPos().z == -18)  ||	// Z-Road Below -15
					        			(context.chunkPos().x == -19 && context.chunkPos().z == -18) 		// Z-Road Below -15
					        			) {
					        		blockpos = context.chunkPos().getBlockAt(15,-15,15);
					        		if (context.chunkPos().x == -19 && context.chunkPos().z == -18)  {
					        			blockpos = context.chunkPos().getBlockAt(0,-15,15);
						        		newConfig = new JigsawConfiguration(
								                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
								                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
								                1
								        );
					        		}
					        		if (context.chunkPos().x == -19 && context.chunkPos().z == 17) {
					        			blockpos = context.chunkPos().getBlockAt(0,-15,0);
					        		}
					        		if (context.chunkPos().x == 18 && context.chunkPos().z == -18) {
					        			blockpos = context.chunkPos().getBlockAt(0,-15,0);
					        		}
					        	}
					        	
					        	// Seed Fixes
					        	if ( context.chunkPos().x == 14 && context.chunkPos().z == 15 ) {	// Quadrant I
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	if ( context.chunkPos().x == 14 && context.chunkPos().z == 16 ) {	// Quadrant I
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	if ( context.chunkPos().x == 14 && context.chunkPos().z == 17 ) {	// Quadrant I
					        		blockpos = context.chunkPos().getBlockAt(0, 1, 0);
					        	}
					        	if (context.chunkPos().x == -15 && context.chunkPos().z == -16) {
					        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	if 	(context.chunkPos().x == -15 && context.chunkPos().z == -17) {
					        		blockpos = context.chunkPos().getBlockAt(15,1,0);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == -19 && context.chunkPos().z == -16) {
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	
					        	if (context.chunkPos().x == -19 && context.chunkPos().z == 15) {
					        		blockpos = context.chunkPos().getBlockAt(0,1,0);
					        	}
					        	if (context.chunkPos().x == 14 && context.chunkPos().z == -17)  {
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}
					        	if (context.chunkPos().x == 14 && context.chunkPos().z == -18)  {
					        		blockpos = context.chunkPos().getBlockAt(15,1,15);
					        	}
					        	if (context.chunkPos().x == 18 && context.chunkPos().z == -16) {
					        		blockpos = context.chunkPos().getBlockAt(0,1,15);
					        		newConfig = new JigsawConfiguration(
							                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
							                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
							                1
							        );
					        	}

	        }
	        // For Corner Roads	       	
	        // Quadrant I
        	if ((context.chunkPos().x == 18 && context.chunkPos().z == 14)) {
        		blockpos = context.chunkPos().getBlockAt(0, 1, 0);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
		                1
		        );
        	}      	
        	if ((context.chunkPos().x == 14 && context.chunkPos().z == 18)) {
        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_xtonegz_corner")),	
		                1
		        );
        	}	        
        	
        	// Quadrant II
        	if (context.chunkPos().x == -15 && context.chunkPos().z == 18) {
        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
		                1
		        );
        	}
        	if (context.chunkPos().x == -19 && context.chunkPos().z == 14) {
        		blockpos = context.chunkPos().getBlockAt(0, 1, 15);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
		                1
		        );
        	}
        	
        	// Quadrant III
        	if (context.chunkPos().x == 14 && context.chunkPos().z == -19)  {
        		blockpos = context.chunkPos().getBlockAt(15,1,0);
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
		                1
		        );
        	}
        	if (context.chunkPos().x == 18 && context.chunkPos().z == -15) {
        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
		                1
		        );
        	}
        	
        	// Quadrant IV
        	if (context.chunkPos().x == -19 && context.chunkPos().z == -15) {
        		blockpos = context.chunkPos().getBlockAt(15, 1, 0);	
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_xtonegz_corner")),	
		                1
		        );
        	}
        	if (context.chunkPos().x == -15 && context.chunkPos().z == -19) {
        		newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
		                1
		        );
        	}
	        
	        // Stairs
	        
	        if (
	        		(context.chunkPos().x == 18 && context.chunkPos().z == 16) ||
	        		(context.chunkPos().x == 16 && context.chunkPos().z == 18) ||
	        		
	        		(context.chunkPos().x == -19 && context.chunkPos().z == 16) ||
	        		(context.chunkPos().x == -17 && context.chunkPos().z == 18) ||
	        		
	        		(context.chunkPos().x == 18 && context.chunkPos().z == -17) ||
	        		(context.chunkPos().x == 16 && context.chunkPos().z == -19) ||
	        		
	        		(context.chunkPos().x == -19 && context.chunkPos().z == -17) ||
	        		(context.chunkPos().x == -17 && context.chunkPos().z == -19) 
	        		) {
	        	
	        	// Quadrant I
	        	if ( context.chunkPos().x == 18 && context.chunkPos().z == 16 ) {
	        		blockpos = context.chunkPos().getBlockAt(0,-15,0);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negz_stairs")),	
			                1
			        );
	        	}
	        	if ( context.chunkPos().x == 16 && context.chunkPos().z == 18 ) {
	        		blockpos = context.chunkPos().getBlockAt(0,-15,0);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negx_stairs")),	
			                1
			        );
	        	}
	        	
	        	// Quadrant II
	        	if ( context.chunkPos().x == -19 && context.chunkPos().z == 16 ) {
	        		blockpos = context.chunkPos().getBlockAt(0,-15,0);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negz_stairs")),	
			                1
			        );
	        	}
	        	if ( context.chunkPos().x == -17 && context.chunkPos().z == 18 ) {
	        		blockpos = context.chunkPos().getBlockAt(15,-15,15);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negx_stairs")),	
			                1
			        );
	        	}
	        	
	        	// Quadrant III
	        	if ( context.chunkPos().x == 18 && context.chunkPos().z == -17 ) {
	        		blockpos = context.chunkPos().getBlockAt(15,-15,0);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_x_stairs")),	
			                1
			        );
	        	}
	        	if ( context.chunkPos().x == 16 && context.chunkPos().z == -19 ) {
	        		blockpos = context.chunkPos().getBlockAt(15,-15,15);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_x_stairs")),	
			                1
			        );
	        	}
	        	
	        	// Quadrant IV
	        	if ( context.chunkPos().x == -19 && context.chunkPos().z == -17 ) {
	        		blockpos = context.chunkPos().getBlockAt(0,-15,15);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negx_stairs")),	
			                1
			        );
	        	}
	        	if ( context.chunkPos().x == -17 && context.chunkPos().z == -19 ) {
	        		blockpos = context.chunkPos().getBlockAt(15,-15,0);
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/ascent/kyrosian_negz_stairs")),	
			                1
			        );
	        	}
	        	
	        	
	        	
	        	
	        }
	
	        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        
        	if ((context.chunkPos().x == -19 && context.chunkPos().z == -15) ||
        			(context.chunkPos().x == 18 && context.chunkPos().z == 16) ||
        			(context.chunkPos().x == -19 && context.chunkPos().z == 16) ||
        			(context.chunkPos().x == -17 && context.chunkPos().z == -19)
        			) {
        		newContext = new PieceGeneratorSupplier.Context<>(
    	                context.chunkGenerator(),
    	                context.biomeSource(),
    	                2,
    	                context.chunkPos(),
    	                newConfig,
    	                context.heightAccessor(),
    	                context.validBiome(),
    	                context.structureManager(),
    	                context.registryAccess()
    	        );	   
        	}
	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
	        return structurePiecesGenerator;
	    }
    	
    	if (
    			( context.chunkPos().x <= 6 && context.chunkPos().z <= 6 ) &&
    			( context.chunkPos().x >= -6 && context.chunkPos().z >= -6 )
    			) {
    				
    		// Initialize to the 4-way and adjust the seeds with blockpos.
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,1,0);	      
	        
    		if (
    				( context.chunkPos().x == 1 && context.chunkPos().z == 0 ) ||
        			( context.chunkPos().x == 1 && context.chunkPos().z == -1 ) ||
    				( context.chunkPos().x == 0 && context.chunkPos().z == -2 ) ||
        			( context.chunkPos().x == -1 && context.chunkPos().z == -2 ) ||
    				( context.chunkPos().x == 0 && context.chunkPos().z == 1 ) ||
        			( context.chunkPos().x == -1 && context.chunkPos().z == 1 ) ||
        			( context.chunkPos().x == -2 && context.chunkPos().z == 0 ) ||
        			( context.chunkPos().x == -2 && context.chunkPos().z == -1 )
    				) {
    			
    			if ( context.chunkPos().x == 1 && context.chunkPos().z == 0 ) {
   
    			}
    			
    			if ( context.chunkPos().x == 1 && context.chunkPos().z == -1 ) {
    		        blockpos = context.chunkPos().getBlockAt(15, 1, 15);	      
    			}
    			
    			if ( context.chunkPos().x == 0 && context.chunkPos().z == -2 ) {
    		        blockpos = context.chunkPos().getBlockAt(0, 1, 15);	      
    			}
    			
    			if ( context.chunkPos().x == -1 && context.chunkPos().z == -2 ) {
    		        blockpos = context.chunkPos().getBlockAt(15, 1, 0);	      
    			}
    			
    			if ( context.chunkPos().x == 0 && context.chunkPos().z == 1 ) {
    		        blockpos = context.chunkPos().getBlockAt(15, 1, 15);	      
    			}
	
    			if ( context.chunkPos().x == -1 && context.chunkPos().z == 1 ) {
    		        blockpos = context.chunkPos().getBlockAt(15, 1, 15);	      
    			}
    			
    			if ( context.chunkPos().x == -2 && context.chunkPos().z == 0 ) {
    				
    			}
    			
    			if ( context.chunkPos().x == -2 && context.chunkPos().z == -1 ) {
    		        blockpos = context.chunkPos().getBlockAt(0, 1, 15);	      
    			}
    			
    	        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
    	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
    	                context.chunkGenerator(),
    	                context.biomeSource(),
    	                1,
    	                context.chunkPos(),
    	                newConfig,
    	                context.heightAccessor(),
    	                context.validBiome(),
    	                context.structureManager(),
    	                context.registryAccess()
    	        );	        
            	if ((context.chunkPos().x == -19 && context.chunkPos().z == -15) ||
            			(context.chunkPos().x == 18 && context.chunkPos().z == 16) ||
            			(context.chunkPos().x == -19 && context.chunkPos().z == 16) ||
            			(context.chunkPos().x == -17 && context.chunkPos().z == -19)
            			) {
            		newContext = new PieceGeneratorSupplier.Context<>(
        	                context.chunkGenerator(),
        	                context.biomeSource(),
        	                0,
        	                context.chunkPos(),
        	                newConfig,
        	                context.heightAccessor(),
        	                context.validBiome(),
        	                context.structureManager(),
        	                context.registryAccess()
        	        );	   
            	}
    	
    	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
    	                JigsawPlacement.addPieces(
    	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
    	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
    	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
    	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
    	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
    	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
    	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
    	                );
    	        
    	        
    	        
    	        
    	        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
    	        //return structurePiecesGenerator;
    			return structurePiecesGenerator;

    		}
    		
    	}
    	
    	if (context.chunkPos().x == -1 && context.chunkPos().z == -1 ) {
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_portal_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(31,-15,0);	      
	        
	        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        

    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
    	}
    	
    	//Memory Nexus Inner Corners
    	if ( (context.chunkPos().x == 1 && context.chunkPos().z == 1) ||
    			(context.chunkPos().x == 1 && context.chunkPos().z == -2) ||
    			(context.chunkPos().x == -2 && context.chunkPos().z == 1) ||
    			(context.chunkPos().x == -2 && context.chunkPos().z == -2) 
    			) {
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,1,0);	      
	        
	        if (context.chunkPos().x == 1 && context.chunkPos().z == 1) {
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
		                1
		        );
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	      
	        }
	        
	        if (context.chunkPos().x == 1 && context.chunkPos().z == -2) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	      
	        }
	        
	        if (context.chunkPos().x == -2 && context.chunkPos().z == -2) {
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negxtoz_corner")),	
		                1
		        );	        	 
	        }
	        
	        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        

    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
    	}
    	
    	if (	(context.chunkPos().x == 7 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 10) ||
    			
    			(context.chunkPos().x == -8 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 10) ||
    			
    			(context.chunkPos().x == 7 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -11) ||
    			
    			(context.chunkPos().x == -8 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -11) 
    			) {
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,1,0);	      
	        
	        if (context.chunkPos().x == 7 && context.chunkPos().z == 7) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	      
	        }
	        if (context.chunkPos().x == 7 && context.chunkPos().z == 10) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	
	        }
	        if (context.chunkPos().x == 10 && context.chunkPos().z == 10) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	
	        }      
	        
	        if (context.chunkPos().x == -8 && context.chunkPos().z == 7) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	     
	        }
	        if (context.chunkPos().x == -8 && context.chunkPos().z == 10) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	  
	        }
	        if (context.chunkPos().x == -11 && context.chunkPos().z == 7) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	  
	        }
	        if (context.chunkPos().x == -11 && context.chunkPos().z == 10) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	
	        }
	        
	        if (context.chunkPos().x == 7 && context.chunkPos().z == -8) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        }
	        if (context.chunkPos().x == 7 && context.chunkPos().z == -11) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	
	        }
	        if (context.chunkPos().x == 10 && context.chunkPos().z == -8) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        }
	        if (context.chunkPos().x == 10 && context.chunkPos().z == -11) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	
	        }
	        
	        if (context.chunkPos().x == -8 && context.chunkPos().z == -8) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	
	        }
	        if (context.chunkPos().x == -8 && context.chunkPos().z == -11) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        }
	        if (context.chunkPos().x == -11 && context.chunkPos().z == -8) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        }
	        if (context.chunkPos().x == -11 && context.chunkPos().z == -11) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        }

	        if (context.chunkPos().x == 10 && context.chunkPos().z == 7) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
	 	                1
	 	        );
	        }
	        if (context.chunkPos().x == 7 && context.chunkPos().z == 10) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
	 	                1
	 	        );
	        }
	        
	        if (context.chunkPos().x == 10 && context.chunkPos().z == -8) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_xtonegz_corner")),	
	 	                1
	 	        );
	        }
	        if (context.chunkPos().x == 7 && context.chunkPos().z == -11) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
	 	                1
	 	        );
	        }
	        
	        if (context.chunkPos().x == -11 && context.chunkPos().z == 7) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_xtonegz_corner")),	
	 	                1
	 	        );
	        }
	        if (context.chunkPos().x == -8 && context.chunkPos().z == 10) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
	 	                1
	 	        );
	        }
	        
	        if (context.chunkPos().x == -11 && context.chunkPos().z == -8) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negxtoz_corner")),	
	 	                1
	 	        );
	        }
	        if (context.chunkPos().x == -8 && context.chunkPos().z == -11) {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
	 	                1
	 	        );
	        }
	        
	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
    	}

    	if (	(context.chunkPos().x == 7 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == -1) ||
    			
    			(context.chunkPos().x == 0 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == -8)
    			) {
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,1,0);	  
	        
	        
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 7)  {
	        	 newConfig = new JigsawConfiguration(
	 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
	 	                1
	 	        );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 7) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		 	                1
		 	        );
	        }
	        
	        if (context.chunkPos().x == 7 && context.chunkPos().z == 0) {
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		 	                1
		 	        );
	        }
	        if (context.chunkPos().x == 7 && context.chunkPos().z == -1) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	 
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		 	                1
		 	        );
	        }
	        
	        if (context.chunkPos().x == -8 && context.chunkPos().z == 0) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	 
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		 	                1
		 	        );
	        }
	        if (context.chunkPos().x == -8 && context.chunkPos().z == -1) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	 
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		 	                1
		 	        );
	        }
	        
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -8) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	  
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		 	                1
		 	        );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -8) {
	        	 newConfig = new JigsawConfiguration(
		 	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		 	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		 	                1
		 	        );
	        	
	        }

	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
    	}
    	
    	if (
    			(context.chunkPos().x == 2 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == 3 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == 4 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == 5 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == 6 && context.chunkPos().z == 0) ||
    			
    			(context.chunkPos().x == 2 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == 3 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == 4 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == 5 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == 6 && context.chunkPos().z == -1) ||
    			
    			(context.chunkPos().x == -3 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == -4 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == -5 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == -6 && context.chunkPos().z == 0) ||
    			(context.chunkPos().x == -7 && context.chunkPos().z == 0) ||
    			
    			(context.chunkPos().x == -3 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == -4 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == -5 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == -6 && context.chunkPos().z == -1) ||
    			(context.chunkPos().x == -7 && context.chunkPos().z == -1) ||
    			
    			(context.chunkPos().x == 0 && context.chunkPos().z == 2) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == 3) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == 4) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == 5) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == 6) ||
    			
    			(context.chunkPos().x == -1 && context.chunkPos().z == 2) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == 3) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == 4) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == 5) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == 6) ||
    			
    			(context.chunkPos().x == 0 && context.chunkPos().z == -3) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == -4) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == -5) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == -6) ||
    			(context.chunkPos().x == 0 && context.chunkPos().z == -7) ||
    			
    			(context.chunkPos().x == -1 && context.chunkPos().z == -3) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == -4) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == -5) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == -6) ||
    			(context.chunkPos().x == -1 && context.chunkPos().z == -7)
    			) {
    		
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,context.chunkPos().x,0);	      
	        
	        if (context.chunkPos().x == 2 && context.chunkPos().z == 0) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	      
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 3 && context.chunkPos().z == 0) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	   
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 4 && context.chunkPos().z == 0) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	   
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 5 && context.chunkPos().z == 0) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 6 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	  
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        
	        if (context.chunkPos().x == 2 && context.chunkPos().z == -1) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 3 && context.chunkPos().z == -1) {
	           blockpos = context.chunkPos().getBlockAt(15,1,0);	 	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 4 && context.chunkPos().z == -1) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);      
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 5 && context.chunkPos().z == -1) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 6 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	 	  
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        
	        
	        
	        if (context.chunkPos().x == -3 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -4 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -5 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -6 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	 	 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -7 && context.chunkPos().z == 0)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        
	        if (context.chunkPos().x == -3 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);    
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -4 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	 	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -5 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -6 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	 	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -7 && context.chunkPos().z == -1)  {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);     
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        
	        
	        
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 2) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 3) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 4) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 5) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == 6) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15); 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 2) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15); 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 3) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0); 
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 4) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 5) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == 6) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        
	        
	        
	        
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -3) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -4) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );	        	
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -5) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -6) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_z")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == 0 && context.chunkPos().z == -7) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -3) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negx")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -4) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_negz")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -5) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -6) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        if (context.chunkPos().x == -1 && context.chunkPos().z == -7) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_3-way_road_facing_x")),	
		                1
		                );
	        }
	        
	        
	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
    		
    		
    	}
    	
    	if (
    			( context.chunkPos().x == 1 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == 2 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == 3 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == 4 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == 5 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == 6 && context.chunkPos().z == 7 ) ||
    			
    			( context.chunkPos().x == -2 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == -3 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == -4 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == -5 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == -6 && context.chunkPos().z == 7 ) ||
    			( context.chunkPos().x == -7 && context.chunkPos().z == 7 ) ||
    			
    			( context.chunkPos().x == 1 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == 2 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == 3 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == 4 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == 5 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == 6 && context.chunkPos().z == -8 ) ||   			
    			
    			( context.chunkPos().x == -2 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == -3 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == -4 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == -5 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == -6 && context.chunkPos().z == -8 ) ||
    			( context.chunkPos().x == -7 && context.chunkPos().z == -8 ) ||
    			
    			( context.chunkPos().x == 7 && context.chunkPos().z == 1 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == 2 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == 3 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == 4 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == 5 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == 6 ) ||
    			
    			( context.chunkPos().x == 7 && context.chunkPos().z == -2 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == -3 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == -4 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == -5 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == -6 ) ||
    			( context.chunkPos().x == 7 && context.chunkPos().z == -7 ) ||
    			
    			( context.chunkPos().x == -8 && context.chunkPos().z == 1 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == 2 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == 3 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == 4 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == 5 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == 6 ) ||   			
    			
    			( context.chunkPos().x == -8 && context.chunkPos().z == -2 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == -3 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == -4 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == -5 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == -6 ) ||
    			( context.chunkPos().x == -8 && context.chunkPos().z == -7 ) 
    			
    			) {
    		
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,4,0);	      
    		
	        if ( (context.chunkPos().x == 1 && context.chunkPos().z == 7) ||
	        		( context.chunkPos().x == 3 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == 5 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 5 ) ||
	        		( context.chunkPos().x == 3 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -2 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -3 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -4 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -6 ) ||
	        		( context.chunkPos().x == -2 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 6 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 5 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 4 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -2 ) ||
	        		( context.chunkPos().x == -5 && context.chunkPos().z == -8 ) 
	        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);
	        }
	        
	        if (
	        		( context.chunkPos().x == 2 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 2 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 3 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 4 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 6 ) ||
	        		( context.chunkPos().x == 1 && context.chunkPos().z == -8 ) ||	        		
	        		( context.chunkPos().x == 5 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -5 ) ||
	        		( context.chunkPos().x == -3 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == -5 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 2 ) ||
	        		( context.chunkPos().x == -3 && context.chunkPos().z == -8 ) ||

	        		( context.chunkPos().x == -7 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -4 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -6 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -5 ) 
	        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);
	        }
	        
	        if (
	        		( context.chunkPos().x == 4 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == 7 && context.chunkPos().z == -7 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 3 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == 1 ) ||
	        		( context.chunkPos().x == -6 && context.chunkPos().z == 7 ) || 
	        		( context.chunkPos().x == -7 && context.chunkPos().z == 7 ) || 
	        		( context.chunkPos().x == -2 && context.chunkPos().z == -8) ||
	        		( context.chunkPos().x == -4 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == -6 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -7 ) 
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);
	        }
	        
	        if (
	        		( context.chunkPos().x == 7 && context.chunkPos().z == 1 ) ||
	        		( context.chunkPos().x == 6 && context.chunkPos().z == 7 ) ||
	        		( context.chunkPos().x == 2 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == 4 && context.chunkPos().z == -8 ) ||
	        		( context.chunkPos().x == -4 && context.chunkPos().z == 7 ) ||	        		
	        		( context.chunkPos().x == 6 && context.chunkPos().z == -8 ) ||	  
	        		( context.chunkPos().x == -8 && context.chunkPos().z == -3 )
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);
	        }
	        
	        if (context.chunkPos().x == 7 || context.chunkPos().x == -8) {
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
		                1
		        );
	        	if (
	        			(context.chunkPos().x == 7 && context.chunkPos().z == 6) ||
	        			(context.chunkPos().x == 7 && context.chunkPos().z == 4) ||
	        			(context.chunkPos().x == 7 && context.chunkPos().z == 3) ||
	        			(context.chunkPos().x == 7 && context.chunkPos().z == 2) ||
	        			(context.chunkPos().x == 7 && context.chunkPos().z == -5) ||
	        			(context.chunkPos().x == 7 && context.chunkPos().z == -7) ||
	        			
	        			(context.chunkPos().x == -8 && context.chunkPos().z == -7) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == -6) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == -5) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == -4) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == 1) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == 2) ||
	        			(context.chunkPos().x == -8 && context.chunkPos().z == 3) 
	        			
	        			) {
	        		newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
			                1
			        );
	        	}
	        }
	        
	        
	        
	        if (context.chunkPos().z == 7 || context.chunkPos().z == -8) {
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID,  "nexus/continuous/kyrosian_x_road")),	
		                1
		        );
	        	if (
	        			(context.chunkPos().x == 4 && context.chunkPos().z == 7) ||
	        			(context.chunkPos().x == 2 && context.chunkPos().z == 7) ||
	        			(context.chunkPos().x == -3 && context.chunkPos().z == 7) ||
	        			(context.chunkPos().x == -5 && context.chunkPos().z == 7) ||
	        			(context.chunkPos().x == -6 && context.chunkPos().z == 7) ||
	        			(context.chunkPos().x == -7 && context.chunkPos().z == 7) ||
	        			

	        			(context.chunkPos().x == 1 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == 5 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == -2 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == -3 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == -4 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == -6 && context.chunkPos().z == -8) ||
	        			(context.chunkPos().x == -7 && context.chunkPos().z == -8) 
	        			
	        			) {
		        	newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID,  "nexus/continuous/kyrosian_z_road")),	
			                1
			        );
	        	}     	
	        }
	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
	        
    	}
    	
    	if (
    			(context.chunkPos().x == 8 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == 9 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == 8 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 9 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == 8) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == 9) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 8) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 9) ||
    			
    			(context.chunkPos().x == -9 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == -10 && context.chunkPos().z == 7) ||
    			(context.chunkPos().x == -9 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -10 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == 8) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == 9) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 8) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 9) ||
    			
    			(context.chunkPos().x == 8 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == 9 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == 8 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 9 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == -9) ||
    			(context.chunkPos().x == 7 && context.chunkPos().z == -10) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -9) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -10) ||
    			
    			(context.chunkPos().x == -9 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == -10 && context.chunkPos().z == -8) ||
    			(context.chunkPos().x == -9 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -10 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == -9) ||
    			(context.chunkPos().x == -8 && context.chunkPos().z == -10) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -9) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -10) 
    			
    			) {
    		
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,4,0);	      
    		if (
    				(context.chunkPos().x == 7 && context.chunkPos().z == 8) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == 8 && context.chunkPos().z == 10) ||
    				(context.chunkPos().x == 10 && context.chunkPos().z == 9) ||
    				
    				(context.chunkPos().x == -9 && context.chunkPos().z == 10) ||
    				(context.chunkPos().x == -11 && context.chunkPos().z == 8) ||
    				
    				(context.chunkPos().x == 7 && context.chunkPos().z == -9) ||
    				
    				(context.chunkPos().x == -8 && context.chunkPos().z == -10) ||
    				(context.chunkPos().x == -9 && context.chunkPos().z == -8) ||
    				(context.chunkPos().x == -11 && context.chunkPos().z == -9) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == -11) 
    				) {
    			blockpos = context.chunkPos().getBlockAt(0,1,0);	 
    		}
    		
    		if (
    				(context.chunkPos().x == 8 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == -8 && context.chunkPos().z == 9) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == 10) ||    								
    				
    				(context.chunkPos().x == 7 && context.chunkPos().z == -10) ||
    				(context.chunkPos().x == 10 && context.chunkPos().z == -10) ||
    				
    				(context.chunkPos().x == 9 && context.chunkPos().z == -11) ||
    				
    				(context.chunkPos().x == -11 && context.chunkPos().z == -10)
    				) {
    			blockpos = context.chunkPos().getBlockAt(15,1,0);	 
    		}
    		
    		if (
    				(context.chunkPos().x == 7 && context.chunkPos().z == 8) ||
    				(context.chunkPos().x == 7 && context.chunkPos().z == 9) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == 10) ||
    				(context.chunkPos().x == 10 && context.chunkPos().z == 8) ||
    				
    				(context.chunkPos().x == -9 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == -11 && context.chunkPos().z == 9) ||
    				
    				(context.chunkPos().x == 8 && context.chunkPos().z == -11) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == -8) ||
    				(context.chunkPos().x == 10 && context.chunkPos().z == -9) ||
    				
    				
    				(context.chunkPos().x == -10 && context.chunkPos().z == -8) 
    				
    				) {
    			blockpos = context.chunkPos().getBlockAt(0,1,15);	 
    		}
    		
    		if (
    				(context.chunkPos().x == -8 && context.chunkPos().z == 8) ||
    				(context.chunkPos().x == 8 && context.chunkPos().z == -8) ||
    				
    				(context.chunkPos().x == -8 && context.chunkPos().z == -9) ||
    				(context.chunkPos().x == -9 && context.chunkPos().z == -11) 
    				) {
    			blockpos = context.chunkPos().getBlockAt(15,1,15);	 
    		}
    		
    		if (
    				(context.chunkPos().x == 8 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == 10) ||
    				(context.chunkPos().x == 10 && context.chunkPos().z == 9) ||
    				
    				(context.chunkPos().x == -8 && context.chunkPos().z == 8) ||
    				(context.chunkPos().x == -11 && context.chunkPos().z == 8) ||
    				(context.chunkPos().x == -9 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == 7) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == 10) ||
    				
    				(context.chunkPos().x == 7 && context.chunkPos().z == -9) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == -8) ||
    				(context.chunkPos().x == 8 && context.chunkPos().z == -11) ||
    				(context.chunkPos().x == 9 && context.chunkPos().z == -11) ||
    				
    				(context.chunkPos().x == -8 && context.chunkPos().z == -9) ||
    				(context.chunkPos().x == -8 && context.chunkPos().z == -10) ||
    				(context.chunkPos().x == -10 && context.chunkPos().z == -8) ||
    				(context.chunkPos().x == -11 && context.chunkPos().z == -9) 

    				) {
    			newConfig = new JigsawConfiguration(
    	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
    	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
    	                1
    	        );
    		}
	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
	        
    	}
    		
    	
    	if (
    			(context.chunkPos().x == 10 && context.chunkPos().z == 11) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 12) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 13) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == 11 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == 12 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == 13 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == 11 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 12 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 13 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == 11) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == 12) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == 13) ||
    			
    			(context.chunkPos().x == -11 && context.chunkPos().z == 11) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 12) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 13) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == -12 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == -13 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == -14 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == 14) ||
    			(context.chunkPos().x == -12 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -13 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -14 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == 10) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == 11) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == 12) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == 13) ||
    			
    			(context.chunkPos().x == 10 && context.chunkPos().z == -12) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -13) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -14) ||
    			(context.chunkPos().x == 10 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == 11 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == 12 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == 13 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == 11 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 12 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 13 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == -12) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == -13) ||
    			(context.chunkPos().x == 14 && context.chunkPos().z == -14) ||
    			
    			(context.chunkPos().x == -11 && context.chunkPos().z == -12) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -13) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -14) ||
    			(context.chunkPos().x == -11 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == -12 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == -13 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == -14 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == -15) ||
    			(context.chunkPos().x == -12 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -13 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -14 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == -11) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == -12) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == -13) ||
    			(context.chunkPos().x == -15 && context.chunkPos().z == -14)
    			
    			) {
    		
	        JigsawConfiguration newConfig = new JigsawConfiguration(
	                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_x_road")),	
	                1
	        );
	        BlockPos blockpos = context.chunkPos().getBlockAt(0,4,0);	      
	        
	        if (
	        		(context.chunkPos().x == 10 && context.chunkPos().z == 11) ||
	        		(context.chunkPos().x == 10 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == 12 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == 12) ||
	        		(context.chunkPos().x == 13 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == 12 && context.chunkPos().z == 14) ||
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == 13) ||	        		
	        		(context.chunkPos().x == -13 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == -14 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 11) ||
	        		
	        		(context.chunkPos().x == 12 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == 13 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == -13) ||
	        		
	        		(context.chunkPos().x == -15 && context.chunkPos().z == -12) 

	        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,0);	      
	        }
	        if (
	        		(context.chunkPos().x == 13 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == 11) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == 13) ||
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == 11) ||
	        		(context.chunkPos().x == -12 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == -13 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == -12 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 12) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 13) ||
	        		
	        		(context.chunkPos().x == 10 && context.chunkPos().z == -14) ||
	        		(context.chunkPos().x == 11 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == 10 && context.chunkPos().z == -15) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == -12) ||
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == -13) ||
	        		(context.chunkPos().x == -12 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == -13 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == -14 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == -11) ||
	        		(context.chunkPos().x == -14 && context.chunkPos().z == -15) 

	        		
	        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(0,1,15);	      
	        }
	        if (
	        		(context.chunkPos().x == 11 && context.chunkPos().z == 10) ||
	        		(context.chunkPos().x == 10 && context.chunkPos().z == 12) ||
	        		(context.chunkPos().x == 10 && context.chunkPos().z == 13) ||
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == 12) ||
	        		(context.chunkPos().x == -11 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == -14 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 10) ||
	        		
	        		(context.chunkPos().x == 11 && context.chunkPos().z == -15) ||
	        		(context.chunkPos().x == 12 && context.chunkPos().z == -15) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == -14) ||
	        		
	        		(context.chunkPos().x == -15 && context.chunkPos().z == -13) ||
	        		(context.chunkPos().x == -13 && context.chunkPos().z == -15) 
	        		
	        		
	        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,0);	      
	        }
	        if (
	        		(context.chunkPos().x == 11 && context.chunkPos().z == 14) ||
	        		
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 13) ||
	        		
	        		(context.chunkPos().x == 10 && context.chunkPos().z == -12) ||
	        		(context.chunkPos().x == 10 && context.chunkPos().z == -13) ||
	        		(context.chunkPos().x == 13 && context.chunkPos().z == -15) ||
	        		
	        		(context.chunkPos().x == -15 && context.chunkPos().z == -14) ||
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == -12) ||
	        		(context.chunkPos().x == -11 && context.chunkPos().z == -14) || 
	        		(context.chunkPos().x == -11 && context.chunkPos().z == -15) || 
	        		(context.chunkPos().x == -12 && context.chunkPos().z == -15)
	        		
	        			        		
	        		) {
	        	blockpos = context.chunkPos().getBlockAt(15,1,15);	      	        		        	
	        }
	        
	        if (
	        		
	        		(context.chunkPos().x == 10 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == 10) || 
	        		
	        		(context.chunkPos().x == 10 && context.chunkPos().z == -15) ||
	        		(context.chunkPos().x == 14 && context.chunkPos().z == -11) || 
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == 14) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == 10) || 
	        		
	        		(context.chunkPos().x == -11 && context.chunkPos().z == -15) ||
	        		(context.chunkPos().x == -15 && context.chunkPos().z == -11) 
	        		
	        		) {
		        	newConfig = new JigsawConfiguration(
			                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
			                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
			                1
			        );
		        	
		        	if (context.chunkPos().x == 14 && context.chunkPos().z == 10) {
			        	newConfig = new JigsawConfiguration(
				                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_xtonegz_corner")),	
				                1
				        );
			        	
			        if (context.chunkPos().x == -11 && context.chunkPos().z == 14)  {
			        	newConfig = new JigsawConfiguration(
				                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
				                1
				        );
			        }
			        
			        if (context.chunkPos().x == 10 && context.chunkPos().z == -15) {
			        	newConfig = new JigsawConfiguration(
				                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
				                1
				        );
			        }
			        
			        if (context.chunkPos().x == 14 && context.chunkPos().z == -11)  {
			        	newConfig = new JigsawConfiguration(
				                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_negztonegx_corner")),	
				                1
				        );
			        }
		        	
			        if (context.chunkPos().x == -15 && context.chunkPos().z == -11)  {
			        	newConfig = new JigsawConfiguration(
				                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
				                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_2-way_ztox_corner")),	
				                1
				        );
			        }
			        
	        	}
	        	
	        }
	        	
        	if(		(context.chunkPos().x == 14 && context.chunkPos().z == 12) ||
        			(context.chunkPos().x == 13 && context.chunkPos().z == 10) ||
        			(context.chunkPos().x == 11 && context.chunkPos().z == 10) ||
        			(context.chunkPos().x == 10 && context.chunkPos().z == 11) ||
        			
        			(context.chunkPos().x == -11 && context.chunkPos().z == 13) ||
        			(context.chunkPos().x == -12 && context.chunkPos().z == 14) ||
        			(context.chunkPos().x == -13 && context.chunkPos().z == 14) ||
        			(context.chunkPos().x == -14 && context.chunkPos().z == 14) ||
        			(context.chunkPos().x == -12 && context.chunkPos().z == 10) ||
        			(context.chunkPos().x == -15 && context.chunkPos().z == 11) ||
        			(context.chunkPos().x == -15 && context.chunkPos().z == 13) ||
        			
        			(context.chunkPos().x == 10 && context.chunkPos().z == -12) ||
        			(context.chunkPos().x == 10 && context.chunkPos().z == -13) ||
        			(context.chunkPos().x == 11 && context.chunkPos().z == -11) ||
        			(context.chunkPos().x == 11 && context.chunkPos().z == -15) ||
        			(context.chunkPos().x == 12 && context.chunkPos().z == -15) ||
        			(context.chunkPos().x == 14 && context.chunkPos().z == -13) ||
        			
        			(context.chunkPos().x == -12 && context.chunkPos().z == -11) ||
        			(context.chunkPos().x == -13 && context.chunkPos().z == -11) ||
        			(context.chunkPos().x == -14 && context.chunkPos().z == -11) ||
        			(context.chunkPos().x == -11 && context.chunkPos().z == -12) ||
        			(context.chunkPos().x == -11 && context.chunkPos().z == -14) ||
        			(context.chunkPos().x == -15 && context.chunkPos().z == -12) ||
        			(context.chunkPos().x == -15 && context.chunkPos().z == -14) ||
        			(context.chunkPos().x == -13 && context.chunkPos().z == -15) ||
        			(context.chunkPos().x == -14 && context.chunkPos().z == -15) 
        			
        			) {
	        	newConfig = new JigsawConfiguration(
		                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
		                        .get(new ResourceLocation(Skyspace.ID, "nexus/continuous/kyrosian_z_road")),	
		                1
		        );
        	}
	        
	        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	        	        
	     
    		newContext = new PieceGeneratorSupplier.Context<>(
	                context.chunkGenerator(),
	                context.biomeSource(),
	                1,
	                context.chunkPos(),
	                newConfig,
	                context.heightAccessor(),
	                context.validBiome(),
	                context.structureManager(),
	                context.registryAccess()
	        );	   
        	
	        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
	                JigsawPlacement.addPieces(
	                        newContext, // Used for JigsawPlacement to get all the proper behaviors done.
	                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
	                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
	                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
	                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
	                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
	                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
	                );
	        
	        return structurePiecesGenerator;
	        
    	}

		return Optional.empty();
    }
}