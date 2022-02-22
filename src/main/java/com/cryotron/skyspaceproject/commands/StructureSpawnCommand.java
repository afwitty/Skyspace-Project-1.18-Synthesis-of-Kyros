package com.cryotron.skyspaceproject.commands;
 
import com.cryotron.skyspaceproject.Skyspace;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.SinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
 
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
 
public class StructureSpawnCommand {
    public static Optional<PieceGenerator<JigsawConfiguration>> generateStructure(BlockPos centerPos, ResourceLocation structureStartPoolRL, int depth, boolean heightmapSnap, boolean legacyBoundingBoxRule, boolean disableProcessors, Long randomSeed, WorldGenRegion cs) {
 
 
        @SuppressWarnings("deprecation")
		ServerLevel level = cs.getLevel(); 
        //BlockPos centerPos = c.getBlockPos(cs.getSource());
        if(heightmapSnap) centerPos = centerPos.below(centerPos.getY()); //not a typo. Needed so heightmap is not offset by player height.
 
        StructureTemplatePool templatePool = level.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(structureStartPoolRL);
 
        if(templatePool == null || templatePool.size() == 0) {
            String errorMsg = structureStartPoolRL + " template pool does not exist or is empty";
            Skyspace.LOGGER.error(errorMsg);
            throw new CommandRuntimeException(new TextComponent(errorMsg));
        }
 
 
        JigsawConfiguration newConfig = new JigsawConfiguration(
                () -> templatePool,
                depth
        );
 
 

        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                level.getChunkSource().getGenerator(),
                level.getChunkSource().getGenerator().getBiomeSource(),
                level.getSeed(),
                new ChunkPos(0,0), //randomSeed == null ? new ChunkPos(centerPos) : new ChunkPos(0, 0),
                newConfig,
                level,
                (b) -> true,
                level.getStructureManager(),
                level.registryAccess()
        );
 

        Optional<PieceGenerator<JigsawConfiguration>> pieceGenerator = JigsawPlacement.addPieces(
                newContext,
                PoolElementStructurePiece::new,
                centerPos,
                legacyBoundingBoxRule,
                heightmapSnap);
 
//        Skyspace.LOGGER.info("CHECKING IF THERE IS A PIECE TO ADD");
//        if(pieceGenerator.isPresent()) {
//            StructurePiecesBuilder structurepiecesbuilder = new StructurePiecesBuilder();
//            pieceGenerator.get().generatePieces(
//                    structurepiecesbuilder,
//                    new PieceGenerator.Context<>(
//                            newContext.config(),
//                            newContext.chunkGenerator(),
//                            newContext.structureManager(),
//                            newContext.chunkPos(),
//                            newContext.heightAccessor(),
//                            new WorldgenRandom(new LegacyRandomSource(0L)),
//                            newContext.seed()));
// 
//            WorldgenRandom worldgenrandom;
//            Skyspace.LOGGER.info("THERE IS A PIECE TO ADD");
//            if(randomSeed == null) {
//                worldgenrandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.seedUniquifier()));
//                long i = worldgenrandom.setDecorationSeed(newContext.seed(), centerPos.getX(), centerPos.getZ());
//                worldgenrandom.setFeatureSeed(i, 0, 0);
//                Skyspace.LOGGER.info("USING XOROSHIRO RANDOM SOURCE");
//            }
//            else {
//                worldgenrandom = new WorldgenRandom(new LegacyRandomSource(randomSeed));
//                Skyspace.LOGGER.info("USING LEGACY RANDOM SOURCE");
//            }
// 
//            Skyspace.LOGGER.info("LISTING THE PIECES");
//            BlockPos finalCenterPos = centerPos;
//            List<StructurePiece> structurePieceList = structurepiecesbuilder.build().pieces();
// 
//            structurePieceList.forEach(piece -> {
//                if(disableProcessors) {
//                    if(piece instanceof PoolElementStructurePiece poolElementStructurePiece) {
//                        if(poolElementStructurePiece.getElement() instanceof SinglePoolElement singlePoolElement) {
//                            Supplier<StructureProcessorList> oldProcessorList = singlePoolElement.processors;
//                            singlePoolElement.processors = () -> ProcessorLists.EMPTY;
//                            Skyspace.LOGGER.info("ENTERING GENERATE PIECE WHILE DISABLE PROCESSOR CONDITION MET");
//                            generatePiece(level, newContext, worldgenrandom, finalCenterPos, piece);
//                            Skyspace.LOGGER.info("EXITING GENERATE PIECE WHILE DISABLE PROCESSOR CONDITION MET");
//                            singlePoolElement.processors = oldProcessorList; // Set the processors back or else our change is permanent.
//                            Skyspace.LOGGER.info("STRUCTURE PIECE LISTED");
//                        }
//                    }
//                }
//                else {
//                    Skyspace.LOGGER.info("ENTERING GENERATE PIECE WHILE DISABLE PROCESSOR CONDITION NOT MET");
//                    generatePiece(level, newContext, worldgenrandom, finalCenterPos, piece);        
//                    Skyspace.LOGGER.info("STRUCTURE PIECE GENERATED");
//                }
//            });
// 
//            if(!structurePieceList.isEmpty()) {
//                //Utilities.refreshChunksOnClients(level);
//                Skyspace.LOGGER.info("STRUCTURE IS EMPTY");
//            }
//            else {
//                String errorMsg = structureStartPoolRL + " Template Pool spawned no pieces.";
//                Skyspace.LOGGER.error(errorMsg);
//                throw new CommandRuntimeException(new TextComponent(errorMsg));
//            }
//        }
//        else {
//            String errorMsg = structureStartPoolRL + " Template Pool spawned no pieces.";
//            Skyspace.LOGGER.error(errorMsg);
//            throw new CommandRuntimeException(new TextComponent(errorMsg));
//        }
 

        return pieceGenerator;
    }
 
    private static void generatePiece(ServerLevel level, PieceGeneratorSupplier.Context<JigsawConfiguration> newContext, WorldgenRandom worldgenrandom, BlockPos finalCenterPos, StructurePiece piece) {
        piece.postProcess(
                level,
                level.structureFeatureManager(),
                newContext.chunkGenerator(),
                worldgenrandom,
                BoundingBox.infinite(),
                newContext.chunkPos(),
                finalCenterPos
        );
    }
}