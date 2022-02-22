package com.cryotron.skyspaceproject.setup;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Objects;

public class SSConfiguredStructures {
	private SSConfiguredStructures() {}
	
	public static ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> KYROSIAN_MAZE = SSStructures.KYROSIAN_MAZE.get().configured(FeatureConfiguration.NONE);
	
    public static void registerStructureFeatures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        
        registerConfiguredStructure(registry, KYROSIAN_MAZE);
    }
	
    private static void registerConfiguredStructure(Registry<ConfiguredStructureFeature<?, ?>> registry, ConfiguredStructureFeature<?, ?> configuredStructureFeature) {
        //Registry.register(registry, Objects.requireNonNull(configuredStructureFeature.feature.getRegistryName()), configuredStructureFeature);
    }
}
