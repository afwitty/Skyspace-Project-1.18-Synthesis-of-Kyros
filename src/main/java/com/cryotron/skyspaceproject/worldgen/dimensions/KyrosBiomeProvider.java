package com.cryotron.skyspaceproject.worldgen.dimensions;

import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.stream.Collectors;


public class KyrosBiomeProvider extends BiomeSource  {
    public static final Codec<KyrosBiomeProvider> CODEC = RegistryLookupCodec.create(Registry.BIOME_REGISTRY)
            .xmap(KyrosBiomeProvider::new, KyrosBiomeProvider::getBiomeRegistry).codec();
    

	private static final List<ResourceKey<Biome>> KYROS_BIOMES = ImmutableList.of( //TODO: Can we do this more efficiently?
			SkyspaceRegistration.KYROS_QUADRANT_AXIS
	);

    private final Biome biome;
    private final Registry<Biome> biomeRegistry;
    //private static final List<ResourceKey<Biome>> SPAWN = Collections.singletonList(Biomes.PLAINS);

    public KyrosBiomeProvider(Registry<Biome> biomeRegistry) {
        super(getStartBiomes(biomeRegistry));
        this.biomeRegistry = biomeRegistry;
              
        biome = biomeRegistry.get(SkyspaceRegistration.KYROS_QUADRANT_AXIS.location());
        //
    }

    private static List<Biome> getStartBiomes(Registry<Biome> registry) {
        return KYROS_BIOMES.stream().map(s -> registry.get(s.location())).collect(Collectors.toList());
    }

    public Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return this;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return biome;
    }
}
