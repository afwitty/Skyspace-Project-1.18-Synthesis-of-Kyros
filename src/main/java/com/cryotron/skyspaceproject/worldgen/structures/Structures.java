package com.cryotron.skyspaceproject.worldgen.structures;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.SSConfiguredStructures;
import com.cryotron.skyspaceproject.setup.SSStructures;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Structures {

//		public static ConfiguredStructureFeature<?, ?> CONFIGURED_KYROSIAN_MAZE = SSStructures.KYROSIAN_MAZE.get()
//				.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
		
//		public static void registerConfiguredStructures() {
//			Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Skyspace.ID, "kyrosian_maze"), CONFIGURED_KYROSIAN_MAZE);
//		}
		
//		public static void setupStructures() {
//			setupMapSpacingAndLand(SkyspaceRegistration.KYROSIAN_CHUNK_CUBE.get(), 	new StructureFeatureConfiguration(1, 0, 2147483647), false); //1234567890
//		}
		
//	    private static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
//	            F structure,
//	            StructureFeatureConfiguration structureFeatureConfiguration,
//	            boolean transformSurroundingLand)
//	    {
//	        // Add our own structure into the structure feature map. Otherwise you get errors
//	        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
//
//	        // Adapt the surrounding land to the bottom of our structure
//	//	        if (transformSurroundingLand) {
//	//	            StructureFeature.NOISE_AFFECTING_FEATURES =
//	//	                    ImmutableList.<StructureFeature<?>>builder()
//	//	                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
//	//	                            .add(structure)
//	//	                            .build();
//	//	        }
//
//	        // This is the map that holds the default spacing of all structures. This is normally
//	        // private and final. That's why we need an access transformer.
//	        // Always add your structure to here so that other mods can utilize it if needed
//		        StructureSettings.DEFAULTS =
//		                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
//		                        .putAll(StructureSettings.DEFAULTS)
//		                        .put(structure, structureFeatureConfiguration)
//		                        .build();
//
//
//	        // Add our structure to all the noise generator settings.
//	        // structureConfig requires AccessTransformer
//		        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
//		            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();
//	
//		            // Be careful with mods that make the structure map immutable (like datapacks do)
//		            if (structureMap instanceof ImmutableMap) {
//		                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
//		                tempMap.put(structure, structureFeatureConfiguration);
//		                settings.getValue().structureSettings().structureConfig = tempMap;
//		            } else {
//		                structureMap.put(structure, structureFeatureConfiguration);
//		            }
//		        });
//	    }
	
	    public static void addDimensionalSpacing(final WorldEvent.Load event) {
	        if (event.getWorld() instanceof ServerLevel serverLevel) {
	            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
	            // Skip superflat to prevent issues with it. Plus, users don't want structures clogging up their superflat worlds.
	            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
	                return;
	            }

	            // No portals just yet.
//	            ConfiguredStructureFeature<?, ?> portalFeature = null;
//	            if (serverLevel.dimension().equals(Level.OVERWORLD)) {
//	                portalFeature = CONFIGURED_PORTAL_OVERWORLD;
//	            } else if (serverLevel.dimension().equals(Dimensions.MYSTERIOUS)) {
//	                portalFeature = CONFIGURED_PORTAL_MYSTERIOUS;
//	            }

	            StructureSettings worldStructureConfig = chunkGenerator.getSettings();

	            /*
	             * NOTE: BiomeLoadingEvent from Forge API does not work with structures anymore.
	             * Instead, we will use the below to add our structure to overworld biomes.
	             * Remember, this is temporary until Forge API finds a better solution for adding structures to biomes.
	             */

	            //////////// BIOME BASED STRUCTURE SPAWNING ////////////
	            /*
	             * NOTE: BiomeLoadingEvent from Forge API does not work with structures anymore.
	             * Instead, we will use the below to add our structure to overworld biomes.
	             * Remember, this is temporary until Forge API finds a better solution for adding structures to biomes.
	             */

	            // Create a mutable map we will use for easier adding to biomes
	            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap = new HashMap<>();

	            // Add the resourcekey of all biomes that this Configured Structure can spawn in.
	            for(Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
	                // Skip all ocean, end, nether, and none category biomes.
	                // You can do checks for other traits that the biome has.
	                Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
	                if(biomeCategory != Biome.BiomeCategory.OCEAN && biomeCategory != Biome.BiomeCategory.THEEND && biomeCategory != Biome.BiomeCategory.NETHER) {
	                	if (serverLevel.dimension().equals(Dimensions.KYROS)) {
		                   associateBiomeToConfiguredStructure(STStructureToMultiMap, SSConfiguredStructures.KYROSIAN_MAZE, biomeEntry.getKey());
		                   associateBiomeToConfiguredStructure(STStructureToMultiMap, SSConfiguredStructures.KYROSIAN_NEXUS, biomeEntry.getKey());
	                    }

	                }
	            }

	            // Alternative way to add our structures to a fixed set of biomes by creating a set of biome resource keys.
	            // To create a custom resource key that points to your own biome, do this:
	            // ResourceKey.of(Registry.BIOME_REGISTRY, new ResourceLocation("modid", "custom_biome"))
//	            ImmutableSet<ResourceKey<Biome>> overworldBiomes = ImmutableSet.<ResourceKey<Biome>>builder()
//	                    .add(Biomes.FOREST)
//	                    .add(Biomes.MEADOW)
//	                    .add(Biomes.PLAINS)
//	                    .add(Biomes.SAVANNA)
//	                    .add(Biomes.SNOWY_PLAINS)
//	                    .add(Biomes.SWAMP)
//	                    .add(Biomes.SUNFLOWER_PLAINS)
//	                    .add(Biomes.TAIGA)
//	                    .build();
//	            overworldBiomes.forEach(biomeKey -> associateBiomeToConfiguredStructure(STStructureToMultiMap, STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE, biomeKey));

	            // Grab the map that holds what ConfigureStructures a structure has and what biomes it can spawn in.
	            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
	            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
	            worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !STStructureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

	            // Add our structures to the structure map/multimap and set the world to use this combined map/multimap.
	            STStructureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

	            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
	            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();

	        }

	    }
	    
	    /**
	     * Helper method that handles setting up the map to multimap relationship to help prevent issues.
	     */
	    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultimap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
	        structureToMultimap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
	        var configuredStructureToBiomeMultiMap = structureToMultimap.get(configuredStructureFeature.feature);
	        if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
	            Skyspace.LOGGER.error("""
	                    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
	                    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
	                    The two conflicting ConfiguredStructures are: {}, {}
	                    The biome that is attempting to be shared: {}
	                """,
	                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
	                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
	                    biomeRegistryKey
	            );
	        } else {
	            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
	        }
	    }
	    
	    /**
	     * Create a copy of a piece generator context with another config. This is used by the structures
	     */
	    @NotNull
	    static PieceGeneratorSupplier.Context<JigsawConfiguration> createContextWithConfig(PieceGeneratorSupplier.Context<JigsawConfiguration> context, JigsawConfiguration newConfig) {
	        return new PieceGeneratorSupplier.Context<>(
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
	    }

}
