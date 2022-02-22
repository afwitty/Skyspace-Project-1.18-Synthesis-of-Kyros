package com.cryotron.skyspaceproject.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.mixin.resources.*;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GeneralUtils {
	private GeneralUtils() {}
	
    // More optimized with checking if the jigsaw blocks can connect
    public static boolean canJigsawsAttach(StructureTemplate.StructureBlockInfo jigsaw1, StructureTemplate.StructureBlockInfo jigsaw2) {
        FrontAndTop prop1 = jigsaw1.state.getValue(JigsawBlock.ORIENTATION);
        FrontAndTop prop2 = jigsaw2.state.getValue(JigsawBlock.ORIENTATION);
        String joint = jigsaw1.nbt.getString("joint");
        if(joint.isEmpty()) {
            joint = prop1.front().getAxis().isHorizontal() ? "aligned" : "rollable";
        }

        boolean isRollable = joint.equals("rollable");
        return prop1.front() == prop2.front().getOpposite() &&
                (isRollable || prop1.top() == prop2.top()) &&
                jigsaw1.nbt.getString("target").equals(jigsaw2.nbt.getString("name"));
    }
    
    ///////////////////////////////
    
    public static List<InputStream> getAllFileStreams(ResourceManager resourceManager, ResourceLocation fileID) throws IOException {
        List<InputStream> fileStreams = new ArrayList<>();

        FallbackResourceManager namespaceResourceManager = ((ReloadableResourceManagerImplAccessor) resourceManager).repurposedstructures_getNamespacedPacks().get(fileID.getNamespace());
        List<PackResources> allResourcePacks = ((NamespaceResourceManagerAccessor) namespaceResourceManager).repurposedstructures_getFallbacks();

        // Find the file with the given id and add its filestream to the list
        for (PackResources resourcePack : allResourcePacks) {
            if (resourcePack.hasResource(PackType.SERVER_DATA, fileID)) {
                InputStream inputStream = ((NamespaceResourceManagerAccessor) namespaceResourceManager).repurposedstructures_callGetWrappedResource(fileID, resourcePack);
                if (inputStream != null) fileStreams.add(inputStream);
            }
        }

        // Return filestream of all files matching id path
        return fileStreams;
    }


    public static Map<ResourceLocation, List<JsonElement>> getAllDatapacksJSONElement(ResourceManager resourceManager, Gson gson, String dataType, int fileSuffixLength) {
        Map<ResourceLocation, List<JsonElement>> map = new HashMap<>();
        int dataTypeLength = dataType.length() + 1;

        // Finds all JSON files paths within the pool_additions folder. NOTE: this is just the path rn. Not the actual files yet.
        for (ResourceLocation fileIDWithExtension : resourceManager.listResources(dataType, (fileString) -> fileString.endsWith(".json"))) {
            String identifierPath = fileIDWithExtension.getPath();
            ResourceLocation fileID = new ResourceLocation(
                    fileIDWithExtension.getNamespace(),
                    identifierPath.substring(dataTypeLength, identifierPath.length() - fileSuffixLength));

            try {
                // getAllFileStreams will find files with the given ID. This part is what will loop over all matching files from all datapacks.
                for (InputStream fileStream : GeneralUtils.getAllFileStreams(resourceManager, fileIDWithExtension)) {
                    try (Reader bufferedReader = new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.UTF_8))) {

                        // Get the JSON from the file
                        JsonElement countsJSONElement = GsonHelper.fromJson(gson, bufferedReader, (Class<? extends JsonElement>) JsonElement.class);
                        if (countsJSONElement != null) {

                            // Create list in map for the ID if non exists yet for that ID
                            if (!map.containsKey(fileID)) {
                                map.put(fileID, new ArrayList<>());
                            }
                            // Add the parsed json to the list we will merge later on
                            map.get(fileID).add(countsJSONElement);
                        }
                        else {
                            Skyspace.LOGGER.error(
                                    "Skyspace (Referencing Repurposed Structures {} MERGER) Couldn't load data file {} from {} as it's null or empty",
                                    dataType,
                                    fileID,
                                    fileIDWithExtension);
                        }
                    }
                }
            }
            catch (IllegalArgumentException | IOException | JsonParseException exception) {
            	Skyspace.LOGGER.error(
                        "Skyspace (Referencing Repurposed Structures {} MERGER) Couldn't parse data file {} from {}",
                        dataType,
                        fileID,
                        fileIDWithExtension,
                        exception);
            }
        }

        return map;
    }

    ///////
    
    public static void centerAllPieces(BlockPos targetPos, List<? extends StructurePiece> pieces) {
        if(pieces.isEmpty()) return;

        Vec3i structureCenter = pieces.get(0).getBoundingBox().getCenter();
        int xOffset = targetPos.getX() - structureCenter.getX();
        int zOffset = targetPos.getZ() - structureCenter.getZ();

        for(StructurePiece structurePiece : pieces) {
            structurePiece.move(xOffset, 0, zOffset);
        }
    }
    
    ////////////
    public static int getMaxTerrainLimit(ChunkGenerator chunkGenerator) {
        return chunkGenerator.getMinY() + chunkGenerator.getGenDepth();
    }
    //////////
    
}
