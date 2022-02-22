package com.cryotron.skyspaceproject.worldgen.structures;

import java.util.Optional;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.worldgen.structures.codeconfigs.GenericJigsawStructureCodeConfig;
import com.cryotron.skyspaceproject.worldgen.structures.pieces.PieceLimitedJigsawManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class PiecesGenerator {
    public static Optional<PieceGenerator<JigsawConfiguration>> Generate(Context<JigsawConfiguration> context, String string) {
		// TODO Auto-generated method stub
    	
        /*
         * The only reason we are using JigsawConfiguration here is that further down, we are using
         * JigsawPlacement.addPieces which requires JigsawConfiguration. However, if you create your own
         * JigsawPlacement.addPieces, you could reduce the amount of workarounds like above that you need
         * and give yourself more opportunities and control over your structures.
         *
         * An example of a custom JigsawPlacement.addPieces in action can be found here:
         * https://github.com/TelepathicGrunt/RepurposedStructures/blob/1.18/src/main/java/com/telepathicgrunt/repurposedstructures/world/structures/pieces/PieceLimitedJigsawManager.java
         */
        JigsawConfiguration newConfig = new JigsawConfiguration(
                // The path to the starting Template Pool JSON file to read.
                //
                // Note, this is "structure_tutorial:run_down_house/start_pool" which means
                // the game will automatically look into the following path for the template pool:
                // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                // This is why your pool files must be in "data/<modid>/worldgen/template_pool/<the path to the pool here>"
                // because the game automatically will check in worldgen/template_pool for the pools.
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(Skyspace.ID, string)),

                // How many pieces outward from center can a recursive jigsaw structure spawn.
                // Our structure is only 1 piece outward and isn't recursive so any value of 1 or more doesn't change anything.
                // However, I recommend you keep this a decent value like 7 so people can use datapacks to add additional pieces to your structure easily.
                // But don't make it too large for recursive structures like villages or you'll crash server due to hundreds of pieces attempting to generate!
                1
        );
        
        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                0,
                new ChunkPos(0,0), //context.chunkPos(),
                newConfig,
                context.heightAccessor(),
                context.validBiome(),
                context.structureManager(),
                context.registryAccess()
        );
    	
        BlockPos blockpos = context.chunkPos().getBlockAt(15, 1, 15); // Corner of that chunk, - (14*16) + (y * 16)
        
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
    
    public static Optional<PieceGenerator<NoneFeatureConfiguration>> GenerateII(Context<NoneFeatureConfiguration> context, GenericJigsawStructureCodeConfig config, ResourceLocation structureID, String string) {
    	
        BlockPos blockpos  = context.chunkPos().getBlockAt(15, 0, 15);      
        
        JigsawConfiguration newConfig = new JigsawConfiguration(
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(Skyspace.ID, string)), 4);
    	
        //		      new JigsawConfiguration(() -> context.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(config.startPool), config.structureSize.get()),
        
		Optional<PieceGenerator<NoneFeatureConfiguration>> piece = 
				PieceLimitedJigsawManager.assembleJigsawStructure(
				      context,
				      newConfig,
				      structureID,
				      blockpos,
				      false,
				      false,
				      Integer.MAX_VALUE,
				      Integer.MIN_VALUE,
				      (structurePiecesBuilder, pieces) -> pieces.get(0).move(0, config.centerOffset, 0)
				);
		
		return piece;
    	
    }
}
