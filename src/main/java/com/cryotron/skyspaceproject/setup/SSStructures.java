package com.cryotron.skyspaceproject.setup;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.mixin.structures.*;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianMaze;
import com.cryotron.skyspaceproject.worldgen.structures.codeconfigs.GenericJigsawStructureCodeConfig;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
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
    //public static final RegistryObject<StructureFeature<JigsawConfiguration>> KYROSIAN_MAZE = addToStructureMaps("kyrosian_maze", () -> (new KyrosianMaze(JigsawConfiguration.CODEC)));		
    		
    private static <T extends StructureFeature<?>> RegistryObject<T> addToStructureMaps(String name, Supplier<T> structure) {
        return STRUCTURES.register(name, structure);
    }
}
