package com.cryotron.skyspaceproject.worldgen.dimensions;

import com.cryotron.skyspaceproject.Skyspace;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dimensions {
    public static final ResourceKey<Level> KYROS = ResourceKey.create(Registry.DIMENSION_REGISTRY, 
    		new ResourceLocation(Skyspace.ID, "kyros"));

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, 
        		new ResourceLocation(Skyspace.ID, "kyrosian_chunkgen"), KyrosChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, 
        		new ResourceLocation(Skyspace.ID, "biomes"), KyrosBiomeProvider.CODEC);
    }
}
