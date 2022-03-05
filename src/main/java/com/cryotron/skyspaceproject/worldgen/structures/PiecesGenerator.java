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
    
    public static Optional<PieceGenerator<NoneFeatureConfiguration>> GenerateII(Context<NoneFeatureConfiguration> context, GenericJigsawStructureCodeConfig config, ResourceLocation structureID, String string) {
    	
        BlockPos blockpos  = context.chunkPos().getBlockAt(15, 0, 15);      
        
//        JigsawConfiguration newConfig = new JigsawConfiguration(
//                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
//                        .get(new ResourceLocation(Skyspace.ID, string)), 16);
//    	
//        		      //new JigsawConfiguration(() -> context.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(config.startPool), config.structureSize.get()),
        
		Optional<PieceGenerator<NoneFeatureConfiguration>> piece = 
				PieceLimitedJigsawManager.assembleJigsawStructure(
				      context,
				      new JigsawConfiguration(() -> context.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(config.startPool), config.structureSize.get()),
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
