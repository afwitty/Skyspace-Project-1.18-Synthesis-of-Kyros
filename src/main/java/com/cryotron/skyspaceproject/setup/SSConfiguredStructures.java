package com.cryotron.skyspaceproject.setup;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.data.worldgen.PlainVillagePools;


import java.util.Objects;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredStructures;

public class SSConfiguredStructures {
	private SSConfiguredStructures() {}
	
	public static ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> KYROSIAN_MAZE = RegisteredStructures.KYROSIAN_MAZE.get().configured(FeatureConfiguration.NONE);
	//public static ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> KYROSIAN_NEXUS = SSStructures.KYROSIAN_NEXUS.get().configured(FeatureConfiguration.NONE);
	
    public static ConfiguredStructureFeature<?, ?> KYROSIAN_NEXUS = RegisteredStructures.KYROSIAN_NEXUS.get()
            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	
    public static void registerStructureFeatures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        
        registerConfiguredStructure(registry, KYROSIAN_MAZE);
        registerConfiguredStructure(registry, KYROSIAN_NEXUS);
    }
	
    private static void registerConfiguredStructure(Registry<ConfiguredStructureFeature<?, ?>> registry, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
        //Registry.register(registry, Objects.requireNonNull(configuredStructureFeature.feature.getRegistryName()), configuredStructureFeature);
    }
    

}
