package com.cryotron.skyspaceproject.setup;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.mixin.structures.*;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianMaze;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianNexus;
import com.cryotron.skyspaceproject.worldgen.structures.codeconfigs.GenericJigsawStructureCodeConfig;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SSStructures {
	
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Skyspace.ID);
    public static final Map<StructureFeature<?>, StructureFeatureConfiguration> SS_STRUCTURES = new HashMap<>();
    public static final Set<ResourceLocation> SS_STRUCTURE_START_PIECES = new HashSet<>();
    
    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> KYROSIAN_MAZE = addToStructureMaps("kyrosian_maze", () -> KyrosianMaze.create(new GenericJigsawStructureCodeConfig.Builder<>(new ResourceLocation(Skyspace.ID, "kyrosian_maze_pool"))
    		.setStructureSize(4)
//    		.setStructureBlacklistRange(1)
    		.setFixedYSpawn(4)
    		.build()));		
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> KYROSIAN_NEXUS = addToStructureMaps("kyrosian_nexus", () -> (new KyrosianNexus(JigsawConfiguration.CODEC)));	
    		
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> KYROSIAN_NEXUS = addToStructureMaps("kyrosian_nexus", () -> KyrosianNexus.create(new GenericJigsawStructureCodeConfig.Builder<>(new ResourceLocation(Skyspace.ID, "kyrosian_nexus_pool"))
//    		.build()));			
    
    private static <T extends StructureFeature<?>> RegistryObject<T> addToStructureMaps(String name, Supplier<T> structure) {
        return STRUCTURES.register(name, structure);
    }
    
    /**
     * This is where we set the rarity of your structures and determine if land conforms to it.
     * See the comments in below for more details.
     */
    public static void setupStructures() {
        setupMapSpacingAndLand(
                SSStructures.KYROSIAN_MAZE.get(), /* The instance of the structure */
                new StructureFeatureConfiguration(1 /* average distance apart in chunks between spawn attempts */,
                        0 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);
        
        setupMapSpacingAndLand(
                SSStructures.KYROSIAN_NEXUS.get(), /* The instance of the structure */
                new StructureFeatureConfiguration(1 /* average distance apart in chunks between spawn attempts */,
                        0 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);


        // Add more structures here and so on
    }
    
    /**
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the StructureFeatureConfiguration argument.
     * This method is called by setupStructures above.
     */
    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureFeatureConfiguration structureFeatureConfiguration,
            boolean transformSurroundingLand)
    {
        /*
         * We need to add our structures into the map in StructureFeature class
         * alongside vanilla structures or else it will cause errors.
         *
         * If the registration is setup properly for the structure,
         * getRegistryName() should never return null.
         */
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();
    }

    
}
