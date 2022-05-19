package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisteredBiomes {

	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ID);

	public static final ResourceKey<Biome> KYROS_QUADRANT_AXIS = makeQuadAxis("kyros_quadrant_axis"); 
	
	private static ResourceKey<Biome> makeQuadAxis(String name) {
		RegisteredBiomes.BIOMES.register(name, () -> new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.biomeCategory(Biome.BiomeCategory.NONE)
				//.depth(0)
				.downfall(0)
				//.scale(0)
				.temperature(0)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.fogColor(37)
						.waterColor(205)
						.waterFogColor(64)
						.skyColor(137)
						.ambientLoopSound(RegisteredSounds.AMBIENT_KYROS_LOOP.get())
						// We'll get to music implementation later...
						.build())
				.generationSettings(new BiomeGenerationSettings.Builder().build())
				.mobSpawnSettings(kyrosSpawning().build()) //new MobSpawnSettings.Builder().build()
				.temperatureAdjustment(Biome.TemperatureModifier.NONE)
				.build());
		return ResourceKey.create(Registry.BIOME_REGISTRY, Skyspace.prefix(name));
	}
	
	public static MobSpawnSettings.Builder kyrosSpawning() {
		MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();	
		
		spawnInfo.creatureGenerationProbability(0.65f);
		
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RegisteredEntities.SYNTHESIZED_ZOMBIE.get(), 20, 1, 4));
		spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RegisteredEntities.SYNTHESIZED_SKELETON.get(), 20, 1, 4));
		
		return spawnInfo;
	}
	
	public static final BiomeDictionary.Type KYROS = BiomeDictionary.Type.getType("KYROS");
	
	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(KYROS_QUADRANT_AXIS, BiomeDictionary.Type.VOID, BiomeDictionary.Type.END);
	}
    
	
}
